package com.example.ServeEase.Controller;

import com.example.ServeEase.DTO.ServiceSeekerDTO;
import com.example.ServeEase.DTO.ServiceSeekerRegDTO;
import com.example.ServeEase.Model.ServiceSeeker;
import com.example.ServeEase.Model.User;
import com.example.ServeEase.Repository.ServiceSeekerRepo;
import com.example.ServeEase.Service.EmailService;
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
import java.util.Map;

@RestController
@RequestMapping("/serviceSeeker")
public class ServiceSeekerController {

    @Autowired
    private ServiceSeekerRepo serviceSeekerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/register")
    public ResponseEntity<String> registerServiceSeeker(@RequestBody ServiceSeekerRegDTO serviceSeekerDTO) {
        ServiceSeeker seeker = new ServiceSeeker();
        seeker.setEmail(serviceSeekerDTO.getEmail());
        seeker.setName(serviceSeekerDTO.getName());
        seeker.setPassword(serviceSeekerDTO.getPassword());
       try {
           if (serviceSeekerRepo.findByEmail(seeker.getEmail()) != null) {
               return new ResponseEntity<>("Email is already registered.", HttpStatus.CONFLICT);
           }
           seeker.setPassword(passwordEncoder.encode(seeker.getPassword()));
           seeker.setRole(User.Role.SEEKER);
           serviceSeekerRepo.save(seeker);
              emailService.sendWelcomeEmail(seeker.getEmail(), seeker.getName());
           return new ResponseEntity<>("Service seeker registered successfully.", HttpStatus.CREATED);
         } catch (Exception e) {
              return ResponseEntity.badRequest().body("Error in registering service seeker.");
       }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginServiceSeeker(@RequestBody ServiceSeekerDTO serviceSeekerDTO) {
        ServiceSeeker seeker = new ServiceSeeker();
        seeker.setEmail(serviceSeekerDTO.getEmail());
        seeker.setPassword(serviceSeekerDTO.getPassword());
      try
        {
            Map<String, Object> response = new HashMap<>();

            ServiceSeeker serviceSeeker = serviceSeekerRepo.findByEmail(seeker.getEmail());
            if (serviceSeeker == null) {
                response.put("message", "Service seeker not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            if (!passwordEncoder.matches(seeker.getPassword(), serviceSeeker.getPassword())) {
                response.put("message", "Invalid password.");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            String token = jwtUtil.generateToken(serviceSeeker.getEmail(), serviceSeeker.getRole().name());
            response.put("message", "Login successful.");
            response.put("token", token);
            response.put("role", serviceSeeker.getRole().name());
            response.put("id", serviceSeeker.getUserId());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
