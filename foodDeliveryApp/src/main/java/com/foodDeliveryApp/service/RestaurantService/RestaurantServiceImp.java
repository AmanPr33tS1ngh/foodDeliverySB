package com.foodDeliveryApp.service.RestaurantService;

import com.foodDeliveryApp.DTO.RestaurantDTO;
import com.foodDeliveryApp.Repository.AddressRepo.AddressRepository;
import com.foodDeliveryApp.Repository.RestaurantRepo.RestaurantRepository;
import com.foodDeliveryApp.Repository.UserRepo.UserRepository;
import com.foodDeliveryApp.Request.CreateRestaurantReq;
import com.foodDeliveryApp.model.Address;
import com.foodDeliveryApp.model.Restaurant;
import com.foodDeliveryApp.model.User;
import com.foodDeliveryApp.service.RestaurantService.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RestaurantServiceImp implements RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantReq req, User user) {
        Address address = this.addressRepository.save(req.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setImages(req.getImages());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setName(req.getName());
        restaurant.setRegistrationDate(LocalDate.now());
        restaurant.setOwner(user);
        return this.restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantReq req) throws Exception {
        Restaurant restaurant = this.getRestaurant(restaurantId);
        if(restaurant.getCuisineType()!=null){
            restaurant.setCuisineType(req.getCuisineType());
        }
        if(restaurant.getDescription()!=null){
            restaurant.setDescription(req.getDescription());
        }
        if(restaurant.getName()!=null){
            restaurant.setName(req.getName());
        }
        return this.restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = this.getRestaurant(restaurantId);
        this.restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getRestaurants() {
        return this.restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurants(String keyword) {
        return this.restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant getRestaurant(Long id) throws Exception {
        Optional<Restaurant> optionalRestaurant = this.restaurantRepository.findById(id);
        if(optionalRestaurant.isEmpty())throw new Exception("Restaurant not found");
        return optionalRestaurant.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = this.restaurantRepository.findByOwnerId(userId);
        if(restaurant == null)throw new Exception("Restaurant not found with provided user");
        return restaurant;
    }

    @Override
    public RestaurantDTO addToFavourites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = this.getRestaurant(restaurantId);
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setDescription(restaurant.getDescription());
        restaurantDTO.setImages(restaurant.getImages());
        restaurantDTO.setTitle(restaurant.getName());
        restaurantDTO.setId(restaurantId);

        boolean isFavorite = false;
        for(RestaurantDTO favorite: user.getFavorites()){
            if(favorite.getId().equals(restaurantId)){
                isFavorite = true;
                break;
            }
        }
        if(isFavorite){
            user.getFavorites().remove(restaurantDTO);
        }else user.getFavorites().add(restaurantDTO);
        this.userRepository.save(user);
        return restaurantDTO;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        Restaurant restaurant = this.getRestaurant(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurant;
    }
}
