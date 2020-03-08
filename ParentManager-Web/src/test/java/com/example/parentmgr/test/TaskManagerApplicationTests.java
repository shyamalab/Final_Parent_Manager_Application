package com.example.parentmgr.test;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.web.client.HttpClientErrorException;

import com.example.parentmgr.exception.ErrorDetails;
import com.example.parentmgr.model.Parent_Task;
import com.example.parentmgr.model.Project;
import com.example.parentmgr.model.Task;
import com.example.parentmgr.model.Users;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskManagerApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void contextLoads() {

	}

	private String getRootUrl() {
		return "http://localhost:8082/api";
	}

	/**
	 * Index Page Return Text - Success
	 * 
	 * @throws Exception
	 */

	@Test
	public void test01_indexPageShouldReturnDefaultMessage() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:8082/home/", String.class))
				.contains("Welcome to Project Manager Application Page");
	}

	/**
	 * Test to Create New User- Success
	 */

	@Test
	public void test02CreateNewUser() {
		for(int i=0 ;i<=2;i++) {
		Users user = new Users();
		user.setEmployeeID("40660"+i);
		user.setFirstName("Shyamala_"+i);
		user.setLastName("B"+i);
		ResponseEntity<Users> postResponse = restTemplate.postForEntity(getRootUrl() + "/add/user", user, Users.class);
		assertNotNull(postResponse);
		assertEquals(200, postResponse.getStatusCodeValue());
		assertNotNull(postResponse.getBody());
		}
	}

	/**
	 * Test to Update User - Success
	 */
	@Test
	public void test03UpdateUser() {
		int id = 1;
		Users user = restTemplate.getForObject(getRootUrl() + "/get/user/" + id, Users.class);
		user.setFirstName("Shyamala-Upd");
		user.setLastName("Balu");
		restTemplate.put("http://localhost:8082/api/users/" + id, user);
		Users updatedUser = restTemplate.getForObject(getRootUrl() + "/get/user/" + id, Users.class);
		assertNotNull(updatedUser);
	}

	/**
	 * Test to Get All Users - Success
	 */
	@Test
	public void test04GetAllUsers() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/all/users/", HttpMethod.GET, entity,
				String.class);
		Assert.assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
	}

	/**
	 * Test to Get User by Id - Success
	 */

	@Test
	public void test05GetUserById() {
		int id = 1;
		Users user = restTemplate.getForObject(getRootUrl() + "/get/user/" + id, Users.class);
		assertNotNull(user);
	}

	/**
	 * Test to Get User by Invalid Id - Success
	 */

	@Test
	public void test06GetUserByInvalidId() {
		int id = 5;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/get/user/" + id, HttpMethod.GET,
				entity, String.class);
		Assert.assertEquals(404, response.getStatusCodeValue());

	}

	@Test
	public void test07CreateNewUseralreadyExistEmployeeID() {
		Users user = new Users();
		user.setEmployeeID("406602");
		user.setFirstName("sowmiya");
		user.setLastName("Guna");
		ResponseEntity<ErrorDetails> postResponse = restTemplate.postForEntity(getRootUrl() + "/add/user", user,
				ErrorDetails.class);
		assertEquals(409, postResponse.getStatusCodeValue());
	}

	/**
	 * Test to Delete User- Success
	 */
	@Test
	public void test08DeleteUser() {
		int id = 1;
		Users user = restTemplate.getForObject(getRootUrl() + "/get/user/" + id, Users.class);
		assertNotNull(user);
		restTemplate.delete(getRootUrl() + "/delete/user/" + id);
		try {
			user = restTemplate.getForObject(getRootUrl() + "/delete/user/" + id, Users.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Test to Create New Project- Success
	 */

	@Test
	public void test09CreateNewProject() {
		for(int i=0;i<=2;i++) {
		Project project = new Project();
		project.setProject("My First Project"+i);
		project.setStart_Date("11/11/2019");
		project.setEnd_Date("12/11/2020");
		project.setPriority("12");
		project.setProjectstatus(false);
		int id = 2;
		Users user = restTemplate.getForObject(getRootUrl() + "/get/user/" + id, Users.class);
		assertNotNull(user);
		project.setUsers(user);
		ResponseEntity<Project> postResponse = restTemplate.postForEntity(getRootUrl() + "/add/project", project,
				Project.class);
		assertNotNull(postResponse);
		assertEquals(200, postResponse.getStatusCodeValue());
		assertNotNull(postResponse.getBody());
		}
	}

	@Test
	public void test10CreateNewProjectInvalidUserId() {
		Project project = new Project();
		project.setProject("First Project");
		project.setStart_Date("12/11/2019");
		project.setEnd_Date("11/11/2020");
		project.setPriority("12");
		project.setProjectstatus(false);
		this.test06GetUserByInvalidId();
	}

	/**
	 * Test to Update Project - Success
	 */
	@Test
	public void test11UpdateProject() {
		int id = 1;
		int user_id = 2;
		Project project = restTemplate.getForObject(getRootUrl() + "/get/project/" + id, Project.class);
		project.setProject("My Second Project");
		project.setStart_Date("10/11/2020");
		project.setEnd_Date("11/11/2020");
		project.setPriority("16");
		project.setProjectstatus(false);
		if (user_id != project.getUsers().getUser_ID()) {
			Users user = restTemplate.getForObject(getRootUrl() + "/get/user/" + user_id, Users.class);
			assertNotNull(user);
			project.setUsers(user);
		}
		HttpEntity<Project> entity = new HttpEntity<Project>(project);
		restTemplate.exchange("http://localhost:8082/api/project/" + id, HttpMethod.PUT, entity, Project.class);
		Project updatedproject = restTemplate.getForObject(getRootUrl() + "/get/project/" + id, Project.class);
		assertNotNull(updatedproject);
	}

	/**
	 * Test to Get All Projects - Success
	 */
	@Test
	public void test12GetAllProjects() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/all/projects/", HttpMethod.GET, entity,
				String.class);
		Assert.assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
	}

	/**
	 * Test to Get All Active Projects - Success
	 */
	@Test
	public void test13GetAllActiveProjects() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/all/projects/active", HttpMethod.GET,
				entity, String.class);
		Assert.assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
	}

	/**
	 * Test to Get Project by Id - Success
	 */

	@Test
	public void test15GetProjectById() {
		int id = 1;
		Project project = restTemplate.getForObject(getRootUrl() + "/get/project/" + id, Project.class);
		assertNotNull(project);
	}

	/**
	 * Test to Get Project by Invalid Id - Success
	 */

	@Test
	public void test16GetProjectByInvalidId() {
		int id = 7;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/get/project/" + id, HttpMethod.GET,
				entity, String.class);
		Assert.assertEquals(404, response.getStatusCodeValue());

	}

	@Test
	public void test17SuspendProject() {
		int id = 1;
		Project project = restTemplate.getForObject(getRootUrl() + "/get/project/" + id, Project.class);
		project.setProjectstatus(true);
		HttpEntity<Project> entity = new HttpEntity<Project>(project);
		restTemplate.exchange("http://localhost:8082/api/project/" + id, HttpMethod.PUT, entity, Project.class);
		Project suspendproject = restTemplate.getForObject(getRootUrl() + "/get/project/" + id, Project.class);
		assertNotNull(suspendproject);
		assertEquals(true, suspendproject.isProjectstatus());
	}

	@Test
	public void test18AlreadySuspendProject() {
		int id = 1;
		Project project = restTemplate.getForObject(getRootUrl() + "/get/project/" + id, Project.class);
		assertNotNull(project);
		assertEquals(true, project.isProjectstatus());
	}

	/**
	 * Test to Create New Task- Success
	 */

	@Test
	public void test19CreateNewParentTask() {
			Task task = new Task();
			task.setIsparent(true);
			task.setTask("Parent Task");
			ResponseEntity<Task> postResponse = restTemplate.postForEntity(getRootUrl() + "/add/task", task,
					Task.class);
			assertNotNull(postResponse);
			assertEquals(200, postResponse.getStatusCodeValue());
	}
	
	@Test
	public void test20CreateNewTask() {
			Task task = new Task();
			task.setIsparent(false);
			task.setTask("Task 1");
			task.setStart_Date("11/11/2011");
			task.setEnd_Date("11/11/2012");
			task.setPriority("22");
			int parent_id = 1;
			int user_id = 2;
			int project_id = 2;
			Project project = restTemplate.getForObject(getRootUrl() + "/get/project/" + project_id, Project.class);
			Parent_Task parent = restTemplate.getForObject(getRootUrl() + "/get/parenttask/" + parent_id,
					Parent_Task.class);
			Users user = restTemplate.getForObject(getRootUrl() + "/get/user/" + user_id, Users.class);
			assertNotNull(project);
			assertNotNull(parent);
			assertNotNull(user);
			if (parent.getParTask() == null || parent.getParTask().trim().equalsIgnoreCase("")) {
				if (task.getParenTask() == null || task.getParenTask().trim().equalsIgnoreCase("")) {
					task.setParenTask("This Task has No Parent");

				} else {
					task.setParenTask(" Main Task");
				}
			} else {
				task.setParenTask(parent.getParTask());
			}
			task.setParent_Task(parent);
			task.setProject(project);
			task.setUsers(user);
			ResponseEntity<Task> postResponse = restTemplate.postForEntity(getRootUrl() + "/add/task", task,
					Task.class);
			assertNotNull(postResponse);
			assertEquals(200, postResponse.getStatusCodeValue());
	}

	/**
	 * Test to Update TAsk - Success
	 */
	@Test
	public void test21UpdateTask() {

		int task_id = 1;
		Task task = restTemplate.getForObject(getRootUrl() + "/get/task/" + task_id, Task.class);
		task.setIsparent(false);
		task.setTask("Task 1-Updated");
		task.setStart_Date("11/11/2011");
		task.setEnd_Date("11/11/2012");
		task.setPriority("22");
		int parent_id = 1;
		int user_id = 2;
		int project_id = 2;
		if (project_id != task.getProject().getProjectID()) {
			Project project = restTemplate.getForObject(getRootUrl() + "/get/project/" + project_id, Project.class);
			assertNotNull(project);
			task.setProject(project);
		}
		if (user_id != task.getUsers().getUser_ID()) {
			Users user = restTemplate.getForObject(getRootUrl() + "/get/user/" + user_id, Users.class);
			assertNotNull(user);
			task.setUsers(user);
		}
		if (parent_id != task.getParent_Task().getParent_ID()) {
			Parent_Task parent = restTemplate.getForObject(getRootUrl() + "/get/parenttask/" + parent_id,
					Parent_Task.class);
			assertNotNull(parent);
			task.setParent_Task(parent);
		}
		HttpEntity<Task> entity = new HttpEntity<Task>(task);
		restTemplate.exchange("http://localhost:8082/api/task/" + task_id, HttpMethod.PUT, entity, Task.class);
		Task updatedtask = restTemplate.getForObject(getRootUrl() + "/get/task/" + task_id, Task.class);
		assertNotNull(updatedtask);
	}

	/**
	 * Test to Get All Task - Success
	 */
	@Test
	public void test22GetAllTasks() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/all/tasks/", HttpMethod.GET, entity,
				String.class);
		Assert.assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
	}

	/**
	 * Test to Get All Active Task - Success
	 */
	@Test
	public void test23GetAllTaskswithoutCurrentTask() {
		int task_id = 1;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/all/tasks/current/" + task_id,
				HttpMethod.GET, entity, String.class);
		Assert.assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
	}

	/**
	 * Test to Get All Parent Task - Success
	 */
	@Test
	public void test24GetAllParentTasks() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/all/parenttasks/", HttpMethod.GET,
				entity, String.class);
		Assert.assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
	}

	/**
	 * Test to Get TAsk by Id - Success
	 */

	@Test
	public void test25GetTaskById() {
		int id = 1;
		Task task = restTemplate.getForObject(getRootUrl() + "/get/task/" + id, Task.class);
		assertNotNull(task);
	}

	/**
	 * Test to Get Task by Id - Success
	 */

	@Test
	public void test26GetTaskByInvalidId() {
		int id = 3;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/get/task/" + id, HttpMethod.GET,
				entity, String.class);
		Assert.assertEquals(404, response.getStatusCodeValue());

	}

	@Test
	public void test27EndTask() {
		int id = 1;
		Task task = restTemplate.getForObject(getRootUrl() + "/get/task/" + id, Task.class);
		task.setEndTask(true);
		HttpEntity<Task> entity = new HttpEntity<Task>(task);
		restTemplate.exchange("http://localhost:8082/api/end/tasks/" + id, HttpMethod.PUT, entity, Task.class);
		Task endtask = restTemplate.getForObject(getRootUrl() + "/get/task/" + id, Task.class);
		assertNotNull(endtask);
		assertEquals(true, endtask.isEndTask());
	}

	@Test
	public void test28AlreadyEndTask() {
		int id = 1;
		Task task = restTemplate.getForObject(getRootUrl() + "/get/task/" + id, Task.class);
		assertNotNull(task);
		assertEquals(true, task.isEndTask());
	}

}