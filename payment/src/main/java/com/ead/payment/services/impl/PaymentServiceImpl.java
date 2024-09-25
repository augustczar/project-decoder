package com.ead.payment.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.payment.dtos.PaymentRequestDto;
import com.ead.payment.enums.PaymentControl;
import com.ead.payment.models.CreditCardModel;
import com.ead.payment.models.PaymentModel;
import com.ead.payment.models.UserModel;
import com.ead.payment.repositories.CreditCardRepository;
import com.ead.payment.repositories.PaymentRepository;
import com.ead.payment.repositories.UserRepository;
import com.ead.payment.services.PaymentService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	CreditCardRepository creditCardRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Transactional	
	@Override
	public PaymentModel requestPayment(@Valid PaymentRequestDto paymentRequestDto, UserModel userModel) {
		var creditCarModel = new CreditCardModel();
		var creditCardModelOptional = creditCardRepository.findByUser(userModel);
		
		if(creditCardModelOptional.isPresent()) {
			creditCarModel = creditCardModelOptional.get();
		}
		
		BeanUtils.copyProperties(paymentRequestDto, creditCarModel);
		creditCarModel.setUser(userModel);
		creditCardRepository.save(creditCarModel);
		
		var paymentModel = new PaymentModel();
		paymentModel.setPaymentControl(PaymentControl.REQUESTED);
		paymentModel.setPaymentRequestDate(LocalDateTime.now(ZoneId.of("UTC")));
		paymentModel.setPaymentExpirationDate(LocalDateTime.now(ZoneId.of("UTC")).plusDays(30));
		paymentModel.setLastDigitsCreditCatd(paymentRequestDto.getCreditCardNumber()
				.substring(paymentRequestDto.getCreditCardNumber().length() - 4));
		paymentModel.setValuePaid(paymentRequestDto.getValuePaid());
		paymentModel.setUser(userModel);
		paymentRepository.save(paymentModel);
		
		
		// send reuqest to queue
		return paymentModel;
	}

	@Override
	public Optional<PaymentModel> findLastPaymentByUser(UserModel userModel) {
		
		return paymentRepository.findTopByUserOrderByPaymentRequestDateDesc(userModel);
	}
	
	

}
