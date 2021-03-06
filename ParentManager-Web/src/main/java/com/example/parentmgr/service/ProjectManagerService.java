package com.example.parentmgr.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.parentmgr.exception.ResourceNotFoundException;
import com.example.parentmgr.model.Project;
import com.example.parentmgr.model.Users;
import com.example.parentmgr.repository.ProjectRepository;
import com.example.parentmgr.repository.UsersRepository;

@Service
public class ProjectManagerService {

	private final static Logger logger = LoggerFactory.getLogger(ProjectManagerService.class);

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	UsersRepository usersRepository;

	public Project addProject(Project project) {

		logger.debug("Service to create a new project");

		if (usersRepository.findById(project.getUsers().getUser_ID()) == null) {
			throw new ResourceNotFoundException("Users", "firstName", project.getUsers().getFirstName());
		} else {
			project.setUsers(usersRepository.findById(project.getUsers().getUser_ID()).get());
		}
		return projectRepository.save(project);
	}

	public Project getProjectByID(Long projectId) {

		logger.debug("Service to get tasks by id");

		Optional<Project> project = projectRepository.findById(projectId);

		if (project.isPresent()) {
			return project.get();
		} else {
			throw new ResourceNotFoundException("Project", "projectID", projectId);
		}
	}

	public Project updateProject(Long projectId, Project projectDetails) {

		logger.debug("Service to create a Update Project");

		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new ResourceNotFoundException("Project", "projectID", projectId));

		project.setProject(projectDetails.getProject());
		project.setStart_Date(projectDetails.getStart_Date());
		project.setEnd_Date(projectDetails.getEnd_Date());
		project.setPriority(projectDetails.getPriority());
		project.setProjectstatus(projectDetails.isProjectstatus());

		Optional<Users> user = usersRepository.findById(projectDetails.getUsers().getUser_ID());
		
		if (user.isPresent()) {
			project.setUsers(user.get());
		} else {
			throw new ResourceNotFoundException("Users", "firstName", projectDetails.getUsers().getFirstName());
		}

		logger.debug("Service to update the existing Project");

		return projectRepository.save(project);

	}

	public List<Project> getAllProjects() {

		logger.debug("Service to get list of Projects");
		return projectRepository.findAll();
	}
	
	public List<Project> getAllActiveProjects() {

		logger.debug("Service to get list of Active Projects");
		return projectRepository.findAllByProjectstatus(false);
	}
	


}
