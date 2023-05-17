package appServer.Utils.Interfaces;

import java.util.function.Function;

import javax.json.JsonObject;

import org.bson.types.ObjectId;
import org.json.JSONObject;

public interface IFactory {

    public void addCreator(String key,Function<JSONObject, ObjectId> constructor);
    public ObjectId executeCommand(String key, JSONObject params);
	
}



