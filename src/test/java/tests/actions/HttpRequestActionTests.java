package tests.actions;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import helio.blueprints.components.ComponentType;
import helio.blueprints.components.Components;

/**
 * Set of test which validates the HTTP Request Action from Action Request Module.
 * 
 * @author Emilio
 *
 */
public class HttpRequestActionTests {


	@BeforeClass
	public static void setup() {
		// For version 0.4.22
		/*
		Params:
			null -> jar localization
			string -> route for the class in this project
			enum -> component type

		Components.register(null, 
			"helio.builder.siot.experimental.actions.module.request.HttpRequestAction",
			ComponentType.ACTION);
		*/
	}


	/*********************************
	 * Http Request type tests
	 ********************************/

	/**
	 * Performs a request without 'url' parameter which is mandatory.
	 */
	@Test
	public void test01_HttpRequestWithoutUrlParameter_Then_ThrowsException() {
		assertTrue(false);
	}

	/**
	 * Performs a request without 'method' parameter which is mandatory.
	 */
	@Test
	public void test02_HttpRequestWithoutMethodParameter_Then_ThrowsException() {
		assertTrue(false);
	}

	/**
	 * Performs a request which 'url' parameter not exists.
	 */
	@Test
	public void test03_HttpRequestWithUrlWhichNotExists_Then_ThrowsException() {
		assertTrue(false);
	}

	/**
	 * Performs a request which 'method' parameter not exists.
	 */
	@Test
	public void test04_HttpRequestWithMethodWhichNotExists_Then_ThrowsException() {
		assertTrue(false);
	}

	/**
	 * Performs a request which 'header' parameter is incorrect format.
	 */
	@Test
	public void test05_HttpRequestWithIncorrectFormatHeader_Then_ThrowsException() {
		assertTrue(false);
	}





	/*********************************
	 * GET Request tests
	 ********************************/

	/**
	 * Performs a GET request without 'data' parameter which is optional.
	 */
	@Test
	public void test06_GetRequestWithoutDataParameter_Then_NoThrowsException() {
		assertTrue(false);
	}

	/**
	 * Performs a GET request without 'header' parameter which is optional.
	 */
	@Test
	public void test07_GetRequestWithoutHeaderParameter_Then_NoThrowsException() {
		assertTrue(false);
	}

