package com.example.ServeEase.Repository;

import com.example.ServeEase.Model.ServiceSeeker;
import com.example.ServeEase.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceSeekerRepo extends JpaRepository<ServiceSeeker,Long> {
    ServiceSeeker findByEmail(String email);
}
