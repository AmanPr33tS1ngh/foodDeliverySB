package com.foodDeliveryApp.Controller.FoodController;

import com.foodDeliveryApp.Request.FoodReq.CreateFoodReq;
import com.foodDeliveryApp.Response.MessageResponse;
import com.foodDeliveryApp.model.Food.Food;
import com.foodDeliveryApp.model.Restaurant.Restaurant;
import com.foodDeliveryApp.model.User.User;
import com.foodDeliveryApp.service.FoodService.FoodService;
import com.foodDeliveryApp.service.RestaurantService.RestaurantService;
import com.foodDeliveryApp.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/food")
public class AdminFoodController {
    @Autowired
    FoodService foodService;
    @Autowired
    UserService userService;
    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/create")
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodReq req, @RequestHeader("Authorization") String jwt ) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);

        Restaurant restaurant = this.restaurantService.getRestaurant(req.getRestaurantId());
        Food food = this.foodService.createFood(req, req.getCategory(), restaurant);
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
}
