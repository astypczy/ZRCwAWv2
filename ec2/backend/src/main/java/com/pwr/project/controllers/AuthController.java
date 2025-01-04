package com.pwr.project.controllers;
import com.pwr.project.dto.JwtDTO;
import com.pwr.project.dto.LoginDTO;
import com.pwr.project.dto.RegisterDTO;
import com.pwr.project.dto.auth.CognitoTokenResponseDTO;
import com.pwr.project.entities.User;
import com.pwr.project.exceptions.InvalidJWTException;
import com.pwr.project.repositories.UserRepository;
import com.pwr.project.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://52.20.47.142:4200", allowCredentials = "true")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO registerDTO) throws InvalidJWTException {
        authService.register(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        try {
            JwtDTO jwtDTO = authService.login(loginDTO);
            return ResponseEntity.ok(jwtDTO);
        } catch (InvalidJWTException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/current-user")
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        log.info("Received authentication header: {}", authHeader);
        try {
            User currentUser = authService.getCurrentUser();
            log.info("Found user: {}", currentUser);
            return ResponseEntity.ok(currentUser);
        } catch (Exception e) {
            log.error("Error getting user: ", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Fetching all users for ADMIN request");
        try {
            List<User> users = authService.getAllUsers();
            log.info("Number of users found: {}", users.size());
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            log.error("Error retrieving users: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

