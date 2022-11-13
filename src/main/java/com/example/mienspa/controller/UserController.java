package com.example.mienspa.controller;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.example.mienspa.dto.UsersDTO;
import com.example.mienspa.models.Users;
import com.example.mienspa.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();
	ObjectMapper mapper = new ObjectMapper();
	
	@GetMapping(value = "/Users")
	@PreAuthorize("hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<List<UsersDTO>> getAll(){
		try {
			return new ResponseEntity<>(
					service.getAll()
					.stream()
					.map(post -> modelMapper.map(post, UsersDTO.class))
					.collect(Collectors.toList()), 
					responseHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
//	
//	@GetMapping(value = "/Users")
//	public List<Users> Check(@RequestBody Users Users){
//		try {
//			return service.getAll();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	@GetMapping(value = "/Users/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<UsersDTO> getById(@PathVariable("id") String id){
		try {
			Users entity = service.getById(id);
			UsersDTO dto = modelMapper.map(entity, UsersDTO.class);
			return new ResponseEntity<>(dto, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@PostMapping(value = "/Users")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<UsersDTO> create(@RequestBody UsersDTO dto) {
		try {
			String hash = BCrypt.hashpw(dto.getUsPassword(), BCrypt.gensalt(12));
			dto.setUsPassword(hash);
	        Users entityResquest = modelMapper.map(dto, Users.class);
	        Users entity = service.save(entityResquest);
	        UsersDTO dtoReponse = modelMapper.map(entity, UsersDTO.class);
			return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping(value = "/Users/{id}")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<UsersDTO> update(@PathVariable("id") String id,  @RequestPart(required = false) String json,
			@RequestPart(required = false) @ApiParam(required = true, value = "") MultipartFile file) {
		try {
			if (service.getById(id) != null) {		       
				if (file == null) {
					UsersDTO dto = mapper.readValue(json, UsersDTO.class);
					if(dto.getUsPassword() != null) {
						Users entityOld = service.getById(id);
						dto.setUsPassword(entityOld.getUsPassword());
					}else {
						String hash = BCrypt.hashpw(dto.getUsPassword(), BCrypt.gensalt(12));
						dto.setUsPassword(hash);
					}
					Users entityRequest = modelMapper.map(dto, Users.class);
					Users entity = service.save(entityRequest);
				    UsersDTO dtoReponse = modelMapper.map(entity, UsersDTO.class);
					dtoReponse.setUsId(id);
					return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.ACCEPTED);
				} else {				
					UsersDTO dto = mapper.readValue(json, UsersDTO.class);
					//delete old image
					if(dto.getUsImage() != null) {
						Path oldPath = Paths.get("Images/Users/"+id+"/"+"/"+dto.getUsImage());
						Files.delete(oldPath);
					}
					if(dto.getUsPassword() != null) {
						Users entityOld = service.getById(id);
						dto.setUsPassword(entityOld.getUsPassword());
					}else {
						String hash = BCrypt.hashpw(dto.getUsPassword(), BCrypt.gensalt(12));
						dto.setUsPassword(hash);
					}
					Users entityRequest = modelMapper.map(dto, Users.class);
					File folder=new File("Images/Users/"+entityRequest.getUsId());
					folder.mkdirs();
					Path path = Paths.get(folder.getPath());
					InputStream inputStream = file.getInputStream();
					Files.copy(inputStream, path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
					entityRequest.setUsImage(file.getOriginalFilename().toLowerCase());
					Users entity = service.save(entityRequest);
					UsersDTO dtoReponse = modelMapper.map(entity, UsersDTO.class);
					return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.ACCEPTED);

				}
			}
			return  new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping(value = "/UsersArray")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ACCOUNT') or hasRole('ADMIN')")
	public void deleteArrayUsers(@RequestBody String[] id) {
		service.deleteArray(id);
	}
	
	@DeleteMapping(value = "/Users")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> deleteUsers(@RequestBody String id) {
		try {
			Users entity = service.getById(id);
			if (service.getById(id) != null) {
				if(entity.getUsImage() != null) {
					File directoryToDelete = new File("Images/Users/"+entity.getUsImage());
					FileSystemUtils.deleteRecursively(directoryToDelete);
				}
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
