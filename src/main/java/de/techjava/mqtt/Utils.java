package de.techjava.mqtt;

import java.util.AbstractMap;
import java.util.Map.Entry;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Utils {

	public static final MqttMessage message(final byte[] payload, final boolean retain) {
		final MqttMessage message = new MqttMessage();
		message.setRetained(retain);
		message.setPayload(payload);
		return message;
	}

	public static final MqttMessage message(final String payload, final boolean retain) {
		return message(payload.getBytes(), retain);
	}

	/**
	 * Constructs the entry.
	 * 
	 * @param topic
	 *            name of the topic.
	 * @param message
	 *            message.
	 * @return the entry.
	 */
	public static final Entry<String, MqttMessage> entry(final String topic, final MqttMessage message) {
		return new AbstractMap.SimpleEntry<String, MqttMessage>(topic, message);
	}
}
