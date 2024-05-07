package com.ftp.osmserverproj.Service.impl;

import com.ftp.osmserverproj.Model.Client;
import com.ftp.osmserverproj.Repository.ClientRepository;
import com.ftp.osmserverproj.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client createClient(Client client) {
        // You can add any necessary validation logic here before saving the client
        return clientRepository.save(client);
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }
    @Override
    public Client findClientByFirstnameLastnameAndNcin(String firstname, String lastname, String ncin) {
        return clientRepository.findByFirstnameAndLastnameAndNcin(firstname, lastname, ncin);
    }
    @Override
    public Client findClientByNcin(String ncin) {
        return clientRepository.findByNcin(ncin);
    }


}
