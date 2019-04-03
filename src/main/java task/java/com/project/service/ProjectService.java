package com.project.service;

import java.util.List;

import com.project.entity.Project;

public interface ProjectService {
	public List<Project> addProject(Project project);
	public List<Project> deleteProject(long projectId);
	public List<Project> editProject(long projectId,Project project);
	public List<Project> viewProjects();
	public List<Project> sortProjects(long sortType);
	public List<Project> searchProjectByName(String projectName);

}
