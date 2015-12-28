package de.techjava.mqtt.test;

import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.techjava.mqtt.Utils;
import static de.techjava.mqtt.assertion.MqttAssertions.*;

public class MqttRuleTest {

	private Logger logger = LoggerFactory.getLogger(MqttRuleTest.class);
	
	@Rule
	public MqttTestRule rule = new MqttTestRule();

	@Test
	public void testTheRule() throws InterruptedException {
		rule.publishMessage("foo", false, "bar");
		rule.publishMessage("foo", false, "bar2");
		rule.publishMessage("foo", false, "bar3");
		
		Thread.sleep(1000);
		logger.info("Msgs {}", rule.getMessagesReceived());
		
		assertThat(rule.getMessagesReceived()).isNotEmpty();
		assertThat(rule.getMessagesReceived()).contains(Utils.entry("foo", Utils.message("bar", false)));
	}
}