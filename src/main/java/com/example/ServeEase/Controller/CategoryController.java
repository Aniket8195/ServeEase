package com.example.ServeEase.Controller;


import com.example.ServeEase.DTO.CategoryDTO;
import com.example.ServeEase.DTO.CategoryResDTO;
import com.example.ServeEase.DTO.ServiceProviderResponseDTO;
import com.example.ServeEase.Model.Category;
import com.example.ServeEase.Model.ServiceProvider;
import com.example.ServeEase.Repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepo categoryRepo;

    @PostMapping("/add")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDTO categoryDTO){

        if (categoryRepo.existsByCategoryName(categoryDTO.getCategoryName())) {
            return ResponseEntity.badRequest().body("Category already exists.");
        }
        try {
            Category category = new Category();
            category.setCategoryName(categoryDTO.getCategoryName());
            categoryRepo.save(category);
            return ResponseEntity.ok("Category added successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryResDTO>> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryResDTO> categoryResDTOs = categories.stream()
                .map(category -> new CategoryResDTO(category.getCategoryId(), category .getCategoryName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryResDTOs);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCategory(@RequestBody CategoryDTO categoryDTO){
        if (!categoryRepo.existsByCategoryName(categoryDTO.getCategoryName())) {
            return ResponseEntity.badRequest().body("Category does not exist.");
        }
        try {
            categoryRepo.deleteByCategoryName(categoryDTO.getCategoryName());
            return ResponseEntity.ok("Category deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error in deleting category.");
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        try {
            System.out.println("Fetching category with ID: " + id);

            Category category = categoryRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            System.out.println("Found category: " + category.getCategoryName());

            List<ServiceProviderResponseDTO> serviceProviderDTOs = category.getServiceProviders().stream()
                    .map(sp -> new ServiceProviderResponseDTO(sp.getUserId(), sp.getName(), sp.getEmail(), sp.getRating()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(serviceProviderDTOs);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error in fetching category.");
        }
    }

}
