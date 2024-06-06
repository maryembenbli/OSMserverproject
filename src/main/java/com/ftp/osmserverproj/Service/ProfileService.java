package com.ftp.osmserverproj.Service;

import com.ftp.osmserverproj.Model.Group;
import com.ftp.osmserverproj.Model.Profil;
import com.ftp.osmserverproj.Repository.GroupRepository;
import com.ftp.osmserverproj.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProfileService {

    private final ProfileRepository profilRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public ProfileService(ProfileRepository profilRepository, GroupRepository groupRepository) {
        this.profilRepository = profilRepository;
        this.groupRepository = groupRepository;
    }

    public Profil createProfile(String titre, Set<Group> groups) {
        Profil profil = new Profil();
        profil.setTitre(titre);
        profil.setGroups(groups);
        return profilRepository.save(profil);
    }

    public List<Profil> getAllProfiles() {
        return profilRepository.findAll();
    }

    public Optional<Profil> getProfileById(Long id) {
        return profilRepository.findById(id);
    }

    public Profil updateProfile(Long id, String newTitle) {
        Optional<Profil> optionalProfil = profilRepository.findById(id);
        if (optionalProfil.isPresent()) {
            Profil profil = optionalProfil.get();
            profil.setTitre(newTitle);
            return profilRepository.save(profil);
        } else {
            // Handle profile not found
            return null;
        }
    }

    public void deleteProfile(Long id) {
        profilRepository.deleteById(id);
    }
    public Profil addGroupsToProfile(Long profileId, Set<Long> groupIds) {
        Optional<Profil> optionalProfil = profilRepository.findById(profileId);
        if (optionalProfil.isPresent()) {
            Profil profil = optionalProfil.get();
            Set<Group> groups = profil.getGroups();
            for (Long groupId : groupIds) {
                Optional<Group> optionalGroup = groupRepository.findById(groupId);
                if (optionalGroup.isPresent()) {
                    groups.add(optionalGroup.get());
                } else {
                    // Handle group not found
                }
            }
            profil.setGroups(groups);
            return profilRepository.save(profil);
        } else {
            // Handle profile not found
            return null;
        }
    }

}
