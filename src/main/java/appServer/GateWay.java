package appServer;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.json.JSONObject;

import appServer.BL.RequestHandler;
import appServer.Utils.SingeltonCommandFactory.SingeltonCommandFactory;
import appServer.Utils.ThreadPool.ThreadPool;


public class GateWay extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final RequestHandler requestHandler = RequestHandler.getInstance();


	public GateWay() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Future responseFuture =  requestHandler.handleNewRequest(request);
		
		try {
			response.getWriter().println(responseFuture.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		

	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		requestHandler.handleNewRequest(request);
	}
	

	private JSONObject createJsonObject(HttpServletRequest request) throws IOException {

		String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		return new JSONObject(requestBody);

	}



}
