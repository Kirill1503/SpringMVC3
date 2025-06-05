package com.example.springmvc3.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Column(nullable = false)
    private LocalDate orderDate;
    @Column(nullable = false)
    private String shippingAddress;
    @Column(nullable = false)
    private BigDecimal totalPrice;
    @Column(nullable = false)
    private String orderStatus;
    @JoinColumn(nullable = false)
    @ManyToOne
    private Customer customer;
    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;
}
