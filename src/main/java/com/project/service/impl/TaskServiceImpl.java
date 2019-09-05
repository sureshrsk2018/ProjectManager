//tASK
package com.project.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.ParentTaskDao;
import com.project.dao.TaskDao;
import com.project.dao.UserDao;
import com.project.entity.ParentTask;
import com.project.entity.Task;
import com.project.entity.User;
import com.project.service.TaskService;

@Service
public class  TaskServiceImpl implements TaskService {
	
	@Autowired
	TaskDao<Task> taskDao;
	
	@Autowired
	ParentTaskDao<ParentTask> parentTaskDao;
	
	@Autowired
	UserDao<User> userDao;
	
	@Override
	public List<Task> addTask(Task task) {
		ParentTask parentTask = task.getParentTask();
		List<Task> taskLst = taskDao.findByParentTaskId(parentTask.getParentTaskId());
		Set<Task> taskSet = new HashSet<>();
		taskSet.addAll(taskLst);
		taskSet.add(task);
		parentTask.setTaskSet(taskSet);
		task.setParentTask(parentTask);
		User user = task.getUser();
		task.setUser(null);
		if(task.getParentTask()==null || task.getParentTask().getParentTaskName()==null) {
			task.setParentTask(null);
		}
		Task savedTask =taskDao.save(task);
		user.setTask(savedTask);
		userDao.save(user);
		return viewTasks();
	}

	public List<ParentTask> addParentTask(ParentTask parentTask){
		parentTaskDao.save(parentTask);
		return viewParentTasks();
	}
	@Override
	public List<Task> deleteTask(long taskId) {
		System.out.println("delete request task id "+taskId);
		taskDao.delete(taskId);
		System.out.println("success delete");
		return viewTasks();
	}

	@Override
	public List<Task> editTask(long taskId, Task task) {
		taskDao.save(task);
		return viewTasks();
	}

	@Override
	public List<Task> viewTasks() {
		Iterable<Task> lstItr = taskDao.findAll();
		List<Task> lstTasks = new ArrayList<>();
		lstItr.forEach(lstTasks::add);
		return lstTasks;
	}

	@Override
	public List<ParentTask> viewParentTasks() {
		Iterable<ParentTask> lstItr = parentTaskDao.findAll();
		List<ParentTask> lstTasks = new ArrayList<>();
		lstItr.forEach(lstTasks::add);
		return lstTasks;
	}

	
	@Override
	public List<Task> sortTasks(long sortType) {
		// TODO Auto-generated method stub
		Iterable<Task> lstItr = null;
		List<Task> lstTasks = new ArrayList<>();
		if(sortType ==1) {
			lstItr = taskDao.findAllByOrderByStartDateAsc();
		}else if(sortType ==2) {
			lstItr = taskDao.findAllByOrderByEndDateAsc();
		}else if(sortType ==3) {
			lstItr = taskDao.findAllByOrderByPriorityAsc();
		}else {
			lstItr = taskDao.findAllByOrderByStatusAsc();
		}
		
		lstItr.forEach(lstTasks::add);
		return lstTasks;
	}
	
	public List<Task> searchTaskByName(String taskName){
		Iterable<Task> lstItr = null;
		List<Task> lstTasks = new ArrayList<>();
		lstItr = taskDao.findByTaskNameContainingIgnoreCase(taskName);
		lstItr.forEach(lstTasks::add);
		return lstTasks;
	}
	
	public List<Task> searchTaskByProjectId(long projectId){
		Iterable<Task> lstItr = null;
		List<Task> lstTasks = new ArrayList<>();
		lstItr = taskDao.findByProject(projectId);
		lstItr.forEach(lstTasks::add);
		return lstTasks;
	}
	
	public Task searchTaskByTaskId(long taskId){
		return taskDao.findOne(taskId);
	}
}
