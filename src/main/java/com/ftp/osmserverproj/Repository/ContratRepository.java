package com.ftp.osmserverproj.Repository;

import com.ftp.osmserverproj.Model.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratRepository extends JpaRepository<Contrat, Long> {
    Contrat findByNtel(String ntel);
}
