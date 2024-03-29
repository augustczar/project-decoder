package com.ead.authuser.clients;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.ResponsePageDto;
import com.ead.authuser.service.UtilsService;
import com.fasterxml.jackson.annotation.JsonIdentityReference;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@JsonIdentityReference
public class CourseClient {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	UtilsService utilsService;
	
	@Value("${ead.api.url.course}")
	String REQUEST_URL_COURSE;

	private List<CourseDto> searchResult = null;
	
	//@Retry(name = "retryInstance", fallbackMethod = "retryFallBack")
	@CircuitBreaker(name = "circuitbreakerInstance")
	public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable){

		String url = REQUEST_URL_COURSE + utilsService.createUrlGetAllCoursesByUser(userId, pageable);	
				
		log.debug("Request URL: {}", url);
		log.info("Request URL: {}", url);
		
		try {
			ParameterizedTypeReference<ResponsePageDto<CourseDto>> 
			responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {};
					ResponseEntity<ResponsePageDto<CourseDto>> 
					result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
					searchResult = result.getBody().getContent();
					
					log.debug("Response Number of Elements: {}", searchResult.size());
			
		} catch (HttpStatusCodeException e) {
			log.error("Error request /courses: {}", e);
		}
		log.info("Ending request /courses userId: {}", userId);
		return new PageImpl<>(searchResult);
	}
	
	public Page<CourseDto> circuitBreakerFallBack(UUID userId, Pageable pageable, Throwable t){
		log.error("Inside circuit Breaker FallBack, couse - {}", t.toString());
		List<CourseDto> searchResult = new ArrayList<>();
		return new PageImpl<>(searchResult);
	}
	
	public Page<CourseDto> retryFallBack(UUID userId, Pageable pageable, Throwable t){
		log.error("Inside retry retryFallback, couse - {}", t.toString());
		List<CourseDto> searchResult = new ArrayList<>();
		return new PageImpl<>(searchResult);
	}
}