package com.dataextractor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * This configuration class is used to configure the Kafka producer application
 * properties.
 * 
 * It provides beans for getting and setting the producer application ID,
 * bootstrap servers, and topic name.
 */
@Configuration
public class KafkaConfig {

	@Value("${producerApplicationID}")
	public String producerApplicationID;

	@Value("${bootstrapServers}")
	public String bootstrapServers;

	@Value("${topicName}")
	public String topicName;
}
