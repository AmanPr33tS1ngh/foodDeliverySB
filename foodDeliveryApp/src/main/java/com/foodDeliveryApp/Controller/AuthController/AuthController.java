package com.foodDeliveryApp.Controller.AuthController;

import com.foodDeliveryApp.Config.JwtProvider;
import com.foodDeliveryApp.Repository.UserRepo.UserRepository;
import com.foodDeliveryApp.Request.LoginRequest;
import com.foodDeliveryApp.Response.Response;
import com.foodDeliveryApp.model.ROLE;
import com.foodDeliveryApp.model.User;
import com.foodDeliveryApp.service.CustomerService.CustomerUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserDetailService customerUserDetailService;
    @PostMapping("/signup")
    public ResponseEntity<Response> createUserHandler(@RequestBody User user) throws Exception {
        User emailExists = this.userRepository.findByEmail(user.getEmail());
        if(emailExists != null){
            throw new Exception("Email is already in use with another account");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setAddresses(user.getAddresses());
        newUser.setFullName(user.getFullName());
        newUser.setRole(user.getRole());
        newUser.setPassword(passwordEncoder.encode(user.getEmail()));

        User savedUser = this.userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = this.jwtProvider.generateToken(authentication);

        Response response = new Response();
        response.setJwt(jwt);
        response.setMessage("Register Success!");
        response.setRole(user.getRole());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Response> signIn(@RequestBody LoginRequest request) throws Exception {
        System.out.println(request);
        String username = request.getEmail();
        String password = request.getPassword();
        Authentication authentication = authenticate(username, password);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        String jwt = this.jwtProvider.generateToken(authentication);

        Response response = new Response();
        response.setJwt(jwt);
        response.setMessage("Register Success!");
        response.setRole(ROLE.valueOf(role));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserDetailService.loadUserByUsername(username);
        if(userDetails == null) throw new BadCredentialsException("Invalid username..");
        System.out.println(password + "," + userDetails.getPassword());

//        if(!passwordEncoder.matches(password, userDetails.getPassword())) throw new BadCredentialsException("Invalid password...");
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
