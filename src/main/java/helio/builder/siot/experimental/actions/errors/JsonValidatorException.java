package helio.builder.siot.experimental.actions.errors;

import helio.blueprints.exceptions.ActionException;

public class JsonValidatorException extends ActionException {

    private static final long serialVersionUID = 5757442L;

    public JsonValidatorException(String msg) {
        super(JsonValidatorException.class.getCanonicalName() + ": " + msg);
    }

}
