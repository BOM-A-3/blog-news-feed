package com.bom.newsfeed.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bom.newsfeed.domain.category.constant.CategoryType;
import com.bom.newsfeed.domain.category.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

	Category findByCategory(CategoryType cateGoryType);

}
