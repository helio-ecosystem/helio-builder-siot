package helio.builder.siot.experimental.actions.errors;

public class ActionParameterNotFoundException extends Exception {

	private static final long serialVersionUID = 32499341424L;

	public ActionParameterNotFoundException(String actionParam) {
		super(ActionParameterNotFoundException.class.getCanonicalName() + ": Parameter '" + actionParam + "' not found in action directive.");
	}

}
