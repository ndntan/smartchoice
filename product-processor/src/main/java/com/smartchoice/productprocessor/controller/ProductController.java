package com.smartchoice.productprocessor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartchoice.productprocessor.dto.ProductInfo;
import com.smartchoice.productprocessor.model.Product;
import com.smartchoice.productprocessor.services.product.ProductService;

@Controller
@RequestMapping ("/rest/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAll(){
        List<Product> products = productService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getById(@PathVariable Long id){
        Product product = productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody ProductInfo productInfo) {
       Product product = productInfo.toProduct();
        Product createdProduct = productService.save(product);
        return ResponseEntity.status(HttpStatus.OK).body(createdProduct);
    }
}
