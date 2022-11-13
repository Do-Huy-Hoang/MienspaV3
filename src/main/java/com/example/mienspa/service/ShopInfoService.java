package com.example.mienspa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mienspa.models.ShopInfo;
import com.example.mienspa.repository.ShopInfoRepository;



@Service
public class ShopInfoService {
	@Autowired
	private ShopInfoRepository repository;
	
	public ShopInfo save(ShopInfo ShopInfo) {
		return repository.save(ShopInfo);
	}
	
	public List<ShopInfo> getAll() {
		return repository.findAll();
	}

	public ShopInfo getById(String id) {
		return repository.findById(id).orElse(null);
	}
	public void deleteArray(String[] id) {
		for (String item : id) {
			repository.delete(repository.findById(item).orElse(null));
		}
	}


	public void delete(ShopInfo shopInfo) {
		repository.delete(shopInfo);
	}
	

}
