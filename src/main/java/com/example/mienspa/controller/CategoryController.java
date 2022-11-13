package com.example.mienspa.controller;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mienspa.dto.CategoryDTO;
import com.example.mienspa.models.Category;
import com.example.mienspa.service.CategoryService;



@CrossOrigin
@RestController
@RequestMapping("/api")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();
	Date date = Date.from(Instant.now());
	@GetMapping("/Category")
	@PreAuthorize("hasRole('USER') or hasRole('CATEGORY') or hasRole('ADMIN')")
	public ResponseEntity<List<CategoryDTO>> getAll(){
		
		try {
			return new ResponseEntity<>(
					service.getAll()
					.stream()
					.map(post -> modelMapper.map(post, CategoryDTO.class))
					.collect(Collectors.toList()), 
					responseHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/Category/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('CATEGORY') or hasRole('ADMIN')")
	public ResponseEntity<CategoryDTO>  getById(@PathVariable("id") Integer id){
		try {		 
			Category entity = service.getById(id);
			if (service.getById(id) != null) {
				CategoryDTO dto = modelMapper.map(entity, CategoryDTO.class);
				return new ResponseEntity<>(dto, responseHeaders, HttpStatus.OK);
			}else {
				return  new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@PostMapping(value = "/Category")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('CATEGORY') or hasRole('ADMIN')")
	public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO dto) {
		try {
			Category entityRequest = modelMapper.map(dto, Category.class);
			Category entity = service.save(entityRequest);
			CategoryDTO dtoReponse = modelMapper.map(entity, CategoryDTO.class);
			return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping(value = "/Category/{id}")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('CATEGORY') or hasRole('ADMIN')")
	public ResponseEntity<CategoryDTO> update(@PathVariable("id") Integer id, @RequestBody CategoryDTO dto) {
		try {
			if (service.getById(id) != null) {
				Category entityRequest = modelMapper.map(dto, Category.class);
				entityRequest.setCateId(id);
				Category entity = service.save(entityRequest);
				CategoryDTO dtoReponse = modelMapper.map(entity, CategoryDTO.class);
				return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.ACCEPTED);
			}
			return  new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping(value = "/CategoryArray")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('CATEGORY') or hasRole('ADMIN')")
	public void deleteArrayCategory(@RequestBody Integer[] id) {
		service.deleteArray(id);
	}
	
	@DeleteMapping(value = "/Category")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('CATEGORY') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> deleteCategory(@RequestBody Integer id) {	
		try {
			Category entity = service.getById(id);
			if (service.getById(id) != null) {
				service.delete(entity);
				return new ResponseEntity<>(true, responseHeaders, HttpStatus.OK);
			}else {
				return  new ResponseEntity<>(false, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
}
