package um.ssdd.proyecto_ssdd.grpc.entrenamiento.apps;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoRequest;
import um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoResponse;
import um.ssdd.proyecto_ssdd.grpc.entrenamiento.Entrenamiento;
import um.ssdd.proyecto_ssdd.grpc.entrenamiento.EntrenamientoServiceGrpc;

public class ClienteEntrenamiento extends Thread
{
    private static final Logger logger = Logger.getLogger(ClienteEntrenamiento.class.getName());

    private final ManagedChannel channel;
    private final EntrenamientoServiceGrpc.EntrenamientoServiceStub asyncStub;
    private final String idFichero;
    //private final ProductorEntrenamiento productor;
    
    public ClienteEntrenamiento(String host, int port, String fid)
    {
    	channel = ManagedChannelBuilder.forAddress(host, port)
    								   .usePlaintext()
    								   .build();
    	asyncStub    = EntrenamientoServiceGrpc.newStub(channel);
    	
    	idFichero = fid;
    	
    	//productor = new ProductorEntrenamiento("w2vec");
    }
    
    public void shutdown() throws InterruptedException
    {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
    
    public void Entrenar()
    {
    	logger.info("Solicitando entrenamiento...");
    	Entrenamiento entrenamientoCreado = Entrenamiento.newBuilder()
    													 .setId(idFichero)
    													 .build();
    	
    	CrearEntrenamientoRequest crearEntrenamientoRequest = CrearEntrenamientoRequest.newBuilder()
    																				   .setEntrenamiento(entrenamientoCreado)
    																				   .build();
    	
    	//productor.EnviarMensaje(crearEntrenamientoRequest);
    	
    	try
    	{
    		final CountDownLatch finishLatch = new CountDownLatch(1);
    		
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
    		
    		asyncStub.entrenar(crearEntrenamientoRequest, responseObserver);
    		
            // Esperar la respuesta
            if (finishLatch.await(5, TimeUnit.MINUTES))
                logger.info("Received response.");
            else
                logger.info("Not received response!");
    	} catch (StatusRuntimeException e)
        {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    public void run()
    {
    	try
    	{
    		this.Entrenar();
    	} finally 
    	{
    		try {
				this.shutdown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
}
