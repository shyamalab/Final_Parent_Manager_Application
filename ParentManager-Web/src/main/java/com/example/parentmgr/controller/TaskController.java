package com.example.parentmgr.controller;

import com.example.parentmgr.model.Users;
import com.example.parentmgr.repository.UsersRepository;
import com.example.parentmgr.service.UserManagerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class TaskController {
	
	private final static  Logger logger = LoggerFactory.getLogger(TaskController.class);
 
    @Autowired
    UserManagerService usermgrService;
        
    @Autowired
    UsersRepository usersRepository;
    
	@PostMapping(path="/add/user")
	public Users addUser(@Valid @RequestBody Users user) {
		logger.info("Add a New User");
		return usermgrService.addUser(user);
	}
	
	@GetMapping(path="/all/users", produces = "application/json")
	public ResponseEntity<List<Users>>  getAllUsers() {
		logger.info("Listed all User details"+usermgrService.getAllUsers());
		List<Users> userslist = usermgrService.getAllUsers();
		
		return ResponseEntity.ok().body(userslist);
	}
	
	@GetMapping(path="/get/user/{id}", produces = "application/json")
	public ResponseEntity<Users> getUser(@PathVariable(value = "id") Long user_Id) {
		logger.info("Retrieve the existing User By Id");
		
		Users user = usermgrService.getUserByID(user_Id);

		return ResponseEntity.ok().body(user);
	}

	@PutMapping(path="/users/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Users> updateUser(@PathVariable(value = "id") Long user_Id, @Valid @RequestBody Users userDetails) {
		logger.info("Updated the existing User");

		Users user =  usermgrService.updateuser(user_Id, userDetails);
		
		return ResponseEntity.ok(user);
	}

}
