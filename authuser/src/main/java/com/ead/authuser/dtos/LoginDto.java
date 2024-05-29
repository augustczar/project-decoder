package com.ead.authuser.dtos;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto implements Serializable {
	
	private static final long serialVersionUID = -3225921621976426471L;

	@NotBlank
	private String userName;
	
	@NotBlank
	private String password;
}
