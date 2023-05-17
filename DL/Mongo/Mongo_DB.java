package appServer.DL.Mongo;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import java.util.Arrays;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import appServer.Utils.Interfaces.IMongoCrud;

public class Mongo_DB implements IMongoCrud {

	MongoDatabase database = null;

	public Mongo_DB(MongoDatabase database) {
		this.database = database;
	}

	@Override
	public ObjectId create(JSONObject json, String collectionName) {
		MongoCollection<Document> collection = database.getCollection(collectionName);
		Document doc = Document.parse(json.toString());
		
		
	    String[] Data = {};
	    doc.append("Data", Arrays.asList(Data));

		collection.insertOne(doc);
		return doc.getObjectId("_id");
	}

	@Override
	public ObjectId read(JSONObject json,  String collectionName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectId update(JSONObject json,  String collectionName) {
	    MongoCollection<Document> collection = database.getCollection(collectionName);

	    String deviceDataId = json.getString("device_data_id");

	    collection.updateOne(
	            Filters.eq("device_data_id", deviceDataId),
	            Updates.push("Data", json.getString("data"))
	    );

	    Document filter = new Document("device_data_id", deviceDataId);
	    Document result = collection.find(filter).first();
	    ObjectId objectId = result.getObjectId("_id");

	    return objectId;

	}

	@Override
	public ObjectId delete(JSONObject json,  String collectionName) {
		// TODO Auto-generated method stub
		return null;
	}

}
