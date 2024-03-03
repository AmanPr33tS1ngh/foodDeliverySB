package com.foodDeliveryApp.service.FoodService;

import com.foodDeliveryApp.Repository.FoodRepo.FoodRepository;
import com.foodDeliveryApp.Request.FoodReq.CreateFoodReq;
import com.foodDeliveryApp.model.Category.Category;
import com.foodDeliveryApp.model.Food.Food;
import com.foodDeliveryApp.model.Restaurant.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImp implements FoodService{
    @Autowired
    FoodRepository foodRepository;
    @Override
    public Food createFood(CreateFoodReq req, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setFoodCategory(req.getCategory());
        food.setDescription(req.getDescription());
        food.setRestaurant(restaurant);
        food.setImages(req.getImages());
        food.setPrice(req.getPrice());
        food.setName(req.getName());
        food.setSeasonal(req.isSeasonal());
        food.setVegetarian(req.isVegetarian());
        food.setIngredients(req.getIngredientItems());

        Food savedFood = this.foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = this.getFood(foodId);
        food.setRestaurant(null);
        this.foodRepository.save(food);
    }

    @Override
    public List<Food> getRestaurantFood(Long restaurantId, boolean isVegetarian,boolean isNonVegetarian,
                                        boolean isSeasonal, String foodCategory) {
        List<Food> foods = this.foodRepository.findByRestaurantId(restaurantId);
        if(isVegetarian){
            foods = this.filterByVegetarian(foods, isVegetarian);
        }
        if(isNonVegetarian){
            foods = this.filterByNonVegetarian(foods, isNonVegetarian);
        }
        if(isSeasonal){
            foods = this.filterBySeasonal(foods, isSeasonal);
        }
        if(foodCategory!=null && !foodCategory.equals("")){
            foods = this.filterByCategory(foods, foodCategory);
        }
        return foods;
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegetarian).collect(Collectors.toList());
    }
    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }
    private List<Food> filterByNonVegetarian(List<Food> foods, boolean isNonVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian() != isNonVegetarian).collect(Collectors.toList());
    }
    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getFoodCategory() != null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Food> getFoodByKeyword(String keyword) {
        return this.foodRepository.searchFood(keyword);
    }

    @Override
    public Food getFood(Long foodId) throws Exception {
        Optional<Food> food = this.foodRepository.findById(foodId);
        if (food.isEmpty())throw new Exception("food not found");
        return food.get();
    }

    @Override
    public Food updateFoodAvailability(Long foodId) throws Exception {
        Food food = this.getFood(foodId);
        food.setAvailable(!food.isAvailable());
        return this.foodRepository.save(food);
    }
}
