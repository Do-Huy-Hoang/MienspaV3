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

import com.example.mienspa.dto.OrderProDetailsDTO;
import com.example.mienspa.models.OrdersProDetail;
import com.example.mienspa.service.OrderProDetailService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class OrderProDetailsController {
	
	@Autowired
	private OrderProDetailService service;

	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();
	@GetMapping(value = "/OrderProDetail/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<List<OrderProDetailsDTO>> getAllByOrProId(@PathVariable("id") String id){
		try {
			if(service.getAllByOrProId(id) != null) {
				List<OrdersProDetail> entityList = service.getAllByOrProId(id);
				List<OrderProDetailsDTO> dtos = entityList.stream().map(user -> modelMapper.map(user, OrderProDetailsDTO.class))
						.collect(Collectors.toList());

				for (OrdersProDetail entity : entityList) {
					for (OrderProDetailsDTO dto : dtos) {
						if (dto.getOrdProId().equals(entity.getOrdProId())) {
							dto.setOrdProOrderId(entity.getOrderspro().getOrProId());
							dto.setOrdProProductId(entity.getProduct().getProId());
						}
					}
				}
				return new ResponseEntity<>(dtos, responseHeaders, HttpStatus.OK);
			}
			return  new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
