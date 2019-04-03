package com.project.service;

import java.util.List;

import com.project.entity.ParentTask;
import com.project.entity.Task;

public interface TaskService {

	public List<Task> addTask(Task task);
	//public List<Task> deleteTask(long taskId);
	public List<Task> editTask(long taskId,Task task);
	public List<Task> viewTasks();
	public List<ParentTask> viewParentTasks();
	public List<Task> sortTasks(long sortType);
	public List<ParentTask> addParentTask(ParentTask parentTask);
	public List<Task> searchTaskByName(String taskName);
	public List<Task> searchTaskByProjectId(long projectId);
	public Task searchTaskByTaskId(long taskId);
	
}
