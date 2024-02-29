package com.ead.course.dtos;

import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.ead.course.models.UserModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserEventDto {

	private UUID userId;
	private String userName;
	private String email;
	private String fullName;
	private String userStatus;
	private String userType;
	private String phoneNumber;
	private String cpf;
	private String imageUrl;
	private String actionType;
	
	public UserModel convertToUserModel() {
		var userModel = new UserModel();
		BeanUtils.copyProperties(this, userModel);
		return userModel;
	}
}
