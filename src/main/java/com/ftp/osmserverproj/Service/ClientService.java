package com.ftp.osmserverproj.Service;

import com.ftp.osmserverproj.Model.Client;

public interface  ClientService {
    Client getClientById(Long id);

    Client createClient(Client client);
    Client findClientByFirstnameLastnameAndNcin(String firstname, String lastname, String ncin);
    Client findClientByNcin(String ncin);

}
