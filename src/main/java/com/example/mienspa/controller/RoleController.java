package com.example.mienspa.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mienspa.dto.RoleDTO;
import com.example.mienspa.models.Role;
import com.example.mienspa.service.RoleService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RoleController {
	
	@Autowired
	private RoleService service;
	
	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();
	
	@GetMapping(value = "/Role")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<RoleDTO>> getAll(){
		try {
			return new ResponseEntity<>(
					service.getAll()
					.stream()
					.map(post -> modelMapper.map(post, RoleDTO.class))
					.collect(Collectors.toList()), 
					responseHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/Role/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<RoleDTO> getById(@PathVariable("id") Integer id){
		try {		 
			Role entity = service.getById(id);
			if (service.getById(id) != null) {
				RoleDTO dto = modelMapper.map(entity, RoleDTO.class);
				return new ResponseEntity<>(dto, responseHeaders, HttpStatus.OK);
			}else {
				return  new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
