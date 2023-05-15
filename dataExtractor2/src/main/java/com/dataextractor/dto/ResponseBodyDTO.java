package com.dataextractor.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * A class representing a response body with data, call dates, and status.
 * 
 * @param <T> the type of data contained in the response body
 */
@Getter
@Setter
public class ResponseBodyDTO<T> {

	private T data;

	private Date startCallDate;
	private Date endCallDate;
	private HttpStatus status;

	public ResponseBodyDTO() {
	}

	public ResponseBodyDTO(T data, Date startCallDate, Date endCallDate, HttpStatus status) {
		this.data = data;
		this.startCallDate = startCallDate;
		this.endCallDate = endCallDate;
		this.status = status;
	}

	public ResponseBodyDTO(Date startCallDate, Date endCallDate, HttpStatus status) {
		this.startCallDate = startCallDate;
		this.endCallDate = endCallDate;
		this.status = status;
	}
}
