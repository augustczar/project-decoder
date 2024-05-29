package com.ead.authuser.dtos;

import java.io.Serializable;

import io.github.resilience4j.core.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class JwtDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NonNull
	private String token;
	
	@NotBlank
	private String type = "Bearer";
	
	public JwtDto(String token) {
		this.token = token;
	}
}