	/**
	 * Performs a GET request and receives a response with data.
	 */
	@Test
	public void test08_GetRequest_Then_ReceiveResponseWithData() {
		try {
			ActionDirectiveTestUtils.executeTestWithTemplate(
					ActionDirectiveTestUtils.DIR_REQUEST_RESOURCES + "01_http-get.txt");
			assertTrue(true);
		}
		catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a GET request and receives an empty response.
	 */
	@Test
	public void test09_GetRequest_Then_ReceiveEmptyResponse() {
		assertTrue(false);
	}

	/**
	 * Performs a GET request with correct headers and receives a response with OK status.
	 */
	@Test
	public void test10_GetRequestWithCorrectHeaders_Then_ReceiveResponseWithOkStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a GET request with incorrect headers and receives a response with error status.
	 */
	@Test
	public void test11_GetRequestWithIncorrectHeaders_Then_ReceiveResponseWithErrorStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a GET request and receives a response with error status.
	 */
	@Test
	public void test12_GetRequest_Then_ReceiveResponseWithErrorStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a GET request and receives a response with internal server error status.
	 */
	@Test
	public void test13_GetRequest_Then_ReceiveResponseWithServerErrorStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a GET request which takes too much time.
	 */
	@Test
	public void test14_GetRequestWhichTakesToMuch_Then_ReceiveTimeout() {
		assertTrue(false);
	}






	/*********************************
	 * POST Request tests
	 ********************************/

	/**
	 * Performs a POST request without 'data' parameter which is mandatory.
	 */
	@Test
	public void test15_PostRequestWithoutDataParameter_Then_NoThrowsException() {
		assertTrue(false);
	}

	/**
	 * Performs a POST request without 'header' parameter which is optional.
	 */
	@Test
	public void test16_PostRequestWithoutHeaderParameter_Then_NoThrowsException() {
		assertTrue(false);
	}

	/**
	 * Performs a POST request and receives a response with ok status.
	 */
	@Test
	public void test17_PostRequestWithCorrectData_Then_ReceiveOkStatus() {
		try {
			String r = ActionDirectiveTestUtils.executeTestWithTemplate(
					ActionDirectiveTestUtils.DIR_REQUEST_RESOURCES + "03_http-post.txt");

			System.out.println("Content : " + r);
			assertTrue(true);
		}
		catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Performs a POST request with incorrect data and receives a response with error status.
	 */
	@Test
	public void test18_PostRequestWithIncorrectData_Then_ReceiveErrorStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a POST request and receives an empty response.
	 */
	@Test
	public void test19_PostRequest_Then_ReceiveEmptyResponse() {
		assertTrue(false);
	}

	/**
	 * Performs a POST request with correct headers and receives a response with OK status.
	 */
	@Test
	public void test20_PostRequestWithCorrectHeaders_Then_ReceiveResponseWithOkStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a POST request with incorrect headers and receives a response with error status.
	 */
	@Test
	public void test21_PostRequestWithIncorrectHeaders_Then_ReceiveResponseWithErrorStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a POST request and receives a response with error status.
	 */
	@Test
	public void test22_PostRequest_Then_ReceiveResponseWithErrorStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a POST request and receives a response with internal server error status.
	 */
	@Test
	public void test23_PostRequest_Then_ReceiveResponseWithServerErrorStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a POST request which takes too much time.
	 */
	@Test
	public void test24_PostRequestWhichTakesToMuch_Then_ReceiveTimeout() {
		assertTrue(false);
	}






	/*********************************
	 * PUT Request tests
	 ********************************/

	/**
	 * Performs a PUT request without 'data' parameter which is mandatory.
	 */
	@Test
	public void test25_PutRequestWithoutDataParameter_Then_NoThrowsException() {
		assertTrue(false);
	}

	/**
	 * Performs a PUT request without 'header' parameter which is optional.
	 */
	@Test
	public void test26_PutRequestWithoutHeaderParameter_Then_NoThrowsException() {
		assertTrue(false);
	}

	/**
	 * Performs a PUT request and receives a response with ok status.
	 */
	@Test
	public void test27_PutRequestWithCorrectData_Then_ReceiveOkStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a PUT request with incorrect data and receives a response with error status.
	 */
	@Test
	public void test28_PutRequestWithIncorrectData_Then_ReceiveErrorStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a PUT request and receives an empty response.
	 */
	@Test
	public void test29_PutRequest_Then_ReceiveEmptyResponse() {
		assertTrue(false);
	}

	/**
	 * Performs a PUT request with correct headers and receives a response with OK status.
	 */
	@Test
	public void test30_PutRequestWithCorrectHeaders_Then_ReceiveResponseWithOkStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a PUT request with incorrect headers and receives a response with error status.
	 */
	@Test
	public void test31_PutRequestWithIncorrectHeaders_Then_ReceiveResponseWithErrorStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a PUT request and receives a response with error status.
	 */
	@Test
	public void test32_PutRequest_Then_ReceiveResponseWithErrorStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a PUT request and receives a response with internal server error status.
	 */
	@Test
	public void test33_PutRequest_Then_ReceiveResponseWithServerErrorStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a PUT request which takes too much time.
	 */
	@Test
	public void test34_PutRequestWhichTakesToMuch_Then_ReceiveTimeout() {
		assertTrue(false);
	}






	/*********************************
	 * DELETE Request tests
	 ********************************/

	/**
	 * Performs a DELETE request without 'data' parameter which is optional.
	 */
	@Test
	public void test35_DeleteRequestWithoutDataParameter_Then_NoThrowsException() {
		assertTrue(false);
	}

	/**
	 * Performs a DELETE request without 'header' parameter which is optional.
	 */
	@Test
	public void test36_DeleteRequestWithoutHeaderParameter_Then_NoThrowsException() {
		assertTrue(false);
	}

	/**
	 * Performs a DELETE request and receives a response with data.
	 */
	@Test
	public void test37_DeleteRequest_Then_ReceiveResponseWithData() {
		assertTrue(false);
	}

	/**
	 * Performs a DELETE request and receives an empty response.
	 */
	@Test
	public void test38_DeleteRequest_Then_ReceiveEmptyResponse() {
		assertTrue(false);
	}

	/**
	 * Performs a DELETE request with correct headers and receives a response with OK status.
	 */
	@Test
	public void test39_DeleteRequestWithCorrectHeaders_Then_ReceiveResponseWithOkStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a DELETE request with incorrect headers and receives a response with error status.
	 */
	@Test
	public void test40_DeleteRequestWithIncorrectHeaders_Then_ReceiveResponseWithErrorStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a DELETE request and receives a response with error status.
	 */
	@Test
	public void test41_DeleteRequest_Then_ReceiveResponseWithErrorStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a DELETE request and receives a response with internal server error status.
	 */
	@Test
	public void test42_DeleteRequest_Then_ReceiveResponseWithServerErrorStatus() {
		assertTrue(false);
	}

	/**
	 * Performs a DELETE request which takes too much time.
	 */
	@Test
	public void test43_DeleteRequestWhichTakesToMuch_Then_ReceiveTimeout() {
		assertTrue(false);
	}

}
