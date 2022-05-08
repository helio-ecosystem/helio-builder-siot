package tests.actions;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
	public void test02_ValidateCorrectJSONDataWithJSONSChema() {
		assertTrue(false);
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
