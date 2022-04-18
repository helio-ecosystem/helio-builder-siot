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
		try {
			DataHandler handlerObj = Components.newHandlerInstance(handler);
			return new HandlerWrapper(handlerObj);
		}catch(Exception e) {
			throw new TemplateModelException(e.toString());
		}
		
	}

}
