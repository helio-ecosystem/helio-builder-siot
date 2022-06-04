package helio.builder.siot.experimental.actions.module;

import helio.blueprints.Action;
import helio.builder.siot.experimental.actions.errors.ActionNotFoundException;

public interface ActionModule {

	String moduleName();

	default boolean isDefined(String type) {
		return type.toLowerCase().matches("^.*" + moduleName().toLowerCase() + "$");
	}

	Action build(String type) throws ActionNotFoundException;

}
