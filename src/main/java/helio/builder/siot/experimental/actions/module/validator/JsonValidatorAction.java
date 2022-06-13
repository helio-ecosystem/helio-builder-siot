package helio.builder.siot.experimental.actions.module.validator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import helio.blueprints.Action;
import helio.blueprints.exceptions.ActionException;

/**
 * Action class for Validator Module encharge of validating JSON schemas.
 * Return a json as String value with the following structure:
 * - "status": "ok" if it is a valid json or "error" instead.
 * - "message" (Optional). A status description used for "error" status.
 * 
 * @author Emilio
 * 
 */
public class JsonValidatorAction implements Action {

	public static final String TAG = "JsonValidator";

	@Override
	public void configure(JsonObject configuration) {

	}

	@Override
	public String run(String values) throws ActionException {
		JsonObject response = new JsonObject();
		try {
			JsonParser.parseString(values).getAsJsonObject().toString();
			response.addProperty("status", "ok");
		}
		catch (Exception e) {
			response.addProperty("status", "error");
			response.addProperty("message", "Invalid JSON format: " + e.getMessage());
		}
		return response.toString();
	}

}
