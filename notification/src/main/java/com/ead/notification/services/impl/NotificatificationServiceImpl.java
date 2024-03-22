package com.ead.notification.services.impl;

import org.springframework.stereotype.Service;

import com.ead.notification.repositories.NotificationRepository;
import com.ead.notification.services.NotificationService;

@Service
public class NotificatificationServiceImpl implements NotificationService {

	
	final private NotificationRepository notificationRepository;

	public NotificatificationServiceImpl(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}
	
	
}
