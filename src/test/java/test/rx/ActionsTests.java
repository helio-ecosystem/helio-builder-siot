package test.rx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;

import helio.blueprints.TranslationUnit;
import tests.TestUtils;

public class ActionsTests {

	private ExecutorService service = Executors.newFixedThreadPool(2);
	
	@Test
	public void test01() throws Exception {	
		TranslationUnit unit = TestUtils.buildRx("./src/test/resources/action-samples/request/01_http-get.txt");
		String result = TestUtils.runUnit(unit, service);
		//String expected = TestUtils.readFile("./src/test/resources/sync-tests/01-expected.jsonld");
		System.out.println(result);
		Assert.assertTrue(TestUtils.equals(result, ""));
		
	}
	
}
