package com.foodDeliveryApp.service.CartService;

import com.foodDeliveryApp.Request.CartReq.CartReq;
import com.foodDeliveryApp.model.Cart.Cart;
import com.foodDeliveryApp.model.Cart.CartItem;

public interface CartService {
    public CartItem addItemToCart(CartReq req, String jwt) throws Exception;
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception;
    public Long calculateCartItems(Cart cart) throws Exception;
    public Cart findCartById(Long id) throws Exception;
    public Cart findCartByUserId(Long userId) throws Exception;
    public Cart clearCart(Long userId) throws Exception;
}
