package test.rx;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import helio.blueprints.TranslationUnit;
import helio.blueprints.UnitBuilder;
import helio.blueprints.UnitType;
import helio.blueprints.exceptions.ExtensionNotFoundException;
import helio.blueprints.exceptions.IncompatibleMappingException;
import helio.blueprints.exceptions.IncorrectMappingException;
import helio.blueprints.exceptions.TranslationUnitExecutionException;
import helio.builder.siot.SIoTBuilder;
import tests.TestUtils;

public class RxSynchronousTests {

	private ExecutorService service = Executors.newFixedThreadPool(2);

	
	@Test
	public void test01() throws Exception {	
		TranslationUnit unit = TestUtils.buildRx("./src/test/resources/sync-tests/01-mapping.ftl");
		String result = TestUtils.runUnit(unit, service);
		String expected = TestUtils.readFile("./src/test/resources/sync-tests/01-expected.jsonld");

		Assert.assertTrue(TestUtils.equals(result, expected));
		
	}
	

	@Test
	public void test02() throws Exception {
		
		TranslationUnit unit = TestUtils.buildRx("./src/test/resources/sync-tests/02-mapping.ftl");
		String result = TestUtils.runUnit(unit, service);
		
		String expected = TestUtils.readFile("./src/test/resources/sync-tests/02-expected.jsonld");
		Assert.assertTrue(TestUtils.equals(result, expected));
	}

	@Test
	public void test03() throws Exception {
		TranslationUnit unit = TestUtils.buildRx("./src/test/resources/sync-tests/03-mapping.ftl");
		String result = TestUtils.runUnit(unit, service);
		String expected = TestUtils.readFile("./src/test/resources/sync-tests/03-expected.jsonld");
	
		Assert.assertTrue(TestUtils.equals(result, expected));
	}

	/**
	 * It expects an exception to be thrown
	 */
	@Test
	public void test04() throws Exception {
		boolean isThrown = false;
		TranslationUnit unit = TestUtils.buildRx("./src/test/resources/sync-tests/04-mapping.ftl");

		try {
			TestUtils.runUnit(unit, service);
		} catch (Exception e) {
			isThrown = true;
		}
	
		Assert.assertTrue(isThrown);
	}

	@Test
	public void test05() throws Exception {
		TranslationUnit unit = TestUtils.buildRx("./src/test/resources/sync-tests/05-mapping.ftl");
		String result = TestUtils.runUnit(unit, service);
		String expected = TestUtils.readFile("./src/test/resources/sync-tests/05-expected.csv");
		
		Assert.assertTrue(result.trim().equals(expected));
	}

	@Test
	public void test06() throws Exception {
		String expected = TestUtils.readFile("./src/test/resources/sync-tests/06-expected.nt");
		TranslationUnit unit = TestUtils.buildRx("./src/test/resources/sync-tests/06-mapping.ftl");
		String result = TestUtils.runUnit(unit, service);
		Assert.assertTrue(result.trim().equals(expected.trim()));
	}

//	@Test
//	public void test07() throws Exception {
//		//String expected = TestUtils.readFile("./src/test/resources/sync-tests/06-expected.nt");
//
//		TranslationUnit unit = TestUtils.buildRx("./src/test/resources/sync-tests/07-mapping.ftl");
//		String result = TestUtils.runUnit(unit, service);
//		Assert.assertTrue(result.length()==161808193);
//
//	}
	
	@Test
	public void test08() throws Exception {
		// This test checks that providers can get an input from a freemarker variable
		TranslationUnit unit = TestUtils.buildRx("./src/test/resources/sync-tests/08-mapping.ftl");
		String result = TestUtils.runUnit(unit, service);
		String expected = TestUtils.readFile("./src/test/resources/sync-tests/01-expected.jsonld");

		Assert.assertTrue(TestUtils.equals(result, expected));

	}
	
	

	@Test
	public void test09() throws Exception {
		TranslationUnit unit = TestUtils.buildRx("./src/test/resources/sync-tests/01-mapping.ftl");
		String expected = TestUtils.readFile("./src/test/resources/sync-tests/01-expected.jsonld");
		int quantum = 1;
		boolean equalResults = true;
		while(quantum < 100) {
			try {
			String result = TestUtils.runUnit(unit, service);
			quantum++;
			equalResults &= TestUtils.equals(result, expected);
			if(!equalResults)
				break;
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		Assert.assertTrue(equalResults && (quantum==100));
		
		
	}
	
	
	/**
	 * This test check that a synchronous unit task can handle asynchronous providers
	 * @throws Exception
	 */
	@Test
	public void test10() throws Exception {
		TranslationUnit unit = TestUtils.buildRx("./src/test/resources/sync-tests/10-mapping.ftl");
		
		Map<String, Object> values = new HashMap<>();
		values.put("label", "data");
		
		Assert.assertTrue(UnitType.isSync(unit));
		Pattern pattern = Pattern.compile("(data:([0-9]+\\.[0-9]+, )?)+");
		
		
		int i = 0;
		int result = 0;
		while(i++ < 3) {
			String value = TestUtils.runUnit(unit, service, values);
			Matcher m = pattern.matcher(value);
			if(!value.isEmpty()&&  m.find())
					result++;
			Thread.sleep(5000*i);
		}		
		Assert.assertTrue(result == 5 );
		service.shutdownNow();
	}
	
	/**
	 * Mixed providers synchronous and asynchronous in a synchronous translation unit. Events are consumed (disappear)
	 * after the generation of the JSON-LD If the last event should be saved, a user
	 * should rely on a local variable that will be updated when new events are
	 * generated.
	 * @throws ExtensionNotFoundException
	 * @throws IncorrectMappingException
	 * @throws TranslationUnitExecutionException
	 * @throws IncompatibleMappingException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@Test
	public void test11() throws IncompatibleMappingException, TranslationUnitExecutionException, IncorrectMappingException, ExtensionNotFoundException, InterruptedException, ExecutionException {
		TranslationUnit unit = TestUtils.buildRx("./src/test/resources/sync-tests/11-mapping.ftl");

		Assert.assertTrue(UnitType.isSync(unit));
		Pattern pattern = Pattern.compile("1:([0-9]+\\.[0-9]+)?");
		
		int i = 0;
		boolean result = true;

		while (i++ < 3) {
			String value = TestUtils.runUnit(unit, service);
			Matcher m = pattern.matcher(value);
			result &= m.find();
			if(!result)
				break;
			Thread.sleep(1000*i);
		}
		Assert.assertTrue(result);
		service.shutdownNow();
	}
}
