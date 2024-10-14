package com.example.ServeEase.Model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "app_user")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String email;
    private String password;



    private float rating=0;

    @Enumerated(EnumType.STRING)
    private Role role; // Seeker or Provider


    public enum Role {
        SEEKER, PROVIDER
    }

    // Getters and Setters
}
