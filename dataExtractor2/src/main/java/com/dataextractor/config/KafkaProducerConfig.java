package com.dataextractor.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.dataextractor.dto.ComuneDose;
import com.dataextractor.util.JsonSerializer;

/**
 * Configuration class for Kafka producer that defines the Kafka producer
 * factory and the Kafka template to be used to send messages to Kafka using the
 * ComuneDose class.
 */
@Configuration
public class KafkaProducerConfig {

	@Autowired
	private KafkaConfig systemConfig;

	public Map<String, Object> producerConfig() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.CLIENT_ID_CONFIG, systemConfig.producerApplicationID);
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, systemConfig.bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return props;
	}

	@Bean
	public ProducerFactory<String, ComuneDose> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfig());
	}

	@Bean
	public KafkaTemplate<String, ComuneDose> kafkaTemplate(ProducerFactory<String, ComuneDose> producerFactory) {
		return new KafkaTemplate<>(producerFactory);
	}

}