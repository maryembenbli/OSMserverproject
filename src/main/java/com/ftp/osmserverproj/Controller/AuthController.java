package com.ftp.osmserverproj.Controller;

import com.ftp.osmserverproj.Model.History;
import com.ftp.osmserverproj.Model.User;
import com.ftp.osmserverproj.Response.LoginResponse;
import com.ftp.osmserverproj.Service.HistoryService;
import com.ftp.osmserverproj.Service.UserService;
import com.ftp.osmserverproj.dto.LoginDto;
import com.ftp.osmserverproj.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/user")
public class AuthController {
    private final UserService userService;
    @Autowired
    private HistoryService historyService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with the same email already exists");
        }

        userService.saveUser(userDto);
        return ResponseEntity.ok("User registered successfully");
    }
   @GetMapping("/users")
   public List<UserDto> getUsers() {
       return userService.findAllUsers();
   }

    @GetMapping("/users/search")
    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam String query) {
        List<UserDto> users = userService.findUsersByName(query);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }


    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Login page");
    }
   @PostMapping("/login")
   public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDto loginDto, BindingResult result) {
       if (result.hasErrors()) {
           // Return validation errors
           return ResponseEntity.badRequest().body(new LoginResponse(false, "Validation errors"));
       }

       User user = userService.findUserByEmail(loginDto.getEmail());
       if (user == null || !userService.isPasswordMatching(user, loginDto.getPassword())) {
           // Return unauthorized status
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(false, "Invalid email or password"));
       }

       return ResponseEntity.ok(new LoginResponse(true, "Login successful"));
   }

    @GetMapping("/history")
    public List<History> getHistory() {
        return historyService.getHistory();
    }



}