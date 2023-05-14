package appServer.Utils.SingeltonCommandFactory;

import appServer.DL.SQL.SqlController;
import appServer.Utils.Interfaces.ISqlCrud;
import java.sql.SQLException;

import org.json.JSONObject;

public class BasicFactoryMethods {

	private ISqlCrud sql = null;
	private SingeltonCommandFactory singeltonCommandFactory = null;

	public BasicFactoryMethods(String dataBaseUrl, String userName, String password, SingeltonCommandFactory singeltonCommandFactory) {
		try {
			sql = new SqlController(dataBaseUrl, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.singeltonCommandFactory = singeltonCommandFactory;
		initBasicMethodsToFactory();
	}

	public int createNewCompanyRegisterion(JSONObject json) {

		String address = json.getString("address");
		String name = json.getString("name");
		String company_id = json.getString("company_id");
		String phone = json.getString("phone");

		Object[] params = new Object[] { company_id, name, address, phone };

		try {
			return this.sql.create("INSERT INTO Company (company_id, name, address, phone) values (?, ?, ?, ?)", params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public int createNewProdactRegisterion(JSONObject json) {

		String product_id = json.getString("product_id");
		String name = json.getString("name");
		String company_id = json.getString("company_id");

		Object[] params = new Object[] { product_id, name, company_id };

		try {
			return this.sql.create("INSERT INTO Product (product_id, name, company_id) values (?, ?, ?)", params);
		} catch (SQLException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	public int createNewIotDeviceRegisterion(JSONObject json) {

		String product_id = json.getString("product_id");
		String userName = json.getString("user_name");
		String company_id = json.getString("data");
		String device_data_id = json.getString("device_data_id");

		Object[] params = new Object[] { device_data_id, product_id, userName, company_id };

		try {
			return this.sql.create("INSERT INTO DeviceData (device_data_id, data) values (?, ?, ?, ?)", params);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public int updateIotDevice(JSONObject json) {

		String data = json.getString("data");
		String device_data_id = json.getString("device_data_id");

		Object[] params = new Object[] { data, device_data_id };

		try {
			return this.sql.create("UPDATE DeviceData SET data = ?  WHERE device_data_id = ?", params);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	private void initBasicMethodsToFactory() {

		singeltonCommandFactory.addCreator("CR", this::createNewCompanyRegisterion);
		singeltonCommandFactory.addCreator("PR", this::createNewProdactRegisterion);
		singeltonCommandFactory.addCreator("IOTR", this::createNewIotDeviceRegisterion);
		singeltonCommandFactory.addCreator("IOTU", this::updateIotDevice);
	}

}
