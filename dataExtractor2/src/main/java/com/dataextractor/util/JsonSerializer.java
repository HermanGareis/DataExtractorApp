package com.dataextractor.util;

import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A serializer for converting data into JSON format.
 * 
 * This implementation uses the Jackson ObjectMapper to serialize data into a
 * byte array.
 * 
 * @param <T> the type of the data to be serialized
 */
public class JsonSerializer<T> implements Serializer<T> {
	private final ObjectMapper objectMapper = new ObjectMapper();

	public JsonSerializer() {

	}

	public void configure(Map<String, ?> config, boolean isKey) {
		// Nothing to Configure
	}

	/**
	 * Serialize JsonNode
	 *
	 * @param topic Kafka topic name
	 * @param data  data as JsonNode
	 * @return byte[]
	 */
	public byte[] serialize(String topic, T data) {
		if (data == null) {
			return null;
		}
		try {
			return objectMapper.writeValueAsBytes(data);
		} catch (Exception e) {
			throw new SerializationException("Error serializing JSON message", e);
		}
	}

	public void close() {

	}
}
