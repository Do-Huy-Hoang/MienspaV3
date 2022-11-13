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

import com.example.mienspa.dto.OrdersSerDTO;
import com.example.mienspa.models.OrderSerDetail;
import com.example.mienspa.models.OrdersSer;
import com.example.mienspa.models.Serce;
import com.example.mienspa.service.OrderSerDetailService;
import com.example.mienspa.service.OrderSerService;
import com.example.mienspa.service.SerceService;
import com.example.mienspa.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class OrderSerController {
	
	@Autowired
	private OrderSerService service;
	
	@Autowired
	private OrderSerDetailService OrSerDeSer;
	
	@Autowired
	private SerceService SerSer;
	
	@Autowired
	private UserService UseSer;

	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();
	
	@GetMapping(value = "/OrdersSer")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<List<OrdersSerDTO>> getAll(){
		try {
			List<OrdersSer> entityList = service.getAll();
			List<OrdersSerDTO> dtos = entityList.stream().map(user -> modelMapper.map(user, OrdersSerDTO.class))
					.collect(Collectors.toList());

			for (OrdersSer entity : entityList) {
				for (OrdersSerDTO dto : dtos) {
					if (dto.getOrSerId().equals(entity.getOrSerId())) {
						dto.setOrSerUserId(entity.getUsers().getUsId());
					}
				}
			}
			return new ResponseEntity<>(dtos, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/OrdersSer/{userId}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<List<OrdersSerDTO>> getAllByUserId(@PathVariable("id") String id){
		try {
			List<OrdersSer> entityList = service.getAllByUserId(id);
			List<OrdersSerDTO> dtos = entityList.stream().map(user -> modelMapper.map(user, OrdersSerDTO.class))
					.collect(Collectors.toList());

			for (OrdersSer entity : entityList) {
				for (OrdersSerDTO dto : dtos) {
					if (dto.getOrSerId().equals(entity.getOrSerId())) {
						dto.setOrSerUserId(entity.getUsers().getUsId());
					}
				}
			}
			return new ResponseEntity<>(dtos, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@GetMapping(value = "/OrdersSer/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<OrdersSerDTO> getById(@PathVariable("id") String id){
		try {
			OrdersSer entity = service.getById(id);
			if (service.getById(id) != null) {
				OrdersSerDTO dto = modelMapper.map(entity, OrdersSerDTO.class);
				dto.setOrSerUserId(entity.getUsers().getUsId());
				return new ResponseEntity<>(dto, responseHeaders, HttpStatus.OK);
			}
			return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@PostMapping(value = "/OrdersSer")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<OrdersSerDTO> create(@RequestBody OrdersSerDTO dto) {
		try {
			OrdersSer entityRequest = modelMapper.map(dto, OrdersSer.class);
			entityRequest.setUsers(UseSer.getById(dto.getOrSerUserId()));
			OrdersSer entity = service.save(entityRequest);
			for (String serId : dto.getListSerId()) {
				if(!serId.isEmpty()) {
					if(SerSer.getById(serId)!= null) {
						Serce service = SerSer.getById(serId);
						OrderSerDetail orderSerDetail = new OrderSerDetail(entity,service);
						OrSerDeSer.save(orderSerDetail);
					}
				}
			}			
			OrdersSerDTO dtoReponse = modelMapper.map(entity, OrdersSerDTO.class);
			dtoReponse.setOrSerUserId(entity.getUsers().getUsId());
			return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping(value = "/OrdersSer/{id}")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<OrdersSerDTO> update(@PathVariable("id") String id, @RequestBody OrdersSerDTO dto) {
		try {
			if (service.getById(id) != null) {
				OrdersSer entityRequest = modelMapper.map(dto, OrdersSer.class);
				entityRequest.setUsers(UseSer.getById(dto.getOrSerUserId()));
				entityRequest.setOrSerId(id);
				OrdersSer entity = service.save(entityRequest);
				OrdersSerDTO dtoReponse = modelMapper.map(entity, OrdersSerDTO.class);
				dtoReponse.setOrSerUserId(entity.getUsers().getUsId());
				return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.ACCEPTED);
			}
			return  new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping(value = "/OrdersSerArray")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> deleteArrayOrdersSer(@RequestBody String[] id) {
		try {
			for (String orSerId : id) {		
				if(service.getById(orSerId) != null) {
					OrdersSer entity = service.getById(orSerId);
					for (OrderSerDetail item : entity.getOrderserdetails()) {
						OrSerDeSer.delete(item);
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
	
	@DeleteMapping(value = "/OrdersSer")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> deleteOrdersSer(@RequestBody String id) {
		try {
			OrdersSer entity = service.getById(id);
			if (service.getById(id) != null) {
				for (OrderSerDetail item : entity.getOrderserdetails()) {
					OrSerDeSer.delete(item);
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
