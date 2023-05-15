package com.dataextractor.response;

import com.dataextractor.dto.ResponseBodyDTO;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * A utility class for generating HTTP responses with a generic type.
 * 
 * @param <T> the type of the response body.
 */
public class ResponseGenerator<T> {
	public ResponseGenerator() {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> ResponseBodyDTO<T> generateResponse(T response, HttpStatus status, Date startCallDate) {
		return new ResponseBodyDTO(response, startCallDate, new Date(), status);
	}
}
