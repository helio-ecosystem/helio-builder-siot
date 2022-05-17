package helio.builder.siot.experimental.actions.module.request;

import com.google.gson.JsonObject;

import helio.blueprints.Action;

public class HttpRequestAction implements Action {

	public static final String TAG = "HttpRequest";

	private String method;
	private String url;

	@Override
	public void configure(JsonObject configuration) {
		if (configuration == null) {
			return;
		}
		if (configuration.has("url")) {
			this.url = configuration.get("url").getAsString().strip();
		}
		if (configuration.has("method")) {
			this.method = configuration.get("method").getAsString().strip();
		}
	}

	@Override
	public String run(String values) {
		String result = null;

		if (this.url != null && this.method != null) {
			if (this.method.equalsIgnoreCase("get")) {
				result = HttpRequestBuilder.instance().get(this.url);
			}
			else if (this.method.equalsIgnoreCase("post")) {
				result = HttpRequestBuilder.instance().post(this.url, values);
			}
			else if (this.method.equalsIgnoreCase("put")) {
				result = HttpRequestBuilder.instance().put(this.url, values);
			}
			else if (this.method.equalsIgnoreCase("delete")) {
				result = HttpRequestBuilder.instance().get(this.url);
			}
		}

		return result;
	}

}
