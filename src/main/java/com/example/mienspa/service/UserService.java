package com.example.mienspa.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mienspa.models.Users;
import com.example.mienspa.repository.UserRepository;



@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	
	public Users save(Users Users) {
		return repository.save(Users);
	}
	
	public Boolean checkMail(String email) {
		if(repository.getUserByEmail(email) != null) {
			return true;
		}
		return false;
	}
	
	public Integer getCountUserByDate(LocalDate date) {
		return repository.countUserByDate(date);
	}
	public List<Users> getAll() {
		return repository.findAll();
	}

	public Users getById(String id) {
		return repository.findById(id).orElse(null);
	}
	
	public Users getByEmail(String email) {
		return repository.getUserByEmail(email);
	}
	
	
	public void deleteArray(String[] id) {
		for (String item : id) {
			repository.delete(repository.findById(item).orElse(null));
		}
	}


	public void delete(Users users) {
		repository.delete(users);
	}
	

}
