package com.project.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author 396946
 *
 */
@Entity
@Table(name="Project")
public class Project {
	/**
	 * Project Field
	 */
	@Id
    @Column(name = "Project_Id")
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private long projectId;
	/**
	 * Project Field
	 */
	@Column(name = "Project")
	 private String project;
	/**
	 * Project Field
	 */
	@Column(name = "Start_Date")
	 private Date startDate;
	/**
	 * Project Field
	 */
	@Column(name = "End_Date")
	 private Date endDate;
	/**
	 * Project Field
	 */
	@Column(name = "Priority")
	 private int priority;
/*	@Column(name = "Status")
	String status;*/
	/**
	 * Project Field
	 */
	@OneToMany(cascade= {CascadeType.ALL} , fetch = FetchType.EAGER, mappedBy="project")
//	 @OneToMany(cascade= {CascadeType.REMOVE})
     @JsonIgnore
	private Set<Task> taskSet;
	/**
	 * Project Field
	 */
	@Formula("(select count(distinct t.task_id) from FSD.task t where t.project_id=project_id)")
	private int numberOfTasks;
	/**
	 * Project Field
	 */
	@Formula("(select count(distinct t.task_id) from FSD.task t where t.project_id=project_id and t.status='Completed')")
	private int completedTasks;
	@OneToOne(cascade= {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH} ,fetch = FetchType.EAGER, mappedBy = "project")
    User user;
	
	/**
	 * @return the projectId
	 */
	public long getProjectId() {
		return projectId;
	}
	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(final long projectId) {
		this.projectId = projectId;
	}
	/**
	 * @return the project
	 */
	public String getProject() {
		return project;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(final String project) {
		this.project = project;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(final int priority) {
		this.priority = priority;
	}
	/**
	 * @return the taskSet
	 */
	public Set<Task> getTaskSet() {
		return taskSet;
	}
	/**
	 * @param taskSet the taskSet to set
	 */
	public void setTaskSet(final Set<Task> taskSet) {
		this.taskSet = taskSet;
	}
	/**
	 * @return the numberOfTasks
	 */
	public int getNumberOfTasks() {
		return numberOfTasks;
	}
	/**
	 * @param numberOfTasks the numberOfTasks to set
	 */
	public void setNumberOfTasks(final int numberOfTasks) {
		this.numberOfTasks = numberOfTasks;
	}
	/**
	 * @return the completedTasks
	 */
	public int getCompletedTasks() {
		return completedTasks;
	}
	/**
	 * @param completedTasks the completedTasks to set
	 */
	public void setCompletedTasks(final int completedTasks) {
		this.completedTasks = completedTasks;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(final User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", project=" + project + ", startDate=" + startDate + ", endDate="
				+ endDate + ", priority=" + priority + ", numberOfTasks=" + numberOfTasks + ", completedTasks="
				+ completedTasks + "]";
	}
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + (int) (projectId ^ (projectId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Project other = (Project) obj;
		if (projectId != other.projectId) {
			return false;
		}
		return true;
	}
}
