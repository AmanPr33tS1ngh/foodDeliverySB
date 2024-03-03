package com.foodDeliveryApp.Controller.FoodController;

import com.foodDeliveryApp.Response.MessageResponse;
import com.foodDeliveryApp.model.Food.Food;
import com.foodDeliveryApp.model.User.User;
import com.foodDeliveryApp.service.FoodService.FoodService;
import com.foodDeliveryApp.service.RestaurantService.RestaurantService;
import com.foodDeliveryApp.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {
    @Autowired
    FoodService foodService;
    @Autowired
    UserService userService;
    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<Food> searchFood(@RequestParam Long id, @RequestHeader("Authorization") String jwt ) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        Food food = this.foodService.getFood(id);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id, @RequestHeader("Authorization") String jwt ) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        this.foodService.deleteFood(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Food deleted!");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailability(@PathVariable Long id, @RequestHeader("Authorization") String jwt ) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        Food food = this.foodService.updateFoodAvailability(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
    @DeleteMapping("/{restaurantId}/{id}")
    public ResponseEntity<List<Food>> getRestaurantFood(@PathVariable Long restaurantId,@RequestParam boolean isVegetarian,@RequestParam boolean isNonVegetarian,@RequestParam boolean isSeasonal,@RequestParam String foodCategory, @RequestHeader("Authorization") String jwt ) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        List<Food> foods = this.foodService.getRestaurantFood(restaurantId, isVegetarian, isNonVegetarian, isSeasonal, foodCategory);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
