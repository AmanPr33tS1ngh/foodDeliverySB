package com.foodDeliveryApp.Repository.IngredientRepo;

import com.foodDeliveryApp.model.Ingredient.IngredientCategory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {
    List<IngredientCategory> findByRestaurantId(Long id);
}
