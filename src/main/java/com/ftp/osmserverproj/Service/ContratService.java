package com.ftp.osmserverproj.Service;

import com.ftp.osmserverproj.Model.Contrat;

import java.util.List;

public interface ContratService {
    List<Contrat> getAllContrats();
    Contrat getContratById(Long id);

    Contrat createContrat(Contrat contrat);

    Contrat updateContrat(Long id, Contrat contrat);
  //Contrat createOrUpdateContrat(Contrat contrat);

    void deleteContrat(Long id);
    Contrat findContratByNtel(String ntel);

}