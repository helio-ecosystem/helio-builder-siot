package helio.builder.siot;

import java.util.List;
import java.util.Map;

import helio.blueprints.TranslationUnit;
import helio.blueprints.exceptions.TranslationUnitExecutionException;

public interface SIoTUnit extends TranslationUnit {

	public List<String> getDataTranslated(Map<String, Object> model) throws TranslationUnitExecutionException;

}
