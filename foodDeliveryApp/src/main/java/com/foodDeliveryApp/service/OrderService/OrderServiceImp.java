package com.foodDeliveryApp.service.OrderService;

import com.foodDeliveryApp.Repository.AddressRepo.AddressRepository;
import com.foodDeliveryApp.Repository.OrderRepo.OrderItemRepository;
import com.foodDeliveryApp.Repository.OrderRepo.OrderRepository;
import com.foodDeliveryApp.Repository.RestaurantRepo.RestaurantRepository;
import com.foodDeliveryApp.Repository.UserRepo.UserRepository;
import com.foodDeliveryApp.Request.OrderReq.OrderReq;
import com.foodDeliveryApp.model.Address.Address;
import com.foodDeliveryApp.model.Cart.Cart;
import com.foodDeliveryApp.model.Cart.CartItem;
import com.foodDeliveryApp.model.Order.Order;
import com.foodDeliveryApp.model.Order.OrderItem;
import com.foodDeliveryApp.model.Restaurant.Restaurant;
import com.foodDeliveryApp.model.User.User;
import com.foodDeliveryApp.service.CartService.CartService;
import com.foodDeliveryApp.service.RestaurantService.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class OrderServiceImp implements OrderService{
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private CartService cartService;
    @Override
    public Order createOrder(OrderReq req, User user) throws Exception {
        Address shipAddress = req.getAddress();
        Address address = this.addressRepository.save(shipAddress);
        if(!user.getAddresses().contains(address)){
            user.getAddresses().add(address);
            this.userRepository.save(user);
        }
        Restaurant restaurant = this.restaurantService.getRestaurant(req.getRestaurantId());
        Order order = new Order();
        order.setAddress(address);
        order.setCustomer(user);
        order.setRestaurant(restaurant);
        order.setOrderStatus("PENDING");
        order.setCreatedAt(new Date());

        Cart cart = this.cartService.findCartByUserId(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItem cartItem: cart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = this.orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }
        Long totalPrice = this.cartService.calculateCartItems(cart);
        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);
        Order savedOrder = this.orderRepository.save(order);
        restaurant.getOrders().add(savedOrder);
        return savedOrder;
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        this.orderRepository.deleteById(orderId);
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = this.getOrder(orderId);

        if(orderStatus.equals("COMPLETED") || orderStatus.equals("OUT_FOR_DELIVERY") ||
                orderStatus.equals("DELIVERED") || orderStatus.equals("PENDING")){
            order.setOrderStatus(orderStatus);
            return this.orderRepository.save(order);
        }
        throw new Exception("Invalid order status!");
    }

    @Override
    public List<Order> getUserOrder(Long userId) throws Exception {
        return this.orderRepository.findByCustomerId(userId);
    }
    @Override
    public Order getOrder(Long orderId) throws Exception {
        Optional<Order> order = this.orderRepository.findById(orderId);
        if(order.isEmpty())throw new Exception("Order not found!");
        return order.get();
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId) throws Exception {
        return this.orderRepository.findByRestaurantId(restaurantId);
    }
    @Override
    public List<Order> getRestaurantOrderByStatus(Long restaurantId, String status) throws Exception {
        List<Order> orders = this.orderRepository.findByRestaurantId(restaurantId);
        if(orders!=null) {
            orders = orders.stream().filter(order ->
                order.getOrderStatus().equals(status)
            ).collect(Collectors.toList());
        }
        return orders;
    }
}
