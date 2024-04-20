package com.ftp.osmserverproj.Repository;

import com.ftp.osmserverproj.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findOneByEmailAndPassword(String email, String password);
    User findByEmail(String email);
}
