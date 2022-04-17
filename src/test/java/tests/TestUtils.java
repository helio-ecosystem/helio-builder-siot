package tests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import helio.blueprints.TranslationUnit;
import helio.blueprints.UnitBuilder;
import helio.blueprints.components.ComponentType;
import helio.blueprints.components.Components;
import helio.blueprints.exceptions.ExtensionNotFoundException;
import helio.blueprints.exceptions.IncompatibleMappingException;
import helio.blueprints.exceptions.IncorrectMappingException;
import helio.blueprints.exceptions.TranslationUnitExecutionException;
import helio.builder.jld11map.JLD11Builder;

public class TestUtils {
	static {
		try {
			Components.registerAndLoad("/Users/andreacimmino/Desktop/helio-handler-csv-0.1.0.jar",
					"handlers.CsvHandler", ComponentType.HANDLER);
		} catch (ExtensionNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Components.registerAndLoad(
					"https://github.com/helio-ecosystem/helio-handler-jayway/releases/download/v0.1.0/helio-handler-jayway-0.1.0.jar",
					"handlers.JsonHandler", ComponentType.HANDLER);
		} catch (ExtensionNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Components.registerAndLoad(
					"https://github.com/helio-ecosystem/helio-processor-jmapping/releases/download/v0.2.2/helio-processor-jmapping-0.2.2.jar",
					"helio.jmapping.processor.JMappingProcessor", ComponentType.BUILDER);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Components.registerAndLoad(
					"/Users/andreacimmino/Desktop/helio-provider-files-0.1.1.jar",
					"helio.providers.files.FileProvider", ComponentType.PROVIDER);

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Components.registerAndLoad(
					"/Users/andreacimmino/Desktop/helio-provider-files-0.1.1.jar",
					"helio.providers.files.FileWatcherProvider", ComponentType.PROVIDER);

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Components.registerAndLoad(null, "helio.builder.jld11map.DummyProvider", ComponentType.PROVIDER);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readFile(String file){
		try {
			return Files.readString(Path.of(file));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static TranslationUnit build(String mappingFile) throws IncompatibleMappingException, TranslationUnitExecutionException, IncorrectMappingException, ExtensionNotFoundException {
		TranslationUnit unit = null;
	
			String mapping = readFile(mappingFile);

			UnitBuilder builder = new JLD11Builder();
			Set<TranslationUnit> list = builder.parseMapping(mapping);
			unit = list.iterator().next();
		
		return unit;
	}

	public static String runUnit(TranslationUnit unit, ExecutorService service) throws InterruptedException, ExecutionException, TranslationUnitExecutionException {
		String result =  "";
	
		Future<?> f = service.submit(unit.getTask());
		f.get();
		result = unit.getDataTranslated().get(0);
		f.cancel(true);
		service.shutdown();
		
		return result;
	}
	public static final Gson GSON = new Gson();
	public static boolean equals(String result, String expected) {
		JsonObject object1 = GSON.fromJson(result, JsonObject.class);
		JsonObject object2 = GSON.fromJson(expected, JsonObject.class);

		return object1.equals(object2);
	}
}
