package com.foodDeliveryApp.Response;

import com.foodDeliveryApp.model.ROLE;
import lombok.Data;

@Data
public class Response {
    private String jwt;
    private String message;
    private ROLE role;
}
