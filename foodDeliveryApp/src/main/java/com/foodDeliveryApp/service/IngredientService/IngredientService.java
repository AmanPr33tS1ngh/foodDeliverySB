package com.foodDeliveryApp.service.IngredientService;

import com.foodDeliveryApp.model.Ingredient.IngredientCategory;
import com.foodDeliveryApp.model.Ingredient.IngredientItem;

import java.util.List;

public interface IngredientService {
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;
    public List<IngredientCategory> findIngredientCategoriesByRestaurantId(Long restaurantId) throws Exception;
    public IngredientItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;
    public IngredientItem updateStock(Long id) throws Exception;
    public List<IngredientItem> findRestaurantIngredientItems(Long restaurantId) throws Exception;

}