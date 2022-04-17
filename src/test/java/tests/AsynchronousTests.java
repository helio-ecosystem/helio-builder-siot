package tests;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import helio.blueprints.TranslationUnit;
import helio.blueprints.UnitBuilder;
import helio.blueprints.exceptions.ExtensionNotFoundException;
import helio.blueprints.exceptions.IncompatibleMappingException;
import helio.blueprints.exceptions.IncorrectMappingException;
import helio.blueprints.exceptions.TranslationUnitExecutionException;
import helio.builder.jld11map.JLD11Builder;

public class AsynchronousTests {

	/**
	 * Mixed providers synchronous and asynchronous. Events are consumed (disappear)
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
	public void test01() throws IncompatibleMappingException, TranslationUnitExecutionException, IncorrectMappingException, ExtensionNotFoundException, InterruptedException, ExecutionException {
		ExecutorService service = Executors.newFixedThreadPool(4);
		String mapping = TestUtils.readFile("./src/test/resources/async-tests/01-mapping.ldmap");
		UnitBuilder builder = new JLD11Builder();
		Set<TranslationUnit> list = builder.parseMapping(mapping);
		TranslationUnit unit = list.iterator().next();

		int i = 0;
		while (i < 10) {
			Future<?> f = service.submit(unit.getTask());
			f.get();
			System.out.println(unit.getDataTranslated());

			Thread.sleep(500);
		}
	}

}
