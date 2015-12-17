package de.techjava.mqtt.test;

import org.junit.Rule;
import org.junit.Test;

import de.techjava.mqtt.Utils;
import de.techjava.mqtt.assertion.MqttAssertions;

public class MqttRuleTest {

	@Rule
	public MqttTestRule rule = new MqttTestRule();

	@Test
	public void testTheRule() {
		rule.publishMessage("foo", true, "bar");
		MqttAssertions.assertThat(rule.getMessagesReceived()).isNotEmpty()
				.contains(Utils.entry("foo", Utils.message("bar", false)));
	}
}
