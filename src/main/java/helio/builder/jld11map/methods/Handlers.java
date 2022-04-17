package helio.builder.jld11map.methods;

import java.util.List;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import helio.blueprints.DataHandler;
import helio.blueprints.components.Components;

public class Handlers implements TemplateMethodModelEx{


	@Override
	public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
		String handler = String.valueOf(arguments.get(0));
		DataHandler handlerObj = Components.getDataHandlers().get(handler);

		return new HandlerWrapper(handlerObj);
	}

}
