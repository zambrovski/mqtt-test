package de.techjava.mqtt.test;

import java.util.Map.Entry;
import java.util.Stack;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.techjava.mqtt.Utils;

public class MqttClientLauncher {

	private Logger logger = LoggerFactory.getLogger(MqttClient.class);

	String brokerUrl = "tcp://localhost:1883";

	private MqttClient client;
	private Stack<Entry<String, MqttMessage>> messages = new Stack<Entry<String, MqttMessage>>();

	public Stack<Entry<String, MqttMessage>> getMessages() {
		return messages;
	}

	public void init() {
		try {
			client = new MqttClient(brokerUrl, UUID.randomUUID().toString(), new MemoryPersistence());

			final MqttConnectOptions senderOptions = new MqttConnectOptions();
			senderOptions.setCleanSession(true);
			logger.info("Connecting to MQTT broker: {}...", brokerUrl);
			client.connect(senderOptions);
			logger.info("Connected.");
		} catch (MqttException e) {
			logger.error("Error starting MQTT client.", e);
		}
	}

	public void subscribe() {
		client.setCallback(new MqttCallback() {
			public void messageArrived(String topic, MqttMessage message) throws Exception {
				logger.info("Message received on {} in thread {}", topic, Thread.currentThread());
				messages.push(Utils.entry(topic, message));
			}

			public void deliveryComplete(IMqttDeliveryToken token) {
				logger.info("Delivery of a message complete.");
			}

			public void connectionLost(Throwable e) {
				logger.error("Connection lost", e);
			}
		});
		
		try {
			client.subscribe("foo");
		} catch (MqttException e) {
			logger.error("Error subscribing to topic", e);
		}

	}

	public void shutwdown() {
		try {
			client.disconnect();
			logger.info("Disconnected");

			client.close();
		} catch (MqttException e) {
			logger.error("Error disconnecting from MQTT broker", e);
		}
	}

	public void publish(String topic, MqttMessage message) {
	
		try {
			client.publish(topic, message);
		} catch (MqttPersistenceException e) {
			logger.error("Could not store message", e);
		} catch (MqttException e) {
			logger.error("MQTT Error", e);
		}
	}

}
