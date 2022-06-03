package helio.builder.siot.experimental.actions.module;

import helio.blueprints.Action;
import helio.builder.siot.experimental.actions.errors.ActionNotFoundException;
import helio.builder.siot.experimental.actions.module.request.HttpRequestAction;

public class ActionRequestModule implements ActionModule {

	private final static String MODULE_NAME = "Request";
	
	@Override
	public String moduleName() {
		return MODULE_NAME;
	}
	
	@Override
	public Action build(String type) throws ActionNotFoundException {
		Action action;

		if (type.equalsIgnoreCase(HttpRequestAction.TAG)) {
			action = new HttpRequestAction();
		}
		else {
			throw new ActionNotFoundException(type);
		}
		
		return action;
	}

}
