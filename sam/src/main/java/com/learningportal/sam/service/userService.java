package com.learningportal.sam.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;



import org.springframework.stereotype.Service;

import com.learningportal.sam.entity.user;
import com.learningportal.sam.repository.userRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class userService {
	
	private userRepository userRepository;
	private static Logger logger=Logger.getLogger(userService.class.getName());
	
	public user authenticateUser(String email) {
		List<user> userList = userRepository.findByEmail(email);
		
		if (userList.size() != 1) {
			return null;
		}
		
		return userList.get(0);
	}
	
	public List<String> getAllUsers() {
		List<user> userList = userRepository.findAll();
		
		if (userList.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<String> userResponseList = new ArrayList<>();
		userList.forEach(user -> {
			String userResponse = "User Name: " + user.getName() + ", Email: " + user.getEmail() + ", Role: " + user.getRole();
			userResponseList.add(userResponse);
		});
		return userResponseList;
	}
	
	public user findUserById(long id) {
		user userOptional = userRepository.findById(id).get(); 
		return userOptional;
	}
	
	public String createUser(String name, String email, String role) {
		try {
			// validate fields
			if (!isValidEmail(email) || name == null || 
					name.length() == 0 || role == null || 
					!(role.equals("ADMIN") || role.equals("AUTHOR") || role.equals("LEARNER")
			)) {
				throw new Exception("Values to create user are not valid.");
			}
			
			user user = new user();
			user.setName(name);
			user.setEmail(email);
			user.setRole(role);
			
			userRepository.save(user);
			
			return "User Created: " + user.getName();
		} catch (Exception e) {
			System.out.println("Failed to save the user in the database");
			return null;
		}
	}
	
	public boolean deleteUser(long id) {
		Optional<user> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			userRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	public String updateUser(long id, String name, String email, String role) {
		Optional<user> response = userRepository.findById(id);
		
		if (response.isPresent()) {
			user user = response.get();
			
			if (email != null && isValidEmail(email)) {
				user.setEmail(email);
			}
			
			if (name != null) {
				user.setName(name);
			}
			
			if (role != null && (role.equals("ADMIN") || role.equals("AUTHOR") || role.equals("LEARNER"))) {
				user.setRole(role);
			}
			
			userRepository.save(user);
			
			return "User Updated: " + user.getName();
		}
		return "User not found";
	}
	
	boolean isValidEmail(String email) {
		final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		return email.matches(EMAIL_REGEX);
	}
}

