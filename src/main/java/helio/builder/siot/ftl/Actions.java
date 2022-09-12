package helio.builder.siot.ftl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.jena.ext.com.google.common.base.Strings;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import helio.blueprints.Action;
import helio.blueprints.components.Components;
import helio.blueprints.exceptions.ActionException;
import helio.blueprints.exceptions.ExtensionNotFoundException;
import helio.builder.siot.experimental.actions.errors.ActionParameterNotFoundException;

public class Actions implements TemplateDirectiveModel {

	private static final String PARAM_TYPE = "type";
	private static final String PARAM_CONF = "conf";
	private static final String PARAM_DATA = "data";

	@SuppressWarnings("rawtypes")
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
			Action action = Components.newActionInstance(paramsHolder.type);
			action.configure(paramsHolder.configuration);
			String result = action.run(paramsHolder.data);

			// Only returns the result in one variable for the template if its defined
			if (result != null && loopVars.length > 0) {
				loopVars[0] = new SimpleScalar(result);
			}
		} catch (ActionException | ExtensionNotFoundException e) {
			throw new TemplateModelException(e.getMessage());
		}

		// Render the body for the response
		body.render(env.getOut());
	}

	@SuppressWarnings("rawtypes")
	private ParamsHolder getParameters(Map params) {
		ParamsHolder paramsHolder = new ParamsHolder();
		Iterator paramIter = params.entrySet().iterator();

		while (paramIter.hasNext()) {
			Map.Entry ent = (Map.Entry) paramIter.next();

			String paramName = (String) ent.getKey();
			String paramValue = ent.getValue().toString();

			if (paramName.equals(PARAM_TYPE)) {
				paramsHolder.type = paramValue;
			} else if (paramName.equals(PARAM_CONF)) {
				paramsHolder.configuration = JsonParser.parseString(paramValue).getAsJsonObject();
			} else if (paramName.equals(PARAM_DATA)) {
				paramsHolder.data = paramValue;
			}
		}

		return paramsHolder;
	}

	private boolean validateParameters(ParamsHolder params) throws ActionParameterNotFoundException {
		if (Strings.isNullOrEmpty(params.type)) {
			throw new ActionParameterNotFoundException(PARAM_TYPE);
		}
		return true;
	}

	private class ParamsHolder {
		String type;
		JsonObject configuration;
		String data;
	}
}