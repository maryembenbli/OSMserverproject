package com.ftp.osmserverproj.Service;

import com.ftp.osmserverproj.Model.User;
import com.ftp.osmserverproj.dto.UserDto;

import java.util.List;


public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
    List<UserDto> findUsersByName( String name);

    boolean isPasswordMatching(User user, String password);
}