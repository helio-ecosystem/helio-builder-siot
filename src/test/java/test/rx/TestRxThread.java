package test.rx;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonObject;

import helio.blueprints.DataProvider;
import helio.blueprints.components.Components;
import helio.blueprints.exceptions.ExtensionNotFoundException;
import helio.blueprints.exceptions.TranslationUnitExecutionException;
import helio.builder.siot.rx.RxThread;
import tests.TestUtils;

public class TestRxThread {

	@Test
	public void test01() throws TranslationUnitExecutionException, ExtensionNotFoundException {
		String dir = "./src/test/resources/data-samples/sample2.json";
		String file = TestUtils.readFile(dir);

		DataProvider provider = Components.newProviderInstance("FileProvider");
		JsonObject config = new JsonObject();
		config.addProperty("file",dir);
		provider.configure(config);
		int index = 0;
		boolean res = true;
		while (index < 100) {
			RxThread thread = new RxThread(provider);
			thread.start();
			res &= TestUtils.equals(file, thread.getReadings().get(0));
			
			index++;
		}
		Assert.assertTrue(res);
	}
}
