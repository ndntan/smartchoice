package com.smartchoice.productprocessor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartchoice.common.model.gson.SCGson;
import com.smartchoice.productprocessor.dto.CategoryInfo;
import com.smartchoice.productprocessor.services.category.CategoryService;

@RestController
@RequestMapping("/rest/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private Gson getGson() {
        GsonBuilder builder = SCGson
                .getBuilder(SCGson.GsonAdapter.SUPPRESS, SCGson.GsonAdapter.PROXY,
                        SCGson.GsonAdapter.ISO_8601_NO_MILLI);
        return builder.create();
    }

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAll() {
        return new ResponseEntity<>(getGson().toJson(categoryService.findAll()), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable Long id) {
        return new ResponseEntity<>(getGson().toJson(categoryService.findById(id)), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable Long id) {
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody CategoryInfo categoryInfo) {
        return new ResponseEntity<>(getGson().toJson(categoryService.save(categoryInfo)), HttpStatus.OK);
    }

    @PostMapping(path = "/bulkCreate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity bulkCreate(@RequestBody List<CategoryInfo> categoryInfos) {
        return new ResponseEntity<>(getGson().toJson(categoryService.save(categoryInfos)), HttpStatus.OK);
    }

    @GetMapping(path = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity refresh(@RequestParam Long categoryId) {
        categoryService.refresh(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
