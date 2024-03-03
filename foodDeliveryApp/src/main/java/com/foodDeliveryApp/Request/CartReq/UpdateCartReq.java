package com.foodDeliveryApp.Request.CartReq;

import lombok.Data;

@Data
public class UpdateCartReq {
    private Long cartItemId;
    private int quantity;
}
