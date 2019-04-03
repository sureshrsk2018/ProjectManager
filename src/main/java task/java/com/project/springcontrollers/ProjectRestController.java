package com.project.springcontrollers;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//import com.exception.ResourceNotFoundException;
import com.project.entity.Project;
import com.project.service.ProjectService;

@RestController
@RequestMapping("/project")
public class ProjectRestController {
	@Autowired
	ProjectService projectService;
	
	@RequestMapping(value = "/addProject",
			method = RequestMethod.POST,produces = "application/json")
	 @ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public List<Project> addProject(@RequestBody Project project){
		return projectService.addProject(project);
	}
	
	@RequestMapping(value = "/deleteProject/{id}", method = RequestMethod.DELETE,produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Project> deleteProject(@PathVariable("id") long projectId){
		return projectService.deleteProject(projectId);
	}
	
	@RequestMapping(value = "/editProject/{id}", method = RequestMethod.PUT,produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Project> editProject(@PathVariable("id") long projectId,@RequestBody Project project) {
		return projectService.editProject(projectId, project);
	}

	@RequestMapping(value = "/viewProjects",
			method = RequestMethod.GET,produces = "application/json")
	 @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Project> viewProjects() {
		return projectService.viewProjects();
	}
	
	@RequestMapping(value = "/sortProjects/{sorttype}",
			method = RequestMethod.GET,produces = "application/json")
	 @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Project> sortProjects(@PathVariable("sorttype") long sortType) {
		return projectService.sortProjects(sortType);
	}
	
	@RequestMapping(value = "/searchProject",
			method = RequestMethod.GET,produces = "application/json")
	 @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Project> searchUserByName(@QueryParam("projectName") String projectName) {
		return projectService.searchProjectByName(projectName);
	}


}
