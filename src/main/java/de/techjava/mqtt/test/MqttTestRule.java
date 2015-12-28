package de.techjava.mqtt.test;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.techjava.mqtt.Utils;
import io.moquette.proto.messages.PublishMessage;
import io.moquette.proto.messages.AbstractMessage.QOSType;

public class MqttTestRule extends ExternalResource {

	private final Logger logger = LoggerFactory.getLogger(MqttTestRule.class);
	private MqttBrokerLauncher broker = new MqttBrokerLauncher();
	private MqttClientLauncher client = new MqttClientLauncher();

	@Override
	protected void before() throws Throwable {
		broker.init();
		client.init();
		client.subscribe();
		Thread.sleep(1000);
	}

	@Override
	protected void after() {
		client.shutwdown();
		broker.shutdown();
	}

	public void publishMessage(final String topic, final boolean retain, final byte[] payload) {
		// client.publish(topic, Utils.message(payload, retain));
		final PublishMessage message = new PublishMessage();
		message.setRetainFlag(retain);
		message.setTopicName(topic);
		message.setQos(QOSType.MOST_ONE);
		message.setPayload(ByteBuffer.allocate(payload.length).put(payload));
		broker.internalpublish(message);
	}

	public void publishMessage(final String topic, final boolean retain, final String payload) {
		publishMessage(topic, retain, payload.getBytes());
	}

	public List<Entry<String, MqttMessage>> getMessagesReceived() {
		return client.getMessages();
	}

}
