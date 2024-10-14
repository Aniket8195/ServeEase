package com.example.ServeEase.DTO;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceSeekerRegDTO {
    private String email;
    private String name;
    private String password;
}
