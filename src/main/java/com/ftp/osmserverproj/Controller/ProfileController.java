package com.ftp.osmserverproj.Controller;

import com.ftp.osmserverproj.Model.*;
import com.ftp.osmserverproj.Repository.GroupRepository;
import com.ftp.osmserverproj.Repository.ProfileRepository;
import com.ftp.osmserverproj.Service.GroupService;
import com.ftp.osmserverproj.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private final ProfileService profileService;
    @Autowired
    private ProfileRepository profilRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

@PostMapping
public ResponseEntity<Profil> createProfile(@RequestBody Map<String, Object> requestBody) {
    String titre = (String) requestBody.get("titre").toString();
    List<Map<String, String>> groupsList = (List<Map<String, String>>) requestBody.get("groups");

    // Convert the list of maps to a set of Group objects
    Set<Group> groups = groupsList.stream()
            .map(groupMap -> {
                Group group = new Group();
                group.setId(Long.parseLong(String.valueOf(groupMap.get("id"))));
                group.setNameG((String) groupMap.get("nameG"));
                return group;
            })
            .collect(Collectors.toSet());

    Profil createdProfile = profileService.createProfile(titre, groups);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
}


    @GetMapping
    public ResponseEntity<List<Profil>> getAllProfiles() {
        List<Profil> profiles = profileService.getAllProfiles();
        if(profiles !=null && !profiles.isEmpty()){
            for (Profil profil:profiles
                 ) {
                setProfil(profil);

            }
        }
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profil> getProfileById(@PathVariable Long id) {
        Optional<Profil> optionalProfile = profileService.getProfileById(id);
        if (optionalProfile.isPresent()) {
            Profil profil = optionalProfile.get();
            setProfil(profil);
            return ResponseEntity.ok(profil);
        } else {
            return ResponseEntity.notFound().build();
        }}

    @PutMapping("/{id}")
    public ResponseEntity<Profil> updateProfile(@PathVariable Long id, @RequestParam String title) {
        Profil updatedProfile = profileService.updateProfile(id, title);
        return updatedProfile != null ?
                ResponseEntity.ok(updatedProfile) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
    public Profil setProfil(Profil profil) {
        if (profil.getUsers() != null && !profil.getUsers().isEmpty()) {

            for (User user :
                    profil.getUsers()) {
                user.setProfil(null);
                user.setRoles(null);

            }
        }
        if(profil.getGroups()!=null && !profil.getGroups().isEmpty()){
            for (Group group:profil.getGroups()
                 ) {
                group.setCatalogs(null);
                group.setProfils(null);

            }
        }

        return profil;
    }


    @GetMapping("/profileGroup")
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
        if(group.getProfils()!=null &&!group.getProfils().isEmpty()){
            for (Profil profil
            :group.getProfils()){
                profil.setGroups(null);
                profil.setUsers(null);
            }
        }

        return group;
    }


    @PutMapping("/{profileId}/groups")
    public ResponseEntity<Profil> addGroupsToProfile(@PathVariable Long profileId, @RequestBody Set<Long> groupIds) {
        Profil updatedProfile = profileService.addGroupsToProfile(profileId, groupIds);
        if (updatedProfile != null) {
            return ResponseEntity.ok(setProfil(updatedProfile));
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}
