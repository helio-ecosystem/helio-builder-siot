package tests.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

	public static final String TAG_TEST_FAIL = "This test should be fail.";

	public static void compared(String[] expected, String[] obtained) {
		assertTrue(expected.length == obtained.length);
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i].strip(), obtained[i].strip());
		}
	}

}
