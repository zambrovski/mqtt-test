package de.techjava.mqtt.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.messages.InterceptPublishMessage;

public class PublisherInterceptor extends AbstractInterceptHandler {

	private Logger logger = LoggerFactory.getLogger(PublisherInterceptor.class);

	@Override
	public void onPublish(InterceptPublishMessage msg) {
		logger.info("Received on topic: {} content: {}", msg.getTopicName(), new String(msg.getPayload().array()));
	}
}