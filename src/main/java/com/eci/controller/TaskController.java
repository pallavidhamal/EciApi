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

import com.eci.bean.Task;
import com.eci.service.TaskService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class TaskController {

	@Autowired
	TaskService taskService;
	
//	list all tasks for select box
	@RequestMapping(value = "/getTasks/{userid}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getTasks(@PathVariable int userid) {		
		ObjectNode listOfTask = taskService.getAllTasks(userid);	
		System.out.println("hi i am coming");
		return listOfTask;
	}
	
//	get details of Customers for edit
	@RequestMapping(value = "/getTaskDetail/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getTaskDetail(@PathVariable int id) {	
		System.out.println("id========="+id);
		ObjectNode listOfTasks = taskService.getTaskDetail(id);	
		return listOfTasks;
	}
	
//	insert Customer
	@RequestMapping(value = "/insertTask", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode insertTask(@RequestBody Task task) throws ClassNotFoundException, IOException, SQLException {		
		ObjectNode flag = taskService.insertTask(task);	
		System.out.println("hi i am coming1");
		return flag;
	}
	
	
	//edit Customer
	@RequestMapping(value = "/updateTask", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ObjectNode updateCustomer(@RequestBody Task task) {		
		ObjectNode flag = taskService.updateTask(task);	
		System.out.println("hi i am coming3");
		return flag;
	}
	
	
	//edit regions
		@RequestMapping(value = "/inactivateTask", method = RequestMethod.PUT, headers = "Accept=application/json")
		public ObjectNode inactivateTask(@RequestBody Task task) {		
			ObjectNode flag = taskService.inactivateTask(task);	
			return flag;
		}
	
		@RequestMapping(value = "/activateTask", method = RequestMethod.PUT, headers = "Accept=application/json")
		public ObjectNode activateTask(@RequestBody Task task) {		
			ObjectNode flag = taskService.activateTask(task);	
			return flag;
		}
	
}
