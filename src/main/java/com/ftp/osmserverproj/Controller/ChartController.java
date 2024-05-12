package com.ftp.osmserverproj.Controller;

import com.ftp.osmserverproj.Service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/charts")
public class ChartController {
    @Autowired
    private ChartService chartService;

    @GetMapping("/sales-by-date")
    public ResponseEntity<Map<String, Integer>> getSalesByDate() {
        Map<String, Integer> salesByDate = chartService.getSalesByDate();
        return ResponseEntity.ok(salesByDate);
    }

    @GetMapping("/sales-by-place")
    public ResponseEntity<Map<String, Integer>> getSalesByPlace() {
        Map<String, Integer> salesByPlace = chartService.getSalesByPlace();
        return ResponseEntity.ok(salesByPlace);
    }

    @GetMapping("/gender-distribution")
    public ResponseEntity<Map<String, Integer>> getGenderDistribution() {
        Map<String, Integer> genderDistribution = chartService.getGenderDistribution();
        return ResponseEntity.ok(genderDistribution);
    }
}
