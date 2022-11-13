package com.example.mienspa.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@CrossOrigin
@Controller
@RequestMapping("/image")
public class GetImageController {
	 @RequestMapping(value = "/user/{photo}", method = RequestMethod.GET)
	 @ResponseBody
	    public ResponseEntity<ByteArrayResource> getUserImages(@PathVariable("photo") String photo) {
	        if (photo != null || !photo.equals(" ")) {
	            try {
	                Path fileName = Paths.get("Images/Users", photo);
	                byte[] buffet = Files.readAllBytes(fileName);
	                ByteArrayResource byteArrayResource = new ByteArrayResource(buffet);
	                return ResponseEntity
	                        .ok()
	                        .contentLength(buffet.length)
	                        .contentType(MediaType.parseMediaType("image/png"))
	                        .body(byteArrayResource);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return ResponseEntity.badRequest().build();
	    }
	 
	 @RequestMapping(value = "/product/{photo}", method = RequestMethod.GET)
	 @ResponseBody
	    public ResponseEntity<ByteArrayResource> getProductImages(@PathVariable("photo") String photo) {
	        if (photo != null || !photo.equals(" ")) {
	            try {
	                Path fileName = Paths.get("Images/Product", photo);
	                byte[] buffet = Files.readAllBytes(fileName);
	                ByteArrayResource byteArrayResource = new ByteArrayResource(buffet);
	                return ResponseEntity
	                        .ok()
	                        .contentLength(buffet.length)
	                        .contentType(MediaType.parseMediaType("image/png"))
	                        .body(byteArrayResource);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return ResponseEntity.badRequest().build();
	    }
	 
	 @RequestMapping(value = "/service/{photo}", method = RequestMethod.GET)
	 @ResponseBody
	    public ResponseEntity<ByteArrayResource> getServiceImages(@PathVariable("photo") String photo) {
	        if (photo != null || !photo.equals(" ")) {
	            try {
	                Path fileName = Paths.get("Images/Service", photo);
	                byte[] buffet = Files.readAllBytes(fileName);
	                ByteArrayResource byteArrayResource = new ByteArrayResource(buffet);
	                return ResponseEntity
	                        .ok()
	                        .contentLength(buffet.length)
	                        .contentType(MediaType.parseMediaType("image/png"))
	                        .body(byteArrayResource);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return ResponseEntity.badRequest().build();
	    }
}
