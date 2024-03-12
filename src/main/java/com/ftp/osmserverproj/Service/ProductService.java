package com.ftp.osmserverproj.Service;

import com.ftp.osmserverproj.Model.Product;
import com.ftp.osmserverproj.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product){
        return  productRepository.save(product);
    }
}
