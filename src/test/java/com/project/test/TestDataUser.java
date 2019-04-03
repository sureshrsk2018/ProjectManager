package com.project.test;

import java.util.ArrayList;
import java.util.List;

import com.project.entity.User;

@SuppressWarnings("PMD")
public class TestDataUser {
	
	public static Object[] provideUsers() {
		User user1 = new User();
		user1.setUserId(1);
		user1.setFirstName("suresh");
		user1.setLastName("rengan");
		user1.setEmployeeId(396946);

		User user2 = new User();
		user2.setUserId(2);
		user2.setFirstName("rajesh");
		user2.setLastName("rengan");
		user2.setEmployeeId(396947);

		List<User> userList = new ArrayList<>();
		userList.add(user1);
		userList.add(user2);
		return (new Object[]{
				userList
		}
				);
	}
	
	public static Object[] provideAddUsers() {
		User user1 = new User();
		user1.setUserId(1);
		user1.setFirstName("suresh");
		user1.setLastName("rajesh");
		user1.setEmployeeId(396948);

		User user2 = new User();
		user2.setUserId(2);
		user2.setFirstName("suresh");
		user2.setLastName("sankar");
		user2.setEmployeeId(2323222);

		return (new Object[]{
				user1,user2
		}
				);
	}
	
	
	public static Object[] provideDelUsers() {
		

		return (new Object[]{
				1,2
		}
				);
	}

	
	public static Object[] provideEditUsers() {
		User user1 = new User();
		user1.setUserId(1);
		user1.setFirstName("prakash");
		user1.setLastName("rengan");
		user1.setEmployeeId(393333);

		User user2 = new User();
		user2.setUserId(2);
		user2.setFirstName("naresh");
		user2.setLastName("suresh");
		user2.setEmployeeId(3999333);

		return (new Object[][]{
				{user1,user1.getFirstName()},{user2,user2.getFirstName()}
		}
				);
	}
	
	
	public static Object[] provideUsersForSort() {
		User user1 = new User();
		user1.setUserId(1);
		user1.setFirstName("kaviya");
		user1.setLastName("shankar");
		user1.setEmployeeId(396777);

		User user2 = new User();
		user2.setUserId(2);
		user2.setFirstName("kousik");
		user2.setLastName("sankar");
		user2.setEmployeeId(3923232);

		List<User> userList = new ArrayList<>();
		userList.add(user1);
		userList.add(user2);
		return (new Object[][]{
				{userList,1},{userList,2},{userList,3}
		}
				);
	}

	public static Object[] provideSearchByName() {
		User user1 = new User();
		user1.setUserId(1);
		user1.setFirstName("deepthi");
		user1.setLastName("suresh");
		user1.setEmployeeId(3923232);
		List<User> user1List = new ArrayList<>();
		user1List.add(user1);
		
		User user2 = new User();
		user2.setUserId(2);
		user2.setFirstName("suresh");
		user2.setLastName("rengan1");
		user2.setEmployeeId(3923223);
		List<User> user2List = new ArrayList<>();
		user2List.add(user2);

		return (new Object[][]{
				{user1List,"suresh"},{user2List,"rajesh"}
		}
				);
	}


}
