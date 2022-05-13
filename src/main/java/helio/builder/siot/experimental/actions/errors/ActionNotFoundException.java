package helio.builder.siot.experimental.actions.errors;

public class ActionNotFoundException extends Exception {

	private static final long serialVersionUID = 12238394L;

	public ActionNotFoundException(String actionType) {
		super("Action class " + actionType + " not found.");
	}
	
}
