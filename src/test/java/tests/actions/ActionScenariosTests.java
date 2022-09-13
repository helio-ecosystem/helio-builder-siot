package tests.actions;


import helio.blueprints.TranslationUnit;
import helio.blueprints.exceptions.IncorrectMappingException;
import test.TestUtils;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class with different scenarios from action feature.
 * 
 * @author Emilio
 *
 */
public class ActionScenariosTests {

	private final String TAG_TEST_FAIL = "This test should be fail.";
	private ExecutorService service = Executors.newFixedThreadPool(2);

	/**
	 * A test with an action directive inside other action directive. The aim of this test is the template priority in directives.
	 * This test is expected to execute parent first and then child.
	 *
	 * Scenario 01:
	 * 1. Send Get request for parent.
	 * 2. Send Get request for child.
	 * 3. Response template is executed first parent and then child.
	 */
	@Test
	public void scenario01_Then_FirstParentSecondChild() {
		try {
			TranslationUnit unit = TestUtils.buildRx(ActionDirectiveTestUtils.DIR_SCENARIOS_RESOURCES + "scenario_01.txt");
			String result = TestUtils.runUnit(unit, service);
			String[] expected = new String[] {"father", "child"};
			String[] obtained = result.strip().split("\n");
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
		catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}


	/**
	 * Scenario 02:
	 * 1. Send a GET request.
	 * 2. Validates the response expected with Json format.
	 * 3. Throws a JsonSyntaxException.
	 */
	@Test
	public void scenario02_Then_SuccessfullValidation() {
		try {
			TranslationUnit unit = TestUtils.buildRx(ActionDirectiveTestUtils.DIR_SCENARIOS_RESOURCES + "scenario_02_1.txt");
			String result = TestUtils.runUnit(unit, service);
			assertTrue(result.contains("{\"status\":\"ok\"}"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Scenario 02:
	 * 1. Send a GET request.
	 * 2. Validates the response expected with Json format.
	 * 3. Throws a JsonSyntaxException.
	 */
	@Test
	public void scenario02_Then_ThrowsJsonSyntaxException() {
		try {
			TranslationUnit unit = TestUtils.buildRx(ActionDirectiveTestUtils.DIR_SCENARIOS_RESOURCES + "scenario_02_2.txt");
			TestUtils.runUnit(unit, service);
			assertTrue(TAG_TEST_FAIL, false);
		}
		catch (Exception e) {
			String expected = IncorrectMappingException.class.getCanonicalName().strip();
			String obtained = e.getMessage().split(":")[0].strip();
			assertEquals(expected, obtained);
		}
	}


	/**
	 * Scenario 03:
	 * 1. Send a GET request.
	 * 2. Validates the response expected with Json format.
	 * 3. Send a Post request with the previous data.
	 * 4. Receive a response expected.
	 */
	@Test
	public void scenario03_Then_ResponseIsSuccess() {
		try {
			
			TranslationUnit unit = TestUtils.buildRx(ActionDirectiveTestUtils.DIR_SCENARIOS_RESOURCES + "scenario_03.txt");
			String obtained = TestUtils.runUnit(unit, service);
			
			String expected = "success!";
			assertEquals(expected, obtained.strip());
		}
		catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}


	/**
	 * Scenario 04:
	 * 1. Send a GET request.
	 * 2. Send a Post request with the previous data which is a String.
	 * 3. Receive a response expected.
	 */
	@Test
	public void scenario04_Then_ResponseIsSuccess() {
		try {
			TranslationUnit unit = TestUtils.buildRx(ActionDirectiveTestUtils.DIR_SCENARIOS_RESOURCES + "scenario_04.txt");
			String obtained = TestUtils.runUnit(unit, service);
			
			String expected = "success!";
			assertEquals(expected, obtained.strip());
		}
		catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}


}
