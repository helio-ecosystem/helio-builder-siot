package helio.builder.siot.experimental.actions;


public class ActionBuilder {

	public static Action build(String type) throws ActionNotFoundException {
		Action action = null;
		
		if (type.equals(ValidatorAction.NAME)) {
			action = new ValidatorAction();
		}
		else if (type.equals(HttpRequestAction.NAME)) {
			action = new HttpRequestAction();
		}
		else {
			throw new ActionNotFoundException(type);
		}
		
		return action;
	}
	
	
	
}
