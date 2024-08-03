package com.project_1_API.restApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project_1_API.restApi.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
}
