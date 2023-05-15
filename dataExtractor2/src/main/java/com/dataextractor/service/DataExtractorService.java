package com.dataextractor.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dataextractor.config.ApplicationConfig;
import com.dataextractor.config.KafkaConfig;
import com.dataextractor.dto.Comune;
import com.dataextractor.dto.ComuneApiDataFetcher;
import com.dataextractor.dto.ComuneDose;

@Service
public class DataExtractorService {

	@Autowired
	private KafkaConfig kafkaConfig;

	@Autowired
	private ApplicationConfig applicationConfig;

	@Autowired
	private KafkaTemplate<String, ComuneDose> kafkaTemplate;

	/**
	 * Retrieves a map of Comune names and their corresponding province codes from a
	 * remote REST API.
	 * 
	 * The map is built from the data returned by the API, and the Comune objects
	 * are transformed into
	 * 
	 * key-value pairs using the Comune name as the key and the province code as the
	 * value.
	 * 
	 * @return a Map object representing the Comune-Province mapping.
	 * 
	 * @throws RestTemplateException if there is an issue with the REST API call or
	 *                               the response cannot be processed.
	 */
	private Map<String, String> getComuneProvinceMap() {

		String url = applicationConfig.urlProvince;

		RestTemplate restTemplate = new RestTemplate();
		Comune[] comunes = restTemplate.getForObject(url, Comune[].class);

		Map<String, String> comunesMap = Arrays.asList(comunes).stream()
				.collect(Collectors.toMap(comune -> comune.getNome().toUpperCase(), Comune::getSigla));

		return comunesMap;
	}

	/**
	 * 
	 * Retrieves the list of {@link ComuneApiDataFetcher} instances by fetching data
	 * from the external Comune API and mapping them to {@link ComuneDose}
	 * instances. The mapping process also involves mapping the name of each comune
	 * to its corresponding province abbreviation by using the
	 * {@code getComuneProvinceMap} method.
	 * 
	 * @return the list of {@link ComuneDose} instances and a Map object
	 *         representing the Comune-Province mapping.
	 */
	public List<ComuneDose> getComuneDoseList() {

		Map<String, String> comuneMap = getComuneProvinceMap();

		String url = applicationConfig.urlDosi;

		RestTemplate restTemplate = new RestTemplate();
		ComuneApiDataFetcher[] comuneAPIList = restTemplate.getForObject(url, ComuneApiDataFetcher[].class);

		return transferComuneData(comuneAPIList, comuneMap);
	}

	/**
	 * Transfer data from ComuneApiDataFetcher to ComuneDose list.
	 * 
	 * @param comuneAPIData array of ComuneApiDataFetcher objects
	 * @param comuneMap     map of comune and province
	 * @return List of ComuneDose objects
	 */
	public List<ComuneDose> transferComuneData(ComuneApiDataFetcher[] comuneAPIData, Map<String, String> comuneMap) {

		return Arrays.stream(comuneAPIData).map(comuneApiDataFetcher -> {
			ComuneDose comuneDose = new ComuneDose();
			comuneDose.setCodice(comuneApiDataFetcher.getCodistat_comune_dom());
			comuneDose.setComune(comuneApiDataFetcher.getComune_dom());
			comuneDose.setProvincia(comuneApiDataFetcher.getProvincia_dom());
			comuneDose.setDose1(Integer.parseInt(comuneApiDataFetcher.getTot_solo_dose_1()));
			comuneDose.setDose2(Integer.parseInt(comuneApiDataFetcher.getTot_dose_2_unica()));
			comuneDose.setBooster(Integer.parseInt(comuneApiDataFetcher.getTot_dose_addizionale_booster()));
			comuneDose.setRichiamo(Integer.parseInt(comuneApiDataFetcher.getTot_dose_richimm_rich2_()));
			comuneDose.setSigla(comuneMap.get(comuneApiDataFetcher.getProvincia_dom()));
			return comuneDose;
		}).collect(Collectors.toList());
	}

	/**
	 * Sends the comune dose records to Kafka topic
	 * 
	 * @return a list of ComuneDose objects that were sent
	 */
	public List<ComuneDose> sendRecords() {

		List<ComuneDose> comuneDoseList = getComuneDoseList();

		comuneDoseList.stream()
				.forEach(comune -> kafkaTemplate.send(kafkaConfig.topicName, comune.getCodice(), comune));

		return comuneDoseList;
	}

}
