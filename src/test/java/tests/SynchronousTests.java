package tests;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Assert;
import org.junit.Test;

import helio.blueprints.TranslationUnit;
import helio.builder.siot.rx.SIoTRxBuilder;

@Deprecated
public class SynchronousTests {

	@Test
	public void test01() throws Exception {
		ExecutorService service = Executors.newFixedThreadPool(4);
		TranslationUnit unit = TestUtils.build("./src/test/resources/sync-tests/01-mapping.ldmap");
		String result = TestUtils.runUnit(unit, service);
		String expected = TestUtils.readFile("./src/test/resources/sync-tests/01-expected.jsonld");
		service.shutdownNow();
		Assert.assertTrue(TestUtils.equals(result, expected));
	}

	@Test
	public void test02() throws Exception {
		ExecutorService service = Executors.newFixedThreadPool(4);
		TranslationUnit unit = TestUtils.build("./src/test/resources/sync-tests/02-mapping.ldmap");
		String result = TestUtils.runUnit(unit, service);
		service.shutdownNow();
		String expected = TestUtils.readFile("./src/test/resources/sync-tests/02-expected.jsonld");
		Assert.assertTrue(TestUtils.equals(result, expected));
	}

	@Test
	public void test03() throws Exception {
		ExecutorService service = Executors.newFixedThreadPool(4);
		TranslationUnit unit = TestUtils.build("./src/test/resources/sync-tests/03-mapping.ldmap");
		String result = TestUtils.runUnit(unit, service);
		String expected = TestUtils.readFile("./src/test/resources/sync-tests/03-expected.jsonld");
		service.shutdownNow();
		Assert.assertTrue(TestUtils.equals(result, expected));
	}

	/**
	 * It expects an exception to be thrown
	 */
	@Test
	public void test04() throws Exception {
		boolean isThrown = false;
		ExecutorService service = Executors.newCachedThreadPool();
		TranslationUnit unit = TestUtils.build("./src/test/resources/sync-tests/04-mapping.ldmap");

		try {
			TestUtils.runUnit(unit, service);
		} catch (Exception e) {
			isThrown = true;
			// System.out.println(e.toString());
		}
		Assert.assertTrue(isThrown);
	}

	@Test
	public void test05() throws Exception {
		ExecutorService service = Executors.newFixedThreadPool(4);
		TranslationUnit unit = TestUtils.build("./src/test/resources/sync-tests/05-mapping.ldmap");
		String result = TestUtils.runUnit(unit, service);
		String expected = TestUtils.readFile("./src/test/resources/sync-tests/05-expected.csv");
		service.shutdownNow();
		// System.out.println(result.trim());
		Assert.assertTrue(result.trim().equals(expected));

		// System.out.println(result);
	}

	@Test
	public void test06() throws Exception {
		String expected = TestUtils.readFile("./src/test/resources/sync-tests/06-expected.nt");
		ExecutorService service = Executors.newFixedThreadPool(4);
		TranslationUnit unit = TestUtils.build("./src/test/resources/sync-tests/06-mapping.ldmap");
		String result = TestUtils.runUnit(unit, service);
		service.shutdownNow();
		Assert.assertTrue(result.trim().equals(expected));

	}

	@Test
	public void test07() throws Exception {
		//String expected = TestUtils.readFile("./src/test/resources/sync-tests/06-expected.nt");

		ExecutorService service = Executors.newFixedThreadPool(4);
		TranslationUnit unit = TestUtils.build("./src/test/resources/sync-tests/07-mapping.ldmap");
		String result = TestUtils.runUnit(unit, service);
		System.out.println(result);
		service.shutdownNow();
		//Assert.assertTrue(result.trim().equals(expected));

	}
	
	@Test
	public void test08() throws Exception {
		//String expected = TestUtils.readFile("./src/test/resources/sync-tests/06-expected.nt");

		ExecutorService service = Executors.newFixedThreadPool(4);
		TranslationUnit unit = TestUtils.build("./src/test/resources/sync-tests/08-mapping.ldmap");
		String result = TestUtils.runUnit(unit, service);
		System.out.println(result);
		service.shutdownNow();
		//Assert.assertTrue(result.trim().equals(expected));

	}
	
	

	@Test
	public void test09() throws Exception {
		ExecutorService service = Executors.newScheduledThreadPool(5);
		TranslationUnit unit = TestUtils.build("./src/test/resources/sync-tests/01-mapping.ftl");
		int quantum = 1000;
		while(true) {
			try {
			String result = TestUtils.runUnit(unit, service);
			System.out.println(result);
			quantum = quantum*2;
			if(quantum > 1000000000)
				break;
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		//Assert.assertTrue(TestUtils.equals(result, expected));
	}
	
	
	
}
