package com.example.ServeEase.Controller;


import com.example.ServeEase.DTO.UserDTO;
import com.example.ServeEase.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userRepo.findAll().stream()
                .map(user -> new UserDTO(user.getUserId(), user.getName(), user.getEmail(), user.getRole().name(),user.getRating()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?>getUser(@PathVariable("id") Long id){
        UserDTO user=userRepo.findById(id).map(user1 -> new UserDTO(user1.getUserId(), user1.getName(), user1.getEmail(), user1.getRole().name(),user1.getRating())).orElse(null);
        if(user==null){
            return ResponseEntity.badRequest().body(Map.of(
                    "error","User not found"
            ));
        }

        return ResponseEntity.ok(Map.of(
                "user",user
        ));

    }
}
