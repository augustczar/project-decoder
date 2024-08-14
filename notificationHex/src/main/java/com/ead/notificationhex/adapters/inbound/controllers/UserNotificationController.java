package com.ead.notificationhex.adapters.inbound.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.notificationhex.adapters.dtos.NotificationDto;
import com.ead.notificationhex.core.domain.NotificationDomain;
import com.ead.notificationhex.core.domain.PageInfo;
import com.ead.notificationhex.core.ports.NotificationServicePort;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserNotificationController {

	private NotificationServicePort notificationServicePort;
	
	private PageInfo pageInfo;
	
	public UserNotificationController(NotificationServicePort notificationServicePort) {
		this.notificationServicePort = notificationServicePort;
	}
	
	@PreAuthorize("hasAnyRole('STUNDENT')")
	@GetMapping("/{userId}/notifications")
	public ResponseEntity<Page<NotificationDomain>> getAllNotificationsByUser(@PathVariable(value = "userId") UUID userId,
			@PageableDefault(page = 0, size = 10, sort = "notificationId", direction = Sort.Direction.ASC) Pageable pageable,
			Authentication autnetication){
	
		pageInfo = new PageInfo();
		BeanUtils.copyProperties(pageable, pageInfo);
		List<NotificationDomain> notificationDomainList = notificationServicePort.findAllNotificationsByUser(userId, pageInfo);
		return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<NotificationDomain>(notificationDomainList, pageable, notificationDomainList.size()));
	}
	
	@PreAuthorize("hasAnyRole('STUNDENT')")
	@PutMapping("/{userId}/notifications/{notificationId}")
	public ResponseEntity<Object> updateNotificationModel(@PathVariable(value = "userId") UUID userId,
			@PathVariable(value = "notificationId") UUID notificationId, @RequestBody @Valid NotificationDto notificationDto){
		
		Optional<NotificationDomain> notificationModelOptional = notificationServicePort.findByNotificationIdAndUserId(notificationId, userId);
		if (notificationModelOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found!");
		}
		notificationModelOptional.get().setNotificationStatus(notificationDto.getNotificationStatus());
		notificationServicePort.saveNotification(notificationModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body(notificationModelOptional.get());
	}
}
