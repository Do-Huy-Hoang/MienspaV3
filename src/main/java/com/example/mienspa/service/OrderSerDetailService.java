package com.example.mienspa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mienspa.models.OrderSerDetail;
import com.example.mienspa.repository.OrderSerDetailsRepository;





@Service
public class OrderSerDetailService {
	@Autowired
	private OrderSerDetailsRepository repository;
	
	public OrderSerDetail save(OrderSerDetail OrderSerDetail) {
		return repository.save(OrderSerDetail);
	}
	
	public List<OrderSerDetail> getAll() {
		return repository.findAll();
	}
	public List<OrderSerDetail> getAllByOrSerId(String id) {
		return repository.findAllByOrSerId(id);
	}
	public OrderSerDetail getById(Integer id) {
		return repository.findById(id).orElse(null);
	}
	public void deleteArray(Integer[] id) {
		for (Integer item : id) {
			repository.delete(repository.findById(item).orElse(null));
		}
	}


	public void delete(OrderSerDetail orderSerDetail) {
		repository.delete(orderSerDetail);
	}
	

}
