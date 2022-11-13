package com.example.mienspa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mienspa.models.Product;
import com.example.mienspa.repository.ProductRepository;



@Service
public class ProductService {
	@Autowired
	private ProductRepository repository;
	
	public Product save(Product Product) {
		return repository.save(Product);
	}
	
	public List<Product> getAll() {
		return repository.findAll();
	}

	public Product getById(String id) {
		return repository.findById(id).orElse(null);
	}
	public void deleteArray(String[] id) {
		for (String item : id) {
			repository.delete(repository.findById(item).orElse(null));
		}
	}


	public void delete(Product product) {
		repository.delete(product);
	}
	

}
