package de.techjava.mqtt.test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;

import java.nio.ByteBuffer;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.techjava.mqtt.test.MqttClientLauncher.TopicMessage;
import io.moquette.proto.messages.AbstractMessage;
import io.moquette.proto.messages.PublishMessage;

public class MqttTestRule extends ExternalResource {

	private final Logger logger = LoggerFactory.getLogger(MqttTestRule.class);
	private MqttBrokerLauncher broker = new MqttBrokerLauncher();
	private MqttClientLauncher client = new MqttClientLauncher();

	@Override
	protected void before() throws Throwable {
		broker.initBroker(asList(new PublisherInterceptor()));
		client.initClient();
		super.before();
	}

	@Override
	protected void after() {
		super.after();
		client.shotwdownClient();
		broker.shutdownServer();
	}

	public void publishMessage(String topic, boolean retain, byte[] payload) {
		MqttMessage message = new MqttMessage();
		message.setRetained(retain);
		message.setPayload(payload);
		client.publish(topic, message);
	}

	public void publishMessage(String topic, boolean retain, String payload) {
		publishMessage(topic, retain, payload.getBytes());
	}

	public MessageAssert assertMessageReceived() {
		assertFalse("No message received", client.getMessages().isEmpty());
		TopicMessage topicMessage = client.getMessages().pop();
		return new MessageAssert(topicMessage.topic, topicMessage.message);
	}

}
