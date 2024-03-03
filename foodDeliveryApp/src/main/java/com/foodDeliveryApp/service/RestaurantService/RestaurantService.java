package com.foodDeliveryApp.service.RestaurantService;

import com.foodDeliveryApp.DTO.RestaurantDTO;
import com.foodDeliveryApp.Request.RestaurantReq.CreateRestaurantReq;
import com.foodDeliveryApp.model.Restaurant.Restaurant;
import com.foodDeliveryApp.model.User.User;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantReq req, User user);
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantReq req) throws Exception;
    public void deleteRestaurant(Long restaurantId) throws Exception;
    public List<Restaurant> getRestaurants();
    public List<Restaurant> searchRestaurants(String keyword);
    public Restaurant getRestaurant(Long id) throws Exception;
    public Restaurant getRestaurantByUserId(Long userId) throws Exception;
    public RestaurantDTO addToFavourites(Long restaurantId, User user) throws Exception;
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;
}
