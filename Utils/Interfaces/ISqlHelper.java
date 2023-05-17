package appServer.Utils.Interfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ISqlHelper {

	public Connection getConnection();

	public void close() throws SQLException;

	public int executeUpdate(String query, Object... params) throws SQLException;

	public ResultSet executeQuery(String query, Object... params) throws SQLException;

}
