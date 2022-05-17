package tests.actions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.JsonObject;

import helio.blueprints.Action;
import helio.blueprints.TranslationUnit;
import helio.builder.siot.experimental.actions.errors.ActionNotFoundException;
import helio.builder.siot.experimental.actions.module.ActionModule;
import tests.TestUtils;

/**
 * Utility class for executing all action tests.
 * 
 * @author Emilio
 *
 */
public class ActionDirectiveTestUtils {

	public static final String DIR_DIRECTIVE_RESOURCES = "./src/test/resources/action-samples/directive/";
	public static final String DIR_VALIDATOR_RESOURCES = "./src/test/resources/action-samples/validator/";
	public static final String DIR_REQUEST_RESOURCES = "./src/test/resources/action-samples/request/";
	

	public static MockupActionTestModule getMockupModule() {
		return new MockupActionTestModule();
	}

	public static String executeTestWithTemplate(String templateFile) throws Exception {
		ExecutorService service = Executors.newFixedThreadPool(4);
		TranslationUnit unit = TestUtils.build(templateFile);
		String result = TestUtils.runUnit(unit, service);
		service.shutdownNow();
		return result;
	}

	public static boolean isExceptionMessageExpected(String exceptionMessage, String exceptionExpected) {
		return exceptionMessage.split(":")[0].strip().equals(exceptionExpected);
	}

	private static class MockupActionTestModule implements ActionModule {

		@Override
		public String moduleName() {
			return "Mockup";
		}

		@Override
		public Action build(String type) throws ActionNotFoundException {
			if (!type.equalsIgnoreCase("Mockup")) {
				throw new ActionNotFoundException(type);
			}
			return new MockupAction();
		}

	}

	private static class MockupAction implements Action {

		@Override
		public void configure(JsonObject configuration) {

		}

		@Override
		public String run(String values) {
			return "";
		}

	}

}
