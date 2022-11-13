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

import com.example.mienspa.dto.OrderSerDetailDTO;
import com.example.mienspa.models.OrderSerDetail;
import com.example.mienspa.service.OrderSerDetailService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class OrderSerDetailController {
	
	@Autowired
	private OrderSerDetailService service;
	
	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();
	@GetMapping(value = "/OrderSerDetail/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<List<OrderSerDetailDTO>> getAllByOrProId(@PathVariable("id") String id){
		try {
			if(service.getAllByOrSerId(id) != null) {
				List<OrderSerDetail> entityList = service.getAllByOrSerId(id);
				List<OrderSerDetailDTO> dtos = entityList.stream().map(user -> modelMapper.map(user, OrderSerDetailDTO.class))
						.collect(Collectors.toList());

				for (OrderSerDetail entity : entityList) {
					for (OrderSerDetailDTO dto : dtos) {
						if (dto.getOrdSerId().equals(entity.getOrdSerId())) {
							dto.setOrdSerOrderId(entity.getOrdersser().getOrSerId());
							dto.setOrdSerServiceId(entity.getSerce().getSeId());
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
