package com.ead.payment.dtos;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class PaymentRequestDto {

	@NotNull
	@DecimalMin(value ="0.0", inclusive = false)
	@Digits(integer = 5, fraction = 2)
	private BigDecimal valuePaid;
	
	@NotBlank	
	private String cardHolderFullName;
	
	@NotBlank
	@CPF
	private String cardHolderCpf;
	
	@NotBlank
	@Size(min = 16, max = 20)
	private String creditCardNumber;
	
	@NotBlank
	@Size(min = 4, max = 10)
	private String expirationDate;
	
	@NotBlank
	@Size(min = 3, max = 3)
	private String cvvCode;
	
}
