package tests.actions;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import helio.blueprints.TranslationUnit;
import test.TestUtils;


/**
 * Set of test which validates the action directive.
 * 
 * @author Emilio
 *
 */
public class ActionDirectiveTests {

	private final String TAG_TEST_FAIL = "This test should be fail.";

	private ExecutorService service = Executors.newFixedThreadPool(2);


	/**
	 * Validates that 'type' parameter in action directive is mandatory.
	 */
	@Test
	public void test01_ActionDirectiveWithoutType() {
		try {
			TranslationUnit unit = TestUtils.buildRx(ActionDirectiveTestUtils.DIR_DIRECTIVE_RESOURCES + "01_action-no-type.txt");
			TestUtils.runUnit(unit, service);
			
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String exceptionMessage = e.getMessage().split(":")[2].trim();
	
			assertTrue(exceptionMessage.contains("Parameter 'type' not found in action directive."));
		}
	}

	/**
	 * Validates that 'conf' parameter in action directive is optional.
	 */
	@Test
	public void test02_ActionDirectiveWithoutConfiguration() {
		try {
			TranslationUnit unit = TestUtils.buildRx(ActionDirectiveTestUtils.DIR_DIRECTIVE_RESOURCES + "02_action-no-conf.txt");
			TestUtils.runUnit(unit, service);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Validates that 'data' parameter in action directive is optional.
	 */
	@Test
	public void test03_ActionDirectiveWithoutData() {
		try {
			TranslationUnit unit = TestUtils.buildRx(ActionDirectiveTestUtils.DIR_DIRECTIVE_RESOURCES + "03_action-no-data.txt");
			String result = TestUtils.runUnit(unit, service);
			
			assertTrue(!result.isBlank());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Validates that 'type' parameter in action directive exists in modules.
	 */
	@Test
	public void test04_ActionDirectiveWithTypeWhichNotExists() {
		try {
			TranslationUnit unit = TestUtils.buildRx(ActionDirectiveTestUtils.DIR_DIRECTIVE_RESOURCES + "04_action-type-not-exists.txt");
			TestUtils.runUnit(unit, service);
			
			
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String obtained = e.getMessage().split(":")[2].strip().trim();
			assertTrue(obtained.contains("Provided clazz 'DummyTest' is not a loaded component"));
		}
	}
	
	

}
