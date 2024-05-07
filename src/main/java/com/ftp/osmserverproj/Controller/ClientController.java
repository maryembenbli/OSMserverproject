package com.ftp.osmserverproj.Controller;

import com.ftp.osmserverproj.Model.Client;
import com.ftp.osmserverproj.Model.Contrat;
import com.ftp.osmserverproj.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/{ncin}")
    public Client getClientByNcin(@PathVariable String ncin) {
        Client client = clientService.findClientByNcin(ncin);
        if (client == null) {
            throw new IllegalArgumentException("Client with Ncin " + ncin + " not found");
        }
        if(client != null){

            setClinet(client);
        }
        return client;
    }

    public Client setClinet(Client client) {
        if (client.getContrats() != null && !client.getContrats().isEmpty()){
            for (Contrat contrat:client.getContrats()
                 ) {
                contrat.setClient(null);
                contrat.getCatalog().setGroup(null);
                contrat.getCatalog().setProducts(null);
                contrat.getCatalog().setContrats(null);

            }
        }




        return client;
    }

}
