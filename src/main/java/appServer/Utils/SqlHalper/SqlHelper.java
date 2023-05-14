package appServer.Utils.SqlHalper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import appServer.Utils.Interfaces.ISqlHelper;

public class SqlHelper implements ISqlHelper {
	
	
	private static Connection connection = null;

	public SqlHelper(String dataBaseUrl, String userName, String password) throws SQLException {
		
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		connection = DriverManager.getConnection(dataBaseUrl, userName, password);
	}

	public Connection getConnection() {
		return connection;
	}

	public void close() throws SQLException {
		connection.close();
	}

	public int executeUpdate(String query, Object... params) throws SQLException {

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			for (int i = 0; i < params.length; i++) {
				statement.setObject(i + 1, params[i]);
			}
			return statement.executeUpdate();
		}
	}

	public ResultSet executeQuery(String query, Object... params) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(query);
		for (int i = 0; i < params.length; i++) {
			statement.setObject(i + 1, params[i]);
		}
		return statement.executeQuery();
	}
	
	
}
