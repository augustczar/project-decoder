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

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", userName=" + userName + ", email=" + email + ", password=" + password
				+ ", oldPassword=" + oldPassword + ", fullName=" + fullName + ", phoneNumber=" + phoneNumber + ", cpf="
				+ cpf + ", imageUrl=" + imageUrl + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, email, imageUrl, oldPassword, password, userId, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(email, other.email)
				&& Objects.equals(imageUrl, other.imageUrl) && Objects.equals(oldPassword, other.oldPassword)
				&& Objects.equals(password, other.password) && Objects.equals(userId, other.userId)
				&& Objects.equals(userName, other.userName);
	}
	
}
