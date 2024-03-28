package com.ead.notification.services.impl;

import org.springframework.stereotype.Service;

import com.ead.notification.models.NotificationModel;
import com.ead.notification.repositories.NotificationRepository;
import com.ead.notification.services.NotificationService;

@Service
public class NotificatificationServiceImpl implements NotificationService {

	
	final NotificationRepository notificationRepository;

	public NotificatificationServiceImpl(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Override
	public NotificationModel saveNotification(NotificationModel notificationModel) {
		return notificationRepository.save(notificationModel);
	}
	
	
}
