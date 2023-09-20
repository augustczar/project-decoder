package com.ead.course.configs;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * Utilizar o ObjectMapper quando for necessário a configuração de data global 
 * para utilização do sistema 
 */
//@Configuration
public class DateConfig {

	public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static LocalDateTimeSerializer LOCALDATETIMESERIALIZER = new LocalDateTimeSerializer(DateTimeFormatter
			.ofPattern(DATETIME_FORMAT));

    @Bean
    @Primary
    ObjectMapper objectMapper() {
		JavaTimeModule module = new JavaTimeModule();
		module.addSerializer(LOCALDATETIMESERIALIZER);
		return new ObjectMapper()
				.registerModule(module);
	}
}

