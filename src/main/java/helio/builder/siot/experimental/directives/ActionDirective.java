package helio.builder.siot.experimental.directives;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.jena.ext.com.google.common.base.Strings;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModelException;

import helio.blueprints.Action;
import helio.builder.siot.experimental.actions.ActionBuilder;
import helio.builder.siot.experimental.actions.errors.ActionNotFoundException;
import helio.builder.siot.experimental.actions.errors.ActionParameterNotFoundException;

public class ActionDirective implements TemplateDirectiveModel {

	private static final String PARAM_NAME_TYPE = "type";
	private static final String PARAM_NAME_CONF = "conf";
	private static final String PARAM_NAME_DATA = "data";

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		// Retrieve data from directive
		ParamsHolder paramsHolder = getParameters(params);

		// Validate directive parameters
		try {
			validateParameters(paramsHolder);
		} catch (ActionParameterNotFoundException e) {
			throw new TemplateModelException(e.getMessage());
		}

		// Execute action
		try {
			Action action = ActionBuilder.instance().build(paramsHolder.type);
			action.configure(paramsHolder.configuration);
			String result = action.run(paramsHolder.data);

			if (loopVars.length > 0) {
				loopVars[0] = new SimpleScalar(result);
			}
		} catch (ActionNotFoundException e) {
			throw new TemplateModelException(e.getMessage());
		}
	}

	private ParamsHolder getParameters(Map params) {
		ParamsHolder paramsHolder = new ParamsHolder();
		Iterator paramIter = params.entrySet().iterator();

		while (paramIter.hasNext()) {
			Map.Entry ent = (Map.Entry) paramIter.next();

			String paramName = (String) ent.getKey();
			String paramValue = ((SimpleScalar) ent.getValue()).getAsString();

			if (paramName.equals(PARAM_NAME_TYPE)) {
				paramsHolder.type = paramValue;
			} else if (paramName.equals(PARAM_NAME_CONF)) {
				paramsHolder.configuration = JsonParser.parseString(paramValue).getAsJsonObject();
			} else if (paramName.equals(PARAM_NAME_DATA)) {
				paramsHolder.data = paramValue;
			}
		}

		return paramsHolder;
	}

	private boolean validateParameters(ParamsHolder params) throws ActionParameterNotFoundException {
		if (Strings.isNullOrEmpty(params.type)) {
			throw new ActionParameterNotFoundException(PARAM_NAME_TYPE);
		}
		/*
		 * else if (params.configuration.isJsonNull() ||
		 * params.configuration.entrySet().isEmpty()) {
		 * throw new ActionParameterNotFoundException("configuration");
		 * }
		 */
		else if (Strings.isNullOrEmpty(params.data)) {
			throw new ActionParameterNotFoundException(PARAM_NAME_DATA);
		}
		return true;
	}

	private class ParamsHolder {
		String type;
		JsonObject configuration;
		String data;
	}

}
