package com.ftp.osmserverproj.Service;

import com.ftp.osmserverproj.Repository.ContratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ChartService {

    Map<String, Integer> getSalesByDate();
    Map<String, Integer> getSalesByPlace();
    Map<String, Integer> getGenderDistribution();
}
