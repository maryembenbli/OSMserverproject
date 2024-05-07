package com.ftp.osmserverproj.Service.impl;
import com.ftp.osmserverproj.Model.Group;
import com.ftp.osmserverproj.Repository.GroupRepository;
import com.ftp.osmserverproj.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group findByGroupName(String nameG) {
        return groupRepository.findByNameG(nameG); // Assuming you have a method in GroupRepository to find by group name
    }

    @Override
    public void save(Group group) {
        groupRepository.save(group);
    }
    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
