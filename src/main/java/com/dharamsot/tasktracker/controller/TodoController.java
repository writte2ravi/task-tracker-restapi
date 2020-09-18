package com.dharamsot.tasktracker.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dharamsot.tasktracker.entity.Todo;
import com.dharamsot.tasktracker.repository.TodoJpaRepository;
import java.time.LocalDateTime;
@RestController
public class TodoController {
	
	@Autowired
	private TodoJpaRepository todoJpaRepository;

	
	@GetMapping("/api/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username){
		
		List<Todo> todoList=new ArrayList<>();
		todoList.addAll(todoJpaRepository.findByUsername(username));
		todoList.addAll(todoJpaRepository.findByManager(username));
		

		return todoList;
	}

	@GetMapping("/api/users/{username}/todos/{id}")
	public Todo getTodo(@PathVariable String username, @PathVariable long id){
		return todoJpaRepository.findById(id).get();
		

	}

	

	@DeleteMapping("/api/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(
			@PathVariable String username, @PathVariable long id) {

		todoJpaRepository.deleteById(id);

		return ResponseEntity.noContent().build();
	}
	

	
	@PutMapping("/api/users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(
			@PathVariable String username,
			@PathVariable long id, @RequestBody Todo todo){
		
		todo.setUsername(username);
		
		Todo todoUpdated = todoJpaRepository.save(todo);
		
		return new ResponseEntity<Todo>(todoUpdated, HttpStatus.OK);
	}
	
	@PostMapping("/api/users/{username}/todos")
	public ResponseEntity<Void> createTodo(
			
			@PathVariable String username, @RequestBody Todo todo){
		
		todo.setUsername(username);		
		Todo createdTodo = todoJpaRepository.save(todo);
		
		//Location
		//Get current resource url
		///{id}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
		
}
