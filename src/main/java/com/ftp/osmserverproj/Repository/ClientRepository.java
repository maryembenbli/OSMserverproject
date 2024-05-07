package com.ftp.osmserverproj.Repository;

import com.ftp.osmserverproj.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByFirstnameAndLastnameAndNcin(String firstname, String lastname, String ncin);
    Client  findByNcin(String ncin);
}
