package com.foodDeliveryApp.service.CategoryService;

import com.foodDeliveryApp.Repository.CategoryRepo.CategoryRepository;
import com.foodDeliveryApp.model.Category.Category;
import com.foodDeliveryApp.model.Restaurant.Restaurant;
import com.foodDeliveryApp.service.RestaurantService.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RestaurantService restaurantService;
    @Override
    public Category createCategory(String name, Long userId) throws Exception {
        Restaurant restaurant = this.restaurantService.getRestaurantByUserId(userId);
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);
        return this.categoryRepository.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
        Restaurant restaurant = this.restaurantService.getRestaurant(id);
        return this.categoryRepository.findByRestaurantId(restaurant.getId());
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> category = this.categoryRepository.findById(id);
        if(category.isEmpty())throw new Exception("Category not found");
        return category.get();
    }
}
