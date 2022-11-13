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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mienspa.dto.UserRoleDTO;
import com.example.mienspa.models.UserRole;
import com.example.mienspa.service.RoleService;
import com.example.mienspa.service.UserRoleService;
import com.example.mienspa.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserRoleController {

	@Autowired
	private UserRoleService service;
	
	@Autowired
	private UserService UseSer;
	
	@Autowired
	private RoleService RolSer;
	
	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();
	
	@GetMapping(value = "/UserRole")
	@PreAuthorize("hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<List<UserRoleDTO>> getAll(){
		try {
			List<UserRole> entityList = service.getAll();
			List<UserRoleDTO> dtos = entityList.stream().map(user -> modelMapper.map(user, UserRoleDTO.class))
					.collect(Collectors.toList());

			for (UserRole entity : entityList) {
				for (UserRoleDTO dto : dtos) {
					if (dto.getUsrId().equals(entity.getUsrId())) {
						dto.setUsrUserId(entity.getUsers().getUsId());
						dto.setUsrRoleId(entity.getRole().getRoId());
					}
				}
			}
			return new ResponseEntity<>(dtos, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/UserRole/{id}")
	@PreAuthorize("hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<UserRoleDTO> getById(@PathVariable("id") Integer id){
		try {
			UserRole entity = service.getById(id);
			if (service.getById(id) != null) {
				UserRoleDTO dto = modelMapper.map(entity, UserRoleDTO.class);
				dto.setUsrUserId(entity.getUsers().getUsId());
				dto.setUsrRoleId(entity.getRole().getRoId());
				return new ResponseEntity<>(dto, responseHeaders, HttpStatus.OK);
			}
			return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@PostMapping(value = "/UserRole")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<UserRoleDTO> create(@RequestBody UserRoleDTO dto) {
		try {
			if (UseSer.getById(dto.getUsrUserId()) != null && RolSer.getById(dto.getUsrRoleId()) != null) {
				UserRole entityRequest = modelMapper.map(dto, UserRole.class);
				entityRequest.setUsers(UseSer.getById(dto.getUsrUserId()));
				entityRequest.setRole(RolSer.getById(dto.getUsrRoleId()));
				UserRole entity = service.save(entityRequest);
				UserRoleDTO dtoReponse = modelMapper.map(entity, UserRoleDTO.class);
				dtoReponse.setUsrUserId(entity.getUsers().getUsId());
				dtoReponse.setUsrRoleId(entity.getRole().getRoId());
				return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.CREATED);
			}
			return  new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping(value = "/UserRole/{id}")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<UserRoleDTO> update(@PathVariable("id") Integer id, @RequestBody UserRoleDTO dto) {
		try {
			if (service.getById(id) != null && UseSer.getById(dto.getUsrUserId()) != null && RolSer.getById(dto.getUsrRoleId()) != null) {
				UserRole entityRequest = modelMapper.map(dto, UserRole.class);
				entityRequest.setUsers(UseSer.getById(dto.getUsrUserId()));
				entityRequest.setRole(RolSer.getById(dto.getUsrRoleId()));
				entityRequest.setUsrId(id);
				UserRole entity = service.save(entityRequest);
				UserRoleDTO dtoReponse = modelMapper.map(entity, UserRoleDTO.class);
				dtoReponse.setUsrUserId(entity.getUsers().getUsId());
				dtoReponse.setUsrRoleId(entity.getRole().getRoId());
				return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.CREATED);
			}
			return  new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping(value = "/UserRoleArray")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ACCOUNT') or hasRole('ADMIN')")
	public void deleteArrayUserRole(@RequestBody Integer[] id) {
		service.deleteArray(id);
	}
	
	@DeleteMapping(value = "/UserRole")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> deleteUserRole(@RequestBody Integer id) {
		try {
			UserRole entity = service.getById(id);
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
