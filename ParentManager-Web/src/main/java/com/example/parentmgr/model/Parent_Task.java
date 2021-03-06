package com.example.parentmgr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;


@Entity
@Table(name = "Parent_Task")
public class Parent_Task implements Serializable{
	
	 private static final long serialVersionUID = 8495817802073010928L;
	
	    @Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long parent_ID;
	
	    @Column(nullable = false)
	    private String parTask;
	 	    
		
		public Parent_Task() {
			super();
		}


		public String getParTask() {
			return parTask;
		}


		public void setParTask(String parTask) {
			this.parTask = parTask;
		}


		public Long getParent_ID() {
			return parent_ID;
		}


		public void setParent_ID(Long parent_ID) {
			this.parent_ID = parent_ID;
		}





}
