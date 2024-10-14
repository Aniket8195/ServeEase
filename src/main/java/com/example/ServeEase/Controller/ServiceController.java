package com.example.ServeEase.Controller;


import com.example.ServeEase.Repository.ServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceRepo serviceRepo;

    @PostMapping("/add")
    public String addService(){
        return "Service deleted";
    }

    @DeleteMapping("/delete")
    public String deleteService(){
        return "Service deleted";
    }
}
