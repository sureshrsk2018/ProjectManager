package com.project.springcontrollers;

import java.util.List;
import java.util.Locale;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entity.ParentTask;
import com.project.entity.Task;
import com.project.service.TaskService;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/task")
public class TaskRestController {
	@Autowired
	TaskService taskService;
	
	@Autowired
    private MessageSource messageSource;
	
	@RequestMapping(value = "/addTask",
			method = RequestMethod.POST,produces = "application/json")
	 @ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public String addTask(@RequestBody Task task){
		ObjectMapper obj=new ObjectMapper();
		/*try {
		String str = obj.writeValueAsString(task);
		System.out.println("Tets suresh"+str);
		}catch(Exception e) {
			e.printStackTrace();
		}*/
		taskService.addTask(task);
		String responseMsg = messageSource.getMessage("ADDTASK_MESSAGE_SUCCESS", new Object[] {task.getTaskName()},Locale.US);
		return responseMsg;
	}
	
	@RequestMapping(value = "/addParentTask",
			method = RequestMethod.POST,produces = "application/json")
	 @ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public String addParentTask(@RequestBody ParentTask parentTask){
		/*ObjectMapper obj=new ObjectMapper();
		try {
			String str = obj.writeValueAsString(parentTask);
			System.out.println("Tets parent task suresh"+str);
			}catch(Exception e) {
				e.printStackTrace();
			}*/
		taskService.addParentTask(parentTask);
		String responseMsg = messageSource.getMessage("PARENT_TASK_MESSAGE_SUCCESS", new Object[] {parentTask.getParentTaskName()},Locale.US);
		return responseMsg;
	}
	
	@RequestMapping(value = "/deleteTask/{id}", method = RequestMethod.DELETE,produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Task> deleteTask(@PathVariable("id") long taskId){
		return taskService.deleteTask(taskId);
	}
	
	@RequestMapping(value = "/editTask/{id}", method = RequestMethod.PUT,produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String editTask(@PathVariable("id") long taskId,@RequestBody Task task) {
		/*ObjectMapper obj=new ObjectMapper();
		try {
			String str = obj.writeValueAsString(task);
			System.out.println("Tets edit task suresh"+str);
			}catch(Exception e) {
				e.printStackTrace();
			}*/
		//System.out.println("Tets edit task suresh"+taskId);
		taskService.editTask(taskId, task);
		String responseMsg = messageSource.getMessage("EDITTASK_MESSAGE_SUCCESS", new Object[] {task.getTaskName()},Locale.US);
		return responseMsg;

	}

	@RequestMapping(value = "/viewTasks",
			method = RequestMethod.GET,produces = "application/json")
	 @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Task> viewTasks() {
		return taskService.viewTasks();
	}
	
	@RequestMapping(value = "/viewParentTasks",
			method = RequestMethod.GET,produces = "application/json")
	 @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<ParentTask> viewParentTasks() {
		return taskService.viewParentTasks();
	}
	
	@RequestMapping(value = "/sortTasks/{sorttype}",
			method = RequestMethod.GET,produces = "application/json")
	 @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Task> sortTasks(@PathVariable("sorttype") long sortType) {
		System.out.println("Tets sort task - sort type suresh"+sortType);
		return taskService.sortTasks(sortType);
	}
	
	@RequestMapping(value = "/searchTask",
			method = RequestMethod.GET,produces = "application/json")
	 @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Task> searchTask(@QueryParam("taskName") String taskName,@QueryParam("projectId") long projectId) {
		
		System.out.println("Tets search task - task name suresh"+taskName);
		System.out.println("Tets search task - project id suresh"+projectId);
		if(taskName!=null) {
			return taskService.searchTaskByName(taskName);
		}else {
			return taskService.searchTaskByProjectId(projectId);
		}
	}
	
	@RequestMapping(value = "/getTask/{taskId}",
			method = RequestMethod.GET,produces = "application/json")
	 @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Task getTaskById(@PathVariable("taskId") long taskId) {
			return taskService.searchTaskByTaskId(taskId);
	}

}
