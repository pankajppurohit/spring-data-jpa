package com.wipro.week3.springdatajpa.repositories;

import com.wipro.week3.springdatajpa.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}