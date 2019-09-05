package com.project.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.project.entity.ParentTask;
import com.project.entity.Project;
import com.project.entity.Task;

/**
 * @author 396946
 *
 */
@SuppressWarnings("PMD")
public class TestDataTask {
	
	public static Object[] provideTasks() {
    	Task task1 = new Task();
    	task1.setEndDate(new Date());
    	task1.setPriority(40);
    	task1.setTaskId(111);
    	task1.setStartDate(new Date());
    	task1.setStatus("Completed");
    	Task task2 = new Task();
    	task2.setEndDate(new Date());
    	task2.setPriority(90);
    	task2.setTaskId(222);
    	task2.setStartDate(new Date());
    	task2.setStatus("Inprogress");

		List<Task> taskList = new ArrayList<>();
		taskList.add(task1);
		taskList.add(task2);
		return (new Object[]{
				taskList
		}
				);
	}
	
	public static Object[] provideAddTasks() {
		ParentTask pt=new ParentTask();
		ParentTask pt1=new ParentTask();
		pt.setParentTaskId(2);
		pt.setParentTaskName("ParentTaskNmae1");
		pt1.setParentTaskId(3);
		pt1.setParentTaskName("ParentTaskNmae2");
	   	Task task1 = new Task();
    	task1.setEndDate(new Date());
    	task1.setPriority(40);
    	task1.setTaskId(111);
    	task1.setTaskName("Task1");
    	task1.setParentTask(pt);
    	task1.setStartDate(new Date());
    	task1.setStatus("Completed");
    	Task task2 = new Task();
    	task2.setEndDate(new Date());
    	task2.setPriority(90);
    	task2.setTaskId(222);
    	task2.setTaskName("taskname2");
    	task2.setParentTask(pt1);
    	task2.setStartDate(new Date());
    	task2.setStatus("Inprogress");

		return (new Object[]{
				task1,task2
		}
				);
	}
	
	
	public static Object[] provideDelTasks() {
		

		return (new Object[]{
				111,222
		}
				);
	}

	
	public static Object[] provideEditTasks() {
	   	Task task1 = new Task();
    	task1.setEndDate(new Date());
    	task1.setPriority(40);
    	task1.setTaskId(111);
    	task1.setStartDate(new Date());
    	task1.setTaskName("COMS");
    	task1.setStatus("Completed");
    	Task task2 = new Task();
    	task2.setEndDate(new Date());
    	task2.setPriority(90);
    	task2.setTaskName("TIM");
    	task2.setTaskId(222);
    	task2.setStartDate(new Date());
    	task2.setStatus("Inprogress");

		return (new Object[][]{
				{task1,task1.getTaskName()},{task2,task2.getTaskName()}
		}
				);
	}
	
	
	public static Object[] provideTasksForSort() {
	   	Task task1 = new Task();
    	task1.setEndDate(new Date());
    	task1.setPriority(40);
    	task1.setTaskId(111);
    	task1.setStartDate(new Date());
    	task1.setStatus("Completed");
    	Task task2 = new Task();
    	task2.setEndDate(new Date());
    	task2.setPriority(90);
    	task2.setTaskId(222);
    	task2.setStartDate(new Date());
    	task2.setStatus("Inprogress");

		List<Task> taskList = new ArrayList<>();
		taskList.add(task1);
		taskList.add(task2);
		return (new Object[][]{
				{taskList,1},{taskList,2},{taskList,3}
		}
				);
	}

	public static Object[] provideSearchTasksByTaskName() {
	   	Task task1 = new Task();
    	task1.setEndDate(new Date());
    	task1.setTaskName("test");
    	task1.setPriority(40);
    	task1.setTaskId(111);
    	task1.setStartDate(new Date());
    	task1.setStatus("Completed");
    	task1.setProject(new Project());
		List<Task> taskList = new ArrayList<>();
		taskList.add(task1);

    	Task task2 = new Task();
    	task2.setTaskName("test2");
    	task2.setEndDate(new Date());
    	task2.setPriority(90);
    	task2.setTaskId(222);
    	task2.setStartDate(new Date());
    	task2.setStatus("Inprogress");
    	task2.setProject(new Project());
		List<Task> taskList2 = new ArrayList<>();
		taskList2.add(task2);
		return (new Object[][]{
				{taskList,"test"},{taskList2,"test2"}
		}
				);
	}


	public static Object[] provideSearchByProjectId() {
	   	Task task1 = new Task();
    	task1.setEndDate(new Date());
    	task1.setTaskName("test");
    	task1.setPriority(40);
    	task1.setTaskId(111);
    	task1.setStartDate(new Date());
    	task1.setStatus("Completed");
    	Project p1 = new Project();
    	p1.setProject("1");
    	task1.setProject(p1);
		List<Task> taskList = new ArrayList<>();
		taskList.add(task1);

    	Task task2 = new Task();
    	task2.setTaskName("test2");
    	task2.setEndDate(new Date());
    	task2.setPriority(90);
    	task2.setTaskId(222);
    	task2.setStartDate(new Date());
    	task2.setStatus("Inprogress");
    	Project p2 = new Project();
    	p2.setProject("2");
    	task2.setProject(p2);
		List<Task> taskList2 = new ArrayList<>();
		taskList2.add(task2);
		return (new Object[][]{
				{taskList,p1.getProjectId()},{taskList2,p2.getProjectId()}
		}
				);
	}
	

	public static Object[] provideSearchByTaskId() {
	   	Task task1 = new Task();
    	task1.setEndDate(new Date());
    	task1.setTaskName("test");
    	task1.setPriority(40);
    	task1.setTaskId(111);
    	task1.setStartDate(new Date());
    	task1.setStatus("Completed");
    	Project p1 = new Project();
    	p1.setProject("1");
    	task1.setProject(p1);
		List<Task> taskList = new ArrayList<>();
		taskList.add(task1);

    	Task task2 = new Task();
    	task2.setTaskName("test2");
    	task2.setEndDate(new Date());
    	task2.setPriority(90);
    	task2.setTaskId(222);
    	task2.setStartDate(new Date());
    	task2.setStatus("Inprogress");
    	Project p2 = new Project();
    	p2.setProject("2");
    	task2.setProject(p2);
		List<Task> taskList2 = new ArrayList<>();
		taskList2.add(task2);
		return (new Object[][]{
				{task1,task1.getTaskId()},{task2,task2.getTaskId()}
		}
				);
	}
	
	public static Object[] provideParentTask() {
	   	ParentTask task1 = new ParentTask();
    	task1.setParentTaskId(1);
    	task1.setParentTaskName("P1");
    	
    	ParentTask task2 = new ParentTask();
    	task2.setParentTaskId(2);
    	task2.setParentTaskName("P2");

    	ParentTask task3 = new ParentTask();
    	task3.setParentTaskId(3);
    	task3.setParentTaskName("P3");

    	ParentTask task4 = new ParentTask();
    	task4.setParentTaskId(4);
    	task4.setParentTaskName("P4");

 		List<ParentTask> taskList1 = new ArrayList<>();
		taskList1.add(task1);
		taskList1.add(task2);

 		List<ParentTask> taskList2 = new ArrayList<>();
		taskList2.add(task3);
		taskList2.add(task4);
		
		return (new Object[][]{
				{taskList1,task1},{taskList1,task2},{taskList2,task3}
		}
				);
	}
}
