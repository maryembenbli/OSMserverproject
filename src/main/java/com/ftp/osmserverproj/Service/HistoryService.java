package com.ftp.osmserverproj.Service;

import com.ftp.osmserverproj.Model.EmailDetails;
import com.ftp.osmserverproj.Model.History;
import com.ftp.osmserverproj.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoryService {
    void saveHistory(String status, LocalDateTime createdAt, EmailDetails emailDetails);
    List<History> getHistory();
}