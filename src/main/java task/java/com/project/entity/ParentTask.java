package com.project.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author 396946
 *
 */
@Entity
@Table(name="Parent_Task")
public class ParentTask {
	/**
	 * ParentTask Field
	 */
	@Id
	@Column(name = "Parent_Id")
	private int parentTaskId;
	/**
	 * ParentTask Field
	 */
	@Column(name = "Parent_Task")
	private String parentTaskName;
	/**
	 * ParentTask Field
	 */
	@OneToMany(cascade= {CascadeType.ALL} ,fetch = FetchType.EAGER, mappedBy="parentTask")
	@JsonIgnore
	private Set<Task> taskSet;
	/**
	 * @return the parentTaskId
	 */
	public int getParentTaskId() {
		return parentTaskId;
	}
	/**
	 * @param parentTaskId the parentTaskId to set
	 */
	public void setParentTaskId(int parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	/**
	 * @return the parentTaskName
	 */
	public String getParentTaskName() {
		return parentTaskName;
	}
	/**
	 * @param parentTaskName the parentTaskName to set
	 */
	public void setParentTaskName(String parentTaskName) {
		this.parentTaskName = parentTaskName;
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
	public void setTaskSet(Set<Task> taskSet) {
		this.taskSet = taskSet;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParentTask [parentTaskId=" + parentTaskId + ", parentTaskName=" + parentTaskName + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + parentTaskId;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		ParentTask other = (ParentTask) obj;
		if (parentTaskId != other.parentTaskId) {
			return false;
		}
		return true;
	}
	

}
