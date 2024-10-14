package com.example.ServeEase.DTO;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ServiceProviderRegDTO {
    private String name;
    private String email;
    private String password;
    private List<Long> categoryIds; // List of category IDs
}