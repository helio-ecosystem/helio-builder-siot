package tests.actions;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import helio.blueprints.TranslationUnit;
import tests.TestUtils;

/**
 * Set of test which validates the validator action module.
 * 
 * @author Emilio
 *
 */
public class ValidatorActionTests {

	private final String DIR_RESOURCES = "./src/test/resources/action-samples/validator/";

	/**
	 * The JSON source is incorrect and the validator throws an error.
	 */
	@Test
	public void test01_ValidateInvalidJSONDataWithJSONSChema() {
		assertTrue(false);
	}

	/**
	 * The JSON source is correct and the validator verifies it.
	 */
	@Test
	public void test02_ValidateCorrectJSONDataWithJSONSChema() {
		try {
			executeTestWithTemplate(DIR_RESOURCES + "02_json-template.txt");
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * The JSON source is correct but the validator expected a XML data.
	 */
	@Test
	public void test03_ValidateJSONDataWithXMLChema() {
		assertTrue(false);
	}

	/**
	 * The XML source is incorrect and the validator throws an error.
	 */
	@Test
	public void test04_ValidateInvalidXMLDataWithXMLSChema() {
		assertTrue(false);
	}

	/**
	 * The XML source is correct and the validator verifies it.
	 */
	@Test
	public void test05_ValidateCorrectXMLDataWithXMLSChema() {
		assertTrue(false);
	}

	/**
	 * The XML source is correct but the validator expected a JSON data.
	 */
	@Test
	public void test06_ValidateXMLDataWithJSONSChema() {
		assertTrue(false);
	}

	private void executeTestWithTemplate(String templateFile) throws Exception {
		ExecutorService service = Executors.newFixedThreadPool(4);
		TranslationUnit unit = TestUtils.build(templateFile);
		String result = TestUtils.runUnit(unit, service);
		service.shutdownNow();
		System.out.println(result);
	}

}
