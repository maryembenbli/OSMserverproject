package com.ftp.osmserverproj.Controller;

import com.ftp.osmserverproj.Model.Product;
import com.ftp.osmserverproj.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product createProduct= productService.createProduct(product);
        return  ResponseEntity.ok(createProduct);
    }
}
