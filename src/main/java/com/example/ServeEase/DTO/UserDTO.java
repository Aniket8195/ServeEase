package com.example.ServeEase.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private String role;
    private float rating;
}