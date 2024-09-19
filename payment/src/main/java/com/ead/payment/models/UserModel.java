package com.ead.payment.models;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
public class UserModel implements Serializable{

	private static final long serialVersionUID = -5682666644000262824L;

	@Id
	private UUID userId;
	
	@Column(nullable = false, unique = true, length = 50)
	private String email;
	
	@Column(nullable = false, length = 150)
	private String fullName;
	
	@Column(nullable = false)
	private String userStatus;
	
	@Column(nullable = false)
	private String userType;
	
	@Column(length = 20)
	private String cpf;
	
	@Column(length = 20)
	private String phoneNumber;
}
