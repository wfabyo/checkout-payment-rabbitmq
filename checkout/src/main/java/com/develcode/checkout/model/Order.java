package com.develcode.checkout.model;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @UuidGenerator
    private UUID id;
    private String customerName;
    private BigDecimal amount;
    @Enumerated private Status status;
    
    @Getter
    @AllArgsConstructor
    public enum Status {
        PENDING, PAID, CANCELLED
    }
}