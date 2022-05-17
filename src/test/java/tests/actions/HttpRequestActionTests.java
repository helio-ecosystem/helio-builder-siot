package tests.actions;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Set of test which validates the HTTP Request Action from Action Request Module.
 * 
 * @author Emilio
 *
 */
public class HttpRequestActionTests {

	/**
	 * Execute correctly a HTTP request with GET method.
	 */
	@Test
	public void test01_HTTPRequestWithGETMethod() {
		try {
			ActionDirectiveTestUtils.executeTestWithTemplate(
				ActionDirectiveTestUtils.DIR_REQUEST_RESOURCES + "01_http-get.txt");
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * Execute correctly a HTTP request with GET method but
	 * action module receives an error code like 400's or 500's.
	 */
	@Test
	public void test02_HTTPRequestWithGETMethodAndGetsAnErrorCode() {
		assertTrue(false);
	}

	/**
	 * Execute correctly a HTTP request with POST method.
	 */
	@Test
	public void test03_HTTPRequestWithPOSTMethod() {
		assertTrue(false);
	}

	/**
	 * Execute correctly a HTTP request with POST method but
	 * action module receives an error code like 400's or 500's.
	 */
	@Test
	public void test04_HTTPRequestWithPOSTMethodAndGetsAnErrorCode() {
		assertTrue(false);
	}

	/**
	 * Execute correctly a HTTP request with PUT method.
	 */
	@Test
	public void test05_HTTPRequestWithPUTMethod() {
		assertTrue(false);
	}

	/**
	 * Execute correctly a HTTP request with PUT method but
	 * action module receives an error code like 400's or 500's.
	 */
	@Test
	public void test06_HTTPRequestWithPUTMethodAndGetsAnErrorCode() {
		assertTrue(false);
	}

	/**
	 * Execute correctly a HTTP request with DELETE method.
	 */
	@Test
	public void test07_HTTPRequestWithDELETEMethod() {
		assertTrue(false);
	}

	/**
	 * Execute correctly a HTTP request with DELETE method but
	 * action module receives an error code like 400's or 500's.
	 */
	@Test
	public void test08_HTTPRequestWithDELETEMethodAndGetsAnErrorCode() {
		assertTrue(false);
	}

}
