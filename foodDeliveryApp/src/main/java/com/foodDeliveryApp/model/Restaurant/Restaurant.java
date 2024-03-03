package com.foodDeliveryApp.model.Restaurant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodDeliveryApp.model.Address.Address;
import com.foodDeliveryApp.model.ContactInformation.ContactInformation;
import com.foodDeliveryApp.model.Food.Food;
import com.foodDeliveryApp.model.Order.Order;
import com.foodDeliveryApp.model.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User owner;

    private String name;
    private String description;
    private String cuisineType;

    @OneToOne
    private Address address;

    private ContactInformation contactInformation;

    private String openingHours;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @ElementCollection
    @Column(length = 1000)
    private List<String> images;

    private LocalDate registrationDate;
    private boolean open;
    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Food> foods;
}
