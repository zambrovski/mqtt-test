package de.techjava.mqtt.test;

import org.junit.Rule;
import org.junit.Test;

public class MqttRuleTest {

	@Rule
	public MqttTestRule rule = new MqttTestRule();

	@Test
	public void testTheRule() {
		rule.publishMessage("foo", true, "bar");
		rule.assertMessageReceived().hasTopicName("foo").hasPayload("bar").isRetained(true);
	}
}
