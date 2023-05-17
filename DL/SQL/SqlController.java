package appServer.DL.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import appServer.Utils.Interfaces.ISqlCrud;
import appServer.Utils.Interfaces.ISqlHelper;
import appServer.Utils.SqlHelper.SqlHelper;

public class SqlController implements ISqlCrud {

	ISqlHelper connection = null;

	public SqlController(String dataBaseUrl, String userName, String password) throws SQLException {
		connection = new SqlHelper(dataBaseUrl, userName, password);
	}

	@Override
	public int create(String query, Object... params) throws SQLException {
		return connection.executeUpdate(query, params);
	}

	@Override
	public ResultSet read(String query, Object... params) throws SQLException {
		
		return connection.executeQuery(query, params);
	}

	@Override
	public JSONObject update(String query, Object... params) throws SQLException {
		connection.executeUpdate(query, params);
		return null;
	}

	@Override
	public JSONObject delete(String query, Object... params) throws SQLException {
		connection.executeUpdate(query, params);
		return null;
	}

}
