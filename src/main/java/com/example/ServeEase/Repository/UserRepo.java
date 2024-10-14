package com.example.ServeEase.Repository;

import com.example.ServeEase.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
