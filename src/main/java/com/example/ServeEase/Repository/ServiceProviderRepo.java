package com.example.ServeEase.Repository;

import com.example.ServeEase.Model.ServiceProvider;
import com.example.ServeEase.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceProviderRepo extends JpaRepository<ServiceProvider, Long> {
    ServiceProvider findByEmail(String email);
}
