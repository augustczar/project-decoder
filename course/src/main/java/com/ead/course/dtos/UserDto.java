package com.ead.course.dtos;

import java.util.UUID;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.ead.course.enums.UserStatus;
import com.ead.course.enums.UserType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserDto {
	
	private UUID userId;
	
	private String userName;
	
	private String email;
	
	private String fullName;
	
	private UserStatus userStatus;
	
	private UserType userType;
	
	private String phoneNumber;
	
	private String cpf;
	
	private String imageUrl;
	
}
