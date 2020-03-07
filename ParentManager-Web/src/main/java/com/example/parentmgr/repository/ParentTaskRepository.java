package com.example.parentmgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.parentmgr.model.Parent_Task;


@Repository
public interface ParentTaskRepository extends JpaRepository<Parent_Task, Long> {
	
	Parent_Task findByParTask(String parTask);

}
