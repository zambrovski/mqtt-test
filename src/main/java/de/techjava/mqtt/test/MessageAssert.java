package de.techjava.mqtt.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MessageAssert {
	private MqttMessage message;
	private String topic;

	public MessageAssert(final String topic, final MqttMessage message) {
		this.topic = topic;
		this.message = message;
	}

	public MessageAssert hasTopicName(String topic) {
		assertEquals("Expected topic %s but received %s", topic, this.topic);
		return this;
	}

	public MessageAssert isRetained(boolean retained) {
		assertEquals("Expected %s but received %s", retained, message.isRetained());
		return this;
	}

	public MessageAssert hasPayload(String payload) {
		assertEquals("Expected %s but received %s", payload, new String(message.getPayload()));
		return this;
	}

}
