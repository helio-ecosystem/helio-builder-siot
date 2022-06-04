package tests.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import helio.blueprints.components.ComponentType;
import helio.blueprints.components.Components;
import helio.builder.siot.experimental.actions.errors.HttpRequestParametersException;
import helio.builder.siot.experimental.actions.errors.HttpRequestPerformException;
import helio.builder.siot.experimental.actions.module.request.HttpRequestBuilder;
import tests.TestUtils;

/**
 * Set of test which validates the HTTP Request Action from Action Request
 * Module.
 *
 * @author Emilio
 *
 */
public class HttpRequestActionTests {

	private final String BASE_URL = "https://helio-tfm.mocklab.io";

	private final String TAG_TEST_FAIL = "This test should be fail.";

	@BeforeClass
	public static void setup() {
		// For version 0.4.22
		/*
		 * Params:
		 * null -> jar localization
		 * string -> route for the class in this project
		 * enum -> component type
		 *
		 * Components.register(null,
		 * "helio.builder.siot.experimental.actions.module.request.HttpRequestAction",
		 * ComponentType.ACTION);
		 */

		Components.register(null, "helio.builder.siot.experimental.actions.module.request.HttpRequestAction",
				ComponentType.ACTION);

	}

	/*********************************
	 * Http Request type tests
	 ********************************/

