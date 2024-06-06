package com.ftp.osmserverproj.Service.impl;

import com.ftp.osmserverproj.Model.Catalog;
import com.ftp.osmserverproj.Repository.CatalogRepository;
import com.ftp.osmserverproj.Service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {
    private final CatalogRepository catalogRepository;

    @Autowired
    public CatalogServiceImpl(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Override
    public Catalog findByCatalogName(String catalogName) {
        return catalogRepository.findByCatalogName(catalogName); // Assuming you have a method in CatalogRepository to find by catalog name
    }

    @Override
    public void save(Catalog catalog) {
        catalogRepository.save(catalog);
    }
    @Override
    public List<Catalog> getAllCatalogs() {
        return catalogRepository.findAll();
    }
    @Override
    public List<Catalog> getAllCatalogsWithProducts() {
        List<Catalog> catalogs = catalogRepository.findAllWithProducts();
        for (Catalog catalog : catalogs) {
            catalog.getProducts().size(); // Initialize the products collection
        }
        return catalogs;
    }
    @Override
    public void updateCatalogStatus(Long catalogId, String status) {
        Catalog catalog = catalogRepository.findById(catalogId)
                .orElse(null);
        if (catalog != null) {
            catalog.setStatus(status);
            catalogRepository.save(catalog);
        } else {
            throw new IllegalArgumentException("Catalog not found with ID: " + catalogId);
        }
    }
    @Override
    public List<Catalog> findByStatus(String status) {
        return catalogRepository.findByStatus(status);
    }




}
