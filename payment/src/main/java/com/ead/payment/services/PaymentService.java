package com.ead.payment.services;

import java.util.Optional;

import com.ead.payment.dtos.PaymentRequestDto;
import com.ead.payment.models.PaymentModel;
import com.ead.payment.models.UserModel;

import jakarta.validation.Valid;

public interface PaymentService {

	PaymentModel requestPayment(@Valid PaymentRequestDto paymentRequestDto, UserModel userModel);

	Optional<PaymentModel> findLastPaymentByUser(UserModel userModel);

}
