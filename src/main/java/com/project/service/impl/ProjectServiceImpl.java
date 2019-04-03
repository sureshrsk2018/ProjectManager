package com.project.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.ProjectDao;
import com.project.dao.TaskDao;
import com.project.dao.UserDao;
import com.project.entity.Project;
import com.project.entity.Task;
import com.project.entity.User;
import com.project.service.ProjectService;

@Service
public class  ProjectServiceImpl implements ProjectService {
	
	@Autowired
	ProjectDao<Project> projectDao;

	@Autowired
	UserDao<User> userDao;
	
	@Autowired
	TaskDao<Task> taskDao;

	@Override
	public List<Project> addProject(Project project) {
		User user  = project.getUser();
		project.setUser(null);
		//project.setTaskSet(null);
		Project savedProject = projectDao.save(project);
		user.setProject(savedProject);
		//user.setTasks(null);
		userDao.save(user);
		return viewProjects();
	}

	@Override
	public List<Project> deleteProject(long projectId) {
		Project project = projectDao.findOne(projectId);
		User user = project.getUser();
		if(user!=null) {
		user.setProject(null);
		user.setTask(null);
		userDao.save(user);
		}
		Set<Task> taskSet = project.getTaskSet();
		for(Task task:taskSet) {
			task.setProject(null);
			task.setUser(null);
			taskDao.save(task);
			taskDao.delete(task.getTaskId());
		}
		project.getTaskSet().clear();
		project.setTaskSet(null);
		project.setUser(null);
		projectDao.save(project);
		projectDao.delete(projectId);
		return viewProjects();
	}

	@Override
	public List<Project> editProject(long projectId, Project project) {
		projectDao.save(project);
		User user = project.getUser();
		user.setProject(project);
		userDao.save(user);
		//project.
		return viewProjects();
	}

	@Override
	public List<Project> viewProjects() {
		List<Project> lstProjects = new ArrayList<>();
		Iterable<Project> lstItr = projectDao.findAll();
		lstItr.forEach(lstProjects::add);
		return lstProjects;
	}

	@Override
	public List<Project> sortProjects(long sortType) {
		// TODO Auto-generated method stub
		Iterable<Project> lstItr = null;
		List<Project> lstProjects = new ArrayList<>();
		if(sortType ==1) {
			lstItr = projectDao.findAllByOrderByStartDateAsc();
		}else if(sortType ==2) {
			lstItr = projectDao.findAllByOrderByEndDateAsc();
		}else if(sortType ==3) {
			lstItr = projectDao.findAllByOrderByPriorityAsc();
		}else if(sortType ==4) {
			lstItr = projectDao.findAllByOrderByCompletedTasksAsc();
		}else {
			lstItr = projectDao.findAllByOrderByStartDateAsc();
		}
		
		lstItr.forEach(lstProjects::add);
		return lstProjects;
	}

	@Override
	public List<Project> searchProjectByName(String project) {
		Iterable<Project> lstItr = null;
		List<Project> lsProjects = new ArrayList<>();
		lstItr = projectDao.findByProjectContainingIgnoreCase(project);
		lstItr.forEach(lsProjects::add);
		return lsProjects;
		
	}
	
}
