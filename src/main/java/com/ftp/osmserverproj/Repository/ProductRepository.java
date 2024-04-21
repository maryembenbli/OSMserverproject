package com.ftp.osmserverproj.Repository;

import com.ftp.osmserverproj.Model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    List<Product> findByProductId(String productId);
    List<Product> findByNature(String nature);
}
