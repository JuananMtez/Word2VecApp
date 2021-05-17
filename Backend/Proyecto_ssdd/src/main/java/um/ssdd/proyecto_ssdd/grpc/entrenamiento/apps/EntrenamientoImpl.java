package um.ssdd.proyecto_ssdd.grpc.entrenamiento.apps;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.BsonBinarySubType;
import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import um.ssdd.proyecto_ssdd.Entities.Entrenamiento;
import um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoRequest;
import um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoResponse;
import um.ssdd.proyecto_ssdd.grpc.entrenamiento.EntrenamientoServiceGrpc;

public class EntrenamientoImpl extends EntrenamientoServiceGrpc.EntrenamientoServiceImplBase 
{
	
	private Logger logger;
	
	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	
	public EntrenamientoImpl(Logger logger)
	{
		super();
		this.logger = logger;
		this.mongoClient = MongoClients.create("mongodb+srv://juanan:ssdd@cluster0.ip42g.mongodb.net/DatabaseSSDD?retryWrites=true&w=majority");
		this.mongoDatabase = mongoClient.getDatabase("DatabaseSSDD");
	}
	
	@Override
	public void entrenar(CrearEntrenamientoRequest request, StreamObserver<CrearEntrenamientoResponse> responseObserver) 
	{
		logger.info("Nueva solicitud de entrenamiento...");
		um.ssdd.proyecto_ssdd.grpc.entrenamiento.Entrenamiento entrenamientoRecibido = request.getEntrenamiento();
		
		logger.info("Buscando el fichero solicitado en la base de datos.");
		MongoCollection<Document> ficherosMongo = mongoDatabase.getCollection("ficheros");
		Document ficheroMongo = ficherosMongo.find(Filters.eq("_id", new ObjectId(request.getEntrenamiento().getId()))).first();
		
		if (ficheroMongo == null) {
			logger.log(Level.WARNING, "Fichero no encontrado.");
			responseObserver.onError(Status.NOT_FOUND.withDescription("No existe el archivo solicitado.").asRuntimeException());
		} else {
			logger.info("Fichero encontrado.");
			Binary contenido = ficheroMongo.get("fichero", org.bson.types.Binary.class);
			logger.info("Contenido recuperado. Iniciando entrenamiento...");
			
			SentenceIterator iter = new BasicLineIterator(new ByteArrayInputStream(contenido.getData()));
	        TokenizerFactory t = new DefaultTokenizerFactory();

	        t.setTokenPreProcessor(new CommonPreprocessor());

	        Word2Vec vec = new Word2Vec.Builder()
	                .minWordFrequency(5)
	                .iterations(1)
	                .layerSize(100)
	                .seed(42)
	                .windowSize(5)
	                .iterate(iter)
	                .tokenizerFactory(t)
	                .build();

	        vec.fit();


	        ByteArrayOutputStream out = new ByteArrayOutputStream(100*1024);
	        
	        try 
	        {
				WordVectorSerializer.writeWord2VecModel(vec, out);
			} catch (IOException e) 
	        {
				logger.log(Level.WARNING, "W2Vec Training failed: {0}", e.getStackTrace());
				responseObserver.onError(Status.INTERNAL.withDescription("No se ha podido entrenar el archivo.").asRuntimeException());
			}
	        
	        Entrenamiento entrenamiento = new Entrenamiento(ficheroMongo.getString("fileName"), new Binary(BsonBinarySubType.BINARY, out.toByteArray()));
	        logger.info("Entrenamiento finalizado con exito.");
			
	        MongoCollection<Document> entrenamientosMongo = mongoDatabase.getCollection("entrenamientos");
			Document mongoDoc = new Document( "nombreFichero", entrenamiento.getNombreFichero() ).append("entrenamiento", entrenamiento.getEntrenamiento());
			logger.info("Guardando resultados del entrenamiento en base de datos...");
			entrenamientosMongo.insertOne(mongoDoc);
			
			String id = mongoDoc.getObjectId("_id").toString();
			ficheroMongo.append("entrenamiento", mongoDoc);
			logger.info("Actualizando fichero con el entrenamiento...");
			ficherosMongo.replaceOne(Filters.eq("_id", new ObjectId(request.getEntrenamiento().getId())), ficheroMongo);
			
			CrearEntrenamientoResponse respuesta = CrearEntrenamientoResponse.newBuilder()
																			 .setEntrenamiento(entrenamientoRecibido.toBuilder()
																					 								.setId(id)
																					 								.build())
																			 .build();
			responseObserver.onNext(respuesta);
			responseObserver.onCompleted();
		}
	}

}
