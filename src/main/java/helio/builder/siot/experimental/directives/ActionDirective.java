package helio.builder.siot.experimental.directives;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import helio.builder.siot.experimental.actions.Action;
import helio.builder.siot.experimental.actions.ActionBuilder;
import helio.builder.siot.experimental.actions.ActionNotFoundException;

public class ActionDirective implements TemplateDirectiveModel {

	private static final String PARAM_NAME_TYPE = "type";
	private static final String PARAM_NAME_CONF = "conf";
	private static final String PARAM_NAME_DATA = "data";
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		String type = null;
		JsonObject configuration = null;
		String data = null;

		// Retrieve data from directive
		Iterator paramIter = params.entrySet().iterator();
        while (paramIter.hasNext()) {
        	Map.Entry ent = (Map.Entry) paramIter.next();

            String paramName = (String) ent.getKey();
            String paramValue = (String) ent.getValue();

            if (paramName.equals(PARAM_NAME_TYPE)) {
				type = paramValue;
			}
			else if (paramName.equals(PARAM_NAME_CONF)) {
				configuration = JsonParser.parseString(paramValue).getAsJsonObject();
			}
			else if (paramName.equals(PARAM_NAME_DATA)) {
				data = paramValue;
			}
		}

		// Check zone
		// ...
		
		
		try {
			Action action = ActionBuilder.build(type);
			action.configure(configuration);
			action.run(data);
		} catch (ActionNotFoundException e) {
			throw new TemplateModelException(e.getMessage());
		}
	}

}
