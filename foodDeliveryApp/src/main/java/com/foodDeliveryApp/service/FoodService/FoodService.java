package com.foodDeliveryApp.service.FoodService;

import com.foodDeliveryApp.Request.FoodReq.CreateFoodReq;
import com.foodDeliveryApp.model.Category.Category;
import com.foodDeliveryApp.model.Food.Food;
import com.foodDeliveryApp.model.Restaurant.Restaurant;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodReq req, Category category, Restaurant restaurant);
    public void deleteFood(Long foodId) throws Exception;
    public List<Food> getRestaurantFood(Long restaurantId, boolean isVegetarian, boolean isNonVegetarian, boolean isSeasonal, String foodCategory);
    public List<Food> getFoodByKeyword(String keyword);
    public Food getFood(Long foodId) throws Exception;
    public Food updateFoodAvailability(Long foodId) throws Exception;
}
