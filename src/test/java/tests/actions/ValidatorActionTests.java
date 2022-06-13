package tests.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.Test;

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
			String expected = "error";
			JsonObject obtained = JsonParser.parseString(
					ActionDirectiveTestUtils.executeTestWithTemplate(
					ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "01_json-template.txt")).getAsJsonObject();

			assertTrue(obtained.has("status"));
			assertTrue(obtained.has("message"));
			assertEquals(expected, obtained.get("status").getAsString().strip().toLowerCase());
		} catch (Exception e) {
			System.out.println("Error al parsear el resultado");
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * The JSON source is correct and the validator verifies it.
	 */
	@Test
	public void test02_ValidateCorrectJsonDataWithJsonSchema() {
		try {
			//String expected = TestUtils.readFile(ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "json-valid.json");
			String expected = "ok";
			JsonObject obtained = JsonParser.parseString(
					ActionDirectiveTestUtils.executeTestWithTemplate(
					ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "02_json-template.txt")).getAsJsonObject();

			assertTrue(obtained.has("status"));
			assertTrue(!obtained.has("message"));
			assertEquals(expected, obtained.get("status").getAsString().strip().toLowerCase());
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
			String expected = "error";
			JsonObject obtained = JsonParser.parseString(
					ActionDirectiveTestUtils.executeTestWithTemplate(
					ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "03_json-template.txt")).getAsJsonObject();

			assertTrue(obtained.has("status"));
			assertTrue(obtained.has("message"));
			assertEquals(expected, obtained.get("status").getAsString().strip().toLowerCase());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * The XML source is incorrect and the validator throws an error.
	 */
	@Test
	public void test04_ValidateInvalidXmlDataWithXmlSchema() {
		try {
			String expected = "error";
			JsonObject obtained = JsonParser.parseString(
					ActionDirectiveTestUtils.executeTestWithTemplate(
					ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "04_xml-template.txt")).getAsJsonObject();

			assertTrue(obtained.has("status"));
			assertTrue(obtained.has("message"));
			assertEquals(expected, obtained.get("status").getAsString().strip().toLowerCase());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * The XML source is correct and the validator verifies it.
	 */
	@Test
	public void test05_ValidateCorrectXmlDataWithXmlSchema() {
		try {
			String expected = "ok";
			JsonObject obtained = JsonParser.parseString(
					ActionDirectiveTestUtils.executeTestWithTemplate(
					ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "05_xml-template.txt")).getAsJsonObject();

			assertTrue(obtained.has("status"));
			assertTrue(!obtained.has("message"));
			assertEquals(expected, obtained.get("status").getAsString().strip().toLowerCase());
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
			String expected = "error";
			JsonObject obtained = JsonParser.parseString(
					ActionDirectiveTestUtils.executeTestWithTemplate(
					ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "06_xml-template.txt")).getAsJsonObject();

			assertTrue(obtained.has("status"));
			assertTrue(obtained.has("message"));
			assertEquals(expected, obtained.get("status").getAsString().strip().toLowerCase());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

}
