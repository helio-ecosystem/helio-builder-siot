package helio.builder.siot;

import java.util.Set;

import com.google.gson.JsonObject;

import helio.blueprints.TranslationUnit;
import helio.blueprints.UnitBuilder;
import helio.blueprints.exceptions.ExtensionNotFoundException;
import helio.blueprints.exceptions.IncompatibleMappingException;
import helio.blueprints.exceptions.IncorrectMappingException;
import helio.blueprints.exceptions.TranslationUnitExecutionException;

public class SIoTRmlBuilder implements UnitBuilder{

	private SIoTBuilder builder = new SIoTBuilder();

	@Override
	public Set<TranslationUnit> parseMapping(String content) throws IncompatibleMappingException, TranslationUnitExecutionException, IncorrectMappingException, ExtensionNotFoundException {

		return null;
	}

	@Override
	public void configure(JsonObject configuration) {

	}

}
