package com.develcode.checkout.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.develcode.checkout.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}