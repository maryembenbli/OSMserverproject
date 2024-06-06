package com.ftp.osmserverproj.Service.impl;

import com.ftp.osmserverproj.Model.EmailDetails;
import com.ftp.osmserverproj.Model.History;
import com.ftp.osmserverproj.Repository.HistoryRepository;
import com.ftp.osmserverproj.Service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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



    @Override
    public boolean isFileUploaded(String fileName) {
        // Query the database to check if the file name exists in history
        return historyRepository.existsByEmailDetailsAttachment(fileName);
    }

    /*@Override
    public boolean isSubjectUploaded(String subject) {
        // Query the database to check if the subject exists in history
        return historyRepository.existsByEmailDetailsAttachment(subject);
    }*/





    @Override
    public boolean isSubjectUploaded(String subject) {
        // Query the database to check if the subject exists in history
        return historyRepository.existsByEmailDetailsSubject(subject);
    }
    @Override
    public List<History> searchHistoryByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        return historyRepository.findByCreatedAtBetween(startOfDay, endOfDay);
    }
    @Override
    public List<History> searchHistoryByStatus(String status) {
        return historyRepository.findByStatus(status);
    }

}