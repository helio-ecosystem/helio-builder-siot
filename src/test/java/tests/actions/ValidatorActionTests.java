package tests.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.gson.JsonParser;

import helio.builder.siot.experimental.actions.errors.JsonValidatorException;
import helio.builder.siot.experimental.actions.errors.XmlValidatorException;
import tests.TestUtils;

/**
 * Set of test which validates the validator action module.
 *
 * @author Emilio
 *
 */
public class ValidatorActionTests {

	/**
	 * The JSON source is incorrect and the validator throws a JsonSyntaxException.
	 */
	@Test
	public void test01_ValidateInvalidJsonDataWithJsonSchema() {
		try {
			ActionDirectiveTestUtils.executeTestWithTemplate(
					ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "01_json-template.txt");
			assertTrue(false);
		} catch (Exception e) {
			String expected = JsonValidatorException.class.getCanonicalName().strip();
			String obtained = e.getMessage().split(":")[2].strip();
			assertEquals(expected, obtained);
		}
	}

	/**
	 * The JSON source is correct and the validator verifies it.
	 */
	@Test
	public void test02_ValidateCorrectJsonDataWithJsonSchema() {
		try {
			String expected = TestUtils.readFile(ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "json-valid.json");
			String obtained = ActionDirectiveTestUtils.executeTestWithTemplate(
					ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "02_json-template.txt");
			assertEquals(JsonParser.parseString(expected), JsonParser.parseString(obtained));
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * The JSON source is correct but the validator expected a XML data.
	 */
	@Test
	public void test03_ValidateJsonDataWithXmlSchema() {
		try {
			String obtained = ActionDirectiveTestUtils.executeTestWithTemplate(
					ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "03_json-template.txt");

			assertTrue(ActionDirectiveTestUtils.TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String expected = XmlValidatorException.class.getCanonicalName().strip();
			String obtained = e.getMessage().split(":")[2].strip();
			assertEquals(expected, obtained);
		}
	}

	/**
	 * The XML source is incorrect and the validator throws an error.
	 */
	@Test
	public void test04_ValidateInvalidXmlDataWithXmlSchema() {
		try {
			String obtained = ActionDirectiveTestUtils.executeTestWithTemplate(
					ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "04_xml-template.txt");

			assertTrue(ActionDirectiveTestUtils.TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String expected = XmlValidatorException.class.getCanonicalName().strip();
			String obtained = e.getMessage().split(":")[2].strip();
			assertEquals(expected, obtained);
		}
	}

	/**
	 * The XML source is correct and the validator verifies it.
	 */
	@Test
	public void test05_ValidateCorrectXmlDataWithXmlSchema() {
		try {
			String expected = TestUtils.readFile(ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "xml-valid.xml");
			String obtained = ActionDirectiveTestUtils.executeTestWithTemplate(
					ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "05_xml-template.txt");

			assertEquals(expected.strip(), obtained.strip());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * The XML source is correct but the validator expected a JSON data.
	 */
	@Test
	public void test06_ValidateXmlDataWithJsonSchema() {
		try {
			String obtained = ActionDirectiveTestUtils.executeTestWithTemplate(
					ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "06_xml-template.txt");

			assertTrue(ActionDirectiveTestUtils.TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String expected = JsonValidatorException.class.getCanonicalName().strip();
			String obtained = e.getMessage().split(":")[2].strip();
			assertEquals(expected, obtained);
		}
	}

}
