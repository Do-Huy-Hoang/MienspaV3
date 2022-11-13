package com.example.mienspa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mienspa.models.Product;



@Repository
public interface ProductRepository extends JpaRepository<Product, String>{

}
