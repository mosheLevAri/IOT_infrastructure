package appServer.BL;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.json.JSONObject;

import appServer.DL.SQL.SqlController;
import appServer.Utils.Interfaces.ISqlCrud;
import appServer.Utils.SingeltonCommandFactory.BasicFactoryMethods;
import appServer.Utils.SingeltonCommandFactory.SingeltonCommandFactory;
import appServer.Utils.ThreadPool.ThreadPool;
import jakarta.servlet.http.HttpServletRequest;

public class RequestHandler {

	private String end_user_db = "jdbc:mysql://localhost:3306/end_user_db";
	private String dbUser = "root";
	private String dbPass = "Mml8668871!";

	private ISqlCrud sql = null;
	static final SingeltonCommandFactory singeltonCommandFactory = SingeltonCommandFactory.getInstance();
	private ThreadPool<Void> threadPool = null;

	private RequestHandler() {

		threadPool = new ThreadPool<Void>(10);
		new BasicFactoryMethods(end_user_db, dbUser, dbPass, singeltonCommandFactory);

	}

	public Future handleNewRequest(HttpServletRequest request) throws IOException {

		Callable taskCallable = new Callable() {

			@Override
			public Object call() throws Exception {
				JSONObject new_json = getJsonFromRequest(request);
				String command = new_json.getString("command");

				return singeltonCommandFactory.executeCommand(command, new_json);
			}
		};

		return (Future) threadPool.submit(taskCallable);
	}

	// TODO thread pool
	// TODO dir monitor

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
