package com.example.ProductService.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ProductService.config.JwtTokenUtil;
import com.example.ProductService.external.UserClient;
import com.example.ProductService.model.ProductDao;
import com.example.ProductService.model.UserDao;
import com.example.ProductService.service.ProductServiceDetails;

import java.security.Principal;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductController {
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private ProductServiceDetails productServiceDetails;

	@Autowired
	private UserClient userClient;

	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public ResponseEntity<?> addProduct(@RequestHeader("Authorization") String authorizationHeader,
			@Valid @RequestBody ProductDao productDao,
			Principal principal)
			throws Exception {
		String username = jwtTokenUtil.getUsernameFromToken(authorizationHeader.substring(7));
		UserDao user = userClient.getUser(username, authorizationHeader);
		productServiceDetails.addProduct(user, productDao);
		return ResponseEntity.ok("Product Added");
	}

	@RequestMapping(value = "/getProducts", method = RequestMethod.GET)
	public ResponseEntity<?> getProducts(@RequestHeader("Authorization") String authorizationHeader) {
		String username = jwtTokenUtil.getUsernameFromToken(authorizationHeader.substring(7));
		userClient.getUser(username, authorizationHeader);
		return ResponseEntity.ok(productServiceDetails.getAllProducts());
	}

	@RequestMapping(value = "/deleteProduct", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProduct(@RequestHeader("Authorization") String authorizationHeader,
			@RequestParam(name = "pid") String pid)
			throws Exception {
		String username = jwtTokenUtil.getUsernameFromToken(authorizationHeader.substring(7));
		UserDao currentUser = userClient.getUser(username, authorizationHeader);
		productServiceDetails.isRequestValid(pid, currentUser);
		productServiceDetails.deleteProduct(pid);
		return ResponseEntity.ok("Product Deleted Successfully");
	}

	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
	public ResponseEntity<?> updateProduct(@RequestHeader("Authorization") String authorizationHeader,
			@RequestParam("pid") String pid, @Valid @RequestBody Map<String, Object> fields)
			throws Exception {
		String username = jwtTokenUtil.getUsernameFromToken(authorizationHeader.substring(7));
		UserDao currentUser = userClient.getUser(username, authorizationHeader);
		productServiceDetails.isRequestValid(pid, currentUser);
		productServiceDetails.updateProduct(pid, fields);
		return ResponseEntity.ok("Product updated Successfully");
	}
}