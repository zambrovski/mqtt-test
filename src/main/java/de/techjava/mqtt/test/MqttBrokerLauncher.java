package de.techjava.mqtt.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.moquette.BrokerConstants;
import io.moquette.interception.InterceptHandler;
import io.moquette.proto.messages.PublishMessage;
import io.moquette.server.Server;
import io.moquette.server.config.IConfig;
import io.moquette.server.config.MemoryConfig;

public class MqttBrokerLauncher {

	static final Logger logger = LoggerFactory.getLogger(MqttBrokerLauncher.class);

	private final IConfig config = new MemoryConfig(new Properties()) {
		{
			setProperty(BrokerConstants.PERSISTENT_STORE_PROPERTY_NAME, getClass().getResource("/").getFile()
					+ File.separator + BrokerConstants.DEFAULT_MOQUETTE_STORE_MAP_DB_FILENAME);
		}
	};
	private final Server mqttBroker = new Server();

	public MqttBrokerLauncher() {

	}

	public void init() {
		final List<? extends InterceptHandler> userHandlers = new ArrayList<InterceptHandler>();
		initBroker(userHandlers);
	}

	public void initBroker(List<? extends InterceptHandler> userHandlers) {
		Objects.requireNonNull("User handlers must be not null");
		try {
			logger.info("Starting MQTT Broker");
			mqttBroker.startServer(config, userHandlers);
		} catch (IOException e) {
			logger.error("Error starting MQTT Broker", e);
		}
	}

	public void internalpublish(final PublishMessage message) {
		logger.info("Internal broker publishing a message to '{}' in thread {}", message.getTopicName(), Thread.currentThread());
		mqttBroker.internalPublish(message);
	}

	public void shutdown() {
		logger.info("Stopping MQTT broker");
		mqttBroker.stopServer();
		logger.info("Broker MQTT stopped");
	}

}
