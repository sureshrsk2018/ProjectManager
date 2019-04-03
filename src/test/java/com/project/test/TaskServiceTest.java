package com.project.test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.WebApplication;
import com.project.dao.ParentTaskDao;
import com.project.dao.TaskDao;
import com.project.dao.UserDao;
import com.project.entity.ParentTask;
import com.project.entity.Task;
import com.project.entity.User;
import com.project.service.TaskService;

import junitparams.JUnitParamsRunner;

@SuppressWarnings("PMD")
@RunWith(JUnitParamsRunner.class)
//@WebMvcTest(controllers = {TaskRestController.class}, secure=false)
@SpringBootTest(classes = WebApplication.class)
//@ContextConfiguration(classes = WebApplication.class)
public class TaskServiceTest {
	
    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

	@Autowired(required=true)
	TaskService taskService;
	
	@MockBean
	TaskDao<Task> taskRepository; 
	
	@MockBean
	ParentTaskDao<ParentTask> parentTaskDao;
	
	@MockBean
	UserDao<User> userDao;
	

	
List<Task> lstTasks= new ArrayList<>();
    
    @Before
	public void setUp() {
    	Task task1 = new Task();
    	task1.setEndDate(new Date());
    	task1.setPriority(40);
    	task1.setTaskId(111);
    	task1.setStartDate(new Date());
    	task1.setTaskName("COMS1");
    	task1.setStatus("Completed");
    	lstTasks.add(task1);
    	Task task2 = new Task();
    	task2.setEndDate(new Date());
    	task2.setPriority(40);
    	task2.setTaskId(111);
    	task2.setTaskName("COMS2");
    	task2.setStartDate(new Date());
    	task2.setStatus("Completed");
    	lstTasks.add(task2);
    }
	
	@Test
    @junitparams.Parameters(source= TestDataTask.class, method = "provideAddTasks")
	public void testAddTask(Task addtask) {
		User user = new User();
		addtask.setUser(user);
		Mockito.when(taskRepository.save(addtask))
	      .thenReturn(addtask);
		Mockito.when(userDao.save(addtask.getUser()))
	      .thenReturn(addtask.getUser());
		lstTasks.add(addtask);
		Mockito.when(taskRepository.findAll())
	      .thenReturn(lstTasks);
		lstTasks = taskService.addTask(addtask);
		boolean lstSucccess = true;
		if(!lstTasks.contains(addtask)) {
			lstSucccess = false;
		}
		assertTrue("Task add Failed", lstSucccess);

	}
	
/*	@Test
    @junitparams.Parameters(source= TestDataTask.class, method = "provideDelTasks")
	public void testDeleteTask(long taskId) {
		Mockito.when(taskRepository.findAll())
	      .thenReturn(lstTasks);
		lstTasks = taskService.deleteTask(taskId);
		ArgumentCaptor<Long> valueCapture = ArgumentCaptor.forClass(Long.class);
		Mockito.verify(taskRepository).delete( valueCapture.capture());
		long argTaskId = valueCapture.getValue();
		Task delTask = new Task();
		delTask.setTaskId(argTaskId);
		boolean avail = lstTasks.remove(delTask);
		boolean lstSucccess = false;
		if(avail && !lstTasks.contains(delTask)) {
			lstSucccess = true;
		}
		assertTrue("Task del Failed", lstSucccess);

	}*/
	
	@Test
    @junitparams.Parameters(source= TestDataTask.class, method = "provideEditTasks")
	public void testEditTask(Task edittask,String expectedEditTaskName) {
		User user = new User();
		edittask.setUser(user);
		Mockito.when(userDao.save(edittask.getUser()))
	      .thenReturn(edittask.getUser());
		Mockito.when(taskRepository.save(edittask))
	      .thenReturn(edittask);
		lstTasks.add(edittask);
		Mockito.when(taskRepository.findAll())
	      .thenReturn(lstTasks);
		lstTasks = taskService.editTask(edittask.getTaskId(), edittask);
		boolean lstSucccess = true;
		String actulEditTaskName= null;
		if(!lstTasks.contains(edittask)) {
			actulEditTaskName = lstTasks.get(lstTasks.indexOf(edittask)).getTaskName();
			lstSucccess = false;
		}
		assertTrue("Task add Failed", lstSucccess);
		assertFalse(expectedEditTaskName.equals(actulEditTaskName));

	}


