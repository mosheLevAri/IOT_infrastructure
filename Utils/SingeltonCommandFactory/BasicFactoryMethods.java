package appServer.Utils.SingeltonCommandFactory;

import appServer.DL.Mongo.Mongo_DB;
import appServer.DL.SQL.SqlController;
import appServer.Utils.Interfaces.ISqlCrud;
import java.sql.SQLException;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class BasicFactoryMethods {

	private SingeltonCommandFactory singeltonCommandFactory = null;
	Mongo_DB mongo_DB = null;

	public BasicFactoryMethods(SingeltonCommandFactory singeltonCommandFactory) {

		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017/"));

		mongo_DB = new Mongo_DB(mongoClient.getDatabase("end_user_db"));

		this.singeltonCommandFactory = singeltonCommandFactory;
		initBasicMethodsToFactory();
	}

	public ObjectId createNewCompanyRegisterion(JSONObject json) {
		return mongo_DB.create(json, "Companies");

	}

	public ObjectId createNewProdactRegisterion(JSONObject json) {

		return mongo_DB.create(json, "Products");
	}

	public ObjectId createNewIotDeviceRegisterion(JSONObject json) {

		return mongo_DB.create(json, "IOTDevice");

	}

	public ObjectId updateIotDevice(JSONObject json) {

		return mongo_DB.update(json, "IOTDevice");

	}

	public void initBasicMethodsToFactory() {

		singeltonCommandFactory.addCreator("CR", this::createNewCompanyRegisterion);
		singeltonCommandFactory.addCreator("PR", this::createNewProdactRegisterion);
		singeltonCommandFactory.addCreator("IOTR", this::createNewIotDeviceRegisterion);
		singeltonCommandFactory.addCreator("IOTU", this::updateIotDevice);
	}

}
