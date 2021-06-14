package um.ssdd.proyecto_ssdd.grpc.entrenamiento.apps;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoRequest;
import um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoResponse;
import um.ssdd.proyecto_ssdd.grpc.entrenamiento.Entrenamiento;
import um.ssdd.proyecto_ssdd.grpc.entrenamiento.EntrenamientoServiceGrpc;

public class ServidorEntrenamiento 
{
    private static final Logger logger = Logger.getLogger(ServidorEntrenamiento.class.getName());

    /* The port on which the server should run */
    private int port = 50051;
    
    private Server server;
    
    private void start() throws IOException
    {
        server = ServerBuilder.forPort(port)
                .addService(new EntrenamientoImpl(logger))
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run()
            {
                // Use stderr here since the logger may have been reset by its JVM shutdown
                // hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                ServidorEntrenamiento.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }
    
    private void stop()
    {
        if (server != null)
        {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon
     * threads.
     */
    private void blockUntilShutdown() throws InterruptedException
    {
        if (server != null)
        {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException
    {
        if (args.length < 1)
        {
            System.out.println("Usage: consumer <topic>");
            return;
        }
        
        String topic = args[0].toString();
        
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("group.id", "mygroup");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props))
        {
            consumer.subscribe(Collections.singletonList(topic));
            System.out.println("Subscribed to topic " + topic);

            final ServidorEntrenamiento server = new ServidorEntrenamiento();
            server.start();
            
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
					   									  .usePlaintext()
				   										  .build();
            EntrenamientoServiceGrpc.EntrenamientoServiceStub asyncStub = EntrenamientoServiceGrpc.newStub(channel);
            
            while (true)
            {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                	System.out.printf("offset = %d, key = %s, value = %s\n",
                            record.offset(), record.key(), record.value());
                	System.out.println("Creamos el entrenamiento: " + record.value());
                	Entrenamiento entrenamientoCreado = Entrenamiento.newBuilder()
																	 .setId(record.value())
																	 .build();
                	System.out.println("Creamos la peticion...");
                	CrearEntrenamientoRequest crearEntrenamientoRequest = CrearEntrenamientoRequest.newBuilder()
														   										   .setEntrenamiento(entrenamientoCreado)
														   										   .build();
                	
                	try
                	{
                		final CountDownLatch finishLatch = new CountDownLatch(1);
                		System.out.println("Creamos el observer...");
                		StreamObserver<CrearEntrenamientoResponse> responseObserver = new StreamObserver<CrearEntrenamientoResponse>() {

                            @Override
                            public void onError(Throwable t)
                            {
                            	logger.info(t.toString());
                            	finishLatch.countDown();
                            }

                            @Override
                            public void onCompleted()
                            {
                            	logger.info("Completed");
                                finishLatch.countDown();
                            }

            				@Override
            				public void onNext(CrearEntrenamientoResponse value)
            				{
            					logger.info("WID: " + value.toString());
            				}	
                		};
                		System.out.println("Lanzamos el entrenamiento...");
                		asyncStub.entrenar(crearEntrenamientoRequest, responseObserver);
                	} catch (StatusRuntimeException e)
                    {
                        logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
                        return;
                    } 
                }
                    
            }
        }
    }
}
