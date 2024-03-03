package com.foodDeliveryApp.Controller.CategoryController;

import com.foodDeliveryApp.model.Category.Category;
import com.foodDeliveryApp.model.User.User;
import com.foodDeliveryApp.service.CategoryService.CategoryService;
import com.foodDeliveryApp.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                   @RequestHeader("Authorization") String jwt) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        Category createdCategory = this.categoryService.createCategory(category.getName(), user.getId());
        return new ResponseEntity<>(createdCategory, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Category>> getRestaurantCategory(@RequestHeader("Authorization") String jwt) throws Exception{
        User user = this.userService.findUserByJwtToken(jwt);
        List<Category> categories = this.categoryService.findCategoryByRestaurantId(user.getId());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
