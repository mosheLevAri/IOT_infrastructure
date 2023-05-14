package appServer;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import appServer.DL.SQL.SqlController;
import appServer.Utils.Interfaces.ISqlCrud;
import appServer.Utils.JsonHandler.JsonHandler;


public class Product extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String dbURL = "jdbc:mysql://localhost:3306/company_db";
	private String dbUser = "root";
	private String dbPass = "Mml8668871!";

	private ISqlCrud sql = null;
	
	public Product() {
		super();
		try {
			sql = new SqlController(dbURL, dbUser, dbPass);
		} catch (SQLException e) {
			e.printStackTrace();
		}	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		
		String product_id = request.getParameter("company_id");
		ResultSet result = null;
		JSONArray json = null;

		try {

			if (null != product_id) {
				result = sql.read("SELECT * FROM Product WHERE product_id = ? ", product_id);
			} else {
				result = sql.read("SELECT * FROM Product ", 0);
			}
			json = JsonHandler.convertResultSetToJson(result);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		response.getWriter().println(json);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject jsonMessage = JsonHandler.createJsonObject(request);
		createNewMessageToDB(jsonMessage);
		request.setAttribute("jsonData", jsonMessage.toString());
		sendRequestToGateWay(request, response);
				
	}

	private JSONObject createJsonObject(HttpServletRequest request) throws IOException {

		String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		return new JSONObject(requestBody);
	}

	private void sendRequestToGateWay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/GateWay");
		dispatcher.forward(request, response);
	}
	
	private int createNewMessageToDB(JSONObject json) {

		try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
				PreparedStatement statement = conn
						.prepareStatement("INSERT INTO Product (product_id, name, company_id) values (?, ?, ?)")) {
			statement.setString(1, json.getString("product_id"));
			statement.setString(2, json.getString("name"));
			statement.setString(3, json.getString("company_id"));
			return statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
