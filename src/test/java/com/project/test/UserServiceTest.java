package com.project.test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import com.project.WebApplication;
import com.project.dao.UserDao;
import com.project.entity.User;
import com.project.service.UserService;

import junitparams.JUnitParamsRunner;

@SuppressWarnings("PMD")
@RunWith(JUnitParamsRunner.class)
//@WebMvcTest(controllers = {UserRestController.class}, secure=false)
@SpringBootTest(classes = WebApplication.class)
//@ContextConfiguration(classes = WebApplication.class)
public class UserServiceTest {
	
    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

	@Autowired(required=true)
	UserService userService;
	
	@MockBean
	UserDao<User> userRepository; 
	
List<User> lstUsers= new ArrayList<>();

	public UserServiceTest() {
		
	}
    
    @Before
	public void setUp() {
    	User user1 = new User();
    	user1.setUserId(1);
    	user1.setFirstName("suresh");
    	user1.setLastName("rengan");
    	user1.setEmployeeId(3934343);
    	lstUsers.add(user1);
    	User user2 = new User();
    	user2.setUserId(2);
    	user2.setFirstName("rengan");
    	user2.setLastName("rengan");
    	user2.setEmployeeId(392323);
    	lstUsers.add(user2);
    }
	
	@Test
    @junitparams.Parameters(source= TestDataUser.class, method = "provideAddUsers")
	public void testAddUser(User adduser) {
		Mockito.when(userRepository.save(adduser))
	      .thenReturn(adduser);
		lstUsers.add(adduser);
		Mockito.when(userRepository.findAll())
	      .thenReturn(lstUsers);
		lstUsers = userService.addUser(adduser);
		boolean lstSucccess = true;
		if(!lstUsers.contains(adduser)) {
			lstSucccess = false;
		}
		assertTrue("User add Failed", lstSucccess);

	}
	
	@Test
    @junitparams.Parameters(source= TestDataUser.class, method = "provideDelUsers")
	public void testDeleteUser(long userId) {
		Mockito.when(userRepository.findAll())
	      .thenReturn(lstUsers);
		lstUsers = userService.deleteUser(userId);
		ArgumentCaptor<Long> valueCapture = ArgumentCaptor.forClass(Long.class);
		Mockito.verify(userRepository).delete( valueCapture.capture());
		long argUserId = valueCapture.getValue();
		User delUser = new User();
		delUser.setUserId(argUserId);
		boolean avail = lstUsers.remove(delUser);
		boolean lstSucccess = false;
		if(avail && !lstUsers.contains(delUser)) {
			lstSucccess = true;
		}
		assertTrue("User del Failed", lstSucccess);

	}
	
	@Test
    @junitparams.Parameters(source= TestDataUser.class, method = "provideEditUsers")
	public void testEditUser(User edituser,String expectedEditUserName) {
		Mockito.when(userRepository.save(edituser))
	      .thenReturn(edituser);
		lstUsers.add(edituser);
		Mockito.when(userRepository.findAll())
	      .thenReturn(lstUsers);
		lstUsers = userService.editUser(edituser.getUserId(), edituser);
		boolean lstSucccess = true;
		String actulEditUserName= null;
		if(!lstUsers.contains(edituser)) {
			actulEditUserName = lstUsers.get(lstUsers.indexOf(edituser)).getFirstName();
			lstSucccess = false;
		}
		assertTrue("User add Failed", lstSucccess);
		assertFalse(expectedEditUserName.equals(actulEditUserName));

	}


	@Test
    @junitparams.Parameters(source= TestDataUser.class, method = "provideUsers")
	public void testviewUsers(List<User> expectedLstUser) {
		Mockito.when(userRepository.findAll())
	      .thenReturn(lstUsers);
		lstUsers = userService.viewUsers();
		boolean lstSucccess = true;
		if(!expectedLstUser.containsAll(lstUsers)) {
			lstSucccess = false;
		}
		assertTrue("User Viewing failed", lstSucccess);
	}
	
	@Test
    @junitparams.Parameters(source= TestDataUser.class, method = "provideUsersForSort")
	public void testSortUsers(List<User> expectedLstUser,int sortType) {
		Mockito.when(userRepository.findAllByOrderByFirstNameAsc())
	      .thenReturn(lstUsers);
		Mockito.when(userRepository.findAllByOrderByLastNameAsc())
	      .thenReturn(lstUsers);
		Mockito.when(userRepository.findAllByOrderByEmployeeIdAsc())
	      .thenReturn(lstUsers);
		lstUsers = userService.sortUsers(sortType);
		boolean lstSucccess = true;
		if(!expectedLstUser.containsAll(lstUsers)) {
			lstSucccess = false;
		}
		assertTrue("User Sorting failed", lstSucccess);
	}
	
	@Test
	 @junitparams.Parameters(source= TestDataUser.class, method = "provideSearchByName")
	    public void testSearchUserByName(List<User> expectedUserLst,String searchUserName) {
		Mockito.when(userRepository.findByLastNameContainingIgnoreCase(searchUserName))
	      .thenReturn(expectedUserLst);
		lstUsers = userService.searchUserByName(searchUserName);
		boolean lstSucccess = true;
		if(!lstUsers.containsAll(expectedUserLst)) {
			lstSucccess = false;
		}
		assertTrue("User Search failed", lstSucccess);
	}
	

}
