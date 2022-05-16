package helio.builder.siot.experimental.actions;

import java.util.ArrayList;
import java.util.List;

import helio.blueprints.Action;
import helio.builder.siot.experimental.actions.errors.ActionNotFoundException;
import helio.builder.siot.experimental.actions.module.ActionDatabaseModule;
import helio.builder.siot.experimental.actions.module.ActionModule;
import helio.builder.siot.experimental.actions.module.ActionRequestModule;
import helio.builder.siot.experimental.actions.module.ActionValidatorModule;

public class ActionBuilder {

	private static ActionBuilder builder = null;
	private List<ActionModule> modules = null;
	
	private ActionBuilder() {
		modules = new ArrayList<ActionModule>();
		modules.add(new ActionValidatorModule());
		modules.add(new ActionRequestModule());
		modules.add(new ActionDatabaseModule());
	}
	
	public static ActionBuilder instance() {
		if (builder == null) {
			builder = new ActionBuilder();
		}
		return builder;
	}
	
	public List<ActionModule> moduleList() {
		return modules;
	}

	public boolean addActionModule(ActionModule newModule) {
		boolean added = false;
		if (modules.stream().noneMatch(m -> m.moduleName().equals(newModule.moduleName()))) {
			modules.add(newModule);
		}
		return added;
	}
	
	public boolean removeModule(ActionModule newModule) {
		return modules.removeIf(m -> m.moduleName().equals(newModule.moduleName()));
	}
	
	public Action build(String type) throws ActionNotFoundException {
		return modules.stream()
				.filter(m -> m.isDefined(type))
				.findFirst()
				.orElseThrow(() -> new ActionNotFoundException(type))
				.build(type);
	}
	
}
