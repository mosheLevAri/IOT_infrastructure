package appServer.Utils.Interfaces;

import java.util.function.Function;

import javax.json.JsonObject;

import org.json.JSONObject;

public interface IFactory {

    public void addCreator(String key,Function<JSONObject, Integer> constructor);
    public Integer executeCommand(String key, JSONObject params);
	
}



