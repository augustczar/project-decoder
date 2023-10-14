package com.ead.authuser.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

public interface UtilsService {
	
	String createUrl(UUID userId, Pageable pageable) ;

}
