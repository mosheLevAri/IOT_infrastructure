package appServer.BL;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.bson.types.ObjectId;
import org.json.JSONObject;

import appServer.Utils.SingeltonCommandFactory.SingeltonCommandFactory;
import appServer.Utils.ThreadPool.ThreadPool;
import jakarta.servlet.http.HttpServletRequest;

public class RequestHandler {

	static final SingeltonCommandFactory singeltonCommandFactory = SingeltonCommandFactory.getInstance();
	private ThreadPool<Void> threadPool = null;

	private RequestHandler() {

		threadPool = new ThreadPool<Void>(10);
	}

	@SuppressWarnings("unchecked")
	public JSONObject handleNewRequest(HttpServletRequest request) throws IOException {

		JSONObject jsonObject = null;

		Callable taskCallable = new Callable() {

			@Override
			public Object call() throws Exception {
				JSONObject new_json = getJsonFromRequest(request);
				String command = new_json.getString("command");

				return singeltonCommandFactory.executeCommand(command, new_json);
			}
		};

		Future responseFuture = threadPool.submit(taskCallable);

		try {

			ObjectId objectId = (ObjectId) responseFuture.get();
			jsonObject = new JSONObject().put("Object_id", objectId.toString());

		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	public static RequestHandler getInstance() {
		return SingletonHelper.INSTANCE;
	}

	/*----------------------Private Class And Methods------------------------*/

	private static class SingletonHelper {
		private static final RequestHandler INSTANCE = new RequestHandler();
	}

	private static JSONObject getJsonFromRequest(HttpServletRequest request) {
		String string = (String) request.getAttribute("jsonData");
		return new JSONObject(string);
	}

}
