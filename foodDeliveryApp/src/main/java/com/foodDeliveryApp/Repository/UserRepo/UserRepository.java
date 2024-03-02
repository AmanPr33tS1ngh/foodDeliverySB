package com.foodDeliveryApp.Repository.UserRepo;

import com.foodDeliveryApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String username);
}
