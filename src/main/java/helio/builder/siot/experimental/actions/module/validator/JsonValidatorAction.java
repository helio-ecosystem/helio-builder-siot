package helio.builder.siot.experimental.actions.module.validator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import helio.blueprints.Action;

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
	public String run(String values) {
		return JsonParser.parseString(values).getAsJsonObject().toString();
	}

}
