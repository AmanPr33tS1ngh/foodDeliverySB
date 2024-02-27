package com.foodDeliveryApp.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private int price;
    @ManyToOne
    private Category category;
    @Column(length = 1000)
    @ElementCollection
    private List<String> images;
    @ManyToOne
    private Restaurant restaurant;
    private boolean isAvailable;
    private boolean isSeasonal;
    @ManyToMany
    private List<Ingredient> ingredients;
    private Date creationAt;
}
