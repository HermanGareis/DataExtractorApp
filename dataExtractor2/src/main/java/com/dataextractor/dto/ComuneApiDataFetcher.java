package com.dataextractor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ComuneApiDataFetcher is a class that represents a data model of the Comune
 * vaccination API response
 * 
 * It contains fields for the Comune data and the number of vaccine doses
 * administered in the corresponding Comune.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComuneApiDataFetcher {

	private String codistat_comune_dom;
	private String comune_dom;
	private String provincia_dom;
	private String tot_solo_dose_1;
	private String tot_dose_2_unica;
	private String tot_dose_addizionale_booster;
	private String tot_dose_richimm_rich2_;

}
