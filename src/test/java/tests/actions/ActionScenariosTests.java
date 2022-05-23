package tests.actions;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class with different scenarios from action feature.
 * 
 * @author Emilio
 *
 */
public class ActionScenariosTests {

	/**
	 * A test with an action directive inside other action directive. The aim of this test is the template priority in directives.
	 * This test is expected to execute parent first and then child.
	 */
	@Test
	public void test01_templatePriorityActionInsideOtherAction() {
		try {
			String result = ActionDirectiveTestUtils.executeTestWithTemplate(
					ActionDirectiveTestUtils.DIR_SCENARIOS_RESOURCES + "scenario_01.txt");
			String[] expected = new String[] {"father", "child"};
			String[] obtained = result.strip().split("\n");

			assertTrue(expected.length == obtained.length);
			for (int i = 0; i < obtained.length; i++) {
				assertTrue(expected[i].equals(obtained[i].strip()));
			}
		}
		catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

}
