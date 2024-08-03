package com.project_1_API.restApi.controller;

//import java.awt.print.Pageable;
//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project_1_API.restApi.model.Category;
import com.project_1_API.restApi.repository.CategoryRepository;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
	public Page<Category> getAllCategories(@RequestParam(defaultValue="0") int page, Pageable pageable){
		return categoryRepository.findAll(PageRequest.of(page, 10)) ;
		
	}
	@PostMapping
	public Category createCategory(@RequestBody Category category) {
		return categoryRepository.save(category);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
		return categoryRepository.findById(id)
				.map(category -> ResponseEntity.ok().body(category))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category){
		return categoryRepository.findById(id).map(existingCategory ->{
			existingCategory.setName(category.getName());
			return ResponseEntity.ok()
.body(categoryRepository.save(existingCategory));
			}).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCategory(@PathVariable Long id){
		return categoryRepository.findById(id).map(category -> {
			categoryRepository.delete(category);
			return ResponseEntity.noContent().build();
		}).orElse(ResponseEntity.notFound().build()); //written object instead of void 
	}
	
}
