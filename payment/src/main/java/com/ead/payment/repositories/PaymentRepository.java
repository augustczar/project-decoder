package com.ead.payment.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ead.payment.models.PaymentModel;
import com.ead.payment.models.UserModel;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentModel, UUID>, 
	JpaSpecificationExecutor<PaymentModel>{

	Optional<PaymentModel> findTopByUserOrderByPaymentRequestDateDesc(UserModel userModel);
	
	@Query(value = "SELECT * FROM tb_payments WHERE user_user_id = :userId and payment_Id = :paymentId", nativeQuery = true)
	Optional<PaymentModel> findByUserId(@Param("userId") UUID userId, @Param("paymentId") UUID paymentId);
}
