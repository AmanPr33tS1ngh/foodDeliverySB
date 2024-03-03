package com.foodDeliveryApp.Controller.RestaurantController;

import com.foodDeliveryApp.DTO.RestaurantDTO;
import com.foodDeliveryApp.model.Restaurant.Restaurant;
import com.foodDeliveryApp.model.User.User;
import com.foodDeliveryApp.service.RestaurantService.RestaurantService;
import com.foodDeliveryApp.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> getRestaurantByUserId(@RequestHeader("Authorization") String jwt, @RequestBody String keyword) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurants = this.restaurantService.searchRestaurants(keyword);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Restaurant>> getRestaurants(@RequestHeader("Authorization") String jwt, @RequestBody String keyword) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurants = this.restaurantService.getRestaurants();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        Restaurant restaurant = this.restaurantService.getRestaurant(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    @GetMapping("/add-favorite/{id}")
    public ResponseEntity<RestaurantDTO> addFavorites(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        RestaurantDTO restaurant = this.restaurantService.addToFavourites(id, user);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
