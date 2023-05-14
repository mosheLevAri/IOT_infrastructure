package appServer;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import appServer.DL.SQL.SqlController;
import appServer.Utils.Interfaces.ISqlCrud;
import appServer.Utils.JsonHandler.JsonHandler;
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

public class Company extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String dbURL = "jdbc:mysql://localhost:3306/company_db";
	private String dbUser = "root";
	private String dbPass = "Mml8668871!";
	Connection connection = null;
	
	ISqlCrud sql = null;

	public Company() throws SQLException {
		super();

		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		
		sql = new SqlController(dbURL, dbUser, dbPass);
		connection = DriverManager.getConnection(dbURL, dbUser, dbPass);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String product_id = request.getParameter("company_id");
		ResultSet result = null;
		JSONArray json = null;

		try {

			
			if (null != product_id) { result =
			  sql.read("SELECT * FROM Company WHERE company_id = ? ", product_id); } else {
			  result = sql.read("SELECT * FROM Company "); }
			 
			json = JsonHandler.convertResultSetToJson(result);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		response.getWriter().println(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject jsonMessage = JsonHandler.createJsonObject(request);
		createNewCompanyRegisterion(jsonMessage);
		request.setAttribute("jsonData", jsonMessage.toString());
		sendRequestToGateWay(request, response);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject jsonMessage = JsonHandler.createJsonObject(request);
		createNewCompanyRegisterion(jsonMessage);
		request.setAttribute("jsonData", jsonMessage.toString());
		sendRequestToGateWay(request, response);

	}

	private void sendRequestToGateWay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/GateWay");
		dispatcher.forward(request, response);
	}

	private JSONObject createNewCompanyRegisterion(JSONObject json) {

		String address = json.getString("address");
		String name = json.getString("name");
		String company_id = json.getString("company_id");
		String phone = json.getString("phone");

		Object[] params = new Object[] { company_id, name, address, phone };

		try {
			executeUpdate("INSERT INTO Company (company_id, name, address, phone) values (?, ?, ?, ?)", params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * private JSONObject updateNewCompanyRegisterion(JSONObject json) {
	 * 
	 * String address = json.getString("address"); String name =
	 * json.getString("name"); String company_id = json.getString("company_id");
	 * String phone = json.getString("phone");
	 * 
	 * Object[] params = new Object[] { company_id, name, address, phone };
	 * 
	 * try { return sql.create("UPDATE Company SET name=? WHERE company_id=?",
	 * params); } catch (SQLException e) { e.printStackTrace(); } return null; }
	 */

	public int executeUpdate(String query, Object... params) throws SQLException {

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			for (int i = 0; i < params.length; i++) {
				statement.setObject(i + 1, params[i]);
			}
			return statement.executeUpdate();
		}
	}

}
