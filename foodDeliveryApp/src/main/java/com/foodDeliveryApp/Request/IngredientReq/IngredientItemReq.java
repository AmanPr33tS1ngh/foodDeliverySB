package com.foodDeliveryApp.Request.IngredientReq;

import lombok.Data;

@Data
public class IngredientItemReq {
    private String name;
    private Long categoryId;
    private Long restaurantId;
}
