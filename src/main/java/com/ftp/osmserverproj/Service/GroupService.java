package com.ftp.osmserverproj.Service;

import com.ftp.osmserverproj.Model.Catalog;
import com.ftp.osmserverproj.Model.Group;
import com.ftp.osmserverproj.Repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface GroupService {
    Group findByGroupName(String groupName);
    void save(Group group);
    List<Group> getAllGroups();
}
