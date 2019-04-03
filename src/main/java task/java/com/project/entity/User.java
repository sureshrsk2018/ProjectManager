package com.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author 396946
 *
 */
@Entity
@Table(name="Users")
public class User {

	/**
	 *  User Field
	 */
	@Id
    @Column(name = "User_Id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long userId;
	/**
	 *  User Field
	 */
	@Column(name = "First_Name")
	private String firstName;
	/**
	 *  User Field
	 */
	@Column(name = "Last_Name")
	private String lastName;
	/**
	 *  User Field
	 */
	@Column(name = "Employee_Id")
	private int employeeId;
	//@JoinColumn(name="Project_Id",nullable=true,insertable=false,updatable=false)
	/**
	 *  User Field
	 */
	@OneToOne
	//@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "user")
	@JoinColumn(name="Project_Id",nullable=true,insertable=false,updatable=true)
	@JsonIgnore
	//@JsonBackReference
	private Project project;
	/**
	 *  User Field
	 */
	@OneToOne
	@JoinColumn(name="Task_Id",nullable=true,insertable=false,updatable=true)
	//@JsonIgnore
	@JsonBackReference
	private Task task;

	
	
	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(final long userId) {
		this.userId = userId;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the employeeId
	 */
	public int getEmployeeId() {
		return employeeId;
	}
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(final int employeeId) {
		this.employeeId = employeeId;
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
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}
	/**
	 * @param task the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", EmployeeId="
				+ employeeId + "]";
	}
	
	@Override
	public int hashCode() {
		final long prime = 31;
		long result = 1;
		result = prime * result + employeeId;
		result = prime * result + userId;
		return (int)result;
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
		User other = (User) obj;
		if (userId != other.userId) {
			return false;
		}
		return true;
	}
	
}
