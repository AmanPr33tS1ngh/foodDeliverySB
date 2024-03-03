package com.foodDeliveryApp.model.Category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodDeliveryApp.model.Restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;
}
