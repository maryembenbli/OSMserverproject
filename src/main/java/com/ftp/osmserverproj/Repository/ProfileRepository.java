package com.ftp.osmserverproj.Repository;

import com.ftp.osmserverproj.Model.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profil, Long> {
    Profil findByTitre(String titre);

}