package com.ace.blog.controllers;

import com.ace.blog.domain.dtos.CategoryDto;
import com.ace.blog.domain.dtos.CreateCategoryRequest;
import com.ace.blog.domain.entities.Category;
import com.ace.blog.mappers.CategoryMapper;
import com.ace.blog.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories(){
       List<CategoryDto> categories = categoryService.listCategories()
               .stream().map(categoryMapper::toDto).toList();
       return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
           @Valid @RequestBody CreateCategoryRequest createCategoryRequest){
        Category categoryRequest = categoryMapper.toEntity(createCategoryRequest);
       Category savedCategory = categoryService.createCategory(categoryRequest);
       return new ResponseEntity<>(categoryMapper.toDto(savedCategory), HttpStatus.CREATED);
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
