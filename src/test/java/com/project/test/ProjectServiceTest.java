package com.project.test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.WebApplication;
import com.project.dao.ProjectDao;
import com.project.dao.TaskDao;
import com.project.dao.UserDao;
import com.project.entity.Project;
import com.project.entity.Task;
import com.project.entity.User;
import com.project.service.ProjectService;

import junitparams.JUnitParamsRunner;

@SuppressWarnings("PMD")
@RunWith(JUnitParamsRunner.class)
//@WebMvcTest(controllers = {ProjectRestController.class}, secure=false)
@SpringBootTest(classes = WebApplication.class)
//@ContextConfiguration(classes = WebApplication.class)
public class ProjectServiceTest {
	
    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

	@Autowired(required=true)
	ProjectService projectService;
	
	@MockBean
	ProjectDao<Project> projectRepository; 
	
	@MockBean
	UserDao<User> userDao;
	
	@MockBean
	TaskDao<Task> taskDao;

	
List<Project> lstProjects= new ArrayList<>();
    
    @Before
	public void setUp() {
    	User user = new User();
    	Project project1 = new Project();
    	project1.setEndDate(new Date());
    	project1.setPriority(40);
    	project1.setProjectId(111);
    	project1.setStartDate(new Date());
    	project1.setProject("COMS1");
    	project1.setUser(user);
    //	project1.setStatus("Completed");
    	lstProjects.add(project1);
    	Project project2 = new Project();
    	project2.setEndDate(new Date());
    	project2.setPriority(40);
    	project2.setProjectId(222);
    	project2.setProject("COMS2");
    	project2.setStartDate(new Date());
    	project2.setUser(user);
    //	project2.setStatus("Completed");
    	lstProjects.add(project2);
    }
	
	@Test
    @junitparams.Parameters(source= TestDataProject.class, method = "provideAddProjects")
	public void testAddProject(Project addproject) {
		Mockito.when(projectRepository.save(addproject))
	      .thenReturn(addproject);
		Mockito.when(userDao.save(addproject.getUser()))
	      .thenReturn(addproject.getUser());
		lstProjects.add(addproject);
		Mockito.when(projectRepository.findAll())
	      .thenReturn(lstProjects);
		lstProjects = projectService.addProject(addproject);
		boolean lstSucccess = true;
		if(!lstProjects.contains(addproject)) {
			lstSucccess = false;
		}
		assertTrue("Project add Failed", lstSucccess);

	}
	
	@Test
    @junitparams.Parameters(source= TestDataProject.class, method = "provideDelProjects")
	public void testDeleteProject(long projectId) {
		Mockito.when(projectRepository.findAll())
	      .thenReturn(lstProjects);
		Project project =  new Project();
		project.setProjectId(projectId);
		project.setUser(new User());
		project.setTaskSet(new HashSet<>());
		Mockito.when(userDao.save(project.getUser()))
	      .thenReturn(project.getUser());
		Mockito.when(projectRepository.findOne(projectId))
	      .thenReturn(project);
		lstProjects = projectService.deleteProject(projectId);
		ArgumentCaptor<Long> valueCapture = ArgumentCaptor.forClass(Long.class);
		Mockito.verify(projectRepository).delete( valueCapture.capture());
		long argProjectId = valueCapture.getValue();
		Project delProject = new Project();
		delProject.setProjectId(argProjectId);
		boolean avail = lstProjects.remove(delProject);
		boolean lstSucccess = false;
		if(avail && !lstProjects.contains(delProject)) {
			lstSucccess = true;
		}
		assertTrue("Project del Failed", lstSucccess);

	}
	
	@Test
    @junitparams.Parameters(source= TestDataProject.class, method = "provideEditProjects")
	public void testEditProject(Project editproject,String expectedEditProjectName) {
		Mockito.when(projectRepository.save(editproject))
	      .thenReturn(editproject);
		Mockito.when(userDao.save(editproject.getUser()))
	      .thenReturn(editproject.getUser());
		lstProjects.add(editproject);
		Mockito.when(projectRepository.findAll())
	      .thenReturn(lstProjects);
		lstProjects = projectService.editProject(editproject.getProjectId(), editproject);
		boolean lstSucccess = true;
		String actulEditProjectName= null;
		if(!lstProjects.contains(editproject)) {
			actulEditProjectName = lstProjects.get(lstProjects.indexOf(editproject)).getProject();
			lstSucccess = false;
		}
		assertTrue("Project add Failed", lstSucccess);
		assertFalse(expectedEditProjectName.equals(actulEditProjectName));

	}


	@Test
    @junitparams.Parameters(source= TestDataProject.class, method = "provideProjects")
	public void testviewProjects(List<Project> expectedLstProject) {
		Mockito.when(projectRepository.findAll())
	      .thenReturn(lstProjects);
		lstProjects = projectService.viewProjects();
		boolean lstSucccess = true;
		if(!expectedLstProject.containsAll(lstProjects)) {
			lstSucccess = false;
		}
		assertTrue("Project Viewing failed", lstSucccess);
	}
	
	@Test
    @junitparams.Parameters(source= TestDataProject.class, method = "provideProjectsForSort")
	public void testSortProjects(List<Project> expectedLstProject,int sortType) {
		Mockito.when(projectRepository.findAllByOrderByStartDateAsc())
	      .thenReturn(lstProjects);
		Mockito.when(projectRepository.findAllByOrderByEndDateAsc())
	      .thenReturn(lstProjects);
		Mockito.when(projectRepository.findAllByOrderByPriorityAsc())
	      .thenReturn(lstProjects);
		lstProjects = projectService.sortProjects(sortType);
		boolean lstSucccess = true;
		if(!expectedLstProject.containsAll(lstProjects)) {
			lstSucccess = false;
		}
		assertTrue("Project Sorting failed", lstSucccess);
	}
	
	@Test
	@junitparams.Parameters(source= TestDataProject.class, method = "provideProjectsForSearch")
	public void testSearchByName(List<Project> expectedLstproject,String projectName) throws Exception{
		BDDMockito.given(projectRepository.findByProjectContainingIgnoreCase(projectName)).willReturn(expectedLstproject);
		List<Project> lstResultProject = projectService.searchProjectByName(projectName);
		boolean lstSucccess = true;
		if(!expectedLstproject.containsAll(lstResultProject)) {
			lstSucccess = false;
		}
		assertTrue("Project search is not correct", lstSucccess);
	}
	

}
