package com.ead.authuser.configs;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RabbitMqConfig {

	@Autowired
	CachingConnectionFactory cachingConnectionFactory;
	
	@Bean
	RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter());
		return rabbitTemplate;
	}

	@Bean
	Jackson2JsonMessageConverter messageConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		return new Jackson2JsonMessageConverter(objectMapper);
	}
}
