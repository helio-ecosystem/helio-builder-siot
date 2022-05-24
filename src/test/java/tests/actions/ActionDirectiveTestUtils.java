package tests.actions;

import java.io.PrintWriter;
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

	public static final String DIR_RESOURCES = "./src/test/resources/action-samples/";
	public static final String DIR_DIRECTIVE_RESOURCES = DIR_RESOURCES + "directive/";
	public static final String DIR_VALIDATOR_RESOURCES = DIR_RESOURCES + "validator/";
	public static final String DIR_REQUEST_RESOURCES = DIR_RESOURCES + "request/";
	public static final String DIR_SCENARIOS_RESOURCES = DIR_RESOURCES + "scenarios/";

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

	public static String executeTestWithStringTemplate(String content) throws Exception {
		// Creates a file with content
		PrintWriter writer = new PrintWriter(DIR_RESOURCES + "template-test.txt");
		writer.println(content);
		writer.close();

		// Executes test
		return executeTestWithTemplate(DIR_RESOURCES + "template-test.txt");
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
