package com.ftp.osmserverproj.Controller;

import com.ftp.osmserverproj.Model.Catalog;
import com.ftp.osmserverproj.Model.Client;
import com.ftp.osmserverproj.Model.Contrat;
import com.ftp.osmserverproj.Model.Product;
import com.ftp.osmserverproj.Service.ClientService;
import com.ftp.osmserverproj.Service.ContratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contrats")
public class ContratController {

    @Autowired
    private ContratService contratService;
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Contrat>> getAllContrats() {
        List<Contrat> contrats = contratService.getAllContrats();
        if(contrats != null && !contrats.isEmpty()){
            for (Contrat contrat:contrats
                 ) {
                setContract(contrat);
            }

        }

        return new ResponseEntity<>(contrats, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity
            <Contrat> getContratById(@PathVariable Long id) {
        Contrat contrat = contratService.getContratById(id);
        if (contrat != null) {
            return new ResponseEntity<>(setContract(contrat), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
   /* @PostMapping("/add")
    public ResponseEntity
            <Contrat> createContrat(@RequestBody Contrat contrat) {
        Contrat createdContrat = contratService.createContrat(contrat);
        return new ResponseEntity<>(createdContrat, HttpStatus.CREATED);
    }*/
   @PostMapping("/add")
   public ResponseEntity<Contrat> createContrat(@RequestBody Contrat contrat) {
       try {
           Contrat createdContrat = contratService.createContrat(contrat);
           return ResponseEntity.status(HttpStatus.CREATED).body(setContract(createdContrat));
       } catch (IllegalArgumentException ex) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
       }

   }

    //   @PostMapping("/add")
//   public ResponseEntity<Contrat> createContrat(@RequestBody Contrat contrat) {
//       // Check if the provided NCIN already exists
//       Client existingClientWithNCIN = clientService.findClientByNcin(contrat.getClient().getNcin());
//       if (existingClientWithNCIN != null) {
//           // If client exists, add the contract to that client
//           contrat.setClient(existingClientWithNCIN);
//           Contrat createdContrat = contratService.createContrat(contrat);
//           return ResponseEntity.status(HttpStatus.CREATED).body(createdContrat);
//       } else {
//           // If no client exists with the provided NCIN, check if a contract with the provided NTEL exists
//           Contrat existingContratWithNTEL = contratService.findContratByNtel(contrat.getNtel());
//           if (existingContratWithNTEL != null) {
//               // If a contract with the provided NTEL already exists, return an error
//               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // You can return a more detailed error message if needed
//           } else {
//               // If no contract with the provided NTEL exists, create a new client and add the contract to that client
//               Client newClient = clientService.createClient(contrat.getClient());
//               contrat.setClient(newClient);
//               Contrat createdContrat = contratService.createContrat(contrat);
//               return ResponseEntity.status(HttpStatus.CREATED).body(createdContrat);
//           }
//       }
//   }
    @PutMapping("/{id}")
    public ResponseEntity
            <Contrat> updateContrat(@PathVariable Long id, @RequestBody Contrat contrat) {
        Contrat updatedContrat = contratService.updateContrat(id, contrat);
        if (updatedContrat != null) {
            return new ResponseEntity<>(setContract(updatedContrat), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContrat(@PathVariable Long id) {
        contratService.deleteContrat(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /*@PostMapping("/check-unique")
    public ResponseEntity<Map<String, Boolean>> checkUnique(@RequestBody Map<String, String> requestBody) {
        String ncin = requestBody.get("ncin");
        String ntel = requestBody.get("ntel");

        boolean ncinExists = contratService.existsByNcin(ncin);
        boolean ntelExists = contratService.existsByNtel(ntel);

        Map<String, Boolean> response = new HashMap<>();
        response.put("ncinExists", ncinExists);
        response.put("ntelExists", ntelExists);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }*/
    public Contrat setContract(Contrat contrat) {
        if (contrat.getClient() != null)
            contrat.getClient().setContrats(null);
        if (contrat.getCatalog() != null)
            contrat.getCatalog().getGroup().setCatalogs(null);
        contrat.getCatalog().getGroup().setProfils(null);
            contrat.getCatalog().setProducts(null); //car je besoin les produit quand laffichage de catalogue en contrat
            contrat.getCatalog().setContrats(null);




        return contrat;
    }


}