package com.smartchoice.productprocessor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartchoice.common.model.gson.SCGson;
import com.smartchoice.productprocessor.services.product.ProductService;

@Controller
@RequestMapping ("/rest/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private Gson getGson() {
        GsonBuilder builder = SCGson
                .getBuilder(SCGson.GsonAdapter.SUPPRESS, SCGson.GsonAdapter.PROXY,
                        SCGson.GsonAdapter.ISO_8601_NO_MILLI);

        return builder.create();
    }

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> search(@RequestParam String search) {
        return new ResponseEntity<>(getGson().toJson(productService.search(search)), HttpStatus.OK);
    }
}
