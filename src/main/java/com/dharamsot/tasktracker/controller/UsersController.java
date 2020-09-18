package com.dharamsot.tasktracker.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dharamsot.tasktracker.entity.Todo;
import com.dharamsot.tasktracker.entity.Users;
import com.dharamsot.tasktracker.exceptionhandling.UserAlreadyExistException;
import com.dharamsot.tasktracker.repository.UserRepository;

@RestController
public class UsersController {

	
	@Autowired
	private UserRepository  userRepository;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
		
	@PostMapping("/api/register")
	public ResponseEntity<Void> users(@RequestBody Users users) throws UserAlreadyExistException{
		URI uri=null;
		Users checkUser= userRepository.findByUsername(users.getUsername());
		if(checkUser.getId()==null) {
			 uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/api/register").build().toUri();
		throw new UserAlreadyExistException("User already exist");
		}else {
		
		users.setPassword(bcryptEncoder.encode(users.getPassword()));
		
		Users createdUser = userRepository.save(users);
		uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdUser.getId()).toUri();
		}
		
		return ResponseEntity.created(uri).build();
		
	}
		
	
}
