package com.poc.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.poc.exception.InvalidRoleNameException;
import com.poc.exception.UnauthorizedUserUpdateException;
import com.poc.exception.UserAlreadyExistException;
import com.poc.exception.UserNotFoundException;
import com.poc.model.BuyerSellerDao;
import com.poc.model.UserDao;
import com.poc.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.kafka.core.KafkaTemplate;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDao user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		// Added Comment
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	public UserDao getUser(String username) {
		return userRepo.findByUsername(username);
	}

	public List<UserDao> getAllUsers(){
		return userRepo.findAll();
	}

	public UserDao updateUserRole(String username, List<String> newRoles) throws InvalidRoleNameException {
		List<String> roleList = new ArrayList<>();
		roleList.add("admin");
		roleList.add("user");
		for (String i : newRoles) {
			if (roleList.contains(i) == false) {
				throw new InvalidRoleNameException("Please enter valid role");
			}
		}
		UserDao userToUpdate = getUser(username);
		userToUpdate.setRoles(newRoles);
		return userRepo.save(userToUpdate);
	}

	public UserDao updateUser(String username, Map<String, Object> fields) {

		UserDao findUser = userRepo.findByUsername(username);
		if (findUser == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		if (findUser.getRoles().contains("admin") == false && findUser.getUsername().equals(username) == false) {
			throw new UnauthorizedUserUpdateException("Unauthorized User");
		}
		for (Map.Entry<String, Object> entry : fields.entrySet()) {
			if (entry.getKey().equals("firstname")) {
				findUser.setFirstname((String) entry.getValue());
			} else if (entry.getKey().equals("lastname")) {
				findUser.setLastname((String) entry.getValue());
			} else if (entry.getKey().equals("email")) {
				findUser.setEmail((String) entry.getValue());
			}
		}
		// getUpdatedUser().add(username);

		kafkaTemplate.send("java_in", new BuyerSellerDao(findUser.getUsername(), findUser.getFirstname(),
				findUser.getLastname(), findUser.getEmail()));
		// kafkaTemplate.send("java_in", findUser);
		return userRepo.save(findUser);
	}

	public void deleteUser(String username) {
		if (userRepo.findByUsername(username) == null) {
			throw new UserNotFoundException("Username " + username + " is not found");
		}
		UserDao userToDelete = getUser(username);
		userRepo.delete(userToDelete);
	}

	public UserDao createUser(UserDao user) {
		if (userRepo.findByUsername(user.getUsername()) != null) {
			throw new UserAlreadyExistException("Username " + user.getUsername() + " is already taken");
		}
		UserDao newUser = new UserDao(user.getUsername(), user.getFirstname(), user.getLastname(),
				bcryptEncoder.encode(user.getPassword()),
				user.getEmail(), user.getRoles());
		return userRepo.save(newUser);
	}

}