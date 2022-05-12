package helio.builder.siot.experimental.actions.module;

import helio.blueprints.Action;
import helio.builder.siot.experimental.actions.errors.ActionNotFoundException;

public interface ActionModule {
	
	String moduleName();
	
	default boolean isDefined(String type) {
		return type.matches("^.*" + moduleName() + "$");
	}
	
	Action build(String type) throws ActionNotFoundException;
	
}
