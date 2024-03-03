package com.foodDeliveryApp.model.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodDeliveryApp.model.Address.Address;
import com.foodDeliveryApp.model.Restaurant.Restaurant;
import com.foodDeliveryApp.model.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;
    private Long totalAmount;
    private String orderStatus;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @ManyToOne
    private User customer;

    @ManyToOne
    private Address address;

    private Long totalPrice;
    private int totalItem;

    @OneToMany
    private List<OrderItem> orderItems;

}
