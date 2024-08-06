package com.ead.authuser.configs;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RestTemplateConfig {

	static final int TIMEOUT = 5000;
	
    @LoadBalanced
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder, ObjectMapper objectMapper) {
        // Configurando o ObjectMapper com o serializador customizado para PageImpl
        SimpleModule module = new SimpleModule();
        module.addSerializer(new PageImplSerializer());
        objectMapper.registerModule(module);
        
        // Registrar outros módulos se necessário
        objectMapper.registerModule(new JavaTimeModule());

        return builder
                .setConnectTimeout(Duration.ofMillis(TIMEOUT))
                .setReadTimeout(Duration.ofMillis(TIMEOUT))
                .additionalMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }
}
