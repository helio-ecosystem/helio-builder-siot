package helio.builder.siot.experimental.actions.module;

import helio.blueprints.Action;
import helio.builder.siot.experimental.actions.errors.ActionNotFoundException;

public class ActionDatabaseModule implements ActionModule {

	private final static String MODULE_NAME = "Database";

	@Override
	public String moduleName() {
		return MODULE_NAME;
	}

	@Override
	public boolean isDefined(String type) {
		return false;
	}

	@Override
	public Action build(String type) throws ActionNotFoundException {
		throw new ActionNotFoundException(type);
	}

}
