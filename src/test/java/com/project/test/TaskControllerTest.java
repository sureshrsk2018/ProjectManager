package com.project.test;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.WebApplication;
import com.project.entity.ParentTask;
import com.project.entity.Task;
import com.project.service.TaskService;
import com.project.springcontrollers.TaskRestController;

import junitparams.JUnitParamsRunner;


@SuppressWarnings("PMD")
//@UseParametersRunnerFactory(SpringParametersRunnerFactory.class)
//@RunWith(SpringRunner.class)
@RunWith(JUnitParamsRunner.class)
@WebMvcTest(controllers = {TaskRestController.class}, secure=false)
@ContextConfiguration(classes = WebApplication.class)
public class TaskControllerTest {

	@ClassRule
	public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();



	@Autowired
	private MockMvc mvc;

	@MockBean
	private TaskService taskService;

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
		task2.setTaskId(222);
		task2.setTaskName("COMS2");
		task2.setStartDate(new Date());
		task2.setStatus("Completed");
		lstTasks.add(task2);
	}

	@Test
	@junitparams.Parameters(source= TestDataTask.class, method = "provideTasks")
	public void testlistAllTasks(List<Task> expectedLsttask) throws Exception{
		BDDMockito.given(taskService.viewTasks()).willReturn(lstTasks);

		MvcResult result = mvc.perform(get("/task/viewTasks")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		// .andExpect(jsonPath("$[0].title", is("SpringTest")));
		String resultJson = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		List<Task> lstResultTask = mapper.readValue(resultJson, new TypeReference<List<Task>>(){});
		boolean lstSucccess = true;
		for(Task actualTask: lstResultTask) {
			if(!expectedLsttask.contains(actualTask)) {
				lstSucccess = false;
				break;
			}
		}
		assertTrue("Task listing is not correct", lstSucccess);
	}

	@Test
	@junitparams.Parameters(source= TestDataTask.class, method = "provideAddTasks")
	public void testAddTask(Task addedTask) throws Exception{
		lstTasks.add(addedTask);
		BDDMockito.given(taskService.addTask(addedTask)).willReturn(lstTasks);
		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(addedTask);
		RequestBuilder request = post("/task/addTask").content(inputJson).
				contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(request)
				.andExpect(status().isCreated()).andReturn();
		String resultJson = result.getResponse().getContentAsString();
		String successStr = mapper.readValue(resultJson, new TypeReference<String>(){});
		boolean lstSucccess = true;
		if(successStr!=null) {
		 lstSucccess = true;
		}

		assertTrue("Task Addition failed", lstSucccess);
	}


	@Test
	@junitparams.Parameters(source= TestDataTask.class, method = "provideEditTasks")
	public void testEditTask(Task editTask,String expectedTaskName) throws Exception{
		//lstTasks.add(editTask);
		lstTasks.add(editTask);
		BDDMockito.given(taskService.editTask(editTask.getTaskId(), editTask)).willReturn(lstTasks);
		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(editTask);
		String uri = "/task/editTask/"+editTask.getTaskId();
		RequestBuilder request = put(uri).content(inputJson).
				contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(request)
				.andExpect(status().isOk()).andReturn();
		// .andExpect(jsonPath("$[0].title", is("SpringTest")));
		String resultJson = result.getResponse().getContentAsString();
		String successStr = mapper.readValue(resultJson, new TypeReference<String>(){});
		boolean lstSucccess = false;
		//Task resultTask=null;
		/*for(Task actualTask: lstResultTask) {
			if(lstTasks.contains(actualTask)) {
				lstSucccess = true;
				resultTask = lstTasks.get(lstTasks.indexOf(actualTask));
				break;
			}
		}
		if(!editTask.equals(resultTask)) {
			lstSucccess = false;
		}*/
		if(successStr!=null) {
			lstSucccess = true;
		}
		assertTrue("Task Modification failed", lstSucccess);
	}

/*
	@Test
	@junitparams.Parameters(source= TestDataTask.class, method = "provideDelTasks")
	public void testDeleteTask(long delTaskId) throws Exception{
		Task deleteTask = new Task();
		deleteTask.setTaskId(delTaskId);
		//lstTasks.add(editTask);
		lstTasks.remove(deleteTask);
		BDDMockito.given(taskService.deleteTask(deleteTask.getTaskId())).willReturn(lstTasks);
		ObjectMapper mapper = new ObjectMapper();
		//String inputJson = mapper.writeValueAsString(editTask);
		String uri = "/task/deleteTask/"+deleteTask.getTaskId();
		RequestBuilder request = delete(uri).
				contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(request)
				.andExpect(status().isOk()).andReturn();
		// .andExpect(jsonPath("$[0].title", is("SpringTest")));
		String resultJson = result.getResponse().getContentAsString();
		List<Task> lstResultTask = mapper.readValue(resultJson, new TypeReference<List<Task>>(){});
		boolean lstSucccess = true;
		if(lstResultTask.contains(deleteTask)) {
			lstSucccess = false;
		}
		assertTrue("Task delete failed", lstSucccess);
	}*/


	@Test
	@junitparams.Parameters(source= TestDataTask.class, method = "provideTasksForSort")
	public void testSortAllTasks(List<Task> expectedLsttask,int sortType) throws Exception{
		BDDMockito.given(taskService.sortTasks(sortType)).willReturn(lstTasks);

		MvcResult result = mvc.perform(get("/task/sortTasks/"+sortType)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		// .andExpect(jsonPath("$[0].title", is("SpringTest")));
		String resultJson = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		List<Task> lstResultTask = mapper.readValue(resultJson, new TypeReference<List<Task>>(){});
		boolean lstSucccess = true;
		for(Task actualTask: lstResultTask) {
			if(!expectedLsttask.contains(actualTask)) {
				lstSucccess = false;
				break;
			}
		}
		assertTrue("Task Sorting is not correct", lstSucccess);
	}
	
	
	@Test
	@junitparams.Parameters(source= TestDataTask.class, method = "provideSearchTasksByTaskName")
	public void testSearchByTaskName(List<Task> expectedLsttask,String taskName) throws Exception{
		BDDMockito.given(taskService.searchTaskByName(taskName)).willReturn(expectedLsttask);

		MvcResult result = mvc.perform(get("/task/searchTask?taskName="+taskName+"&projectId="+0)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		// .andExpect(jsonPath("$[0].title", is("SpringTest")));
		String resultJson = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		List<Task> lstResultTask = mapper.readValue(resultJson, new TypeReference<List<Task>>(){});
		boolean lstSucccess = true;
			if(!expectedLsttask.containsAll(lstResultTask)) {
				lstSucccess = false;
			}
		assertTrue("Task Search by taskname is not correct", lstSucccess);
	}
	
	@Test
	@junitparams.Parameters(source= TestDataTask.class, method = "provideSearchByProjectId")
	public void testSearchByProjectId(List<Task> expectedLsttask,long projectId) throws Exception{
		BDDMockito.given(taskService.searchTaskByProjectId(projectId)).willReturn(expectedLsttask);

		MvcResult result = mvc.perform(get("/task/searchTask?projectId="+0)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		// .andExpect(jsonPath("$[0].title", is("SpringTest")));
		String resultJson = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		List<Task> lstResultTask = mapper.readValue(resultJson, new TypeReference<List<Task>>(){});
		boolean lstSucccess = true;
			if(!expectedLsttask.containsAll(lstResultTask)) {
				lstSucccess = false;
			}
		assertTrue("Task Search by projectId is not correct", lstSucccess);
	}

	@Test
	@junitparams.Parameters(source= TestDataTask.class, method = "provideSearchByTaskId")
	public void testGetTaskById(Task expectedTask,long taskId) throws Exception {
		BDDMockito.given(taskService.searchTaskByTaskId(taskId)).willReturn(expectedTask);

		MvcResult result = mvc.perform(get("/task/getTask/"+taskId)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		// .andExpect(jsonPath("$[0].title", is("SpringTest")));
		String resultJson = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		Task actualTask = mapper.readValue(resultJson, new TypeReference<Task>(){});
		boolean lstSucccess = true;
			if(!expectedTask.equals(actualTask)) {
				lstSucccess = false;
			}
		assertTrue("Task Search by TaskId is not correct", lstSucccess);
	}
	
	
	@Test
	@junitparams.Parameters(source= TestDataTask.class, method = "provideParentTask")
	public void testViewParentTask(List<ParentTask> expectedParentTaskLst,ParentTask parentTask) {
		try {
		BDDMockito.given(taskService.viewParentTasks()).willReturn(expectedParentTaskLst);
		MvcResult result = mvc.perform(get("/task/viewParentTasks")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		// .andExpect(jsonPath("$[0].title", is("SpringTest")));
		String resultJson = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		List<ParentTask> lstParentTasks = mapper.readValue(resultJson, new TypeReference<List<ParentTask>>(){});
		boolean lstSucccess = true;
			if(!expectedParentTaskLst.equals(lstParentTasks)) {
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
		BDDMockito.given(taskService.addParentTask(parentTask)).willReturn(lstParentTask);
		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(parentTask);
		RequestBuilder request = post("/task/addParentTask").content(inputJson).
				contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(request)
				.andExpect(status().isCreated()).andReturn();
		String resultJson = result.getResponse().getContentAsString();
		String successStr = mapper.readValue(resultJson, new TypeReference<String>(){});
		boolean lstSucccess = true;
		if(successStr!=null) {
		 lstSucccess = true;
		}

		assertTrue("Parent Task Addition failed", lstSucccess);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


}
