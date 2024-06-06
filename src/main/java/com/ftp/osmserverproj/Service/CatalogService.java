package com.ftp.osmserverproj.Service;
import com.ftp.osmserverproj.Model.Catalog;
import com.ftp.osmserverproj.Repository.CatalogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CatalogService {


    Catalog findByCatalogName(String catalogName);
    void save(Catalog catalog);

    List<Catalog> getAllCatalogs();

    List<Catalog> getAllCatalogsWithProducts();
    void updateCatalogStatus(Long catalogId, String status);
    List<Catalog> findByStatus(String status);
}
