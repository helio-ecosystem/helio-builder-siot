package helio.builder.siot.experimental.actions.module.validator;

import com.google.gson.JsonObject;

import helio.blueprints.Action;
import helio.blueprints.exceptions.ActionException;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

/**
 * Action class for Validator Module encharge of validating XML schemas.
 * Return a json as String value with the following structure:
 * - "status": "ok" if it is a valid json or "error" instead.
 * - "message" (Optional). A status description used for "error" status.
 *
 * @author Emilio
 *
 */
public class XmlValidatorAction implements Action {

	public static final String TAG = "XmlValidator";
	
	@Override
	public void configure(JsonObject configuration) {

	}

	@Override
	public String run(String values) throws ActionException {
		JsonObject response = new JsonObject();
		try {
			DocumentBuilderFactory fctr = DocumentBuilderFactory.newInstance();
			DocumentBuilder bldr = fctr.newDocumentBuilder();
			InputSource insrc = new InputSource(new StringReader(values));
			bldr.parse(insrc);
			response.addProperty("status", "ok");
		}
		catch (Exception e) {
			response.addProperty("status", "error");
			response.addProperty("message", "Invalid XML format: " + e.getMessage());
		}

		return response.toString();
	}

}
