package com.project.test;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

//import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.WebApplication;
import com.project.entity.User;
import com.project.service.UserService;
import com.project.springcontrollers.UserRestController;

import junitparams.JUnitParamsRunner;


@SuppressWarnings("PMD")
//@UseParametersRunnerFactory(SpringParametersRunnerFactory.class)
//@RunWith(SpringRunner.class)
//@RunWith(ZeroCodeUnitRunner.class)
@RunWith(JUnitParamsRunner.class)
@WebMvcTest(controllers = {UserRestController.class}, secure=false)
@ContextConfiguration(classes = WebApplication.class)
public class UserControllerTest {
	
    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();


    
	@Autowired
    private MockMvc mvc;
 
    @MockBean
    private UserService userService;
    
    List<User> lstUsers= new ArrayList<>();
    
    @Before
	public void setUp() {
    	User user1 = new User();
    	user1.setUserId(1);
    	user1.setFirstName("suresh");
    	user1.setLastName("rengan");
    	user1.setEmployeeId(3923323);
    	
    	User user2 = new User();
    	user2.setUserId(2);
    	user2.setFirstName("rajesh");
    	user2.setLastName("rengan");
    	user2.setEmployeeId(392323);
    	lstUsers.add(user2);
    }
    
    
    @Test
    @junitparams.Parameters(source= TestDataUser.class, method = "provideUsers")
    public void testlistAllUsers(List<User> expectedLstuser) throws Exception{
    	BDDMockito.given(userService.viewUsers()).willReturn(lstUsers);
    	
      	MvcResult result = mvc.perform(get("/user/viewUsers")
      		      .contentType(MediaType.APPLICATION_JSON))
      		      .andExpect(status().isOk()).andReturn();
      	String resultJson = result.getResponse().getContentAsString();
      	ObjectMapper mapper = new ObjectMapper();
      	List<User> lstResultUser = mapper.readValue(resultJson, new TypeReference<List<User>>(){});
      	boolean lstSucccess = true;
      	for(User actualUser: lstResultUser) {
      		if(!expectedLstuser.contains(actualUser)) {
      			lstSucccess = false;
      			break;
      		}
      	}
	    assertTrue("User listing is not correct", lstSucccess);
    }

  @Test
    @junitparams.Parameters(source= TestDataUser.class, method = "provideAddUsers")
    public void testAddUser(User addedUser) throws Exception{
    	lstUsers.add(addedUser);
    	BDDMockito.given(userService.addUser(addedUser)).willReturn(lstUsers);
    	ObjectMapper mapper = new ObjectMapper();
    	 String inputJson = mapper.writeValueAsString(addedUser);
    	 RequestBuilder request = post("/user/addUser").content(inputJson)
			        .param("employeeId", addedUser.getEmployeeId()+"")
			        .param("firstName", addedUser.getFirstName()+"")
			        .param("lastName", addedUser.getLastName()+"")
			        .param("userId", addedUser.getUserId()+"").
			       contentType(MediaType.APPLICATION_JSON);
      	MvcResult result = mvc.perform(request)
      		      .andExpect(status().isCreated()).andReturn();
      	String resultJson = result.getResponse().getContentAsString();
      	List<User> lstResultUser = mapper.readValue(resultJson, new TypeReference<List<User>>(){});
      	boolean lstSucccess = true;
      	for(User actualUser: lstResultUser) {
      		if(!lstUsers.contains(actualUser)) {
      			lstSucccess = false;
      			break;
      		}
      	}
	    assertTrue("User Addition failed", lstSucccess);
    }


