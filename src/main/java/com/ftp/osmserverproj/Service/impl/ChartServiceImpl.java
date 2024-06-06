package com.ftp.osmserverproj.Service.impl;

import com.ftp.osmserverproj.Model.Contrat;
import com.ftp.osmserverproj.Service.ChartService;
import com.ftp.osmserverproj.Service.ContratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChartServiceImpl  implements ChartService {
    @Autowired
    private ContratService contratService;

    @Override
    public Map<String, Integer> getSalesByDate() {
        List<Contrat> allContrats = contratService.getAllContrats();
        Map<String, Integer> salesByDate = new HashMap<>();

        for (Contrat contrat : allContrats) {
            LocalDateTime createdAt = contrat.getCreatedAt();
            if (createdAt != null) {
                String dateKey = createdAt.toString();
                salesByDate.put(dateKey, salesByDate.getOrDefault(dateKey, 0) + 1);
            }
        }

        return salesByDate;
    }

    @Override
    public Map<String, Integer> getSalesByPlace() {
        List<Contrat> allContrats = contratService.getAllContrats();
        Map<String, Integer> salesByPlace = new HashMap<>();

        for (Contrat contrat : allContrats) {
            String placeKey = contrat.getCatalog().getCatalogName(); // Assuming there's a Catalog reference in Contrat
            salesByPlace.put(placeKey, salesByPlace.getOrDefault(placeKey, 0) + 1);
        }

        return salesByPlace;
    }

    @Override
    public Map<String, Integer> getGenderDistribution() {
        List<Contrat> allContrats = contratService.getAllContrats();
        Map<String, Integer> genderDistribution = new HashMap<>();

        for (Contrat contrat : allContrats) {
            String gender = contrat.getClient().getGender(); // Assuming there's a gender field in Client
            genderDistribution.put(gender, genderDistribution.getOrDefault(gender, 0) + 1);
        }

        return genderDistribution;
    }
}
