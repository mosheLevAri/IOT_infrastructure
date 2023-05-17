package appServer.Utils.SingeltonCommandFactory;
import java.util.function.Function;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

import org.bson.types.ObjectId;
import org.json.JSONObject;

import appServer.Utils.Interfaces.IFactory;

public class SingeltonCommandFactory implements IFactory
{
	
    private final Map<String, Function<JSONObject, ObjectId>> creators =  new HashMap<>();;
    BasicFactoryMethods basicFactoryMethods = null;

	private SingeltonCommandFactory() {
		basicFactoryMethods = new BasicFactoryMethods(this);
	}
    

    private static class SingletonHelper {
        private static final SingeltonCommandFactory INSTANCE = new SingeltonCommandFactory();
    }

    public static SingeltonCommandFactory getInstance() {
        return SingletonHelper.INSTANCE;
    }

	@Override
	public void addCreator(String key,Function<JSONObject, ObjectId> constructor) {
		 creators.put(key, constructor);
		
	}

	@Override
	public ObjectId executeCommand(String key, JSONObject params) {
        Function<JSONObject, ObjectId> creator = creators.get(key);

        if (creator == null) {
            throw new IllegalArgumentException("No creator for key " + key);
        }
        return creator.apply(params);
	}	
	
}