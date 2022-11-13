package com.example.mienspa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mienspa.models.Serce;
import com.example.mienspa.repository.SerceRepository;



@Service
public class SerceService {
	@Autowired
	private SerceRepository repository;
	
	public Serce save(Serce Serce) {
		return repository.save(Serce);
	}
	
	public List<Serce> getAll() {
		return repository.findAll();
	}

	public Serce getById(String id) {
		return repository.findById(id).orElse(null);
	}
	public void deleteArray(String[] id) {
		for (String item : id) {
			repository.delete(repository.findById(item).orElse(null));
		}
	}


	public void delete(Serce serce) {
		repository.delete(serce);
	}
	

}
