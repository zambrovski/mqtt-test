package de.techjava.mqtt.test;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map.Entry;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.techjava.mqtt.Utils;

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
		client.publish(topic, Utils.message(payload, retain));
	}

	public void publishMessage(String topic, boolean retain, String payload) {
		publishMessage(topic, retain, payload.getBytes());
	}

	public List<Entry<String, MqttMessage>> getMessagesReceived() {
		return client.getMessages();
	}

}
