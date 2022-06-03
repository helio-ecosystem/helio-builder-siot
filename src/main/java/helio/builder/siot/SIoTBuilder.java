package helio.builder.siot;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.compress.utils.Sets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import freemarker.template.Configuration;
import freemarker.template.Template;
import helio.blueprints.DataProvider;
import helio.blueprints.TranslationUnit;
import helio.blueprints.UnitBuilder;
import helio.blueprints.components.Components;
import helio.blueprints.exceptions.ExtensionNotFoundException;
import helio.blueprints.exceptions.IncompatibleMappingException;
import helio.blueprints.exceptions.IncorrectMappingException;
import helio.blueprints.exceptions.TranslationUnitExecutionException;
import helio.builder.siot.experimental.directives.ActionDirective;
import helio.builder.siot.methods.Handlers;

public class SIoTBuilder implements UnitBuilder {

	private static final String HANDLERS_FUNCTION_NAME = "handlers";
	private Configuration configuration;

	public SIoTBuilder() {
		configuration = new Configuration(Configuration.VERSION_2_3_31);
		configuration.setInterpolationSyntax(Configuration.SQUARE_BRACKET_INTERPOLATION_SYNTAX);
		configuration.setSharedVariable("action", new ActionDirective());
	}

	@Override
	public Set<TranslationUnit> parseMapping(String mapping) throws IncompatibleMappingException,
			TranslationUnitExecutionException, IncorrectMappingException, ExtensionNotFoundException {
		StringBuilder cleanedMapping = new StringBuilder();
		StringBuilder cleanedMappingTime = new StringBuilder();

		Map<String, DataProvider> providers = extractProviders(mapping, cleanedMapping);
		Integer time = extractTime(cleanedMapping, cleanedMappingTime);
		Template template = createTemplate(cleanedMappingTime.toString());
		SIoTMemoryUnit unit = new SIoTMemoryUnit(template, providers);
		if (time != null) {
			unit.setScheduledTime(time);
		}
		return Sets.newHashSet(unit);
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

	private Template createTemplate(String mapping) throws IncorrectMappingException, IncompatibleMappingException {
		try (Writer out = new StringWriter()) {
			configuration.setSharedVariable(HANDLERS_FUNCTION_NAME, new Handlers());
			return new Template(UUID.randomUUID().toString(), new StringReader(mapping), configuration);
		} catch (IOException e) {
			throw new IncompatibleMappingException(e.toString());
		}
	}

	// Extraer de aqui los providers+variable asociada en el template
	// Quitar del template la asignacion de providers
	// Montar una unidad de traduccion con los distintos providers
	// inyectar en la plantilla el valor devuelto por los providers en la variable
	// guardada
	private static final String PROVIDERS_REGEX = "<#assign\\s*([^=\\s]+)\\s*=providers\\(([^\\)]+)\\)\\s*>";

	private Map<String, DataProvider> extractProviders(String mapping, StringBuilder cleanedMapping)
			throws IncorrectMappingException, ExtensionNotFoundException {
		Map<String, DataProvider> providers = new HashMap<>();
		Pattern p = Pattern.compile(PROVIDERS_REGEX);
		Matcher m = p.matcher(mapping);
		String auxMap = mapping;
		while (m.find()) {
			auxMap = auxMap.replace(m.group(), ""); // term to replace
			String variable = m.group(1); // variable to inject data
			JsonObject configuration = toJsonConfiguration(m.group(2)); // provider
			DataProvider provider = Components.newProviderInstance(configuration.get(KEYWORD_TYPE).getAsString());
			provider.configure(configuration); // configure provider
			providers.put(variable, provider);
		}
		cleanedMapping.append(auxMap);
		return providers;
	}

	private static final Gson GSON = new Gson();
	private static final String KEYWORD_TYPE = "type";
	private static final String KEYWORD_OPEN_ = "{";
	private static final String KEYWORD_CLOSE_ = "}";

	private JsonObject toJsonConfiguration(String raw) throws IncorrectMappingException {
		StringBuilder br = new StringBuilder();
		br.append(KEYWORD_OPEN_).append(raw).append(KEYWORD_CLOSE_);

		JsonObject configuration = GSON.fromJson(br.toString(), JsonObject.class);
		if (!configuration.has(KEYWORD_TYPE))
			throw new IncorrectMappingException(
					"Provider tag must have a 'type' keyworkd indicating a valid DataProvider");

		return configuration;
	}

	@Override
	public void configure(JsonObject configuration) {
		// TODO Auto-generated method stub

	}

}
