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

import com.eci.bean.Installation;
import com.eci.bean.PoKitty;
import com.eci.bean.Task;
import com.eci.bean.TaskStatus;
import com.eci.service.InstallationService;
import com.eci.service.TaskStatusService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class TaskStatusController {  

	@Autowired
	TaskStatusService taskStatusService;
	
	@RequestMapping(value = "/getTaskStatusList/{userid}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getInstallationList(@PathVariable int userid) {		
		
		ObjectNode listOfProd = taskStatusService.getTaskStatusList(userid);	
		
		System.out.println("hi i am coming with getTaskStatusList");
		
		return listOfProd;
	}
	
	
	@RequestMapping(value = "/findPoListByNo/{PoNum}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getCustomerDetail(@PathVariable String PoNum) {	
		
		ObjectNode flag = taskStatusService.findPoListByNo(PoNum);
		
		return flag;
	}
	
	@RequestMapping(value = "/assignPo", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode assignPo(@RequestBody Task task) {	
		
		ObjectNode flag = taskStatusService.assignPo(task);	
		
		System.out.println("hi i am coming assignPo");
		
		return flag;
	}
	
	@RequestMapping(value = "/saveReopenJob", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode saveReopenJob(@RequestBody Task task) {	
		
		ObjectNode flag = taskStatusService.saveReopenJob(task);	
		
		System.out.println("hi i am coming saveReopenJob");
		
		return flag;
	}
	
	@RequestMapping(value = "/getTaskFrmAction/{userid}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getTaskFrmAction(@PathVariable int userid) {		
		
		ObjectNode listOfProd = taskStatusService.getTaskFrmAction(userid);	
		
		System.out.println("hi i am coming with getTaskFrmAction");
		
		return listOfProd;
	}
	

}
