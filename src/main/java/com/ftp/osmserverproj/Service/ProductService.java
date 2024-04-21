package com.ftp.osmserverproj.Service;

import com.ftp.osmserverproj.Model.Product;
import com.ftp.osmserverproj.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public List<Product> getProductById(String productId) {
        return productRepository.findByProductId(productId);
    }
    public List<Product> getProductByNature(String nature) {
        return productRepository.findByNature(nature);
    }
    public Product createProduct(Product product){
        return  productRepository.save(product);
    }
}
