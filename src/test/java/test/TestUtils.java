package test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
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
import helio.builder.siot.rx.SIoTRxBuilder;

public class TestUtils {
	
	static {
		
		try {
			Components.registerAndLoad("https://github.com/helio-ecosystem/helio-providers-web/releases/download/v0.1.2/helio-providers-web-0.1.2.jar",
					 "helio.providers.HttpProvider", ComponentType.PROVIDER);
		} catch (ExtensionNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Components.registerAndLoad("/Users/andreacimmino/Desktop/helio-provider-url-0.1.0.jar",
					"provider.URLProvider", ComponentType.PROVIDER);
		} catch (ExtensionNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			Components.registerAndLoad(
					"https://github.com/helio-ecosystem/helio-handler-jayway/releases/download/v0.1.1/helio-handler-jayway-0.1.1.jar",
					"handlers.JsonHandler", ComponentType.HANDLER);
		} catch (ExtensionNotFoundException e) {
			e.printStackTrace();
		}

	

		try {
			Components.registerAndLoad(
					"https://github.com/helio-ecosystem/helio-provider-files/releases/download/v0.1.1/helio-provider-files-0.1.1.jar",
					"helio.providers.files.FileProvider", ComponentType.PROVIDER);

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Components.registerAndLoad(
					"https://github.com/helio-ecosystem/helio-provider-files/releases/download/v0.1.1/helio-provider-files-0.1.1.jar",
					"helio.providers.files.FileWatcherProvider", ComponentType.PROVIDER);

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Components.registerAndLoad(null, "helio.builder.siot.DummyProvider", ComponentType.PROVIDER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Components.registerAndLoad("https://github.com/helio-ecosystem/helio-action-http-request/releases/download/v0.1.0/helio-action-http-request-0.1.0.jar", "helio.action.http.HttpRequest", ComponentType.ACTION);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Components.registerAndLoad("https://github.com/helio-ecosystem/helio-action-xml-validator/releases/download/v0.1.0/helio-action-xml-validator-0.1.0.jar", "helio.actions.validator.XmlValidator", ComponentType.ACTION);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Components.registerAndLoad("https://github.com/helio-ecosystem/helio-action-json-validator/releases/download/v0.1.0/helio-action-json-validator-0.1.0.jar", "helio.actions.validator.JsonValidator", ComponentType.ACTION);
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

	
	
	public static TranslationUnit buildRx(String mappingFile) throws IncompatibleMappingException, TranslationUnitExecutionException, IncorrectMappingException, ExtensionNotFoundException {
		TranslationUnit unit = null;

			String mapping = readFile(mappingFile);
			UnitBuilder builder = new SIoTRxBuilder();
			Set<TranslationUnit> list = builder.parseMapping(mapping);
			unit = list.iterator().next();

		return unit;
	}
	
	public static String runUnit(TranslationUnit unit, ExecutorService service) throws InterruptedException, ExecutionException, TranslationUnitExecutionException {
		return runUnit(unit, service, null);
	}

	public static String runUnit(TranslationUnit unit, ExecutorService service, Map<String,Object> map) throws InterruptedException, ExecutionException, TranslationUnitExecutionException {
		String result =  "";

		Future<String> f = service.submit(unit.getTask(map));
		result = f.get();
		//f.cancel(true);
		//service.shutdown();

		return result;
	}
	public static final Gson GSON = new Gson();
	public static boolean equals(String result, String expected) {
		try {
		JsonObject object1 = GSON.fromJson(result, JsonObject.class);
		JsonObject object2 = GSON.fromJson(expected, JsonObject.class);

		return object1.equals(object2);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void load() {
	
	}
	
	public static JsonObject toJson(String result) {
		return  GSON.fromJson(result, JsonObject.class);
	}
}
