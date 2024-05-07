package com.ftp.osmserverproj.Service.impl;

import com.ftp.osmserverproj.Model.Catalog;
import com.ftp.osmserverproj.Model.Client;
import com.ftp.osmserverproj.Model.Contrat;

import com.ftp.osmserverproj.Repository.ContratRepository;
import com.ftp.osmserverproj.Service.CatalogService;
import com.ftp.osmserverproj.Service.ClientService;
import com.ftp.osmserverproj.Service.ContratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratServiceImpl implements ContratService {

    @Autowired
    private ContratRepository contratRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CatalogService catalogService;

    @Override
    public List<Contrat> getAllContrats() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat getContratById(Long id) {
        return contratRepository.findById(id).orElse(null);
    }


    /*@Override
    public Contrat createContrat(Contrat contrat) {

        contrat.setNtel(contrat.getNtel());
        if (contrat.getClient() != null && contrat.getClient().getId() == null) {
            Client existingClient = clientService.findClientByFirstnameLastnameAndNcin(
                    contrat.getClient().getFirstname(),
                    contrat.getClient().getLastname(),
                    contrat.getClient().getNcin());

            if (existingClient == null) {
                existingClient = clientService.createClient(contrat.getClient());
            }
            contrat.setClient(existingClient);
        }
        // If Catalog name is provided, find the catalog by name
        if (contrat.getCatalog() != null && contrat.getCatalog().getCatalogName() != null) {
            // Find the catalog by name
            Catalog existingCatalog = catalogService.findByCatalogName(contrat.getCatalog().getCatalogName());
            if (existingCatalog != null) {

                contrat.setCatalog(existingCatalog);
            } else {
                throw new IllegalArgumentException("Catalog with name " + contrat.getCatalog().getCatalogName() + " does not exist");
            }
        }
        return contratRepository.save(contrat);
    }*/

    @Override
    public Contrat createContrat(Contrat contrat) {
        // Check if the provided NTEL already exists
        Contrat existingContratWithNTEL = contratRepository.findByNtel(contrat.getNtel());
        if (existingContratWithNTEL != null) {
            throw new IllegalArgumentException("A contract with NTEL " + contrat.getNtel() + " already exists.");
        }
        if (contrat.getClient() != null && contrat.getClient().getId() == null) {
            Client existingClient = clientService.findClientByFirstnameLastnameAndNcin(
                    contrat.getClient().getFirstname(),
                    contrat.getClient().getLastname(),
                    contrat.getClient().getNcin());

            if (existingClient == null) {
                existingClient = clientService.createClient(contrat.getClient());
            }
            contrat.setClient(existingClient);
        }

        // If Catalog name is provided, find the catalog by name
        if (contrat.getCatalog() != null && contrat.getCatalog().getCatalogName() != null) {
            // Find the catalog by name
            Catalog existingCatalog = catalogService.findByCatalogName(contrat.getCatalog().getCatalogName());
            if (existingCatalog != null) {
                contrat.setCatalog(existingCatalog);
            } else {
                throw new IllegalArgumentException("Catalog with name " + contrat.getCatalog().getCatalogName() + " does not exist");
            }
        }

        // Save the contract
        return contratRepository.save(contrat);
    }



    @Override
    public Contrat updateContrat(Long id, Contrat newContrat) {
        Contrat existingContrat = contratRepository.findById(id).orElse(null);
        if (existingContrat != null) {
            existingContrat.setNtel(newContrat.getNtel());
            existingContrat.setClient(newContrat.getClient());
            existingContrat.setCatalog(newContrat.getCatalog());
            return contratRepository.save(existingContrat);
        }
        return null;
    }

    @Override
    public void deleteContrat(Long id) {
        contratRepository.deleteById(id);
    }

    @Override
    public Contrat findContratByNtel(String ntel) {
        return contratRepository.findByNtel(ntel);
    }
}