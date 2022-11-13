package com.example.mienspa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mienspa.models.UserRole;
import com.example.mienspa.repository.UserRoleRepository;

@Service
public class UserRoleService {
	@Autowired
	private UserRoleRepository repository;
	
	public UserRole save(UserRole UserRole) {
		return repository.save(UserRole);
	}
	
	public List<UserRole> getAll() {
		return repository.findAll();
	}

	public UserRole getById(Integer id) {
		return repository.findById(id).orElse(null);
	}
	public void deleteArray(Integer[] id) {
		for (Integer item : id) {
			repository.delete(repository.findById(item).orElse(null));
		}
	}


	public void delete(UserRole UserRole) {
		repository.delete(UserRole);
	}
	

}
