package helio.builder.siot.experimental.actions.module.validator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import helio.blueprints.Action;

public class JsonValidatorAction implements Action {

	public static final String TAG = "JsonValidator";

	@Override
	public void configure(JsonObject configuration) {
		
	}

	@Override
	public String run(String values) {
		String validatedData = null;

		try {
			JsonObject json = JsonParser.parseString(values).getAsJsonObject();
			validatedData = json.getAsString();
		} catch (JsonParseException e) {
			e.printStackTrace();
		}

		return validatedData;
	}

}
