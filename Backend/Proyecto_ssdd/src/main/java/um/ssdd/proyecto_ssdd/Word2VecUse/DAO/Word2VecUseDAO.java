package um.ssdd.proyecto_ssdd.Word2VecUse.DAO;

import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class Word2VecUseDAO {

	public Binary findEntrenamiento(String id, String wid) {

		MongoClientURI uri = new MongoClientURI(
				"mongodb+srv://juanan:ssdd@cluster0.ip42g.mongodb.net/DatabaseSSDD?retryWrites=true&w=majority");

		try (MongoClient mongoClient = new MongoClient(uri)) {

			MongoDatabase database = mongoClient.getDatabase("DatabaseSSDD");
			MongoCollection<Document> collection = database.getCollection("entrenamientos");

			Document query = new Document("_id", new ObjectId(wid));
			Document result = collection.find(query).iterator().next();

			Object object = result.get("entrenamiento");

			collection = database.getCollection("usuarios");

			Document statics = collection.find(eq("_id", new ObjectId(id))).iterator().next();

			collection.updateOne(eq("_id", new ObjectId(id)),
					set("palabrasConsultadas", statics.getInteger("palabrasConsultadas") + 1));
			collection.updateOne(eq("_id", new ObjectId(id)),
					set("peticionesWord2VecUse", statics.getInteger("peticionesWord2VecUse") + 1));

			return ((Binary) object);

		}
	}
}
