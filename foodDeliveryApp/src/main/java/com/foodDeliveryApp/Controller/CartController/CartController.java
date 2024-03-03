package com.foodDeliveryApp.Controller.CartController;

import com.foodDeliveryApp.Request.CartReq.CartReq;
import com.foodDeliveryApp.Request.CartReq.UpdateCartReq;
import com.foodDeliveryApp.model.Cart.Cart;
import com.foodDeliveryApp.model.Cart.CartItem;
import com.foodDeliveryApp.model.User.User;
import com.foodDeliveryApp.service.CartService.CartService;
import com.foodDeliveryApp.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @PostMapping("addToCart")
    public ResponseEntity<CartItem> addItemsToCart(@RequestBody CartReq req, @RequestHeader("Authorization") String jwt) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        CartItem cartItem = this.cartService.addItemToCart(req, jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartReq req, @RequestHeader("Authorization") String jwt) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        CartItem cartItem = this.cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Cart> deleteCartItem(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception{
        Cart cart = this.cartService.removeItemFromCart(id, jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @PostMapping("/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        Cart cart = this.cartService.clearCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        Cart cart = this.cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Cart> findCartById(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        Cart cart = this.cartService.findCartById(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
