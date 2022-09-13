package tests.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.JsonParser;
import helio.blueprints.TranslationUnit;
import test.TestUtils;

import org.junit.Test;

/**
 * Set of test which validates the validator action module.
 * 
 * @author Emilio
 *
 */
public class ValidatorActionTests {

	private ExecutorService service = Executors.newFixedThreadPool(2);

	
	/**
	 * The JSON source is incorrect and the validator throws a JsonSyntaxException within the mapping.
	 */
	@Test
	public void test01_ValidateInvalidJsonDataWithJsonSchema() {
		try {
			TranslationUnit unit = TestUtils.buildRx(ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "01_json-template.txt");
			String  res = TestUtils.runUnit(unit, service);
			assertTrue(res.contains("com.google.gson.stream.MalformedJsonException: Expected ':' at line 4 column 17 path $.sequence"));
		} catch (Exception e) {
			assertEquals(true, false);
		}
	}

	/**
	 * The JSON source is correct and the validator verifies it.
	 */
	@Test
	public void test02_ValidateCorrectJsonDataWithJsonSchema() {
		try {
			String expected = TestUtils.readFile(ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "json-valid.json");
			
			TranslationUnit unit = TestUtils.buildRx(ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "02_json-template.txt");
			String  obtained = TestUtils.runUnit(unit, service);
		
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
			TranslationUnit unit = TestUtils.buildRx(ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "03_json-template.txt");
			String  obtained = TestUtils.runUnit(unit, service);
			
			assertEquals(obtained.trim(), "{\"status\":\"error\",\"message\":\"Invalid XML format: Content is not allowed in prolog.\"}");
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * The XML source is incorrect and the validator throws an error.
	 */
	@Test
	public void test04_ValidateInvalidXmlDataWithXmlSchema() {
		try {
			TranslationUnit unit = TestUtils.buildRx(ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "04_xml-template.txt");
			String  obtained = TestUtils.runUnit(unit, service);
			
			assertTrue(obtained.contains("{\"status\":\"error\",\"message\":\"Invalid XML format: The end-tag for element type \\\"catalog\\\" must end with a '>' delimiter.\"}"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * The XML source is correct and the validator verifies it.
	 */
	@Test
	public void test05_ValidateCorrectXmlDataWithXmlSchema() {
		try {
			String expected = TestUtils.readFile(ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "xml-valid.xml");
			TranslationUnit unit = TestUtils.buildRx(ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "05_xml-template.txt");
			String  obtained = TestUtils.runUnit(unit, service);
			
			
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
			TranslationUnit unit = TestUtils.buildRx(ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "06_xml-template.txt");
			String  obtained = TestUtils.runUnit(unit, service);
		
			assertTrue(obtained.contains("Invalid JSON format: com.google.gson.stream.MalformedJsonException"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}

}
