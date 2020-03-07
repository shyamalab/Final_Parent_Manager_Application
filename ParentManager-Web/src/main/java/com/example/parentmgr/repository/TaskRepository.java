package com.example.parentmgr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.parentmgr.model.Project;
import com.example.parentmgr.model.Task;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
	List<Task> findBytaskIDNotIn(List<Long> taskid);	
	List<Task> findByEndTask(boolean endTask);
	Long countByproject(Project project);
	Long countByprojectAndEndTask(Project project, boolean endTask);

}

