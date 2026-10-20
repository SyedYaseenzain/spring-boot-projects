package com.blinkit.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blinkit.entity.OrderEntity;
import com.blinkit.entity.PaymentEntity;
import com.blinkit.payment.repository.PaymentRepository;
import com.blinkit.repository.OrderRepository;
import com.blinkit.request.OrderRequest;
import com.blinkit.response.OrderResponse;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	PaymentRepository paymentRepository;

	@Transactional
	public OrderResponse acceptOrder(OrderRequest orderRequest) {
		System.out.println("OrderService.acceptOrder() :::::::::::::::::::::::::::::: START");

		OrderEntity entity = new OrderEntity();

		entity.setCustomerId(orderRequest.getCustomerId());
		entity.setPaymentMethod(orderRequest.getPaymentMethod());
		entity.setProductId(orderRequest.getProductId());
		entity.setQuantity(orderRequest.getQuantity());
		entity.setShippingAdress(orderRequest.getShippingAdress());

		// Bizz logic
		entity.setOrderId(generateOrderID());

		// Creating the Order. #instruction 1
		OrderEntity responseEntity = orderRepository.save(entity);

		// Conforming the payment #instruction 2
		PaymentEntity paymentEntity = new PaymentEntity();
		int amount = generateAmount();
		paymentEntity.setAmount(amount);
		paymentEntity.setPaymentRef(generatePaymentRef());
		paymentEntity.setStatus("PAID");
		
		//Creating a scenario
		String something = null;
		
		OrderResponse orderResponse = new OrderResponse();


		PaymentEntity responsePaymentEntity = paymentRepository.save(paymentEntity);
		
		if(something.equals("nothing")) //NPE
		{
			System.out.println("This is DUMMY.....");
		}

		if (responsePaymentEntity.getId() > 0) {
			orderResponse.setOrderId(responseEntity.getOrderId());
			orderResponse.setCustomerId(responseEntity.getCustomerId());
			orderResponse.setTotalAmount(amount);
			orderResponse.setPaymentStatus("SUCCESS");
			orderResponse.setOrderStatus("PLACED");
		} else {

			orderResponse.setOrderId(responseEntity.getOrderId());
			orderResponse.setCustomerId(responseEntity.getCustomerId());
			orderResponse.setTotalAmount(amount);
			orderResponse.setPaymentStatus("FAILED");
			orderResponse.setOrderStatus("ON HOLD");
		}
		System.out.println("OrderService.acceptOrder() :::::::::::::::::::::::::::::: END");
		
		return orderResponse;
	}

	private int generateOrderID() {
		Random random = new Random();
		int id = 1000 + random.nextInt(9000);
		return id;
	}

	private int generateAmount() {
		Random random = new Random();
		int amount = 1000 + random.nextInt(9000);
		return amount;
	}

	private String generatePaymentRef() {
		Random random = new Random();
		String ref = "REF " + 100 + random.nextInt(900);
		return ref;
	}
}
