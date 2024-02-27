package com.foodDeliveryApp.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