	@Test
    @junitparams.Parameters(source= TestDataTask.class, method = "provideTasks")
	public void testviewTasks(List<Task> expectedLstTask) {
		Mockito.when(taskRepository.findAll())
	      .thenReturn(lstTasks);
		lstTasks = taskService.viewTasks();
		boolean lstSucccess = true;
		if(!expectedLstTask.containsAll(lstTasks)) {
			lstSucccess = false;
		}
		assertTrue("Task Viewing failed", lstSucccess);
	}
	
	@Test
    @junitparams.Parameters(source= TestDataTask.class, method = "provideTasksForSort")
	public void testSortTasks(List<Task> expectedLstTask,int sortType) {
		Mockito.when(taskRepository.findAllByOrderByStartDateAsc())
	      .thenReturn(lstTasks);
		Mockito.when(taskRepository.findAllByOrderByEndDateAsc())
	      .thenReturn(lstTasks);
		Mockito.when(taskRepository.findAllByOrderByPriorityAsc())
	      .thenReturn(lstTasks);
		lstTasks = taskService.sortTasks(sortType);
		boolean lstSucccess = true;
		if(!expectedLstTask.containsAll(lstTasks)) {
			lstSucccess = false;
		}
		assertTrue("Task Sorting failed", lstSucccess);
	}
	
	@Test
	@junitparams.Parameters(source= TestDataTask.class, method = "provideSearchTasksByTaskName")
	public void testSearchByTaskName(List<Task> expectedLsttask,String taskName) throws Exception{
		BDDMockito.given(taskRepository.findByTaskNameContainingIgnoreCase(taskName)).willReturn(expectedLsttask);
		List<Task> lstResultTask = taskService.searchTaskByName(taskName);
		boolean lstSucccess = true;
			if(!expectedLsttask.containsAll(lstResultTask)) {
				lstSucccess = false;
			}
		assertTrue("Task Search by taskname is not correct", lstSucccess);
	}
	
	@Test
	@junitparams.Parameters(source= TestDataTask.class, method = "provideSearchByProjectId")
	public void testSearchByProjectId(List<Task> expectedLsttask,long projectId) throws Exception{
		BDDMockito.given(taskRepository.findByProject(projectId)).willReturn(expectedLsttask);
		List<Task> lstResultTask = taskService.searchTaskByProjectId(projectId);
		boolean lstSucccess = true;
			if(!expectedLsttask.containsAll(lstResultTask)) {
				lstSucccess = false;
			}		assertTrue("Task Search by projectId is not correct", lstSucccess);
	}

	@Test
	@junitparams.Parameters(source= TestDataTask.class, method = "provideSearchByTaskId")
	public void testGetTaskById(Task expectedTask,long taskId) throws Exception {
		BDDMockito.given(taskRepository.findOne(taskId)).willReturn(expectedTask);
		Task resultTask = taskService.searchTaskByTaskId(taskId);
		boolean lstSucccess = true;
			if(!resultTask.equals(expectedTask)) {
				lstSucccess = false;
			}		
			assertTrue("Task Search by TaskId is not correct", lstSucccess);
	}
	
	
	@Test
	@junitparams.Parameters(source= TestDataTask.class, method = "provideParentTask")
	public void testViewParentTask(List<ParentTask> expectedParentTaskLst,ParentTask parentTask) {
		try {
			BDDMockito.given(parentTaskDao.findAll()).willReturn(expectedParentTaskLst);
			List<ParentTask> lstResultTask = taskService.viewParentTasks();
			boolean lstSucccess = true;
				if(!expectedParentTaskLst.containsAll(lstResultTask)) {
					lstSucccess = false;
				}		
		assertTrue("View parent task failed", lstSucccess);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@junitparams.Parameters(source= TestDataTask.class, method = "provideParentTask")
	public void testAddParentTask(List<ParentTask> lstParentTask,ParentTask parentTask){
		//lstTasks.add(addedTask);
		try {
		BDDMockito.given(parentTaskDao.save(parentTask)).willReturn(parentTask);
		BDDMockito.given(parentTaskDao.findAll()).willReturn(lstParentTask);
		
		List<ParentTask> actulLstResultTask = taskService.viewParentTasks();
		boolean lstSucccess = true;
			if(!actulLstResultTask.containsAll(lstParentTask)) {
				lstSucccess = false;
			}		

			assertTrue("Parent Task Addition failed", lstSucccess);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	

}
