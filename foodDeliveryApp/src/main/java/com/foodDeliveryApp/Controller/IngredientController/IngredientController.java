package com.foodDeliveryApp.Controller.IngredientController;

import com.foodDeliveryApp.Request.IngredientReq.IngredientItemReq;
import com.foodDeliveryApp.Request.IngredientReq.IngredientReq;
import com.foodDeliveryApp.model.Ingredient.IngredientCategory;
import com.foodDeliveryApp.model.Ingredient.IngredientItem;
import com.foodDeliveryApp.service.IngredientService.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientReq req) throws Exception{
        IngredientCategory ingredientCategory = this.ingredientService.createIngredientCategory(req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(ingredientCategory, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<IngredientItem> createIngredientItem(@RequestBody IngredientItemReq req) throws Exception{
        IngredientItem ingredientItem = this.ingredientService.createIngredientItem(req.getRestaurantId(), req.getName(), req.getCategoryId());
        return new ResponseEntity<>(ingredientItem, HttpStatus.OK);
    }
    @PutMapping("updateStock/{id}")
    public ResponseEntity<IngredientItem> updateIngredientStock(@PathVariable Long id) throws Exception{
        IngredientItem ingredientItem = this.ingredientService.updateStock(id);
        return new ResponseEntity<>(ingredientItem, HttpStatus.OK);
    }
    @GetMapping("restaurant/{id}")
    public ResponseEntity<List<IngredientItem>> findByRestaurantId(@PathVariable Long id) throws Exception{
        List<IngredientItem> ingredientItems = this.ingredientService.findRestaurantIngredientItems(id);
        return new ResponseEntity<>(ingredientItems, HttpStatus.OK);
    }
    @GetMapping("restaurant/category/{id}")
    public ResponseEntity<List<IngredientCategory>> findCategoriesByRestaurantId(@PathVariable Long id) throws Exception{
        List<IngredientCategory> ingredientCategories = this.ingredientService.findIngredientCategoriesByRestaurantId(id);
        return new ResponseEntity<>(ingredientCategories, HttpStatus.OK);
    }
}
