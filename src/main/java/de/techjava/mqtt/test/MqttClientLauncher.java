package de.techjava.mqtt.test;

import java.util.AbstractMap;
import java.util.Map.Entry;
import java.util.Stack;

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
import de.techjava.mqtt.assertion.MqttAssertions;

public class MqttClientLauncher {

	private Logger logger = LoggerFactory.getLogger(MqttClient.class);

	String broker = "tcp://localhost:1883";
	String clientId = "JavaSample";

	private MemoryPersistence persistence = new MemoryPersistence();
	private MqttClient mqttClient;
	private Stack<Entry<String, MqttMessage>> messages = new Stack<Entry<String, MqttMessage>>();

	public Stack<Entry<String, MqttMessage>> getMessages() {
		return messages;
	}

	public void initClient() {
		try {
			mqttClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			logger.info("Connecting to MQTT broker: " + broker);
			mqttClient.connect(connOpts);
			System.out.println("Connected");
		} catch (MqttException e) {
			logger.error("Error starting MQTT client.", e);
		}
	}

	public void subscribe() {

		mqttClient.setCallback(new MqttCallback() {

			public void messageArrived(String topic, MqttMessage message) throws Exception {
				messages.push(Utils.entry(topic, message));
			}

			public void deliveryComplete(IMqttDeliveryToken token) {

			}

			public void connectionLost(Throwable e) {
				logger.error("Connection lost", e);
			}
		});
		try {
			mqttClient.subscribe("*");
		} catch (MqttException e1) {
			logger.error("Error subscribing to topic", e1);
		}

	}

	public void shotwdownClient() {
		try {
			mqttClient.disconnect();
			logger.info("Disconnected");
		} catch (MqttException e) {
			logger.error("Error disconnecting from MQTT broker", e);
		}
	}

	public void publish(String topic, MqttMessage message) {
		try {
			mqttClient.publish(topic, message);
		} catch (MqttPersistenceException e) {
			logger.error("Could not store message", e);
		} catch (MqttException e) {
			logger.error("MQTT Error", e);
		}
	}


}
