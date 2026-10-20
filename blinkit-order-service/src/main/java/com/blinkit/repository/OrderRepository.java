package com.blinkit.repository;

import org.springframework.data.repository.CrudRepository;

import com.blinkit.entity.OrderEntity;

public interface OrderRepository extends CrudRepository<OrderEntity, Integer> 
{
	
}
