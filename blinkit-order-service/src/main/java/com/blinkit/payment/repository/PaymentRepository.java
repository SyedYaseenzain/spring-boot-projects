package com.blinkit.payment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.blinkit.entity.PaymentEntity;


@Repository
public interface PaymentRepository extends CrudRepository<PaymentEntity, Long>{

}
