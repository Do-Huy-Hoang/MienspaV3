package com.example.mienspa.controller;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

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

import com.example.mienspa.dto.ProductDTO;
import com.example.mienspa.models.Category;
import com.example.mienspa.models.Product;
import com.example.mienspa.service.CategoryService;
import com.example.mienspa.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService service;

	@Autowired
	private CategoryService Cateservice;

	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();
	
	ObjectMapper mapper = new ObjectMapper();

	@GetMapping(value = "/Product")
	@PreAuthorize("hasRole('USER') or hasRole('PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<List<ProductDTO>> getAll() {
		try {
			List<Product> entityList = service.getAll();
			List<ProductDTO> dtos = entityList.stream().map(user -> modelMapper.map(user, ProductDTO.class))
					.collect(Collectors.toList());

			for (Product entity : entityList) {
				for (ProductDTO dto : dtos) {
					if (dto.getProId().equals(entity.getProId())) {
						dto.setCategory_id(entity.getCategory().getCateId());
					}
				}
			}
			return new ResponseEntity<>(dtos, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(value = "/Product/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<ProductDTO> getById(@PathVariable("id") String id) {
		try {
			Product entity = service.getById(id);
			if (service.getById(id) != null) {
				ProductDTO dto = modelMapper.map(entity, ProductDTO.class);
				dto.setCategory_id(entity.getCategory().getCateId());
				return new ResponseEntity<>(dto, responseHeaders, HttpStatus.OK);
			}
			return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping(value = "/Product")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<ProductDTO> create(@RequestPart(required = false) String json,
			@RequestPart(required = false) @ApiParam(required = true, value = "") MultipartFile file) {
		try {
			if (file == null) {

				ProductDTO dto = mapper.readValue(json, ProductDTO.class);
				Product entityRequest = modelMapper.map(dto, Product.class);
				Category cate = Cateservice.getById(dto.getCategory_id());
				entityRequest.setCategory(cate);
				Product entity = service.save(entityRequest);
				ProductDTO dtoReponse = modelMapper.map(entity, ProductDTO.class);
				return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.CREATED);

			} else {

				ProductDTO dto = mapper.readValue(json, ProductDTO.class);
				Product entityRequest = modelMapper.map(dto, Product.class);
				Category cate = Cateservice.getById(dto.getCategory_id());
				entityRequest.setCategory(cate);
				File folder = new File("Images/Products/"+entityRequest.getProId());
				folder.mkdirs();
				Path path = Paths.get(folder.getPath());
				InputStream inputStream = file.getInputStream();
				Files.copy(inputStream, path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				entityRequest.setFeatureImgPath(file.getOriginalFilename().toLowerCase());
				Product entity = service.save(entityRequest);
				ProductDTO dtoReponse = modelMapper.map(entity, ProductDTO.class);
				return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.CREATED);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PutMapping(value = "/Product/{id}")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<ProductDTO> update(@PathVariable("id") String id, @RequestPart(required = false) String json,
			@RequestPart(required = false) @ApiParam(required = true, value = "") MultipartFile file) {
		try {	
			if (service.getById(id) != null) {
				if (file == null) {
					ProductDTO dto = mapper.readValue(json, ProductDTO.class);
					Product entityRequest = modelMapper.map(dto, Product.class);
					Category cate = Cateservice.getById(dto.getCategory_id());
					entityRequest.setCategory(cate);
					entityRequest.setProId(id);
					Product entity = service.save(entityRequest);
					ProductDTO dtoReponse = modelMapper.map(entity, ProductDTO.class);
					dtoReponse.setCategory_id(entity.getCategory().getCateId());
					return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.ACCEPTED);
				} else {				
					ProductDTO dto = mapper.readValue(json, ProductDTO.class);
					//delete old image
					if(dto.getFeatureImgPath() != null) {
						Path oldPath = Paths.get("Images/Products/"+id+"/"+ dto.getFeatureImgPath());
						Files.delete(oldPath);
					}
					
					Product entityRequest = modelMapper.map(dto, Product.class);
					Category cate = Cateservice.getById(dto.getCategory_id());
					entityRequest.setCategory(cate);
					entityRequest.setProId(id);
					File folder=new File("Images/Products/"+entityRequest.getProId());
					folder.mkdirs();
					Path path = Paths.get(folder.getPath());
					InputStream inputStream = file.getInputStream();
					Files.copy(inputStream, path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
					entityRequest.setFeatureImgPath(file.getOriginalFilename().toLowerCase());
					Product entity = service.save(entityRequest);
					ProductDTO dtoReponse = modelMapper.map(entity, ProductDTO.class);
					dtoReponse.setCategory_id(entity.getCategory().getCateId());
					return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.ACCEPTED);

				}
			}
			return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@DeleteMapping(value = "/ProductArray")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> deleteArrayProduct(@RequestBody String[] id) {

		try {
			service.deleteArray(id);
			return new ResponseEntity<>(true, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@DeleteMapping(value = "/Product")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> deleteProduct(@RequestBody String id) {
		try {		
			if (service.getById(id) != null) {
				Product entity = service.getById(id);
				if(entity.getFeatureImgPath() != null) {
					File directoryToDelete = new File("Images/Products/"+entity.getProId());
					FileSystemUtils.deleteRecursively(directoryToDelete);
				}
				service.delete(entity);
				
				return new ResponseEntity<>(true, responseHeaders, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(false, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
