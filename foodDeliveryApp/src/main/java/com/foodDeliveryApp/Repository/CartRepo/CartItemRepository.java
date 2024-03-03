package com.foodDeliveryApp.Repository.CartRepo;

import com.foodDeliveryApp.model.Cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
