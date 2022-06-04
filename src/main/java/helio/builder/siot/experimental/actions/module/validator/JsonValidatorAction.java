package helio.builder.siot.experimental.actions.module.validator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import helio.blueprints.Action;
import helio.blueprints.exceptions.ActionException;
import helio.builder.siot.experimental.actions.errors.JsonValidatorException;

/**
 * Action class for Validator Module encharge of validating JSON schemas.
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
		String result = "";
		try {
			result = JsonParser.parseString(values).getAsJsonObject().toString();
		}
		catch (Exception e) {
			throw new JsonValidatorException("Invalid JSON format: " + e.getMessage());
		}
		return result;
	}

}
