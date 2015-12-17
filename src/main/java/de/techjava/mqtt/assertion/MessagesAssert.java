package de.techjava.mqtt.assertion;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.assertj.core.api.ListAssert;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MessagesAssert extends ListAssert<Entry<String, MqttMessage>> {

	public MessagesAssert(final List<Entry<String, MqttMessage>> messages) {
		super(messages);
	}

	public MessagesAssert(final Entry<String, MqttMessage>... messages) {
		super(Arrays.asList(messages));
	}

}
