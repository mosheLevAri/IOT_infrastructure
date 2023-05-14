package appServer.Utils.JsonHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import jakarta.servlet.http.HttpServletRequest;

public class JsonHandler {

	public static JSONObject createJsonObject(HttpServletRequest request) throws IOException {

		String xString = (String) request.getAttribute("jsonData");
		String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		return new JSONObject(requestBody);

	}
	
	 public static JSONArray convertResultSetToJson(ResultSet rs) throws SQLException {
	        JSONArray jsonArray = new JSONArray();
	        ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
	        int columnCount = metaData.getColumnCount();

	        while (rs.next()) {
	            JSONObject jsonObject = new JSONObject();
	            for (int i = 1; i <= columnCount; i++) {
	                String columnName = metaData.getColumnLabel(i);
	                Object columnValue = rs.getObject(i);
	                jsonObject.put(columnName, columnValue);
	            }
	            jsonArray.put(jsonObject);
	        }

	        return jsonArray;
	    }

}