	/**
	 * Performs a request without 'url' parameter which is mandatory.
	 */
	@Test
	public void test01_HttpRequestWithoutUrlParameter_Then_ThrowsException() {
		try {
			String conf = createConfig("GET", null, null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf + "; result>\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestParametersException.class.getCanonicalName(), "Url not found" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a request without 'method' parameter which is mandatory.
	 */
	@Test
	public void test02_HttpRequestWithoutMethodParameter_Then_ThrowsException() {
		try {
			String conf = createConfig(null, "test", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf + "; result>\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestParametersException.class.getCanonicalName(), "Method not found" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a request which 'url' parameter not exists.
	 */
	@Test
	public void test03_HttpRequestWithUrlWhichNotExists_Then_ThrowsException() {
		try {
			String conf = createConfig("GET", "http://no-exists.es", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf + "; result>\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String obtained = cleanErrorMessage(e.getMessage())[0].strip();
			String expected = HttpRequestPerformException.class.getCanonicalName().strip();
			assertEquals(expected, obtained);
		}
	}

	/**
	 * Performs a request which 'method' parameter not exists.
	 */
	@Test
	public void test04_HttpRequestWithMethodWhichNotExists_Then_ThrowsException() {
		try {
			String conf = createConfig("test", "test", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf + "; result>\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestParametersException.class.getCanonicalName(), "Method test not exists" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a request which 'header' parameter is incorrect format.
	 */
	@Test
	public void test05_HttpRequestWithIncorrectFormatHeader_Then_ThrowsException() {
		try {
			String headers = TestUtils.readFile(ActionDirectiveTestUtils.DIR_REQUEST_RESOURCES + "headers-malformed.json");
			String conf = "{ " +
					"\"method\": \"GET\"," +
					"\"url\": \"" + BASE_URL + "/tfm/header\"," +
					"\"headers\": " + headers +
					"}";
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String obtained = e.getMessage().split(":")[0].strip();
			String expected = "freemarker.core.ParseException";
			assertEquals(expected, obtained);
		}
	}

	/*********************************
	 * GET Request tests
	 ********************************/

	/**
	 * Performs a GET request without 'data' parameter which is optional.
	 */
	@Test
	public void test06_GetRequestWithoutDataParameter_Then_NoThrowsException() {
		try {
			String conf = createConfig("GET", BASE_URL + "/success", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a GET request without 'header' parameter which is optional.
	 */
	@Test
	public void test07_GetRequestWithoutHeaderParameter_Then_NoThrowsException() {
		try {
			String conf = createConfig("GET", BASE_URL + "/success", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a GET request and receives a response with data.
	 */
	@Test
	public void test08_GetRequest_Then_ReceiveResponseWithData() {
		try {
			String conf = createConfig("GET", BASE_URL + "/success", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			String expected = "success!";
			String obtained = ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertEquals(expected, obtained.strip());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a GET request and receives an empty response.
	 */
	@Test
	public void test09_GetRequest_Then_ReceiveEmptyResponse() {
		try {
			String conf = createConfig("GET", BASE_URL + "/empty", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			String expected = "";
			String obtained = ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertEquals(expected, obtained.strip());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a GET request with correct headers and receives a response with OK
	 * status.
	 */
	@Test
	public void test10_GetRequestWithCorrectHeaders_Then_ReceiveResponseWithOkStatus() {
		try {
			String headers = TestUtils.readFile(ActionDirectiveTestUtils.DIR_REQUEST_RESOURCES + "headers-ok.json");
			String conf = createConfig("GET", BASE_URL + "/header", headers);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			String expected = "success!";
			String obtained = ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertEquals(expected, obtained.strip());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a GET request with incorrect headers and receives a response with
	 * error status.
	 */
	@Test
	public void test11_GetRequestWithIncorrectHeaders_Then_ReceiveResponseWithErrorStatus() {
		try {
			String headers = TestUtils.readFile(ActionDirectiveTestUtils.DIR_REQUEST_RESOURCES + "headers-error.json");
			String conf = createConfig("GET", BASE_URL + "/header", headers);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "Bad request (404)" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a GET request and receives a response with error status.
	 */
	@Test
	public void test12_GetRequest_Then_ReceiveResponseWithErrorStatus() {
		try {
			String conf = createConfig("GET", BASE_URL + "/error", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "Bad request (400)" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a GET request and receives a response with internal server error
	 * status.
	 */
	@Test
	public void test13_GetRequest_Then_ReceiveResponseWithServerErrorStatus() {
		try {
			String conf = createConfig("GET", BASE_URL + "/error-server", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "Internal server error (500)" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a GET request which takes too much time.
	 */
	@Test
	public void test14_GetRequestWhichTakesToMuch_Then_ReceiveTimeout() {
		try {
			// We change timeout duration to get the timeout exception
			HttpRequestBuilder.instance().setDefaultTimeout(Duration.of(2, ChronoUnit.SECONDS));

			String conf = createConfig("GET", BASE_URL + "/timeout", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "request timed out" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/*********************************
	 * POST Request tests
	 ********************************/

	/**
	 * Performs a POST request without 'data' parameter which is optional.
	 */
	@Test
	public void test15_PostRequestWithoutDataParameter_Then_NoThrowsException() {
		try {
			String conf = createConfig("POST", BASE_URL + "/empty-body", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a POST request without 'header' parameter which is optional.
	 */
	@Test
	public void test16_PostRequestWithoutHeaderParameter_Then_NoThrowsException() {
		try {
			String conf = createConfig("POST", BASE_URL + "/empty-body", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a POST request with text data and receives a response with ok
	 * status.
	 */
	@Test
	public void test17_PostRequestWitTextData_Then_ReceiveOkStatus() {
		try {
			String r = ActionDirectiveTestUtils.executeTestWithTemplate(
					ActionDirectiveTestUtils.DIR_REQUEST_RESOURCES + "03_http-post.txt");
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a POST request with Json data and receives a response with ok
	 * status.
	 */
	@Test
	public void test18_PostRequestWithJsonData_Then_ReceiveOkStatus() {
		try {
			String conf = createConfig("POST", BASE_URL + "/body-json", null);
			String data = "{\"content\": \"test\"}";
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" data=" + data + " conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			String expected = "success!";
			String obtained = ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertEquals(expected, obtained.strip());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a POST request with incorrect data and receives a response with
	 * error status.
	 */
	@Test
	public void test19_PostRequestWithIncorrectData_Then_ReceiveErrorStatus() {
		try {
			String conf = createConfig("POST", BASE_URL + "/success", null);
			String data = "incorrect-data";
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" data=\"" + data + "\" conf="
					+ conf + "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "Bad request (404)" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a POST request and receives an empty response.
	 */
	@Test
	public void test20_PostRequest_Then_ReceiveEmptyResponse() {
		try {
			String conf = createConfig("POST", BASE_URL + "/empty", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			String expected = "";
			String obtained = ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertEquals(expected, obtained.strip());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a POST request with correct headers and receives a response with OK
	 * status.
	 */
	@Test
	public void test21_PostRequestWithCorrectHeaders_Then_ReceiveResponseWithOkStatus() {
		try {
			String headers = TestUtils.readFile(ActionDirectiveTestUtils.DIR_REQUEST_RESOURCES + "headers-ok.json");
			String conf = createConfig("POST", BASE_URL + "/header", headers);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			String expected = "success!";
			String obtained = ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertEquals(expected, obtained.strip());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a POST request with incorrect headers and receives a response with
	 * error status.
	 */
	@Test
	public void test22_PostRequestWithIncorrectHeaders_Then_ReceiveResponseWithErrorStatus() {
		try {
			String headers = TestUtils.readFile(ActionDirectiveTestUtils.DIR_REQUEST_RESOURCES + "headers-error.json");
			String conf = createConfig("POST", BASE_URL + "/header", headers);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "Bad request (404)" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a POST request and receives a response with error status.
	 */
	@Test
	public void test23_PostRequest_Then_ReceiveResponseWithErrorStatus() {
		try {
			String conf = createConfig("POST", BASE_URL + "/error", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "Bad request (400)" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a POST request and receives a response with internal server error
	 * status.
	 */
	@Test
	public void test24_PostRequest_Then_ReceiveResponseWithServerErrorStatus() {
		try {
			String conf = createConfig("POST", BASE_URL + "/error-server", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "Internal server error (500)" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a POST request which takes too much time.
	 */
	@Test
	public void test25_PostRequestWhichTakesToMuch_Then_ReceiveTimeout() {
		try {
			// We change timeout duration to get the timeout exception
			HttpRequestBuilder.instance().setDefaultTimeout(Duration.of(2, ChronoUnit.SECONDS));

			String conf = createConfig("POST", BASE_URL + "/timeout", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "request timed out" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/*********************************
	 * PUT Request tests
	 ********************************/

	/**
	 * Performs a PUT request without 'data' parameter which is optional.
	 */
	@Test
	public void test26_PutRequestWithoutDataParameter_Then_NoThrowsException() {
		try {
			String conf = createConfig("PUT", BASE_URL + "/empty-body", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			String expected = "success!";
			String obtained = ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertEquals(expected, obtained.strip());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a PUT request without 'header' parameter which is optional.
	 */
	@Test
	public void test27_PutRequestWithoutHeaderParameter_Then_NoThrowsException() {
		try {
			String conf = createConfig("PUT", BASE_URL + "/empty-body", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			String expected = "success!";
			String obtained = ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertEquals(expected, obtained.strip());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a PUT request with text data and receives a response with ok status.
	 */
	@Test
	public void test28_PutRequestWithTextData_Then_ReceiveOkStatus() {
		try {
			String conf = createConfig("PUT", BASE_URL + "/success", null);
			String data = "test-data";
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" data=\"" + data + "\" conf="
					+ conf + "; result>\n[=result]\n</@action>";

			String expected = "success!";
			String obtained = ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertEquals(expected, obtained.strip());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a PUT request with Json data and receives a response with ok status.
	 */
	@Test
	public void test29_PutRequestWithJsonData_Then_ReceiveOkStatus() {
		try {
			String conf = createConfig("PUT", BASE_URL + "/body-json", null);
			String data = "{\"content\": \"test\"}";
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" data=" + data + " conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			String expected = "success!";
			String obtained = ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertEquals(expected, obtained.strip());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a PUT request with incorrect data and receives a response with error
	 * status.
	 */
	@Test
	public void test30_PutRequestWithIncorrectData_Then_ReceiveErrorStatus() {
		try {
			String conf = createConfig("PUT", BASE_URL + "/success", null);
			String data = "incorrect-data";
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" data=\"" + data + "\" conf="
					+ conf + "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "Bad request (404)" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a PUT request and receives an empty response.
	 */
	@Test
	public void test31_PutRequest_Then_ReceiveEmptyResponse() {
		try {
			String conf = createConfig("PUT", BASE_URL + "/empty", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			String expected = "";
			String obtained = ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertEquals(expected, obtained.strip());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a PUT request with correct headers and receives a response with OK
	 * status.
	 */
	@Test
	public void test32_PutRequestWithCorrectHeaders_Then_ReceiveResponseWithOkStatus() {
		try {
			String headers = TestUtils.readFile(ActionDirectiveTestUtils.DIR_REQUEST_RESOURCES + "headers-ok.json");
			String conf = createConfig("PUT", BASE_URL + "/header", headers);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			String expected = "success!";
			String obtained = ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertEquals(expected, obtained.strip());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a PUT request with incorrect headers and receives a response with
	 * error status.
	 */
	@Test
	public void test33_PutRequestWithIncorrectHeaders_Then_ReceiveResponseWithErrorStatus() {
		try {
			String headers = TestUtils.readFile(ActionDirectiveTestUtils.DIR_REQUEST_RESOURCES + "headers-error.json");
			String conf = createConfig("PUT", BASE_URL + "/header", headers);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "Bad request (404)" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a PUT request and receives a response with error status.
	 */
	@Test
	public void test34_PutRequest_Then_ReceiveResponseWithErrorStatus() {
		try {
			String conf = createConfig("PUT", BASE_URL + "/error", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "Bad request (400)" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a PUT request and receives a response with internal server error
	 * status.
	 */
	@Test
	public void test35_PutRequest_Then_ReceiveResponseWithServerErrorStatus() {
		try {
			String conf = createConfig("PUT", BASE_URL + "/error-server", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "Internal server error (500)" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a PUT request which takes too much time.
	 */
	@Test
	public void test36_PutRequestWhichTakesToMuch_Then_ReceiveTimeout() {
		try {
			// We change timeout duration to get the timeout exception
			HttpRequestBuilder.instance().setDefaultTimeout(Duration.of(2, ChronoUnit.SECONDS));

			String conf = createConfig("PUT", BASE_URL + "/timeout", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "request timed out" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/*********************************
	 * DELETE Request tests
	 ********************************/

	/**
	 * Performs a DELETE request without 'data' parameter which is optional.
	 */
	@Test
	public void test37_DeleteRequestWithoutDataParameter_Then_NoThrowsException() {
		try {
			String conf = createConfig("DELETE", BASE_URL + "/success", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a DELETE request without 'header' parameter which is optional.
	 */
	@Test
	public void test38_DeleteRequestWithoutHeaderParameter_Then_NoThrowsException() {
		try {
			String conf = createConfig("DELETE", BASE_URL + "/success", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a DELETE request and receives a response with data.
	 */
	@Test
	public void test39_DeleteRequest_Then_ReceiveResponseWithData() {
		try {
			String conf = createConfig("DELETE", BASE_URL + "/success", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			String expected = "success!";
			String obtained = ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertEquals(expected, obtained.strip());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a DELETE request and receives an empty response.
	 */
	@Test
	public void test40_DeleteRequest_Then_ReceiveEmptyResponse() {
		try {
			String conf = createConfig("DELETE", BASE_URL + "/empty", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			String expected = "";
			String obtained = ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertEquals(expected, obtained.strip());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a DELETE request with correct headers and receives a response with
	 * OK status.
	 */
	@Test
	public void test41_DeleteRequestWithCorrectHeaders_Then_ReceiveResponseWithOkStatus() {
		try {
			String headers = TestUtils.readFile(ActionDirectiveTestUtils.DIR_REQUEST_RESOURCES + "headers-ok.json");
			String conf = createConfig("DELETE", BASE_URL + "/header", headers);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			String expected = "success!";
			String obtained = ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertEquals(expected, obtained.strip());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a DELETE request with incorrect headers and receives a response with
	 * error status.
	 */
	@Test
	public void test42_DeleteRequestWithIncorrectHeaders_Then_ReceiveResponseWithErrorStatus() {
		try {
			String headers = TestUtils.readFile(ActionDirectiveTestUtils.DIR_REQUEST_RESOURCES + "headers-error.json");
			String conf = createConfig("DELETE", BASE_URL + "/header", headers);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "Bad request (404)" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a DELETE request and receives a response with error status.
	 */
	@Test
	public void test43_DeleteRequest_Then_ReceiveResponseWithErrorStatus() {
		try {
			String conf = createConfig("DELETE", BASE_URL + "/error", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "Bad request (400)" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a DELETE request and receives a response with internal server error
	 * status.
	 */
	@Test
	public void test44_DeleteRequest_Then_ReceiveResponseWithServerErrorStatus() {
		try {
			String conf = createConfig("DELETE", BASE_URL + "/error-server", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "Internal server error (500)" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Performs a DELETE request which takes too much time.
	 */
	@Test
	public void test45_DeleteRequestWhichTakesToMuch_Then_ReceiveTimeout() {
		try {
			// We change timeout duration to get the timeout exception
			HttpRequestBuilder.instance().setDefaultTimeout(Duration.of(2, ChronoUnit.SECONDS));

			String conf = createConfig("DELETE", BASE_URL + "/timeout", null);
			String dynamicTemplate = mandatoryLine() + "<@action type=\"HttpRequest\" conf=" + conf
					+ "; result>\n[=result]\n</@action>";

			ActionDirectiveTestUtils.executeTestWithStringTemplate(dynamicTemplate);
			assertTrue(TAG_TEST_FAIL, false);
		} catch (Exception e) {
			String[] obtained = cleanErrorMessage(e.getMessage());
			String[] expected = new String[] { HttpRequestPerformException.class.getCanonicalName(), "request timed out" };
			ActionDirectiveTestUtils.compared(expected, obtained);
		}
	}

	/**
	 * Helper methods
	 */

	private String mandatoryLine() {
		return "<#assign configuration=providers(type=\"FileProvider\", file=\""
				+ ActionDirectiveTestUtils.DIR_REQUEST_RESOURCES + "/http-get-conf.json\")>\n";
	}

	private String createConfig(String method, String url, String headers) {
		JsonObject json = new JsonObject();
		if (method != null) {
			json.addProperty("method", method);
		}
		if (url != null) {
			json.addProperty("url", url);
		}
		if (headers != null) {
			json.add("headers", JsonParser.parseString(headers));
		}
		return json.toString();
	}

	private String[] cleanErrorMessage(String msg) {
		String [] aux = msg.split("\n")[0].split(":");
		return Arrays.copyOfRange(aux, 2, aux.length);
	}

}
