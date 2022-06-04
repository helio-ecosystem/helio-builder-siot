package helio.builder.siot.experimental.actions.module.validator;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.InputSource;

import com.google.gson.JsonObject;

import helio.blueprints.Action;
import helio.blueprints.exceptions.ActionException;
import helio.builder.siot.experimental.actions.errors.XmlValidatorException;

public class XmlValidatorAction implements Action {

	public static final String TAG = "XmlValidator";

	@Override
	public void configure(JsonObject configuration) {

	}

	@Override
	public String run(String values) throws ActionException {
		try {
			DocumentBuilderFactory fctr = DocumentBuilderFactory.newInstance();
			DocumentBuilder bldr = fctr.newDocumentBuilder();
			InputSource insrc = new InputSource(new StringReader(values));
			bldr.parse(insrc);
		}
		catch (Exception e) {
			throw new XmlValidatorException("Invalid XML format: " + e.getMessage());
		}

		return values;
	}

}
