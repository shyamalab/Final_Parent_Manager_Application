package com.example.parentmgr.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Project")
@EntityListeners(AuditingEntityListener.class)
public class Project implements Serializable {

	private static final long serialVersionUID = -748956247024967638L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectID;

	@Column(nullable = false)
	private String project;
	
	@Column(nullable = false)
	private String start_Date;
	
	@Column(nullable = false)
	private String end_Date;
	
	@Column(nullable = false)
	private String priority;
	
	@Column(nullable = false, columnDefinition="bit(1) default 0")
	private boolean projectstatus;
	
	@Column(nullable = true, columnDefinition="bigint(20) default 0")
	private Long taskcount;
	
	@Column(nullable = true, columnDefinition = "bigint(20) default 0")
	private Long taskcompletion;
	
	@ManyToOne(cascade=CascadeType.ALL) 
	@JoinColumn(name = "user_ID", insertable = true, updatable = true)
	private Users users;


	public Project(String project, String start_Date, String end_Date, String priority, boolean projectstatus) {
		super();
		this.project = project;
		this.start_Date = start_Date;
		this.end_Date = end_Date;
		this.priority = priority;
		this.projectstatus = projectstatus;
	}



	public Long getProjectID() {
		return projectID;
	}



	public void setProject_ID(Long projectID) {
		this.projectID = projectID;
	}



	public String getProject() {
		return project;
	}



	public void setProject(String project) {
		this.project = project;
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



	public boolean isProjectstatus() {
		return projectstatus;
	}



	public void setProjectstatus(boolean projectstatus) {
		this.projectstatus = projectstatus;
	}



	public Users getUsers() {
		return users;
	}



	public void setUsers(Users users) {
		this.users = users;
	}



	public Long getTaskcount() {
		return taskcount;
	}



	public void setTaskcount(Long taskcount) {
		this.taskcount = taskcount;
	}



	public Long getTaskcompletion() {
		return taskcompletion;
	}



	public void setTaskcompletion(Long taskcompletion) {
		this.taskcompletion = taskcompletion;
	}



	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
