package com.foodDeliveryApp.Controller.OrderController;

import com.foodDeliveryApp.Repository.OrderRepo.OrderRepository;
import com.foodDeliveryApp.Request.OrderReq.OrderReq;
import com.foodDeliveryApp.model.Order.Order;
import com.foodDeliveryApp.model.User.User;
import com.foodDeliveryApp.service.OrderService.OrderService;
import com.foodDeliveryApp.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderReq req, @RequestHeader("Authenticate") String jwt) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(this.orderService.createOrder(req, user), HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestBody OrderReq req, @RequestHeader("Authenticate") String jwt) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(this.orderService.getUserOrder(user.getId()), HttpStatus.OK);
    }

}
