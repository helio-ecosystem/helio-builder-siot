package tests.actions;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Set of test which validates the Database action module.
 * @author Emilio
 *
 */
public class DatabaseActionTests {

	/**
	 * Store data to Elasticsearch database.
	 */
	@Test
	public void test01_StoreDataInElasticsearchDatabase() {
		assertTrue(false);
	}
	
	/**
	 * Store data to MySQL database.
	 */
	@Test
	public void test02_StoreDataInMySQLDatabase() {
		assertTrue(false);
	}
	
	/**
	 * Store data with no database configuration
	 * and the action module throws an error.
	 */
	@Test
	public void test03_StoreDataWithoutDatabaseConfiguration() {
		assertTrue(false);
	}
	
	/**
	 * Store data with invalid database configuration
	 * and the action module throws an error.
	 */
	@Test
	public void test04_StoreDataWithWrongDatabaseConfiguration() {
		assertTrue(false);
	}
	
}
