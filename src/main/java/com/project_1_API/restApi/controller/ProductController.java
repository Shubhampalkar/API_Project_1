package com.project_1_API.restApi.controller;

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
import com.project_1_API.restApi.model.Product;
import com.project_1_API.restApi.repository.CategoryRepository;
import com.project_1_API.restApi.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
    public Page<Product> getAllProducts(@RequestParam(defaultValue = "0") int page, Pageable pageable) {
        return productRepository.findAll(PageRequest.of(page, 10));
    }
	
	 @PostMapping
	    public Product createProduct(@RequestBody Product product) {
		 Product savedProduct = productRepository.save(product);
		    return ResponseEntity.ok(savedProduct);
	        
	    }
	 
	 @GetMapping("/{id}")
	    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
	        return productRepository.findById(id)
	                .map(product -> {
	                    // Populate category details
	                    Category category = product.getCategory();
	                    product.setCategory(category);
	                    return ResponseEntity.ok().body(product);
	                })
	                .orElse(ResponseEntity.notFound().build());
	    }
	 
	 @PutMapping("/{id}")
	    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
	        return productRepository.findById(id)
	                .map(existingProduct -> {
	                    existingProduct.setName(product.getName());
	                    existingProduct.setCategory(product.getCategory());
	                    return ResponseEntity.ok().body(productRepository.save(existingProduct));
	                })
	                .orElse(ResponseEntity.notFound().build());
	    }
	 
	 @DeleteMapping("/{id}")
	    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
	        return productRepository.findById(id)
	                .map(product -> {
	                    productRepository.delete(product);
	                    return ResponseEntity.noContent().build();
	                })
	                .orElse(ResponseEntity.notFound().build());
	    }
}
