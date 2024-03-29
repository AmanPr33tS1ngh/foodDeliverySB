package com.foodDeliveryApp.service.CustomerService;

import com.foodDeliveryApp.Repository.UserRepo.UserRepository;
import com.foodDeliveryApp.model.ROLE;
import com.foodDeliveryApp.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        System.out.println("username " +  username);
        System.out.println(user);
        if(user == null){
            throw new UsernameNotFoundException("user not found!");
        }
        ROLE role = user.getRole();
        if(role == null)role=ROLE.CUSTOMER;
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority(role.toString()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorityList);
    }
}
