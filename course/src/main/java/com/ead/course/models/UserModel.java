package com.ead.course.models;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
@Table(name = "TB_USERS")
public class UserModel implements Serializable {

	private static final long serialVersionUID = -813218012932319291L;

	@Id
	private UUID userId;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@Column(nullable = false, length = 150)
	private String fullName;
	
	@Column(nullable = false)
	private String usersStatus;
	
	@Column(nullable = false)
	private String userType;
	
	@Column(length = 11)
	private String cpf;
	
	@Column
	private String imageUrl;

	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<CourseModel> courses;
}
