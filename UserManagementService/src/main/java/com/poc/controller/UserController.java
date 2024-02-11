package com.poc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;
import com.poc.model.UserDao;
import com.poc.model.UpdateRole;
import com.poc.service.JwtUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Async
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)

	public CompletableFuture<ResponseEntity<?>> updateUser(@Valid @RequestParam(name = "username") String username,
			@RequestBody Map<String, Object> fields, Principal principal) throws Exception {
		UserDao currentUser = userDetailsService.getUser(principal.getName());
		if (currentUser.getRoles().contains("admin") || currentUser.getUsername().equals(username)) {
			userDetailsService.updateUser(username, fields);
			return CompletableFuture.completedFuture(ResponseEntity.ok("success"));
		} else {
			return CompletableFuture.completedFuture(ResponseEntity.status(401).body("Unauthorized User"));
		}
	}

	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	  public ResponseEntity<?> getUser(@RequestParam(name = "username") String
	  username) throws Exception {
	  UserDao currentUser = userDetailsService.getUser(username);
	  return ResponseEntity.ok(currentUser);
	  }

	@RequestMapping(value = "/updateUserRole", method = RequestMethod.POST)
	public ResponseEntity<?> updateUserRole(@RequestParam(name = "username") String username,
			@RequestBody UpdateRole updateRole,
			Principal principal) throws Exception {
		UserDao currentUser = userDetailsService.getUser(principal.getName());
		if (currentUser.getRoles().contains("admin")) {
			userDetailsService.updateUserRole(username, updateRole.getRoles());
			return ResponseEntity.ok("success");
		} else {
			return ResponseEntity.status(401).body("Not an admin");
		}
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@RequestParam(name = "username") String username, Principal principal)
			throws Exception {
		UserDao currentUser = userDetailsService.getUser(principal.getName());
		if (currentUser.getRoles().contains("admin")) {
			userDetailsService.deleteUser(username);
			return ResponseEntity.status(200).body("success");
		} else {
			return ResponseEntity.status(401).body("Not an admin");
		}
	}

	@RequestMapping(value = "/getAllUsers",method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsers() throws Exception{
		return ResponseEntity.ok(userDetailsService.getAllUsers());
	}

}