    @Test
    @junitparams.Parameters(source= TestDataUser.class, method = "provideEditUsers")
    public void testEditUser(User editUser,String expectedUserName) throws Exception{
    	lstUsers.add(editUser);
    	BDDMockito.given(userService.editUser(editUser.getUserId(), editUser)).willReturn(lstUsers);
    	ObjectMapper mapper = new ObjectMapper();
    	 String inputJson = mapper.writeValueAsString(editUser);
    	 String uri = "/user/editUser/"+editUser.getUserId();
    	 RequestBuilder request = put(uri).content(inputJson).
			       contentType(MediaType.APPLICATION_JSON);
      	MvcResult result = mvc.perform(request)
      		      .andExpect(status().isOk()).andReturn();
      	String resultJson = result.getResponse().getContentAsString();
      	List<User> lstResultUser = mapper.readValue(resultJson, new TypeReference<List<User>>(){});
      	boolean lstSucccess = false;
      	User resultUser=null;
      	for(User actualUser: lstResultUser) {
      		if(lstUsers.contains(editUser)) {
      			lstSucccess = true;
      			resultUser = lstUsers.get(lstUsers.indexOf(editUser));
      			break;
      		}
      	}
      	if(!editUser.equals(resultUser)) {
      		lstSucccess = false;
      	}
	    assertTrue("User Modification failed", lstSucccess);
    }
    
    
    @Test
    @junitparams.Parameters(source= TestDataUser.class, method = "provideAddUsers")
    public void testDeleteUser(User deleteUser) throws Exception{
    	lstUsers.remove(deleteUser);
    	BDDMockito.given(userService.deleteUser(deleteUser.getUserId())).willReturn(lstUsers);
    	ObjectMapper mapper = new ObjectMapper();
    	 String uri = "/user/deleteUser/"+deleteUser.getUserId();
    	 RequestBuilder request = delete(uri).
			       contentType(MediaType.APPLICATION_JSON);
      	MvcResult result = mvc.perform(request)
      		      .andExpect(status().isOk()).andReturn();
      	String resultJson = result.getResponse().getContentAsString();
      	List<User> lstResultUser = mapper.readValue(resultJson, new TypeReference<List<User>>(){});
      	boolean lstSucccess = true;
      	for(User actualUser: lstResultUser) {
      		if(lstUsers.contains(deleteUser)) {
      			lstSucccess = false;
      			break;
      		}
      	}
      assertTrue("User delete failed", lstSucccess);
    }
    
    
    @Test
    @junitparams.Parameters(source= TestDataUser.class, method = "provideUsersForSort")
    public void testSortAllUsers(List<User> expectedLstuser,int sortType) throws Exception{
    	BDDMockito.given(userService.sortUsers(sortType)).willReturn(lstUsers);
      	MvcResult result = mvc.perform(get("/user/sortUsers/"+sortType)
      		      .contentType(MediaType.APPLICATION_JSON))
      		      .andExpect(status().isOk()).andReturn();
      	String resultJson = result.getResponse().getContentAsString();
      	ObjectMapper mapper = new ObjectMapper();
      	List<User> lstResultUser = mapper.readValue(resultJson, new TypeReference<List<User>>(){});
      	boolean lstSucccess = true;
      	for(User actualUser: lstResultUser) {
      		if(!expectedLstuser.contains(actualUser)) {
      			lstSucccess = false;
      			break;
      		}
      	}
	    assertTrue("User Sorting is not correct", lstSucccess);
    }

    @Test
    @junitparams.Parameters(source= TestDataUser.class, method = "provideSearchByName")
    public void testSearchUserByName(List<User> expectedUserLst,String searchUserName) throws Exception{
    	BDDMockito.given(userService.searchUserByName(searchUserName)).willReturn(expectedUserLst);
      	MvcResult result = mvc.perform(get("/user/searchUser/?userName="+searchUserName)
      		      .contentType(MediaType.APPLICATION_JSON))
      		      .andExpect(status().isOk()).andReturn();
      	String resultJson = result.getResponse().getContentAsString();
      	ObjectMapper mapper = new ObjectMapper();
      	List<User> lstResultUser = mapper.readValue(resultJson, new TypeReference<List<User>>(){});
      	boolean lstSucccess = true;
      	for(User actualUser: lstResultUser) {
      		if(actualUser.getLastName().equals(searchUserName)) {
      			lstSucccess = true;
      			break;
      		}
      	}
	    assertTrue("User Search is not correct", lstSucccess);

    }
    

}
