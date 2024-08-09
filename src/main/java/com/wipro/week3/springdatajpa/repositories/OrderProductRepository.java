package com.wipro.week3.springdatajpa.repositories;

import com.wipro.week3.springdatajpa.entities.OrderProduct;
import com.wipro.week3.springdatajpa.entities.OrderProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {
}