package com.ftp.osmserverproj.Repository;

import com.ftp.osmserverproj.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //Optional<User> findOneByEmailAndPassword(String email, String password);
    User findByEmail(String email);

    List<User> findByNameContaining(String name);
    void deleteById(Long id);
    User save(User user);
   // User findById(Long userId);
}
