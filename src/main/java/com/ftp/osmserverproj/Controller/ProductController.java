package com.ftp.osmserverproj.Controller;

import com.ftp.osmserverproj.Model.Catalog;
import com.ftp.osmserverproj.Model.Contrat;
import com.ftp.osmserverproj.Model.Group;
import com.ftp.osmserverproj.Model.Product;
import com.ftp.osmserverproj.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products= productService.getAllProducts();
        if(products != null && !products.isEmpty()){
            for (Product product:products
            ) {
                setProduct(product);
            }

        }

        return new ResponseEntity<>(products, HttpStatus.OK);

    }
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductById(@RequestParam String idProduit) {
        List<Product> products = productService.getProductById(idProduit);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
        }
        return ResponseEntity.ok(products);
    }
    @GetMapping("/searchnature")
    public ResponseEntity<List<Product>> searchProductByNature(@RequestParam String natureProduit) {
        List<Product> products = productService.getProductByNature(natureProduit);
        if(products != null && !products.isEmpty()) {
            for (Product product : products
            ) {
                setProduct(product);
            }
        }
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/catalog/{catalogId}")
    public ResponseEntity<List<Product>> getProductByCatalogueId(@PathVariable Long catalogId) {
        List<Product> products = productService.getProductByCatalogueId(catalogId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product createProduct= productService.createProduct(product);
        return  ResponseEntity.ok(createProduct);
    }

    public Product setProduct(Product product) {
        if (product.getCatalog() != null) {
            product.getCatalog().setGroup(null);
            product.getCatalog().setProducts(null);
            product.getCatalog().setContrats(null);


        }

        return product;
    }
}
