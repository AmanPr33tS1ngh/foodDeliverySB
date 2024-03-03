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
@RequestMapping("/admin/order")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long restaurantId, @RequestBody(required = false) String status, @RequestHeader("Authenticate") String jwt) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(
                this.orderService.getRestaurantOrderByStatus(restaurantId, status), HttpStatus.OK);
    }
    @GetMapping("/restaurant/status/{restaurantId}")
    public ResponseEntity<List<Order>> getOrderHistoryByStatus(@PathVariable Long restaurantId, @RequestBody(required = false) String status, @RequestHeader("Authenticate") String jwt) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(this.orderService.getRestaurantOrderByStatus(restaurantId, status), HttpStatus.OK);
    }
    @PutMapping("/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @PathVariable String orderStatus, @RequestHeader("Authenticate") String jwt) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(this.orderService.updateOrder(orderId, orderStatus), HttpStatus.OK);
    }
}
