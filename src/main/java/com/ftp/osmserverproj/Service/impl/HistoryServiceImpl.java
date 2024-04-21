package com.ftp.osmserverproj.Service.impl;

import com.ftp.osmserverproj.Model.EmailDetails;
import com.ftp.osmserverproj.Model.History;
import com.ftp.osmserverproj.Repository.HistoryRepository;
import com.ftp.osmserverproj.Service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public void saveHistory(String status, LocalDateTime createdAt, EmailDetails emailDetails) {
        History history = new History();
        history.setStatus(status);
        history.setCreatedAt(createdAt);
        history.setEmailDetails(emailDetails); // Set the EmailDetails object
        historyRepository.save(history);
    }
    @Override
    public List<History> getHistory() {
        return historyRepository.findAll();
    }
}