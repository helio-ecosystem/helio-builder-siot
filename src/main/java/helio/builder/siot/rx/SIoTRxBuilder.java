package helio.builder.siot.rx;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.compress.utils.Sets;

import com.google.gson.JsonObject;

import freemarker.template.Configuration;
import freemarker.template.Template;
import helio.blueprints.TranslationUnit;
import helio.blueprints.UnitBuilder;
import helio.blueprints.UnitType;
import helio.blueprints.exceptions.ExtensionNotFoundException;
import helio.blueprints.exceptions.IncompatibleMappingException;
import helio.blueprints.exceptions.IncorrectMappingException;
import helio.blueprints.exceptions.TranslationUnitExecutionException;
import helio.builder.siot.ftl.Actions;
import helio.builder.siot.ftl.Handlers;
import helio.builder.siot.ftl.Providers;

public class SIoTRxBuilder implements UnitBuilder {

	@Override
	public void configure(JsonObject configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<TranslationUnit> parseMapping(String content) throws IncompatibleMappingException,
			TranslationUnitExecutionException, IncorrectMappingException, ExtensionNotFoundException {
		
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
		configuration.setInterpolationSyntax(Configuration.SQUARE_BRACKET_INTERPOLATION_SYNTAX);
		// 	TODO: this configuration could be updated with the configure method
		StringBuilder mappingCleaned = new StringBuilder();
		Integer scheduleTime = extractTime(new StringBuilder(content), mappingCleaned);
		try (Writer out = new StringWriter()) {
			configuration.setSharedVariable("handlers", new Handlers());
			configuration.setSharedVariable("providers", new Providers());
			configuration.setSharedVariable("action", new Actions());
			Template template = new Template(UUID.randomUUID().toString(), new StringReader(content), configuration);
			SIoTRxUnit unit = new SIoTRxUnit(template);
			
			if(scheduleTime!=null) {
				unit.setScheduledTime(scheduleTime);
				unit.setUnitType(UnitType.Scheduled);
			}
			return Sets.newHashSet(unit);
		} catch (IOException e) {
			throw new IncompatibleMappingException(e.toString());
		}
	}

	
	private static final String SCHEDULER_REGEX = "<#schedule\\s*time\\s*=\\s*(\\d+)\\s*>";

	private Integer extractTime(StringBuilder mapping, StringBuilder cleanedMappingTime) {
		Integer value = null;
		Pattern p = Pattern.compile(SCHEDULER_REGEX);
		Matcher m = p.matcher(mapping);
		String auxMap = mapping.toString();
		while (m.find()) {
			auxMap = auxMap.replace(m.group(), ""); // term to replace
			value = Integer.valueOf(m.group(1)); // variable to inject data
		}
		cleanedMappingTime.append(auxMap);
		return value;
	}
}
