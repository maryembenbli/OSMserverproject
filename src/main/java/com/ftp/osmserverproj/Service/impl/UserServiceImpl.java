package com.ftp.osmserverproj.Service.impl;

/*import com.ftp.osmserverproj.Model.Role;
import com.ftp.osmserverproj.Model.User;
import com.ftp.osmserverproj.Repository.UserRepository;
import com.ftp.osmserverproj.Response.LoginMsg;
//import com.ftp.osmserverproj.Repository.UserRepo;
import com.ftp.osmserverproj.Service.UserService;
import com.ftp.osmserverproj.dto.LoginDto;
import com.ftp.osmserverproj.dto.UserDto;
import com.ftp.osmserverproj.dto.UserRegistrationDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getFirstName(),
                registrationDto.getLastName(), registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_USER")));

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}*/
import com.ftp.osmserverproj.Model.Profil;
import com.ftp.osmserverproj.Repository.ProfileRepository;
import com.ftp.osmserverproj.Service.UserService;
import com.ftp.osmserverproj.dto.UserDto;
import com.ftp.osmserverproj.Model.Role;
import com.ftp.osmserverproj.Model.User;
import com.ftp.osmserverproj.Repository.RoleRepository;
import com.ftp.osmserverproj.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
private ProfileRepository profilRepository;
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ProfileRepository profilRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.profilRepository=profilRepository;
    }

    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstname() + " " + userDto.getLastname());
        user.setEmail(userDto.getEmail());
        // Encrypt the password using Spring Security
        user.setPassword(passwordEncoder.encode("Billcom"));

        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = checkRoleExist();
        }
        Profil profil = profilRepository.findByTitre(userDto.getProfil().getTitre());
        logger.warning("Profil : " + userDto.getProfil().getTitre());
        if (profil == null) {
            throw new IllegalArgumentException("Profil not found for titre: " + userDto.getProfil().getTitre());
        }
        user.setProfil(profil);

        userRepository.save(user);
    }

 /*   @Override
    public void saveUserWithProfil(UserDto userDto, String titre) {
        User user = new User();
        user.setName(userDto.getFirstname() + " " + userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode("Billcom"));

        Profil profil = profilRepository.findByTitre(titre);
        if (profil == null) {
            throw new IllegalArgumentException("Profil not found for titre: " + userDto.getProfil().getTitre());
        }
        user.setProfil(profil);

        userRepository.save(user);
    }*/

    private Role checkRoleExist() {
        Role role = new Role();
        //role.setName("ROLE_ADMIN");
        role.setName("ROLE_USER");

        return roleRepository.save(role);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }
    @Override
    public List<UserDto> findUsersByName(String query) {
        List<User> users = userRepository.findByNameContaining(query);
        return users.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        String[] name = user.getName().split(" ");
        userDto.setFirstname(name[0]);
        userDto.setLastname(name[1]);
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        if (user.getProfil() != null) {
            userDto.setProfil(user.getProfil());
        }

        return userDto;
    }
    @Override
    public boolean isPasswordMatching(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
    @Override
    public void updateUser(Long userId, UserDto userDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        existingUser.setName(userDto.getFirstname() + " " + userDto.getLastname());
        existingUser.setEmail(userDto.getEmail());
       // Profil profil = userDto.getProfil();
        // Fetch existing Profil entity based on profileTitle
        Profil existingProfil = profilRepository.findByTitre(userDto.getProfil().getTitre());

        if (existingProfil != null) {
            existingUser.setProfil(existingProfil);
            userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("Profil not found");
        }
        }



    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Delete associated roles before deleting the user
        user.getRoles().forEach(role -> {
            role.getUsers().remove(user); // Remove user from role
            roleRepository.save(role);    // Save the updated role
        });

        // Now delete the user
        userRepository.deleteById(userId);
    }
    @Override
    public UserDto findUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return convertEntityToDto(userOptional.get());
        } else {
            throw new IllegalArgumentException("User not found for ID: " + userId);
        }
    }


}