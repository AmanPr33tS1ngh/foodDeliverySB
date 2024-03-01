package com.foodDeliveryApp.service;

import com.foodDeliveryApp.Config.JwtProvider;
import com.foodDeliveryApp.Repository.UserRepository;
import com.foodDeliveryApp.model.User;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = this.jwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = this.userRepository.findByEmail(email);
        if (user == null)throw new Exception("user not found");
        return user;
    }
}
