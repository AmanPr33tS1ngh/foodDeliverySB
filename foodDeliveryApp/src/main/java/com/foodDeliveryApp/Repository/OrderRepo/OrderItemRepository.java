package com.foodDeliveryApp.Repository.OrderRepo;

import com.foodDeliveryApp.model.Order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
