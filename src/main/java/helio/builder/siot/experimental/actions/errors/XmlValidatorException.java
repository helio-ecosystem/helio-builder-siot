package helio.builder.siot.experimental.actions.errors;

import helio.blueprints.exceptions.ActionException;

public class XmlValidatorException extends ActionException {

    private static final long serialVersionUID = 5757442L;

    public XmlValidatorException(String msg) {
        super(XmlValidatorException.class.getCanonicalName() + ": " + msg);
    }

}
