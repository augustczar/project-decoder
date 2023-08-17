package com.ead.authuser.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
public class UserModel implements Serializable {

	private static final long serialVersionUID = 969341758235417006L;

	@Id
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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime creationDate;
	
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime lastUpdateDate;

	public UserModel() {
	}

	public UserModel(UUID userId, String userName, String email, String password, String fullName,
			UserStatus userStatus, UserType userType, String phoneNumber, String cpf, String imageUrl,
			LocalDateTime creationDate, LocalDateTime lastUpdateDate) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.userStatus = userStatus;
		this.userType = userType;
		this.phoneNumber = phoneNumber;
		this.cpf = cpf;
		this.imageUrl = imageUrl;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
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

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Override
	public String toString() {
		return "UserModel [userId=" + userId + ", userName=" + userName + ", email=" + email + ", password=" + password
				+ ", fullName=" + fullName + ", userStatus=" + userStatus + ", userType=" + userType + ", phoneNumber="
				+ phoneNumber + ", cpf=" + cpf + ", imageUrl=" + imageUrl + ", creationDate=" + creationDate
				+ ", lastUpdateDate=" + lastUpdateDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, creationDate, email, fullName, password, userId, userName, userStatus, userType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserModel other = (UserModel) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(creationDate, other.creationDate)
				&& Objects.equals(email, other.email) && Objects.equals(fullName, other.fullName)
				&& Objects.equals(password, other.password) && Objects.equals(userId, other.userId)
				&& Objects.equals(userName, other.userName) && userStatus == other.userStatus
				&& userType == other.userType;
	}
		
}
