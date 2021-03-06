package com.example.parentmgr.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Task")
@EntityListeners(AuditingEntityListener.class)
public class Task implements Serializable {

	private static final long serialVersionUID = -748956247024967638L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long taskID;

	@Column(nullable = false)
	private String task;
	
	@Column
	private boolean isparent;

	@Column(nullable = false)
	private String start_Date;

	@Column(nullable = false)
	private String end_Date;

	@Column(nullable = false)
	private String priority;

	@Column(nullable = false, columnDefinition="bit(1) default 0")
	private boolean endTask;
	
	@Column(nullable = false)
	private String parenTask;

//	@JsonManagedReference
    @ManyToOne( fetch = FetchType.EAGER,cascade=CascadeType.MERGE) 
	@JoinColumn(name = "parent_ID", updatable = true, nullable = true)
	private Parent_Task parent_Task;

    @ManyToOne(cascade=CascadeType.ALL) 
	@JoinColumn(name = "projectID", insertable = true, updatable = true)
	private Project project;
    
    @ManyToOne(cascade=CascadeType.ALL) 
   	@JoinColumn(name = "user_ID", insertable = true, updatable = true)
   	private Users users;
    
	public Task() {

		// TODO Auto-generated constructor stub
	}

	public Long getTaskID() {
		return taskID;
	}



	public void setTask_ID(Long taskID) {
		this.taskID = taskID;
	}



	public String getTask() {
		return task;
	}



	public void setTask(String task) {
		this.task = task;
	}



	public boolean isIsparent() {
		return isparent;
	}



	public void setIsparent(boolean isparent) {
		this.isparent = isparent;
	}



	public String getStart_Date() {
		return start_Date;
	}



	public void setStart_Date(String start_Date) {
		this.start_Date = start_Date;
	}

	public String getEnd_Date() {
		return end_Date;
	}

	public void setEnd_Date(String end_Date) {
		this.end_Date = end_Date;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public boolean isEndTask() {
		return endTask;
	}

	public void setEndTask(boolean endTask) {
		this.endTask = endTask;
	}

	public Parent_Task getParent_Task() {
		return parent_Task;
	}

	public void setParent_Task(Parent_Task parent_Task) {
		this.parent_Task = parent_Task;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getParenTask() {
		return parenTask;
	}

	public void setParenTask(String parenTask) {
		this.parenTask = parenTask;
	}

	
}
