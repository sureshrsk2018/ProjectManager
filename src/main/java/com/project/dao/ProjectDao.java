package com.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Project;


@Repository
public interface ProjectDao<P> extends CrudRepository<Project,Long>  {
	public List<Project> findAllByOrderByStartDateAsc();
	public List<Project> findAllByOrderByEndDateAsc();
	public List<Project> findAllByOrderByPriorityAsc();
	public List<Project> findAllByOrderByCompletedTasksAsc();
	public List<Project> findByProjectContainingIgnoreCase(String project);
	@Query(value ="select p.project_id,p.Project,p.start_date,p.end_date,p.priority,count(distinct t.task_id) as numberOfTasks, \n" + 
			"(select count(distinct t.task_id) from FSDtask where project_id=p.project_id and status='Completed') as completedTasks \n" + 
			" from FSD.project p inner join FSD.task t \n" + 
			"on p.project_id=t.project_id group by p.project_id,p.Project,p.start_date,p.end_date,p.priority",nativeQuery=true )
		
	public List<Project> finaAllProjectsWithTaskCount();
	
}
