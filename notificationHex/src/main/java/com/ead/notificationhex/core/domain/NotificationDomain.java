package com.ead.notificationhex.core.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.ead.notificationhex.core.domain.enuns.NotificationStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificationDomain implements Serializable{

	private static final long serialVersionUID = 915676501761879335L;

	private UUID notificationId;
	
	private UUID userId;
	
	private String title;
	
	private String message;
	
	private LocalDateTime creationDate;
	
	private NotificationStatus notificationStatus;
	
}