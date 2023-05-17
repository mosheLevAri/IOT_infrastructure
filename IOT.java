package appServer;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

import org.json.JSONObject;

import appServer.Utils.JsonHandler.JsonHandler;

public class IOT extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public IOT() {
		super();
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		sendRequestToGateWay(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sendRequestToGateWay(request, response);
	}

	private JSONObject createJsonObject(HttpServletRequest request) throws IOException {

		String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		return new JSONObject(requestBody);
	}

	private void sendRequestToGateWay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject jsonMessage = JsonHandler.createJsonObject(request);
		request.setAttribute("jsonData", jsonMessage.toString());

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/GateWay");
		dispatcher.forward(request, response);
	}
}
