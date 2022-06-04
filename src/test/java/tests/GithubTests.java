package tests;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import helio.blueprints.TranslationUnit;
import helio.blueprints.exceptions.ExtensionNotFoundException;
import helio.blueprints.exceptions.IncompatibleMappingException;
import helio.blueprints.exceptions.IncorrectMappingException;
import helio.blueprints.exceptions.TranslationUnitExecutionException;

public class GithubTests {



	@Test
	public void testIssue2() throws IncompatibleMappingException, TranslationUnitExecutionException, IncorrectMappingException, ExtensionNotFoundException, InterruptedException, ExecutionException {
		ExecutorService service = Executors.newFixedThreadPool(4);
		TranslationUnit unit = TestUtils.build("./src/test/resources/github-issues/issue02-mapping.txt");
		String result = TestUtils.runUnit(unit, service);
		//String expected = TestUtils.readFile("./src/test/resources/github-issues/05-expected.csv");
		service.shutdownNow();

		System.out.println(result);
	}
}
