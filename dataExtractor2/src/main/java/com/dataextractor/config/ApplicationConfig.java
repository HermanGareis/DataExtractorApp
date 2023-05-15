package com.dataextractor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the application.
 */
@Configuration
public class ApplicationConfig {

	@Value("${url.province}")
	public String urlProvince;

	@Value("${url.dosi}")
	public String urlDosi;

}
