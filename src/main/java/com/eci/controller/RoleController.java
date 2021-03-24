package com.eci.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eci.bean.Customer;
import com.eci.bean.Role;
import com.eci.bean.Task;
import com.eci.service.RoleService;
import com.eci.service.TaskService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class RoleController {

	@Autowired
	RoleService roleService;
	
//	list all tasks for select box
	@RequestMapping(value = "/getRoles", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getRoles() {		
		ObjectNode listOfTask = roleService.getRoles();	
		System.out.println("hi i am coming");
		return listOfTask;
	}
	
	//insert Role
	@RequestMapping(value = "/insertRole", method = RequestMethod.POST, headers = "Accept=application/json")
	public boolean insertRole(@RequestBody Role role) throws ClassNotFoundException, IOException, SQLException {		
		System.out.println("QUERY For getRoleType "+role.getRoleType());
		boolean flag = roleService.insertRole(role);
		
		System.out.println("hi i am coming1");
		System.out.println("QUERY For flag insertRole "+flag);
		
		return flag;
	}
	
	
	//get details of role for edit
	@RequestMapping(value = "/getRoleDetail/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getRoleDetail(@PathVariable int id) {	
		System.out.println("id========="+id);
		ObjectNode listOfTasks = roleService.getRoleDetail(id);	
		return listOfTasks;
	}
	
	
	@RequestMapping(value = "/updateRole", method = RequestMethod.PUT, headers = "Accept=application/json")
	public boolean updateRole(@RequestBody Role role) {		
		boolean flag = roleService.updateRole(role);	
		System.out.println("return flag" + flag);
		return flag;
	}
	

	@RequestMapping(value = "/inactivateRole", method = RequestMethod.PUT, headers = "Accept=application/json")
	public boolean inactivateTask(@RequestBody Role role) {		
			boolean flag = roleService.inactivateRole(role);	
			return flag;
		} 
}
