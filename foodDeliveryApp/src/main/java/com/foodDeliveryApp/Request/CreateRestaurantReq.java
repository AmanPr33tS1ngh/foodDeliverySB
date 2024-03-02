package com.foodDeliveryApp.Request;

import com.foodDeliveryApp.model.Address;
import com.foodDeliveryApp.model.ContactInformation;
import lombok.Data;
import java.util.List;
@Data
public class CreateRestaurantReq {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;
}
