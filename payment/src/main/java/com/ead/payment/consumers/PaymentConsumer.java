package com.ead.payment.consumers;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ead.payment.dtos.PaymentCommandDto;

@Component
public class PaymentConsumer {

	@RabbitListener(bindings = @QueueBinding(
		value = @Queue(value = "${ead.broker.queue.paymentCommandQueue.name}", durable = "true"),
		exchange = @Exchange(value = "${ead.broker.exchange.paymentCommandExchange}", 
		type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"), key = "${ead.broker.key.paymentCommandKey}"
	))
	public void listenPaymentEvent(@Payload PaymentCommandDto paymentCommandDto) {
		System.out.println(paymentCommandDto);
	}
}
