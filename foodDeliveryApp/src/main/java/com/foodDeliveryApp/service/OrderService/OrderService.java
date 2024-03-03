package com.foodDeliveryApp.service.OrderService;

import com.foodDeliveryApp.Request.OrderReq.OrderReq;
import com.foodDeliveryApp.model.Order.Order;
import com.foodDeliveryApp.model.User.User;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderReq req, User user)throws Exception;
    public void cancelOrder(Long orderId) throws Exception;
    public Order updateOrder(Long orderId, String orderStatus) throws Exception;
    public List<Order> getUserOrder(Long userId) throws Exception;

    public Order getOrder(Long orderId) throws Exception;

    public List<Order> getRestaurantOrder(Long restaurantId) throws Exception;
    public List<Order> getRestaurantOrderByStatus(Long restaurantId, String status) throws Exception;
}
