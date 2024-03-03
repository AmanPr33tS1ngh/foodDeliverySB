package com.foodDeliveryApp.Controller.RestaurantController;

import com.foodDeliveryApp.Request.RestaurantReq.CreateRestaurantReq;
import com.foodDeliveryApp.Response.MessageResponse;
import com.foodDeliveryApp.model.Restaurant.Restaurant;
import com.foodDeliveryApp.model.User.User;
import com.foodDeliveryApp.service.RestaurantService.RestaurantService;
import com.foodDeliveryApp.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/restaurants")
public class AdminRestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantReq req, @RequestHeader("Authorization") String jwt ) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        Restaurant restaurant = this.restaurantService.createRestaurant(req, user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantReq req, @RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        Restaurant restaurant = this.restaurantService.updateRestaurant(id, req);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(@RequestBody CreateRestaurantReq req, @RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        this.restaurantService.deleteRestaurant(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Restaurant deleted successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
    @DeleteMapping("/status/{id}")
    public ResponseEntity<MessageResponse> updateRestaurantStatus(@RequestBody CreateRestaurantReq req, @RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        this.restaurantService.updateRestaurantStatus(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Restaurant status updated!");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
    @DeleteMapping("/user")
    public ResponseEntity<Restaurant> getRestaurantByUserId(@RequestHeader("Authorization") String jwt) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        Restaurant restaurant = this.restaurantService.getRestaurantByUserId(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
