package appServer.Utils.SingeltonCommandFactory;
import java.util.function.Function;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

import org.json.JSONObject;

import appServer.Utils.Interfaces.IFactory;

public class SingeltonCommandFactory implements IFactory
{
	
    private final Map<String, Function<JSONObject, Integer>> creators =  new HashMap<>();;


	private SingeltonCommandFactory() {
		
	}
    

    private static class SingletonHelper {
        private static final SingeltonCommandFactory INSTANCE = new SingeltonCommandFactory();
    }

    public static SingeltonCommandFactory getInstance() {
        return SingletonHelper.INSTANCE;
    }

	@Override
	public void addCreator(String key,Function<JSONObject, Integer> constructor) {
		 creators.put(key, constructor);
		
	}

	@Override
	public Integer executeCommand(String key, JSONObject params) {
        Function<JSONObject, Integer> creator = creators.get(key);

        if (creator == null) {
            throw new IllegalArgumentException("No creator for key " + key);
        }
        return creator.apply(params);
	}	
	
}