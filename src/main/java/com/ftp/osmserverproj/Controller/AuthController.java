package com.ftp.osmserverproj.Controller;

import com.ftp.osmserverproj.Model.*;
import com.ftp.osmserverproj.Response.LoginResponse;
import com.ftp.osmserverproj.Service.HistoryService;
import com.ftp.osmserverproj.Service.UserService;
import com.ftp.osmserverproj.dto.LoginDto;
import com.ftp.osmserverproj.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
            //return ResponseEntity.badRequest().body(result.getAllErrors());
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if (existingUser != null) {
           //return ResponseEntity.status(HttpStatus.CONFLICT).body("User with the same email already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with the same email already exists");
        }

        userService.saveUser(userDto);
        //return ResponseEntity.ok("User registered successfully");
        return ResponseEntity.ok().body("{\"message\": \"User registered successfully\"}");
    }
   @GetMapping("/users")
   public List<UserDto> getUsers() {
       List<UserDto> users = userService.findAllUsers();
       if(users != null && !users.isEmpty()){
           for (UserDto user:users
                ) {
               setUser(user);

           }

       }
       return  ResponseEntity.ok(users).getBody();


   }

    @GetMapping("/users/search")
    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam String query) {
        List<UserDto> users = userService.findUsersByName(query);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }
    /*@GetMapping("/users/searchByEmail")
    public ResponseEntity<UserDto> searchUserByEmail(@RequestParam String email) {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }*/
  /*  @PostMapping("/registerWithProfil")
    public void registerUserWithProfil(@RequestBody UserDto userDto, @RequestParam String titre) {
        userService.saveUserWithProfil(userDto, titre);
    }
*/
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Long userId) {
        try {
            UserDto userDto = userService.findUserById(userId);
            if (userDto != null && userDto.getProfil() != null) {
                userDto.getProfil().setUsers(null); // Set Profil's users to null to break circular reference
            }
            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{userId}")
    public void updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        userService.updateUser(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
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

   /* @GetMapping("/history/search")
    public ResponseEntity<List<History>> searchHistoryByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<History> historyList = historyService.searchHistoryByDate(date);
        return ResponseEntity.ok(historyList);
    }*/
   @GetMapping("/history/search")
   public ResponseEntity<List<History>> searchHistoryByDateOrStatus(
           @RequestParam(required = false) LocalDate date,
           @RequestParam(required = false) String status) {
       if (date != null) {
           // Search by date
           List<History> historyList = historyService.searchHistoryByDate(date);
           return ResponseEntity.ok(historyList);
       } else if (status != null) {
           // Search by status
           List<History> historyList = historyService.searchHistoryByStatus(status);
           return ResponseEntity.ok(historyList);
       } else {
           // Handle invalid or missing parameters
           return ResponseEntity.badRequest().build();
       }
   }
    public UserDto setUser(UserDto user) {
        if (user.getProfil()!= null){
            user.getProfil().setUsers(null);}
        user.getProfil().setGroups(null);
        return user;
    }




}