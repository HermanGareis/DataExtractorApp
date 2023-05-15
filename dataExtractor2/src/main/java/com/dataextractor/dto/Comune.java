package com.dataextractor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Comune class represents a municipality with its code, name, abbreviation
 * and region. It is used to retrieve data from an API.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comune {

	private String codice;
	private String nome;
	private String sigla;
	private String regione;
}
