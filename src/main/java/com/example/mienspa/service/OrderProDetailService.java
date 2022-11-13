package com.example.mienspa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mienspa.models.OrdersProDetail;
import com.example.mienspa.repository.OrderProDetailsRepository;





@Service
public class OrderProDetailService {
	@Autowired
	private OrderProDetailsRepository repository;
	
	public OrdersProDetail save(OrdersProDetail OrdersProDetail) {
		return repository.save(OrdersProDetail);
	}
	
	public List<OrdersProDetail> getAll() {
		return repository.findAll();
	}

	public OrdersProDetail getById(Integer id) {
		return repository.findById(id).orElse(null);
	}
	
	public List<OrdersProDetail> getAllByOrProId(String id) {
		return repository.findAllByOrProId(id);
	}
	public void deleteArray(Integer[] id) {
		for (Integer item : id) {
			repository.delete(repository.findById(item).orElse(null));
		}
	}


	public void delete(OrdersProDetail ordersProDetail) {
		repository.delete(ordersProDetail);
	}
	

}
