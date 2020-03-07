package com.example.parentmgr.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.parentmgr.exception.EntityExistsException;
import com.example.parentmgr.model.Users;
import com.example.parentmgr.repository.UsersRepository;

@Service
public class UserManagerService {

	private final static Logger logger = LoggerFactory.getLogger(UserManagerService.class);

	@Autowired
	UsersRepository usersRepository;

	public Users addUser(Users user) {

		logger.debug("Service to add User");

		if (usersRepository.findByemployeeID(user.getEmployeeID()) == null) {
			return usersRepository.save(user);
		} else {
			throw new EntityExistsException("Users", "employee_id", user.getEmployeeID());
		}

	}


}
