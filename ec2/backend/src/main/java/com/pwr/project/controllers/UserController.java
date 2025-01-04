package com.pwr.project.controllers;

import com.pwr.project.entities.User;
import com.pwr.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://52.20.47.142:4200", allowCredentials = "true")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/sync-user")
    public ResponseEntity<User> syncUser(@RequestBody User user) {
        log.info("Received sync-user request for email: {}", user.getEmail());
        try {
        
            User existingUser = userRepository.findByCognitoSub(user.getCognitoSub()).orElse(null);

            if (existingUser != null){
                log.info("Updating existing user with cognitoSub: {}", user.getCognitoSub());

                existingUser.setEmail(user.getEmail());
                existingUser.setFirstName(user.getFirstName());
                existingUser.setSurname(user.getSurname());
                existingUser.setSeller(user.getIsSeller());
                return ResponseEntity.ok(userRepository.save(existingUser));
            }
            log.info("Creating new user with cognitoSub: {}", user.getCognitoSub());
            return ResponseEntity.ok(userRepository.save(user));
    } catch (Exception e){
        log.error("Error synicnig user: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    }
}
