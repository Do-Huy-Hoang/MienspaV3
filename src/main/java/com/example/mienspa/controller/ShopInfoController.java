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

import com.example.mienspa.dto.ShopInfoDTO;
import com.example.mienspa.models.ShopInfo;
import com.example.mienspa.service.ShopInfoService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ShopInfoController {
	
	@Autowired
	private ShopInfoService service;
	
	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();
	
	@GetMapping(value = "/Shopinfo")
	@PreAuthorize("hasRole('USER') or hasRole('SHOPINFOR') or hasRole('ADMIN')")
	public ResponseEntity<List<ShopInfoDTO>> getAll(){

		try {
			return new ResponseEntity<>(
					service.getAll()
					.stream()
					.map(post -> modelMapper.map(post, ShopInfoDTO.class))
					.collect(Collectors.toList()), 
					responseHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/Shopinfo/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('SHOPINFOR') or hasRole('ADMIN')")
	public ResponseEntity<ShopInfoDTO> getById(@PathVariable("id") String id){
		try {		 
			ShopInfo entity = service.getById(id);
			if (service.getById(id) != null) {
				ShopInfoDTO dto = modelMapper.map(entity, ShopInfoDTO.class);
				return new ResponseEntity<>(dto, responseHeaders, HttpStatus.OK);
			}else {
				return  new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@PostMapping(value = "/Shopinfo")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('SHOPINFOR') or hasRole('ADMIN')")
	public ResponseEntity<ShopInfoDTO> create(@RequestBody ShopInfoDTO dto) {
		try {
			ShopInfo entityRequest = modelMapper.map(dto, ShopInfo.class);
			ShopInfo entity = service.save(entityRequest);
			ShopInfoDTO dtoReponse = modelMapper.map(entity, ShopInfoDTO.class);
			return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping(value = "/Shopinfo/{id}")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('SHOPINFOR') or hasRole('ADMIN')")
	public ResponseEntity<ShopInfoDTO> update(@PathVariable("id") String id, @RequestBody ShopInfoDTO dto) {
		try {
			if (service.getById(id) != null) {
				ShopInfo entityRequest = modelMapper.map(dto, ShopInfo.class);
				entityRequest.setShiId(id);
				ShopInfo entity = service.save(entityRequest);
				ShopInfoDTO dtoReponse = modelMapper.map(entity, ShopInfoDTO.class);
				return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.ACCEPTED);
			}
			return  new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping(value = "/ShopinfoArray")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('SHOPINFOR') or hasRole('ADMIN')")
	public void deleteArrayShopinfo(@RequestBody String[] id) {
		service.deleteArray(id);
	}
	
	@DeleteMapping(value = "/Shopinfo")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('SHOPINFOR') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> deleteShopinfo(@RequestBody String id) {
		try {
			ShopInfo entity = service.getById(id);
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
