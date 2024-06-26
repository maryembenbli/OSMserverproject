package com.ftp.osmserverproj.Controller;
import com.ftp.osmserverproj.Config.GateeFile;
import com.ftp.osmserverproj.Model.*;
import com.ftp.osmserverproj.Repository.GroupRepository;
import com.ftp.osmserverproj.Repository.ProfileRepository;
import com.ftp.osmserverproj.Service.GroupService;
import com.ftp.osmserverproj.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MyRestController {

    private final GateeFile gateFile;

    @Autowired
    public MyRestController(GateeFile gateFile) {
        this.gateFile = gateFile;
    }
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }


  /*  @GetMapping("/getFiles")
    public String getFiles() {
        StringBuilder response = new StringBuilder();
        List<File> files = gateFile.mget(".");
        for (File file : files) {
            response.append("File: ").append(file.getAbsolutePath()).append("\n");
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                int nbline=0;
                while ((line = reader.readLine()) != null) {
                    response.append(line).append("\n");
                    nbline++;
                }
                reader.close();
                response.append("Total lines: ").append(nbline).append("\n");
            } catch (IOException e) {
                e.printStackTrace();
                response.append("Error reading file: ").append(e.getMessage()).append("\n");
            }
        }
        return response.toString();
    }*/

     /* @Autowired
       private ProductService productService;
      @PostMapping("/uploadFiles")
       public String uploadFiles() {
           StringBuilder response = new StringBuilder();
           List<File> files = gateFile.mget(".");
           for (File file : files) {
               response.append("File: ").append(file.getAbsolutePath()).append("\n");
               try {
                   BufferedReader reader = new BufferedReader(new FileReader(file));
                   String line;
                   //int v =0;
                   while ((line = reader.readLine()) != null) {
                       String[] values = line.split(",", -1);
                       if (values.length == 30) {
                         Product product = new Product();
                           if (!(values[0] == null || values[0].equals(""))) {
                               product.setProductId(values[0]);
                           }
                           if (!(values[1] == null || values[1].equals(""))) {
                               product.setNature(values[1]);
                           }
                           if (!(values[2] == null || values[2].equals(""))) {
                               product.setNomProduit(values[2]);
                           }
                           if (!(values[3] == null || values[3].equals(""))) {
                               product.setTypeProduit(values[3]);
                           }
                           if (!(values[4] == null || values[4].equals(""))) {
                               product.setFamilleOption(values[4]);
                           }
                           if (!(values[5] == null || values[5].equals(""))) {
                               product.setTarifParDefaut(values[5]);
                           }
                           if (!(values[6] == null || values[6].equals(""))) {
                               product.setTarifHT(Double.parseDouble(values[6]));
                           }
                           if (!(values[7] == null || values[7].equals(""))) {
                               product.setTarifTTC(Double.parseDouble(values[7]));
                           }
                           if (!(values[8] == null || values[8].equals(""))) {
                               product.setCoutDeRevient(Double.parseDouble(values[8]));
                           }
                           if (!(values[9] == null || values[9].equals(""))) {
                               product.setMontant(Double.parseDouble(values[9]));
                           }
                           if (!(values[10] == null || values[10].equals(""))) {
                               product.setOfferShdes(values[10]);
                           }
                           if (!(values[11] == null || values[11].equals(""))) {
                               product.setOfferValue(values[11]);
                           }
                           if (!(values[12] == null || values[12].equals(""))) {
                               product.setServiceShdes(values[12]);
                           }
                           if (!(values[13] == null || values[13].equals(""))) {
                               product.setPackageShdes(values[13]);
                           }
                           if (!(values[14] == null || values[14].equals(""))) {
                               product.setParameterShdes(values[14]);
                           }
                           if (!(values[15] == null || values[15].equals(""))) {
                               product.setParameterValue(values[15]);
                           }
                           if (!(values[16] == null || values[16].equals(""))) {
                               product.setParameterValueDes(values[16]);
                           }
                           if (!(values[17] == null || values[17].equals(""))) {
                               product.setParameterNo(Integer.parseInt(values[17]));
                           }
                           if (!(values[18] == null || values[18].equals(""))) {
                               product.setFaxIsMandatory(values[18]);
                           }
                           if (!(values[19] == null || values[19].equals(""))) {
                               product.setApnIsMandatory(values[19]);
                           }
                           if (!(values[20] == null || values[20].equals(""))) {
                               product.setCrmArpuOut(values[20]);
                           }
                           if (!(values[21] == null || values[21].equals(""))) {
                               product.setCrmArpuIn(values[21]);
                           }
                           if (!(values[22] == null || values[22].equals(""))) {
                               product.setCrmCoutDirect(values[22]);
                           }
                           if (!(values[23] == null || values[23].equals(""))) {
                               product.setCrmCoutExploitation(values[23]);
                           }
                           if (!(values[24] == null || values[24].equals(""))) {
                               product.setCrmCoutAcquisition(values[24]);
                           }
                           if (!(values[25] == null || values[25].equals(""))) {
                               product.setCrmForfaitHtHorsRecharge(values[25]);
                           }
                           if (!(values[26] == null || values[26].equals(""))) {
                               product.setCrmRecharge(values[26]);
                           }
                           if (!(values[27] == null || values[27].equals(""))) {
                               product.setCrmBudgetSubvention12(values[27]);
                           }
                           if (!(values[28] == null || values[28].equals(""))) {
                               product.setCrmBudgetSubvention24(values[28]);
                           }
                           if (!(values[29] == null || values[29].equals(""))) {
                               product.setCrmBudgetSubvention36(values[29]);
                           }
                           productService.createProduct(product);
                           response.append("Inserted values: ").append(line).append("\n");
                       } else {
                           response.append("Invalid number of values in line: ").append(line).append("\n");
                       }

                   }
                   reader.close();
               } catch (IOException e) {
                   e.printStackTrace();
                   response.append("Error reading file: ").append(e.getMessage()).append("\n");
               }
           }
           return response.toString();
       }*/
   /* @Autowired
    private ProductService productService;
    @PostMapping("/uploadFiles")
    public String uploadFiles() {
        StringBuilder response = new StringBuilder();
        List<File> files = gateFile.mget(".");
        for (File file : files) {
            response.append("File: ").append(file.getAbsolutePath()).append("\n");
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] values =  line.split(",", -1);
                    if (values.length < 30) {
                        // Pad the values array with empty strings to ensure it has 30 elements
                        String[] paddedValues = new String[30];
                        //System.arraycopy(values, 0, paddedValues, 0, values.length);
                        for (int i = 0; i < values.length; i++) {
                            paddedValues[i] = values[i];
                        }
                        for (int i = values.length; i < 30; i++) {
                            paddedValues[i] = ""; // or null if you prefer
                        }
                        values = paddedValues;
                    }

                    Product product = new Product();
                    if (!(values[0] == null || values[0].equals(""))) {
                        product.setProductId(values[0]);
                    }
                    if (!(values[1] == null || values[1].equals(""))) {
                        product.setNature(values[1]);
                    }
                    if (!(values[2] == null || values[2].equals(""))) {
                        product.setNomProduit(values[2]);
                    }
                    if (!(values[3] == null || values[3].equals(""))) {
                        product.setTypeProduit(values[3]);
                    }
                    if (!(values[4] == null || values[4].equals(""))) {
                        product.setFamilleOption(values[4]);
                    }
                    if (!(values[5] == null || values[5].equals(""))) {
                        product.setTarifParDefaut(values[5]);
                    }
                    if (!(values[6] == null || values[6].equals(""))) {
                        product.setTarifHT(Double.parseDouble(values[6]));
                    }
                    if (!(values[7] == null || values[7].equals(""))) {
                        product.setTarifTTC(Double.parseDouble(values[7]));
                    }
                    if (!(values[8] == null || values[8].equals(""))) {
                        product.setCoutDeRevient(values[8]);
                    }
                    if (!(values[9] == null || values[9].equals(""))) {
                        product.setMontant(values[9]);
                    }
                    if (!(values[10] == null || values[10].equals(""))) {
                        product.setOfferShdes(values[10]);
                    }
                    if (!(values[11] == null || values[11].equals(""))) {
                        product.setOfferValue(values[11]);
                    }
                    if (!(values[12] == null || values[12].equals(""))) {
                        product.setServiceShdes(values[12]);
                    }
                    if (!(values[13] == null || values[13].equals(""))) {
                        product.setPackageShdes(values[13]);
                    }
                    if (!(values[14] == null || values[14].equals(""))) {
                        product.setParameterShdes(values[14]);
                    }
                    if (!(values[15] == null || values[15].equals(""))) {
                        product.setParameterValue(values[15]);
                    }
                    if (!(values[16] == null || values[16].equals(""))) {
                        product.setParameterValueDes(values[16]);
                    }
                    if (!(values[17] == null || values[17].equals(""))) {
                        product.setParameterNo(Integer.parseInt(values[17]));
                    }
                    if (!(values[18] == null || values[18].equals(""))) {
                        product.setFaxIsMandatory(values[18]);
                    }
                    if (!(values[19] == null || values[19].equals(""))) {
                        product.setApnIsMandatory(values[19]);
                    }
                    if (!(values[20] == null || values[20].equals(""))) {
                        product.setCrmArpuOut(values[20]);
                    }
                    if (!(values[21] == null || values[21].equals(""))) {
                        product.setCrmArpuIn(values[21]);
                    }
                    if (!(values[22] == null || values[22].equals(""))) {
                        product.setCrmCoutDirect(values[22]);
                    }
                    if (!(values[23] == null || values[23].equals(""))) {
                        product.setCrmCoutExploitation(values[23]);
                    }
                    if (!(values[24] == null || values[24].equals(""))) {
                        product.setCrmCoutAcquisition(values[24]);
                    }
                    if (!(values[25] == null || values[25].equals(""))) {
                        product.setCrmForfaitHtHorsRecharge(values[25]);
                    }
                    if (!(values[26] == null || values[26].equals(""))) {
                        product.setCrmRecharge(values[26]);
                    }
                    if (!(values[27] == null || values[27].equals(""))) {
                        product.setCrmBudgetSubvention12(values[27]);
                    }
                    if (!(values[28] == null || values[28].equals(""))) {
                        product.setCrmBudgetSubvention24(values[28]);
                    }
                    if (!(values[29] == null || values[29].equals(""))) {
                        product.setCrmBudgetSubvention36(values[29]);
                    }
                    /*product.setFaxIsMandatory(Integer.parseInt(values[18]));
                    product.setApnIsMandatory(Integer.parseInt(values[19]));
                    product.setCrmArpuOut(Double.parseDouble(values[20]));
                    product.setCrmArpuIn(Double.parseDouble(values[21]));
                    product.setCrmCoutDirect(Double.parseDouble(values[22]));
                    product.setCrmCoutExploitation(Double.parseDouble(values[23]));
                    product.setCrmCoutAcquisition(Double.parseDouble(values[24]));
                    product.setCrmForfaitHtHorsRecharge(Double.parseDouble(values[25]));
                    product.setCrmRecharge(Double.parseDouble(values[26]));
                    product.setCrmBudgetSubvention12(Double.parseDouble(values[27]));
                    product.setCrmBudgetSubvention24(Double.parseDouble(values[28]));
                    product.setCrmBudgetSubvention36(Double.parseDouble(values[29]));
//lerror houni 5ater n7awel n7ot empty string fi double donc sup double
                    productService.createProduct(product);

                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                response.append("Error reading file: ").append(e.getMessage()).append("\n");
            }
        }
        return response.toString();
    }*/


    //profilcontroller

    /*@Autowired
    private ProfileRepository profileRepository;

    @GetMapping("/profile/{profileId}/groups")
    public ResponseEntity<?> getGroupsByProfileId(@PathVariable Long profileId) {
        Optional<Profil> profileOptional = profileRepository.findById(profileId);
        if (profileOptional.isPresent()) {
            Profil profile = profileOptional.get();
            Set<Group> groups = profile.getGroups();
            return ResponseEntity.ok(groups);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    @Autowired
    private ProfileRepository profilRepository;

    @Autowired
    private GroupRepository groupRepository;

  /*  @GetMapping("/profile")
    public ResponseEntity<?> getProfilesAndGroups() {
        List<Profil> profiles = profilRepository.findAll();
        List<Group> groups = groupRepository.findAll();

        Map<Long, String> groupIdToNameMap = groups.stream()
                .collect(Collectors.toMap(Group::getId, Group::getNameG));

        List<Map<String, String>> result = new ArrayList<>();
        profiles.forEach(profile -> {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(profile.getId()));
            map.put("titre", profile.getTitre());
            map.put("nameg", groupIdToNameMap.getOrDefault(profile.getId(), ""));
            result.add(map);
        });

        return ResponseEntity.ok(result);
    }*/
 /* @GetMapping("/profile")
  public ResponseEntity<?> getProfilesAndGroups() {
      List<Profil> profiles = profilRepository.findAll();
      List<Group> groups = groupRepository.findAll();

      Map<Long, Set<String>> profileIdToGroupNamesMap = new HashMap<>();

      // Populate the profileIdToGroupNamesMap
      for (Profil profile : profiles) {
          Set<String> groupNames = profile.getGroups().stream()
                  .map(Group::getNameG)
                  .collect(Collectors.toSet());
          profileIdToGroupNamesMap.put(profile.getId(), groupNames);
      }

      List<Map<String, Object>> result = new ArrayList<>();
      // Iterate over profiles and create the result
      profiles.forEach(profile -> {
          Map<String, Object> map = new HashMap<>();
          map.put("id", profile.getId());
          map.put("titre", profile.getTitre());
          map.put("groups", profileIdToGroupNamesMap.getOrDefault(profile.getId(), Collections.emptySet()));
          result.add(map);
      });

      return ResponseEntity.ok(result);
  }

    @Autowired
    private GroupService groupService;
    @GetMapping("/group")
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        if(groups != null && !groups.isEmpty()){
            for (Group group : groups
            ) {
                setGroup(group);
            }
        }
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }


    public Group setGroup(Group group) {
        if (group.getCatalogs() != null && !group.getCatalogs().isEmpty()) {
            for (Catalog catalog: group.getCatalogs()
                 ){
                catalog.setGroup(null);
                catalog.setProducts(null);
                catalog.setContrats(null);

            }

        }

        return group;
    }*/



}

