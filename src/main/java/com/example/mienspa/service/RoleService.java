package com.example.mienspa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mienspa.models.Role;
import com.example.mienspa.repository.RoleRepository;



@Service
public class RoleService {
	@Autowired
	private RoleRepository repository;
	
	public Role save(Role Role) {
		return repository.save(Role);
	}
	
	public List<Role> getAll() {
		return repository.findAll();
	}

	public Role getById(Integer id) {
		return repository.findById(id).orElse(null);
	}
	public void deleteArray(Integer[] id) {
		for (Integer item : id) {
			repository.delete(repository.findById(item).orElse(null));
		}
	}
	
	public Role getByName(String role) {
		return repository.getByRoleName(role);
	}
	public void delete(Role role) {
		repository.delete(role);
	}
	

}
