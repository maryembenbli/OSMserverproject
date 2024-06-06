package com.ftp.osmserverproj.Repository;
import com.ftp.osmserverproj.Model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    @Query("SELECT DISTINCT c FROM Catalog c LEFT JOIN FETCH c.products")
    List<Catalog> findAllWithProducts();
    List<Catalog> findByStatus(String status);

    Catalog findByCatalogName(String catalogName);
    Catalog save(Catalog catalog);
    Catalog findCatalogById(Long id);
    @Query("UPDATE Catalog c SET c.status = ?2 WHERE c.id = ?1")
    void updateStatusById(Long id, String status);



}