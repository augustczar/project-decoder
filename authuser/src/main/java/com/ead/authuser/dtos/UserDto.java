package com.ead.authuser.dtos;

import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
	
	/**
	 * Criacao do jsonView em seus atributos e 
	 * implementacao de interface
	 * para diferentes visoes do usuario no registro e na
	 * atualizacao dos dados sem a nescecidade de criacao 
	 * de mais dtos para updade na data base
	 */
	public interface UserView{
		public static interface RegistrationPost{}
		public static interface UserPut{}
		public static interface PasswordPut{}
		public static interface ImagePut{}
	}

	private UUID userId;

	@JsonView(UserView.RegistrationPost.class)
	private String userName;
	
	@JsonView(UserView.RegistrationPost.class)
	private String email;
	
	@JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
	private String password;
	
	@JsonView(UserView.PasswordPut.class)
	private String oldPassword; 
	
	@JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
	private String fullName;
	
	@JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
	private String phoneNumber;
	
	@JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
	private String cpf;
	
	@JsonView(UserView.ImagePut.class)
	private String imageUrl;

	public UserDto() {
	}

	public UserDto(UUID userId, String userName, String email, String password, String oldPassword, String fullName,
			String phoneNumber, String cpf, String imageUrl) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.oldPassword = oldPassword;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.cpf = cpf;
		this.imageUrl = imageUrl;
	}

}
