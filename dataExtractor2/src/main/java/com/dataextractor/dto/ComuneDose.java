package com.dataextractor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the vaccination data for a specific municipality. This is a DTO
 * that will be sent via a Kafka Topic.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComuneDose {

	private String codice;
	private String comune;
	private String provincia;
	private String sigla;
	private int dose1;
	private int dose2;
	private int booster;
	private int richiamo;

}
