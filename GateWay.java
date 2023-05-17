package appServer;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

import org.json.JSONObject;

import appServer.BL.RequestHandler;

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

		JSONObject jsonObject = requestHandler.handleNewRequest(request);
		response.getWriter().println(jsonObject);
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
