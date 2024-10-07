package com.ead.payment.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ead.payment.dtos.PaymentCommandDto;
import com.ead.payment.dtos.PaymentRequestDto;
import com.ead.payment.enums.PaymentControl;
import com.ead.payment.models.CreditCardModel;
import com.ead.payment.models.PaymentModel;
import com.ead.payment.models.UserModel;
import com.ead.payment.publishers.PaymentCommandPublicher;
import com.ead.payment.repositories.CreditCardRepository;
import com.ead.payment.repositories.PaymentRepository;
import com.ead.payment.repositories.UserRepository;
import com.ead.payment.services.PaymentService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	CreditCardRepository creditCardRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	PaymentCommandPublicher paymentCommandPublicher;
	
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
		paymentModel.setLastDigitsCreditCard(paymentRequestDto.getCreditCardNumber()
				.substring(paymentRequestDto.getCreditCardNumber().length() - 4));
		paymentModel.setValuePaid(paymentRequestDto.getValuePaid());
		paymentModel.setUser(userModel);
		paymentRepository.save(paymentModel);
		
		try {
			var paymentCommandDto = new PaymentCommandDto();
			paymentCommandDto.setUserId(userModel.getUserId());
			paymentCommandDto.setPaymentId(paymentModel.getPaymentId());
			paymentCommandDto.setCardId(creditCarModel.getCardId());
			paymentCommandPublicher.publishPaymentCommand(paymentCommandDto);
		} catch (Exception e) {
			log.warn("Error send payment command!");
		}
		
		return paymentModel;
	}

	@Override
	public Optional<PaymentModel> findLastPaymentByUser(UserModel userModel) {
		
		return paymentRepository.findTopByUserOrderByPaymentRequestDateDesc(userModel);
	}

	@Override
	public Page<PaymentModel> findAllByUser(Specification<PaymentModel> spec, Pageable pageable) {
		return paymentRepository.findAll(spec, pageable);
	}

	@Override
	public Optional<PaymentModel> findPaymentByUser(UUID userId, UUID paymentId) {
		return paymentRepository.findByUserId(userId, paymentId);
	}
	
	

}
