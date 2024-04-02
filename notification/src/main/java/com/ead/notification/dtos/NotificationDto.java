package com.ead.notification.dtos;

import java.io.Serializable;

import com.ead.notification.enums.NotificationStatus;

import jakarta.validation.constraints.NotNull;
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
public class NotificationDto implements Serializable{

	private static final long serialVersionUID = 3813845630892461902L;

	@NotNull
	private NotificationStatus notificationStatus;
}
