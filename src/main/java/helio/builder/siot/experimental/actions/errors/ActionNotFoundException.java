package helio.builder.siot.experimental.actions.errors;

import helio.blueprints.exceptions.ActionException;

public class ActionNotFoundException extends ActionException {

	private static final long serialVersionUID = 12238394L;

	public ActionNotFoundException(String actionType) {
		super(ActionNotFoundException.class.getCanonicalName() + ": Action class " + actionType + " not found.");
	}

}
