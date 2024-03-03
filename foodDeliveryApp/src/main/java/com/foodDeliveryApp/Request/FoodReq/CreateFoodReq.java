package com.foodDeliveryApp.Request.FoodReq;

import com.foodDeliveryApp.model.Ingredient.IngredientItem;
import lombok.Data;
import com.foodDeliveryApp.model.Category.Category;

import java.util.List;

@Data
public class CreateFoodReq {
    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images;

    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientItem> ingredientItems;
}
