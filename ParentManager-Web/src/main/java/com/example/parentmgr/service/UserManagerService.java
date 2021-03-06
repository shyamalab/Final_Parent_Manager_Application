package com.example.parentmgr.service;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.parentmgr.exception.EntityExistsException;
import com.example.parentmgr.exception.ResourceNotFoundException;
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
	
	public List<Users> getAllUsers() {

		logger.debug("Service to get list of Users");
		return usersRepository.findAll();
	}

	public Users updateuser(Long userId, Users userDetails) {

		logger.debug("Service to create a Update User");
		
		Users user = usersRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Users", "user_ID", userId));
		
		if (usersRepository.findByemployeeID(userDetails.getEmployeeID()) == null || user.getEmployeeID().equalsIgnoreCase(userDetails.getEmployeeID()) ) {			
			user.setFirstName(userDetails.getFirstName());
			user.setLastName(userDetails.getLastName());
			user.setEmployeeID(userDetails.getEmployeeID());

			logger.debug("Service to update the existing user");
			
		} else {
			throw new EntityExistsException("Users", "employee_id", userDetails.getEmployeeID());
		}	
		return usersRepository.save(user);
	}

	public Users getUserByID(Long userId) throws ResourceNotFoundException {

		logger.debug("Service to get tasks by id");

		Optional<Users> user = usersRepository.findById(userId);

		if (user.isPresent()) {
			return user.get();
		} else {
			throw new ResourceNotFoundException("Users", "user_ID", userId);
		}
	}
	
	public void deleteUserByID(Long userId) {

		logger.debug("Service to delete tasks by id");

		this.getUserByID(userId);

		usersRepository.deleteById(userId);

	}

}
