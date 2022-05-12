package helio.builder.siot.experimental.actions.module;

import helio.blueprints.Action;
import helio.builder.siot.experimental.actions.errors.ActionNotFoundException;

public class ActionRequestModule implements ActionModule {

	private final static String MODULE_NAME = "Request";
	
	@Override
	public String moduleName() {
		return MODULE_NAME;
	}
	
	@Override
	public Action build(String type) throws ActionNotFoundException {
		throw new ActionNotFoundException(type);
	}

}
