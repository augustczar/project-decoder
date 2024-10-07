package com.ead.payment.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.ead.payment.enums.PaymentControl;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "TB_PAYMENTS")
public class PaymentModel  implements Serializable{

	private static final long serialVersionUID = -7229122245902286505L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID paymentId;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentControl paymentControl;
	
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime paymentRequestDate;
	
	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime paymentCompletionDate;
	
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime paymentExpirationDate;
	
	@Column(nullable = false, length = 4)
	private String lastDigitsCreditCard;
	
	@Column(nullable = false)
	private BigDecimal valuePaid;
	
	@Column(length = 150)
	private String paymentMessage;
	
	@Column
	private boolean recurrence;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private UserModel user;
}
