package com.example.parentmgr.controller;

import com.example.parentmgr.model.Users;
import com.example.parentmgr.repository.UsersRepository;
import com.example.parentmgr.service.UserManagerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
