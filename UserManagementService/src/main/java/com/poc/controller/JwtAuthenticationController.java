package com.poc.controller;

import javax.validation.Valid;
import com.poc.config.JwtTokenUtil;
import com.poc.model.JwtRequest;
import com.poc.model.JwtResponse;
import com.poc.model.UserDao;
import com.poc.service.JwtUserDetailsService;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<UserDao> signup(@Valid @RequestBody UserDao user) throws Exception {
		UserDao newUser = userDetailsService.createUser(user);
		return ResponseEntity.ok(newUser);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) throws Exception {
		authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
		final String token = jwtTokenUtil.generateToken(jwtRequest.getUsername());
		return ResponseEntity.ok(new JwtResponse(token));
	}

	public void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			// return true;
		} /*
			 * catch (DisabledException e) {
			 * throw new Exception("USER_DISABLED", e);
			 * }
			 */catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
