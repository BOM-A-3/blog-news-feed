package com.bom.newsfeed.domain.category.entity;

import com.bom.newsfeed.domain.category.constant.CategoryType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "category")
@Getter
@Entity
@NoArgsConstructor
public class Category {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Enumerated(EnumType.STRING)
	private CategoryType category;


	public Category(CategoryType categoryType){
		this.category =  categoryType;
	}


}
