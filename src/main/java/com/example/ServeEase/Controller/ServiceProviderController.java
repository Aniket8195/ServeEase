package com.example.ServeEase.Controller;


import com.example.ServeEase.DTO.LoginResponseDTO;
import com.example.ServeEase.DTO.ServiceProviderDTO;
import com.example.ServeEase.DTO.ServiceProviderRegDTO;
import com.example.ServeEase.Model.Category;

import com.example.ServeEase.Model.ServiceProvider;
import com.example.ServeEase.Model.User;
import com.example.ServeEase.Repository.CategoryRepo;
import com.example.ServeEase.Repository.ServiceProviderRepo;

import com.example.ServeEase.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/serviceProvider")
public class ServiceProviderController {

    @Autowired
    private ServiceProviderRepo serviceProviderRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CategoryRepo categoryRepo;


    @PostMapping("/register")
    public ResponseEntity<String> registerServiceProvider(@RequestBody ServiceProviderRegDTO providerDTO){
       try
         {
             if (serviceProviderRepo.findByEmail(providerDTO.getEmail()) != null) {
                 return new ResponseEntity<>("Email is already registered.", HttpStatus.CONFLICT);
             }

             ServiceProvider provider = new ServiceProvider();
             provider.setName(providerDTO.getName());
             provider.setEmail(providerDTO.getEmail());
             provider.setPassword(passwordEncoder.encode(providerDTO.getPassword()));
             provider.setRole(User.Role.PROVIDER);

             List<Category> categories = categoryRepo.findAllById(providerDTO.getCategoryIds());
             provider.setCategories(categories);

             serviceProviderRepo.save(provider);

             return new ResponseEntity<>("Service provider registered successfully.", HttpStatus.CREATED);
         }
         catch (Exception e)
         {
              return new ResponseEntity<>("Error in registering service provider.", HttpStatus.BAD_REQUEST);
         }
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDTO> loginServiceProvider(@RequestBody ServiceProviderDTO providerDTO) {
//        try {
//            ServiceProvider serviceProvider = serviceProviderRepo.findByEmail(providerDTO.getEmail());
//            if (serviceProvider == null) {
//                return new ResponseEntity<>(new LoginResponseDTO(null, "Service provider not found."), HttpStatus.NOT_FOUND);
//            }
//
//            if (!passwordEncoder.matches(providerDTO.getPassword(), serviceProvider.getPassword())) {
//                return new ResponseEntity<>(new LoginResponseDTO(null, "Invalid password."), HttpStatus.UNAUTHORIZED);
//            }
//
//            String token = jwtUtil.generateToken(serviceProvider.getEmail(), serviceProvider.getRole().name());
//            return new ResponseEntity<>(new LoginResponseDTO(token, "Login successful."), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new LoginResponseDTO(null, "Error in logging in service provider."), HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginServiceProvider(@RequestBody ServiceProviderDTO providerDTO) {
        try {
            ServiceProvider serviceProvider = serviceProviderRepo.findByEmail(providerDTO.getEmail());
            Map<String, Object> response = new HashMap<>();

            if (serviceProvider == null) {
                response.put("token", null);
                response.put("message", "Service provider not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            if (!passwordEncoder.matches(providerDTO.getPassword(), serviceProvider.getPassword())) {
                response.put("token", null);
                response.put("message", "Invalid password.");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            String token = jwtUtil.generateToken(serviceProvider.getEmail(), serviceProvider.getRole().name());
            response.put("token", token);
            response.put("message", "Login successful.");
            response.put("role", serviceProvider.getRole().name());
            response.put("id", serviceProvider.getUserId());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("token", null);
            errorResponse.put("message", "Error in logging in service provider.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }


}
