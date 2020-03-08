package com.example.parentmgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.parentmgr.exception.ResourceNotFoundException;
import com.example.parentmgr.model.Parent_Task;
import com.example.parentmgr.model.Project;
import com.example.parentmgr.model.Task;
import com.example.parentmgr.model.Users;
import com.example.parentmgr.repository.ParentTaskRepository;
import com.example.parentmgr.repository.ProjectRepository;
import com.example.parentmgr.repository.TaskRepository;
import com.example.parentmgr.repository.UsersRepository;


@Service
public class TaskManagerService {

	private final static Logger logger = LoggerFactory.getLogger(TaskManagerService.class);

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	ParentTaskRepository parenttaskRepository;

	@Autowired
	ProjectManagerService projectmanagerservice;

	public List<Task> getAllTasks() {

		logger.debug("Service to get list of tasks");
		return taskRepository.findAll();
	}
	
	public List<Task> getAllTaskswithoutCurrent(Long id) {

		logger.debug("Service to get list of tasks without current Task");
		List<Long> idList = new ArrayList<Long>();
		idList.addAll(taskRepository.findByEndTask(true).stream().map(Task :: getTaskID).collect(Collectors.toList()));
		idList.add(id);
		return taskRepository.findBytaskIDNotIn(idList);
	}

	public List<Parent_Task> getAllParentTasks() {

		logger.debug("Service to get list of parent tasks");
		return parenttaskRepository.findAll();
	}

	public Task getTaskByID(Long id) {

		logger.debug("Service to get tasks by id");

		Optional<Task> task = taskRepository.findById(id);

		if (task.isPresent()) {
			return task.get();
		} else {
			throw new ResourceNotFoundException("Task", "id", id);
		}
	}

	public Parent_Task getParentTaskByID(Long id) {

		logger.debug("Service to get tasks by id");

		Optional<Parent_Task> partask = parenttaskRepository.findById(id);

		if (partask.isPresent()) {
			return partask.get();
		} else {
			throw new ResourceNotFoundException("Parent_Task", "id", id);
		}
	}

	public void createTask(Task task) {

		logger.debug("Service to create a new task");

		if (!task.isIsparent()) {
			Project project = projectRepository.findById(task.getProject().getProjectID()).orElseThrow(
					() -> new ResourceNotFoundException("Project", "projectID", task.getProject().getProjectID()));
			Users user = usersRepository.findById(task.getUsers().getUser_ID())
					.orElseThrow(() -> new ResourceNotFoundException("Users", "user_ID", task.getUsers().getUser_ID()));
			if (task.getParent_Task() != null
					&& task.getParenTask().equalsIgnoreCase(task.getParent_Task().getParTask())) {
				Parent_Task parentTask = parenttaskRepository.findById(task.getParent_Task().getParent_ID())
						.orElseThrow(() -> new ResourceNotFoundException("Parent_Task", "parent_ID",
								task.getParent_Task().getParent_ID()));
				task.setParenTask(parentTask.getParTask());
				task.setParent_Task(parentTask);
			}

			if (task.getParenTask() == null || task.getParenTask().equalsIgnoreCase("")) {
				task.setParenTask("This Task has No Parent Task");
			}

			if (taskRepository.countByproject(project) != null) {
				project.setTaskcount(taskRepository.countByproject(project) + 1);
			}

			task.setProject(project);
			task.setUsers(user);

			taskRepository.save(task);
		}
	}

	public void createParentTask(Task task) {

		logger.debug("Service to create a new Parent task");

		if (task.isIsparent()) {
			Parent_Task parentTask = new Parent_Task();
			parentTask.setParTask(task.getTask());
			parenttaskRepository.save(parentTask);

		}
	}

	public Task updateTask(Long taskId, Task taskDetails) {

		logger.debug("Service to create a Update task");

		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
		task.setTask(taskDetails.getTask());
		task.setPriority(taskDetails.getPriority());
		task.setStart_Date(taskDetails.getStart_Date());
		task.setEnd_Date(taskDetails.getEnd_Date());
		task.setEndTask(taskDetails.isEndTask());
		task.setParenTask(taskDetails.getParenTask());
		Project project = projectRepository.findById(taskDetails.getProject().getProjectID()).orElseThrow(
				() -> new ResourceNotFoundException("Project", "projectID", task.getProject().getProjectID()));
		Users user = usersRepository.findById(taskDetails.getUsers().getUser_ID())
				.orElseThrow(() -> new ResourceNotFoundException("Users", "user_ID", task.getUsers().getUser_ID()));
		if (task.getParent_Task() != null
				&& task.getParenTask().equalsIgnoreCase(task.getParent_Task().getParTask())) {
			Parent_Task parentTask = parenttaskRepository.findById(task.getParent_Task().getParent_ID())
					.orElseThrow(() -> new ResourceNotFoundException("Parent_Task", "parent_ID",
							task.getParent_Task().getParent_ID()));
			task.setParenTask(parentTask.getParTask());
			task.setParent_Task(parentTask);
		}

		if (task.getParenTask() == null || task.getParenTask().equalsIgnoreCase("")) {
			task.setParenTask("This Task has No Parent Task");
		}

		if (task.getProject().getProjectID() != taskDetails.getProject().getProjectID()) {

			if (taskRepository.countByproject(project) != null) {
				project.setTaskcount(taskRepository.countByproject(project) + 1);
			}

			Project prevproject = projectRepository.findById(task.getProject().getProjectID()).orElseThrow(
					() -> new ResourceNotFoundException("Project", "projectID", task.getProject().getProjectID()));

			if (taskRepository.countByproject(prevproject) != null) {
				prevproject.setTaskcount(taskRepository.countByproject(prevproject) - 1);
				projectRepository.save(prevproject);
			}
		}

		task.setProject(project);
		task.setUsers(user);
		logger.debug("Service to update the existing task");

		return taskRepository.save(task);

	}

	public Task updateEndTask(Long taskId, Task taskDetails) {

		logger.debug("Service to end a task");

		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
		task.setTask(taskDetails.getTask());
		task.setPriority(taskDetails.getPriority());
		task.setStart_Date(taskDetails.getStart_Date());
		task.setEnd_Date(taskDetails.getEnd_Date());
		task.setEndTask(taskDetails.isEndTask());
		Project project = projectRepository.findById(taskDetails.getProject().getProjectID()).orElseThrow(
				() -> new ResourceNotFoundException("Project", "projectID", task.getProject().getProjectID()));
		Users user = usersRepository.findById(taskDetails.getUsers().getUser_ID())
				.orElseThrow(() -> new ResourceNotFoundException("Users", "user_ID", task.getUsers().getUser_ID()));
		if (taskDetails.getParent_Task() != null) {
			Parent_Task parentTask = parenttaskRepository.findById(taskDetails.getParent_Task().getParent_ID())
					.orElseThrow(() -> new ResourceNotFoundException("Parent_Task", "parent_ID",
							taskDetails.getParent_Task().getParent_ID()));
			task.setParent_Task(parentTask);
		}

		if (taskRepository.countByprojectAndEndTask(project, true) != null) {
			project.setTaskcompletion(taskRepository.countByprojectAndEndTask(project, true) + 1);
		}

		projectRepository.save(project);

		task.setProject(project);
		task.setUsers(user);
		logger.debug("Service to update the TAsk Completion");

		return taskRepository.save(task);

	}

}
