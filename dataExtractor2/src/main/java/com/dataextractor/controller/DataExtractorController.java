package com.dataextractor.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dataextractor.dto.ResponseBodyDTO;
import com.dataextractor.response.ResponseGenerator;
import com.dataextractor.service.DataExtractorService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DataExtractorController {

	@Autowired
	private DataExtractorService dataExtractorService;

	/**
	 * 
	 * A controller method that handles GET requests to "/requestSendRecords"
	 * endpoint. This method sends records extracted from an external API to a Kafka
	 * topic. This method uses Cross-Origin Resource Sharing (CORS) to allow
	 * cross-domain requests from any origin.
	 * 
	 * @return a ResponseEntity with the ResponseBodyDTO containing information
	 *         about the request sql Copy code execution result, such as the status,
	 *         start and end dates, and any error messages.
	 * @throws Exception if an error occurs while sending records to the Kafka
	 *                   topic.
	 */
	@CrossOrigin(origins = "*", allowedHeaders = "*", originPatterns = "*")
	@GetMapping(value = "/requestSendRecords")
	public ResponseEntity<?> requestSendRecords() {

		@SuppressWarnings("rawtypes")
		ResponseBodyDTO body;

		Date startCallDate = new Date();
		log.info(String.format("requestSendRecords - startCallDate: %s", startCallDate));

		try {
			dataExtractorService.sendRecords();
			body = ResponseGenerator.generateResponse("Correctly requestSendRecords finished.", HttpStatus.OK,
					startCallDate);

		} catch (Exception e) {
			log.error("Error occurred in requestSendRecords: " + e.getMessage());
			body = ResponseGenerator.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, startCallDate);
		}
		return ResponseEntity.ok(body);
	}

}
