package com.ftp.osmserverproj.Controller;

import com.ftp.osmserverproj.Model.Catalog;
import com.ftp.osmserverproj.Model.Contrat;
import com.ftp.osmserverproj.Model.Product;
import com.ftp.osmserverproj.Service.CatalogService;
import com.ftp.osmserverproj.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalogs")
public class CatalogController {
    private final CatalogService catalogService;
    private final ProductService productService;

    @Autowired
    public CatalogController(CatalogService catalogService, ProductService productService) {
        this.catalogService = catalogService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Catalog>> getAllCatalogs() {
        List<Catalog> catalogs = catalogService.getAllCatalogs();
        if(catalogs != null && !catalogs.isEmpty()){
            for (Catalog catalog: catalogs
                 ) {
                setCatalog(catalog);
            }
        }
        return new ResponseEntity<>(catalogs, HttpStatus.OK);
    }

    @GetMapping("/withProducts")
    public ResponseEntity<List<Catalog>> getAllCatalogsWithProducts() {
        List<Catalog> catalogs = catalogService.getAllCatalogsWithProducts();
        return new ResponseEntity<>(catalogs, HttpStatus.OK);
    }

    @GetMapping("/catalog/{catalogId}")
    public ResponseEntity<List<Product>> getProductByCatalogueId(@PathVariable Long catalogId) {
        List<Product> products = productService.getProductByCatalogueId(catalogId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    /*@GetMapping("/catalog/{catalogId}")
    public ResponseEntity<Catalog> getcatalogbyid(@PathVariable Long catalogId) {
        List<Product> products = productService.getProductByCatalogueId(catalogId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }*/

    @GetMapping("/{name}")
    public ResponseEntity<Catalog> getCatalogByName(@PathVariable String name) {
        Catalog catalog = catalogService.findByCatalogName(name);
       setCatalog(catalog);
        if (catalog != null) {
            return new ResponseEntity<>(catalog, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Catalog setCatalog(Catalog catalog) {
        if (catalog.getGroup() != null)
            catalog.getGroup().setCatalogs(null);

        if (catalog.getContrats() != null && !catalog.getContrats().isEmpty()) {

            for (Contrat contrat :
                    catalog.getContrats()) {
                contrat.setCatalog(null);
                contrat.getClient().setContrats(null);


            }
        }
        if (catalog.getProducts() != null && !catalog.getProducts().isEmpty()) {

            for (Product product :
                    catalog.getProducts()) {
                product.setCatalog(null);


            }
        }
        return catalog;
    }

}
