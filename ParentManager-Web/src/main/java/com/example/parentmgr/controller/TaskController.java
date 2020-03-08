package com.example.parentmgr.controller;

import com.example.parentmgr.model.Parent_Task;
import com.example.parentmgr.model.Project;
import com.example.parentmgr.model.Task;
import com.example.parentmgr.model.Users;
import com.example.parentmgr.repository.ParentTaskRepository;
import com.example.parentmgr.repository.ProjectRepository;
import com.example.parentmgr.repository.UsersRepository;
import com.example.parentmgr.service.ProjectManagerService;
import com.example.parentmgr.service.TaskManagerService;
import com.example.parentmgr.service.UserManagerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class TaskController {
	
	private final static Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	TaskManagerService taskmgrService;

	@Autowired
	UserManagerService usermgrService;

	@Autowired
	ProjectManagerService projectmgrService;

	@Autowired
	ParentTaskRepository parenttaskRepository;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	ProjectRepository projectRepository;
	    
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
	
	@DeleteMapping(path="/delete/user/{id}", produces = "application/json")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long user_Id) {
		logger.info("Delete the existing User By Id");
		Map<String, Boolean> response = new HashMap<>();
		if (user_Id != null) {
			usermgrService.deleteUserByID(user_Id);
			
	        response.put("deleted the User Details", Boolean.TRUE);
		} else {
			throw new IllegalArgumentException(" User Id must not be null");
		}
		 return response;
	}
	

	@PostMapping(path="/add/project")
	public Project addProject(@Valid @RequestBody Project project) {
		logger.info("Add a New Project"+project);
		return projectmgrService.addProject(project);
	}
	
	@GetMapping(path="/get/project/{id}", produces = "application/json")
	public ResponseEntity<Project> getProject(@PathVariable(value = "id") Long project_ID) {
		logger.info("Retrieve the existing Project By Id");

		Project project = projectmgrService.getProjectByID(project_ID);
		
		return ResponseEntity.ok().body(project);
	}

	@PutMapping(path="/project/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Project> updateProject(@PathVariable(value = "id") Long project_ID, @Valid @RequestBody Project projectDetails) {
		logger.info("Updated the existing Project");

		Project project = projectmgrService.updateProject(project_ID, projectDetails);
		
		return ResponseEntity.ok().body(project);
	}
	
	@GetMapping(path="/all/projects", produces = "application/json")
	public ResponseEntity<List<Project>> getAllProject() {
		logger.info("Listed all User details");
		List<Project> projectList = projectmgrService.getAllProjects();
		
		return ResponseEntity.ok().body(projectList);
	}
	
	@GetMapping(path="/all/projects/active", produces = "application/json")
	public ResponseEntity<List<Project>> getAllActiveProject() {
		logger.info("Listed all Active Project details");
		List<Project> projectList = projectmgrService.getAllActiveProjects();
		
		return ResponseEntity.ok().body(projectList);
	}
	
	   @PostMapping(path="/add/task")
	    public void addTask(@Valid @RequestBody Task task) {
	    	logger.info("Created a new Task");
	    	
	    	if(task.isIsparent()) {
	    		 taskmgrService.createParentTask(task);
	    	} else {
	             taskmgrService.createTask(task);
	    	}
	  }

	    @GetMapping(path="/all/tasks", produces = "application/json")
	    public ResponseEntity<List<Task>> getAllTasks() {
	    	logger.info("Listed all Task details");
	    	List<Task> taskList = taskmgrService.getAllTasks();
	    
	    	return ResponseEntity.ok().body(taskList);
	    }
	    
	    @GetMapping(path="/all/tasks/current/{id}", produces = "application/json")
	    public ResponseEntity<List<Task>> getAllTaskswithoutCurrent(@PathVariable(value = "id") Long taskId) {
	    	logger.info("Listed all Task details without current task");
	    	List<Task> taskList = taskmgrService.getAllTaskswithoutCurrent(taskId);
	    
	    	return ResponseEntity.ok().body(taskList);
	    }

	    @GetMapping(path="/get/task/{id}", produces = "application/json")
	    public ResponseEntity<Task> getTask(@PathVariable(value = "id") Long taskId) {
	    	logger.info("Retrieve the existing Task By Id");

	    	Task task = taskmgrService.getTaskByID(taskId);
	    	return ResponseEntity.ok().body(task);
	    }
	    
	    @GetMapping(path="/get/parenttask/{id}", produces = "application/json")
	    public ResponseEntity<Parent_Task> getParentTask(@PathVariable(value = "id") Long partaskId) {
	    	logger.info("Retrieve the existing Parent Task By Id");

	    	Parent_Task partask = taskmgrService.getParentTaskByID(partaskId);
	    	return ResponseEntity.ok().body(partask);
	    }
	    
	    @GetMapping(path="/all/parenttasks", produces = "application/json")
	    public ResponseEntity<List<Parent_Task>> getAllParentTasks() {
	    	logger.info("Listed all Parent Task details");
	    	List<Parent_Task> partaskList = taskmgrService.getAllParentTasks();
	    
	    	return ResponseEntity.ok().body(partaskList);
	    }

	    @PutMapping(path="/tasks/{id}", consumes = "application/json", produces = "application/json")
	    public ResponseEntity<Task> updateTask(@PathVariable(value = "id") Long taskId,
	                                           @Valid @RequestBody Task taskDetails) {
	    	logger.info("Updated the existing Task");

	        Task task = taskmgrService.updateTask(taskId, taskDetails);
	        
	        return ResponseEntity.ok().body(task);
	    }
	    
	    @PutMapping(path="/end/tasks/{id}", produces = "application/json")
	    public ResponseEntity<Task> updateEndTask(@PathVariable(value = "id") Long taskId,
	                                           @Valid @RequestBody Task taskDetails) {
	    	logger.info("End a created Task");

	    	Task task = taskmgrService.updateEndTask(taskId, taskDetails);
	    	
	    	return ResponseEntity.ok().body(task);
	    }
	
}
