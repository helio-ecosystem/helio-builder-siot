package tests.actions;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import helio.blueprints.TranslationUnit;
import helio.blueprints.exceptions.ExtensionNotFoundException;
import helio.blueprints.exceptions.IncompatibleMappingException;
import helio.blueprints.exceptions.IncorrectMappingException;
import helio.blueprints.exceptions.TranslationUnitExecutionException;
import tests.TestUtils;

/**
 * Set of test which validates the validator action module.
 * @author Emilio
 *
 */
public class ValidatorActionTests {

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
	public void test02_ValidateCorrectJSONDataWithJSONSChema() throws Exception {
		try {
			ExecutorService service = Executors.newFixedThreadPool(4);
			TranslationUnit unit = TestUtils.build("./src/test/resources/action-samples/validator/json-valid.txt");
			String result = TestUtils.runUnit(unit, service);
			service.shutdownNow();
			System.out.println(result);
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
	
}
