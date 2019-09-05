package com.project.springcontrollers;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.exception.ResourceNotFoundException;
import com.project.entity.User;
import com.project.service.UserService;
import com.project.util.FSDContansts;

/**
 * @author 396946
 *
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserRestController {
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "/addUser",
			method = RequestMethod.POST,produces = FSDContansts.APPLICATION_JSON)
	 @ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody List<User> addUser(@RequestBody User user){
		/*ObjectMapper obj=new ObjectMapper();
		try {
		String str = obj.writeValueAsString(user);
		System.out.println("Tets suresh"+str);
		}catch(Exception e) {
			e.printStackTrace();
		}*/
		System.out.println(user);
		return userService.addUser(user);
	}
	
	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE,produces = FSDContansts.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<User> deleteUser(@PathVariable("id") long userId){
		return userService.deleteUser(userId);
	}
	
	@RequestMapping(value = "/editUser/{id}", method = RequestMethod.PUT,produces = FSDContansts.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<User> editUser(@PathVariable("id") long userId,@RequestBody User user) {
		return userService.editUser(userId, user);
	}

	@RequestMapping(value = "/viewUsers",
			method = RequestMethod.GET,produces = FSDContansts.APPLICATION_JSON)
	 @ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<User> viewUsers() {
		return userService.viewUsers();
	}
	
	@RequestMapping(value = "/sortUsers/{sorttype}",
			method = RequestMethod.GET,produces = FSDContansts.APPLICATION_JSON)
	 @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<User> sortUsers(@PathVariable("sorttype") long sortType) {
		return userService.sortUsers(sortType);
	}

	@RequestMapping(value = "/searchUser",
			method = RequestMethod.GET,produces = FSDContansts.APPLICATION_JSON)
	 @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<User> searchUserByName(@QueryParam("userName") String userName) {
		return userService.searchUserByName(userName);
	}

}
