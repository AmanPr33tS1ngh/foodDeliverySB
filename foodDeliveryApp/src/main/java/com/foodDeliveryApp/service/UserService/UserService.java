package com.foodDeliveryApp.service.UserService;

import com.foodDeliveryApp.model.User.User;


public interface UserService {
    public User findUserByJwtToken(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
}
