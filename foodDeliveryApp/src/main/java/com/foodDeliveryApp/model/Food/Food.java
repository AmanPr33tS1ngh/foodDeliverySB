package com.foodDeliveryApp.model.Food;

import com.foodDeliveryApp.model.Category.Category;
import com.foodDeliveryApp.model.Ingredient.IngredientItem;
import com.foodDeliveryApp.model.Restaurant.Restaurant;
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
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private String name;
    private Long price;

    @ManyToOne
    private Category foodCategory;

    @Column(length = 1000)
    @ElementCollection
    private List<String> images;

    @ManyToOne
    private Restaurant restaurant;

    private boolean isAvailable;
    private boolean isSeasonal;
    private boolean isVegetarian;

    @ManyToMany
    private List<IngredientItem> ingredients;
    private Date creationAt;
}
