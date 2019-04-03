package com.project.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Task Class
 * @author 396946
 *
 */
@Entity
@Table(name="Task")
public class Task {
	/**
	 * TaskId field 
	 */
	@Id
	@Column(name = "Task_Id")
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private long taskId;
	/**
	 * Parentask field 
	 */
	@ManyToOne
	@JoinColumn(name="Parent_Id",nullable=true,insertable=true,updatable=true)
	private ParentTask parentTask;
	/**
	 * task field 
	 */
	@ManyToOne
	@JoinColumn(name="Project_Id",nullable=false,insertable=true,updatable=true)
	private Project project;
	/**
	 * task field 
	 */
	@Column(name="Task")
	private String taskName;
	/**
	 * task field 
	 */
	@Column(name="Start_Date")
	private Date startDate;
	/**
	 * task field 
	 */
	@Column(name="End_Date")
	Date endDate;
	/**
	 * task field 
	 */
	@Column(name="Priority")
	private int priority;
	/**
	 * task field 
	 */
	@Column(name="Status")
	private String status;
	/**
	 * task field 
	 */
	@OneToOne(fetch = FetchType.EAGER, mappedBy="task")
	@JsonManagedReference
	private User user;

	
	/**
	 * @return the taskId
	 */
	public long getTaskId() {
		return taskId;
	}
	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(final long taskId) {
		this.taskId = taskId;
	}
	/**
	 * @return the parentTask
	 */
	public ParentTask getParentTask() {
		return parentTask;
	}
	/**
	 * @param parentTask the parentTask to set
	 */
	public void setParentTask(final ParentTask parentTask) {
		this.parentTask = parentTask;
	}
	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(final Project project) {
		this.project = project;
	}
	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(final String taskName) {
		this.taskName = taskName;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(final String status) {
		this.status = status;
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
		return "Task [taskId=" + taskId + ", parentTask=" + parentTask + ", project=" + project + ", taskName=" + taskName
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", priority=" + priority + ", status=" + status
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (taskId ^ (taskId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Task other = (Task) obj;
		if (taskId != other.taskId) {
			return false;
		}
		return true;
	}
	

}
