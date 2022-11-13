package com.example.mienspa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mienspa.models.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
