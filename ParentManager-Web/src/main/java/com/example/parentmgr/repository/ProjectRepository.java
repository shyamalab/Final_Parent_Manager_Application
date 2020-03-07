package com.example.parentmgr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.parentmgr.model.Project;



@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	List<Project> findAllByProjectstatus(boolean projectStatus);
}
