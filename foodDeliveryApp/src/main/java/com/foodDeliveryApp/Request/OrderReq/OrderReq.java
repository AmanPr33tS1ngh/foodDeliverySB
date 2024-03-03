package com.foodDeliveryApp.Request.OrderReq;

import com.foodDeliveryApp.model.Address.Address;
import lombok.Data;

@Data
public class OrderReq {
    private Long restaurantId;
    private Address address;
}
