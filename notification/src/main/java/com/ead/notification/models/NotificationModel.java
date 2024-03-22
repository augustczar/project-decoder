package com.ead.notification.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.ead.notification.enums.NotificationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_NOTIFICATIONS")
public class NotificationModel implements Serializable{

	private static final long serialVersionUID = 915676501761879335L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID notification;
	
	@Column(nullable = false)
	private UUID userId;
	
	@Column(nullable = false, length = 150)
	private String title;
	
	@Column(nullable = false)
	private String message;
	
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime creationDate;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private NotificationStatus notificationStatus;
	
}












