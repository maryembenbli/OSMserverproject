package com.ftp.osmserverproj.Service;

import com.ftp.osmserverproj.Model.User;
import com.ftp.osmserverproj.dto.UserDto;

import java.util.List;


public interface UserService {
    void saveUser(UserDto userDto);
   // void saveUserWithProfil(UserDto userDto, String titre);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
    List<UserDto> findUsersByName( String name);
    UserDto findUserById(Long userId);

    boolean isPasswordMatching(User user, String password);
    void updateUser(Long userId, UserDto userDto);
    void deleteUser(Long userId);
}