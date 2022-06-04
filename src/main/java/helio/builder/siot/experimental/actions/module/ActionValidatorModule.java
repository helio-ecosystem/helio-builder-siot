package helio.builder.siot.experimental.actions.module;

import helio.blueprints.Action;
import helio.builder.siot.experimental.actions.errors.ActionNotFoundException;
import helio.builder.siot.experimental.actions.module.validator.JsonValidatorAction;
import helio.builder.siot.experimental.actions.module.validator.XmlValidatorAction;

public class ActionValidatorModule implements ActionModule {

	private final static String MODULE_NAME = "Validator";

	@Override
	public String moduleName() {
		return MODULE_NAME;
	}

	@Override
	public Action build(String type) throws ActionNotFoundException {
		Action action;

		if (type.equalsIgnoreCase(JsonValidatorAction.TAG)) {
			action = new JsonValidatorAction();
		}
		else if (type.equalsIgnoreCase(XmlValidatorAction.TAG)) {
			action = new XmlValidatorAction();
		}
		else {
			throw new ActionNotFoundException(type);
		}

		return action;
	}

}
