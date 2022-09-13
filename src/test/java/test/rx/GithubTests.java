package test.rx;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonObject;

import helio.blueprints.TranslationUnit;
import helio.blueprints.exceptions.ExtensionNotFoundException;
import helio.blueprints.exceptions.IncompatibleMappingException;
import helio.blueprints.exceptions.IncorrectMappingException;
import helio.blueprints.exceptions.TranslationUnitExecutionException;
import test.TestUtils;

public class GithubTests {

	
	
	@Test
	public void testIssue2() throws IncompatibleMappingException, TranslationUnitExecutionException, IncorrectMappingException, ExtensionNotFoundException, InterruptedException, ExecutionException {
		ExecutorService service = Executors.newFixedThreadPool(4);
		TranslationUnit unit = TestUtils.buildRx("./src/test/resources/github-issues/issue02-mapping.ftl");
		String result = TestUtils.runUnit(unit, service);
		String expected = TestUtils.readFile("./src/test/resources/github-issues/issue02-expected.json");
		JsonObject resultJson = TestUtils.toJson(result);
		JsonObject expectedJson = TestUtils.toJson(expected);
		resultJson.remove("headers");
		expectedJson.remove("headers");
		Assert.assertTrue(TestUtils.equals(resultJson.toString().trim(), expectedJson.toString().trim()));
	}
}
