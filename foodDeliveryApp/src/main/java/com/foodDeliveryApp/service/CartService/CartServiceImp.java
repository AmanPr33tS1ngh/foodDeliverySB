package com.foodDeliveryApp.service.CartService;

import com.foodDeliveryApp.Repository.CartRepo.CartItemRepository;
import com.foodDeliveryApp.Repository.CartRepo.CartRepository;
import com.foodDeliveryApp.Repository.FoodRepo.FoodRepository;
import com.foodDeliveryApp.Request.CartReq.CartReq;
import com.foodDeliveryApp.model.Cart.Cart;
import com.foodDeliveryApp.model.Cart.CartItem;
import com.foodDeliveryApp.model.Food.Food;
import com.foodDeliveryApp.model.User.User;
import com.foodDeliveryApp.service.FoodService.FoodService;
import com.foodDeliveryApp.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CartServiceImp implements CartService{
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(CartReq req, String jwt) throws Exception {
        User user = this.userService.findUserByJwtToken(jwt);

        Food food = this.foodService.getFood(req.getFoodId());

        Cart cart = this.cartRepository.findByCustomerId(user.getId());
        for(CartItem cartItem: cart.getCartItems()){
            if(cartItem.getFood().equals(food)){
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                return this.updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setFood(food);
        cartItem.setQuantity(req.getQuantity());
        cartItem.setIngredients(req.getIngredients());
        cartItem.setTotalPrice(req.getQuantity() * food.getPrice());

        cart.getCartItems().add(cartItem);

        return cartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItem = this.cartItemRepository.findById(cartItemId);
        if(cartItem.isEmpty())throw new Exception("Cart item not found!");
        CartItem item = cartItem.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice()* quantity);
        return this.cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = this.userService.findUserByJwtToken(jwt);
        Cart cart = this.cartRepository.findByCustomerId(user.getId());
        Optional<CartItem> cartItem = this.cartItemRepository.findById(cartItemId);
        if(cartItem.isEmpty())throw new Exception("Cart item not found!");
        CartItem item = cartItem.get();
        cart.getCartItems().remove(item);
        return this.cartRepository.save(cart);
    }

    @Override
    public Long calculateCartItems(Cart cart) throws Exception {
        Long total = 0L;
        for (CartItem cartItem : cart.getCartItems()){
            total += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> cart = this.cartRepository.findById(id);
        if(cart.isEmpty())throw new Exception("Cart not found!");
        return cart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        return this.cartRepository.findByCustomerId(userId);
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
        Cart cart = this.cartRepository.findByCustomerId(userId);
        cart.getCartItems().clear();
        this.cartRepository.save(cart);
        return cart;
    }
}
