package tests.actions;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import helio.builder.siot.experimental.actions.ActionBuilder;
import helio.builder.siot.experimental.actions.errors.ActionParameterNotFoundException;

/**
 * Set of test which validates the action directive.
 * 
 * @author Emilio
 *
 */
public class ActionDirectiveTests {

	@BeforeClass
	public static void addMockupModule() {
		ActionBuilder.instance().addActionModule(ActionDirectiveTestUtils.getMockupModule());
	}

	@AfterClass
	public static void removeMockupModule() {
		ActionBuilder.instance().removeModule(ActionDirectiveTestUtils.getMockupModule());
	}

	/**
	 * Validates that 'type' parameter in action directive is mandatory.
	 */
	@Test
	public void test01_ActionDirectiveWithoutType() {
		try {
			ActionDirectiveTestUtils.executeTestWithTemplate(
				ActionDirectiveTestUtils.DIR_DIRECTIVE_RESOURCES + "01_action-no-type.txt");
			assertTrue("Test is expected to fail", false);
		} catch (Exception e) {
			String exceptionMessage = e.getMessage().split(":")[2];
			String exceptionExpected = ActionParameterNotFoundException.class.getCanonicalName();
			if (ActionDirectiveTestUtils.isExceptionMessageExpected(exceptionMessage, exceptionExpected)) {
				assertTrue(true);
			} else {
				assertTrue(e.getMessage(), false);
			}
		}
	}

	/**
	 * Validates that 'conf' parameter in action directive is optional.
	 */
	@Test
	public void test02_ActionDirectiveWithoutConfiguration() {
		try {
			ActionDirectiveTestUtils.executeTestWithTemplate(
				ActionDirectiveTestUtils.DIR_DIRECTIVE_RESOURCES + "02_action-no-conf.txt");
			assertTrue(true);
		} catch (Exception e) {
			assertTrue("Test is expected to fail", false);
		}
	}

	/**
	 * Validates that 'data' parameter in action directive is optional.
	 */
	@Test
	public void test03_ActionDirectiveWithoutData() {
		try {
			ActionDirectiveTestUtils.executeTestWithTemplate(
				ActionDirectiveTestUtils.DIR_DIRECTIVE_RESOURCES + "03_action-no-data.txt");
			assertTrue(true);
		} catch (Exception e) {
			assertTrue("Test is expected to fail", false);
		}
	}

	/**
	 * Validates that 'type' parameter in action directive exists in modules.
	 */
	@Test
	public void test04_ActionDirectiveWithTypeWhichNotExists() {
		try {
			ActionDirectiveTestUtils.executeTestWithTemplate(
				ActionDirectiveTestUtils.DIR_DIRECTIVE_RESOURCES + "04_action-type-not-exists.txt");
			assertTrue("Test is expected to fail", false);
		} catch (Exception e) {
			String exceptionMessage = e.getMessage().split(":")[2];
			String exceptionExpected = ActionParameterNotFoundException.class.getCanonicalName();

			if (ActionDirectiveTestUtils.isExceptionMessageExpected(exceptionMessage, exceptionExpected)) {
				assertTrue(true);
			} else {
				assertTrue(e.getMessage(), false);
			}
		}
	}

}
