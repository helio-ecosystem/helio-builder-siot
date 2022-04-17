package tests;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import helio.blueprints.TranslationUnit;

public class ScheduledTests {

	@Test
	public void test01() throws Exception {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		TranslationUnit unit = TestUtils.build("./src/test/resources/scheduled-tests/01-mapping.ldmap");
		ScheduledFuture<?> f = service.scheduleAtFixedRate(unit.getTask(), 0, unit.getScheduledTime(),
				TimeUnit.MICROSECONDS);
		String expected = TestUtils.readFile("./src/test/resources/sync-tests/01-expected.jsonld");

		int i = 0;
		while (i < 5) {
			Thread.sleep(1000);
			try {
				String result = unit.getDataTranslated().get(0);
				Assert.assertTrue(TestUtils.equals(result, expected));
			} catch (Exception e) {
			}

			i++;

		}

		f.cancel(true);
		service.shutdown();
		// String expected =
		// TestUtils.readFile("./src/test/resources/sync-tests/01-expected.jsonld");

	}

}
