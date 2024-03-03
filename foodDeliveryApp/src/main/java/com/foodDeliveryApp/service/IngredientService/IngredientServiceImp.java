package com.foodDeliveryApp.service.IngredientService;

import com.foodDeliveryApp.Repository.IngredientRepo.IngredientCategoryRepository;
import com.foodDeliveryApp.Repository.IngredientRepo.IngredientItemRepository;
import com.foodDeliveryApp.model.Ingredient.IngredientCategory;
import com.foodDeliveryApp.model.Ingredient.IngredientItem;
import com.foodDeliveryApp.model.Restaurant.Restaurant;
import com.foodDeliveryApp.service.RestaurantService.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class IngredientServiceImp implements IngredientService{
    @Autowired
    IngredientCategoryRepository ingredientCategoryRepository;
    @Autowired
    IngredientItemRepository ingredientItemRepository;
    @Autowired
    RestaurantService restaurantService;
    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = this.restaurantService.getRestaurant(restaurantId);
        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setRestaurant(restaurant);
        ingredientCategory.setName(name);
        return this.ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> ingredientCategory = this.ingredientCategoryRepository.findById(id);
        if(ingredientCategory.isEmpty())throw new Exception("Ingredient category not found!");
        return ingredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoriesByRestaurantId(Long restaurantId) throws Exception {
        return this.ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = this.restaurantService.getRestaurant(restaurantId);
        IngredientCategory ingredientCategory = this.findIngredientCategoryById(categoryId);
        IngredientItem ingredientItem = new IngredientItem();
        ingredientItem.setName(ingredientName);
        ingredientItem.setRestaurant(restaurant);
        ingredientItem.setCategory(ingredientCategory);

        IngredientItem newIngredientItem = this.ingredientItemRepository.save(ingredientItem);
        ingredientCategory.getIngredientItems().add(newIngredientItem);
        return newIngredientItem;
    }

    @Override
    public IngredientItem updateStock(Long id) throws Exception {
        Optional<IngredientItem> ingredientItem = this.ingredientItemRepository.findById(id);
        if(ingredientItem.isEmpty()) throw new Exception("Ingredient not found!");
        IngredientItem ingredient = ingredientItem.get();
        ingredient.setInStock(!ingredient.isInStock());
        return this.ingredientItemRepository.save(ingredient);
    }

    @Override
    public List<IngredientItem> findRestaurantIngredientItems(Long restaurantId) throws Exception {

        return this.ingredientItemRepository.findByRestaurantId(restaurantId);
    }
}
