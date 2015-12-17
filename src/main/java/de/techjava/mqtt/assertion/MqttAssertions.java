package de.techjava.mqtt.assertion;

import java.util.List;
import java.util.Map.Entry;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttAssertions {

	public static MessagesAssert assertThat(final List<Entry<String, MqttMessage>> messages) {
		return new MessagesAssert(messages);
	}

	public static MessageAssert assertThat(final String topic, MqttMessage message) {
		return new MessageAssert(topic, message);
	}

}
