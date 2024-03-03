package com.foodDeliveryApp.Request.CartReq;

import lombok.Data;

import java.util.List;

@Data
public class CartReq {
    private Long foodId;
    private int quantity;
    private List<String> ingredients;
}
