package com.example.mienspa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mienspa.models.OrdersPro;
import com.example.mienspa.repository.OrderProRepository;



@Service
public class OrderProService {
	@Autowired
	private OrderProRepository repository;
	
	public OrdersPro save(OrdersPro OrdersPro) {
		return repository.save(OrdersPro);
	}
	
	public List<OrdersPro> getAll() {
		return repository.findAll();
	}
	
	public List<OrdersPro> getAllByUserId(String id) {
		return repository.findAllByUserId(id);
	}

	public OrdersPro getById(String id) {
		return repository.findById(id).orElse(null);
	}


	public void delete(OrdersPro ordersPro) {
		repository.delete(ordersPro);
	}
	

}
