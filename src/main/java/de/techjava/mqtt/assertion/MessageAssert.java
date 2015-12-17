package de.techjava.mqtt.assertion;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Assert for a single message.
 * 
 * @author Simon Zambrovski.
 */
public class MessageAssert {
	private MqttMessage message;
	private String topic;

	/**
	 * Constructs the assert for a message received on a given topic.
	 * 
	 * @param topic
	 *            name of the topic.
	 * @param message
	 *            message
	 */
	public MessageAssert(final String topic, final MqttMessage message) {
		this.topic = topic;
		this.message = message;
	}

	/**
	 * Verifies the topic name,
	 * 
	 * @param topic
	 *            expected topic.
	 * @return fluent instance.
	 */
	public MessageAssert hasTopicName(final String topic) {
		assertThat(this.topic).isEqualTo(topic).overridingErrorMessage("Expected topic %s but received %s", topic,
				this.topic);
		return this;
	}

	/**
	 * Verifies if the message is marked as retained.
	 * 
	 * @param retained
	 *            expectation.
	 * @return fluent instance.
	 */
	public MessageAssert isRetained(boolean retained) {
		assertThat(this.message.isRetained()).isEqualTo(retained).overridingErrorMessage("Expected %s but received %s",
				retained, message.isRetained());
		return this;
	}

	/**
	 * Verifies if the payload is as expected.
	 * 
	 * @param payload
	 *            expected.
	 * @return fluent instance.
	 */
	public MessageAssert hasPayload(String payload) {
		final String messagePayload = new String(message.getPayload());
		assertThat(messagePayload).isEqualTo(payload).overridingErrorMessage("Expected %s but received %s", payload,
				messagePayload);
		return this;
	}

}
