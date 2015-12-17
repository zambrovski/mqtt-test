package de.techjava.mqtt.test;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.moquette.interception.InterceptHandler;
import io.moquette.proto.messages.PublishMessage;
import io.moquette.server.Server;
import io.moquette.server.config.IConfig;
import io.moquette.server.config.MemoryConfig;

public class MqttBrokerLauncher {

	static final Logger logger = LoggerFactory.getLogger(MqttBrokerLauncher.class);
	private final IConfig classPathConfig = new MemoryConfig(new Properties());
	private final Server mqttBroker = new Server();

	public MqttBrokerLauncher() {

	}

	public void initBroker(List<? extends InterceptHandler> userHandlers) {
		try {
			logger.info("Starting MQTT Broker");
			mqttBroker.startServer(classPathConfig, userHandlers);
		} catch (IOException e) {
			logger.error("Error starting MQTT Broker", e);
		}
	}

	public void publish(final PublishMessage message) {
		logger.info("Publishing a message to '{}'", message.getTopicName());
		mqttBroker.internalPublish(message);
	}

	public void shutdownServer() {
		logger.info("Stopping MQTT broker");
		mqttBroker.stopServer();
		logger.info("Broker MQTT stopped");
	}

}
