package com.ead.authuser.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.RepresentationModel;

import com.ead.authuser.dtos.UserEventDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TB_USERS")
@Entity
public class UserModel extends RepresentationModel<UserModel> implements Serializable {

	private static final long serialVersionUID = 969341758235417006L;

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID userId;
	
	@Column(nullable = false, unique = true, length = 50)
	private String userName;
	
	@Column(nullable = false, unique = true, length = 50)
	private String email;
	
	@Column(nullable = false, length = 255)
	@JsonIgnore
	private String password;
	
	@Column(nullable = false, length = 150)
	private String fullName;
	
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;
	
	@Enumerated(EnumType.STRING)
	private UserType userType;
	
	@Column(length = 20)
	private String phoneNumber;
	
	@Column(length = 20)
	private String cpf;
	
	@Column
	private String imageUrl;
	
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime creationDate;
	
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime lastUpdateDate;

	public UserEventDto convertToUserEventDto() {
		var userEventDto = new UserEventDto();
		BeanUtils.copyProperties(this, userEventDto);
		userEventDto.setUserType(this.getUserType().toString());
		userEventDto.setUserStatus(getUserStatus().toString());
		return userEventDto;
	}
}
