package com.example.mienspa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mienspa.models.OrdersSer;
import com.example.mienspa.repository.OrderSerRepository;




@Service
public class OrderSerService {
	@Autowired
	private OrderSerRepository repository;
	
	public OrdersSer save(OrdersSer OrdersSer) {
		return repository.save(OrdersSer);
	}
	
	public List<OrdersSer> getAll() {
		return repository.findAll();
	}

	public List<OrdersSer> getAllByUserId(String id) {
		return repository.findAllByUserId(id);
	}
	
	public OrdersSer getById(String id) {
		return repository.findById(id).orElse(null);
	}
	public void deleteArray(String[] id) {
		for (String item : id) {
			repository.delete(repository.findById(item).orElse(null));
		}
	}


	public void delete(OrdersSer ordersSer) {
		repository.delete(ordersSer);
	}
	

}
