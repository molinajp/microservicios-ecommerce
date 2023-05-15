package com.undefined.sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.undefined.sales.model.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findSalesByClientId(Long id);
}
