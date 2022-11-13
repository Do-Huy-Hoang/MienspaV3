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

import com.example.mienspa.dto.OrderProDTO;
import com.example.mienspa.models.OrdersPro;
import com.example.mienspa.models.OrdersProDetail;
import com.example.mienspa.models.Product;
import com.example.mienspa.service.OrderProDetailService;
import com.example.mienspa.service.OrderProService;
import com.example.mienspa.service.ProductService;
import com.example.mienspa.service.UserService;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class OrderProController {
	
	@Autowired
	private OrderProService service;
	
	@Autowired
	private OrderProDetailService DeProSer;
	
	@Autowired
	private ProductService ProSer;
	@Autowired
	private UserService UseSer;

	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();
	
	@GetMapping(value = "/OrdersPro")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<List<OrderProDTO>> getAll(){
		try {
			List<OrdersPro> entityList = service.getAll();
			List<OrderProDTO> dtos = entityList.stream().map(user -> modelMapper.map(user, OrderProDTO.class))
					.collect(Collectors.toList());

			for (OrdersPro entity : entityList) {
				for (OrderProDTO dto : dtos) {
					if (dto.getOrProId().equals(entity.getOrProId())) {
						dto.setOrProUserId(entity.getUsers().getUsId());
					}
				}
			}
			return new ResponseEntity<>(dtos, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/OrdersPro/{userId}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<List<OrderProDTO>> getAllByUserId(@PathVariable("id") String id){
		try {
			List<OrdersPro> entityList = service.getAllByUserId(id);
			List<OrderProDTO> dtos = entityList.stream().map(user -> modelMapper.map(user, OrderProDTO.class))
					.collect(Collectors.toList());

			for (OrdersPro entity : entityList) {
				for (OrderProDTO dto : dtos) {
					if (dto.getOrProId().equals(entity.getOrProId())) {
						dto.setOrProUserId(entity.getUsers().getUsId());
					}
				}
			}
			return new ResponseEntity<>(dtos, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/OrdersPro/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<OrderProDTO> getById(@PathVariable("id") String id){
		try {
			OrdersPro entity = service.getById(id);
			if (service.getById(id) != null) {
				OrderProDTO dto = modelMapper.map(entity, OrderProDTO.class);
				dto.setOrProUserId(entity.getUsers().getUsId());
				return new ResponseEntity<>(dto, responseHeaders, HttpStatus.OK);
			}
			return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@PostMapping(value = "/OrdersPro")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<OrderProDTO> create(@RequestBody OrderProDTO dto) {
		try {
			OrdersPro entityRequest = modelMapper.map(dto, OrdersPro.class);
			entityRequest.setUsers(UseSer.getById(dto.getOrProUserId()));
			OrdersPro entity = service.save(entityRequest);
			for (String proId : dto.getListProId()) {
				if(!proId.isEmpty()) {
					if(ProSer.getById(proId)!= null) {
						Product product = ProSer.getById(proId);
						OrdersProDetail OrProDeEntity = new OrdersProDetail(entity,product);
						DeProSer.save(OrProDeEntity);
					}
				}
			}			
			OrderProDTO dtoReponse = modelMapper.map(entity, OrderProDTO.class);
			dtoReponse.setOrProUserId(entity.getUsers().getUsId());
			return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping(value = "/OrdersPro/{id}")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<OrderProDTO> update(@PathVariable("id") String id, @RequestBody OrderProDTO dto) {
		try {
			if (service.getById(id) != null) {
				OrdersPro entityRequest = modelMapper.map(dto, OrdersPro.class);
				entityRequest.setUsers(UseSer.getById(dto.getOrProUserId()));
				entityRequest.setOrProId(id);
				OrdersPro entity = service.save(entityRequest);
				OrderProDTO dtoReponse = modelMapper.map(entity, OrderProDTO.class);
				dtoReponse.setOrProUserId(entity.getUsers().getUsId());
				return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.ACCEPTED);
			}
			return  new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping(value = "/OrdersProArray")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> deleteArrayOrdersPro(@RequestBody String[] id) {
		try {
			for (String orProId : id) {		
				if(service.getById(orProId) != null) {
					OrdersPro entity = service.getById(orProId);
					for (OrdersProDetail item : entity.getOrdersprodetails()) {
						DeProSer.delete(item);
					}
					service.delete(entity);
				}
			}		
			return new ResponseEntity<>(true, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping(value = "/OrdersPro")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> deleteOrdersPro(@RequestBody String id) {
		try {
			OrdersPro entity = service.getById(id);
			if (service.getById(id) != null) {
				for (OrdersProDetail item : entity.getOrdersprodetails()) {
					DeProSer.delete(item);
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
