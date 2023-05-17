package appServer.Utils.Interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bson.types.ObjectId;
import org.json.JSONObject;

import com.mongodb.client.MongoDatabase;

public interface IMongoCrud {

	public ObjectId create(JSONObject json, String collectionName);

	public ObjectId read(JSONObject json, String collectionName);

	public ObjectId update(JSONObject json, String collectionName);

	public ObjectId delete(JSONObject json, String collectionName);

}
