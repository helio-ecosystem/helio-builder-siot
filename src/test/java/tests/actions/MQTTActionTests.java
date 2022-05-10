package tests.actions;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Set of test which validates the MQTT action module.
 * @author Emilio
 *
 */
public class MQTTActionTests {

	/**
	 * Send data to a MQTT topic.
	 */
	@Test
	public void test01_SendDataToMQTTTopic() {
		assertTrue(false);
	}
	
	/**
	 * Send data to a MQTT topic without broker configuration
	 * and the action module throws an error.
	 */
	@Test
	public void test02_SendDataToMQTTWithoutBrokerConfiguration() {
		assertTrue(false);
	}
	
	/**
	 * Send data to a MQTT topic with invalid broker configuration
	 * and the action module throws an error.
	 */
	@Test
	public void test03_SendDataToMQTTWithWrongBrokerConfiguration() {
		assertTrue(false);
	}
	
}
