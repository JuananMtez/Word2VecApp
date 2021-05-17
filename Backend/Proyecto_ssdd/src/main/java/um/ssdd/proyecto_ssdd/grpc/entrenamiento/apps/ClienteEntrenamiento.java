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

public class ClienteEntrenamiento
{
    private static final Logger logger = Logger.getLogger(ClienteEntrenamiento.class.getName());

    private final ManagedChannel channel;
   // private final EntrenamientoServiceGrpc.EntrenamientoServiceBlockingStub blockingStub;
    private final EntrenamientoServiceGrpc.EntrenamientoServiceStub asyncStub;
    
    public ClienteEntrenamiento(String host, int port)
    {
    	channel = ManagedChannelBuilder.forAddress(host, port)
    								   .usePlaintext()
    								   .build();
    	//blockingStub = EntrenamientoServiceGrpc.newBlockingStub(channel);
    	asyncStub    = EntrenamientoServiceGrpc.newStub(channel);
    }
    
    public void shutdown() throws InterruptedException
    {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
    
    public void Entrenar(String idFichero, String idUsuario)
    {
    	logger.info("Solicitando entrenamiento...");
    	Entrenamiento entrenamientoCreado = Entrenamiento.newBuilder()
    													 .setId(idFichero)
    													 .build();
    	
    	CrearEntrenamientoRequest crearEntrenamientoRequest = CrearEntrenamientoRequest.newBuilder()
    																				   .setEntrenamiento(entrenamientoCreado)
    																				   .build();
    	/*try
    	{
    		CrearEntrenamientoResponse crearEntrenamientoResponse = blockingStub.entrenar(crearEntrenamientoRequest);
    		logger.info("TID: " + crearEntrenamientoResponse.toString());
    	} catch (StatusRuntimeException e)
    	{
    		logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
    		return;
    	}*/
    	
    	try
    	{
    		final CountDownLatch finishLatch = new CountDownLatch(1);
    		
    		StreamObserver<CrearEntrenamientoResponse> responseObserver = new StreamObserver<CrearEntrenamientoResponse>() {

                @Override
                public void onError(Throwable t)
                {
                    finishLatch.countDown();
                }

                @Override
                public void onCompleted()
                {
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
    
    public static void main(String[] args) throws Exception
    {
    	if ( args.length == 3 )
    	{
	    	ClienteEntrenamiento cliente = new ClienteEntrenamiento(args[0], 50051);
	    	try
	    	{
	    		cliente.Entrenar(args[1], args[2]);
	    	} finally 
	    	{
	    		cliente.shutdown();
	    	}
    	}
    }
}
