package helio.builder.siot.experimental.actions.module.request;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import helio.blueprints.Action;
import helio.blueprints.exceptions.ActionException;
import helio.builder.siot.experimental.actions.errors.HttpRequestParametersException;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestAction implements Action {

	public static final String TAG = "HttpRequest";

	private String method;
	private String url;
	private Map<String, String> headers;

	@Override
	public void configure(JsonObject configuration) {
		if (configuration == null) {
			return;
		}
		if (configuration.has("method")) {
			this.method = configuration.get("method").getAsString().strip();
		}
		if (configuration.has("url")) {
			this.url = configuration.get("url").getAsString().strip();
		}
		if (configuration.has("headers")) {
			this.headers = new HashMap<String, String>();
			JsonObject jsonHeaders = configuration.get("headers").getAsJsonObject();
			for (Map.Entry<String, JsonElement> header : jsonHeaders.entrySet()) {
				this.headers.put(header.getKey(), header.getValue().getAsString());
			}
		}
	}

	@Override
	public String run(String values) throws ActionException {
		String result = null;

		if (this.url == null) {
			throw new HttpRequestParametersException("Url not found");
		}
		else if (this.method == null) {
			throw new HttpRequestParametersException("Method not found");
		}

		if (this.method.equalsIgnoreCase("get")) {
			result = HttpRequestBuilder.instance().get(this.url, this.headers);
		}
		else if (this.method.equalsIgnoreCase("post")) {
			result = HttpRequestBuilder.instance().post(this.url, values, this.headers);
		}
		else if (this.method.equalsIgnoreCase("put")) {
			result = HttpRequestBuilder.instance().put(this.url, values, this.headers);
		}
		else if (this.method.equalsIgnoreCase("delete")) {
			result = HttpRequestBuilder.instance().delete(this.url, this.headers);
		}
		else {
			throw new HttpRequestParametersException("Method " + this.method + " not exists");
		}

		return result;
	}

}
