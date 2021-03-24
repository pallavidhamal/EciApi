
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

import com.eci.bean.SICompany;
import com.eci.bean.Task;
import com.eci.service.SICompanyService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class SICompanyController {

	@Autowired
	SICompanyService companyservice;
	
//	list all tasks for select box
	@RequestMapping(value = "/getSICompany/{userid}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getSICompany(@PathVariable int userid) {		
		ObjectNode listOfTask = companyservice.getSICompany(userid);	
		System.out.println("hi i am coming SI Company");
		return listOfTask;
	}
	@RequestMapping(value = "/getSIDetail/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getSIDetail(@PathVariable int id) {	
		System.out.println("id========="+id);
		ObjectNode listOfTasks = companyservice.getSIDetail(id);	
		return listOfTasks;
	}
	@RequestMapping(value = "/insertSICompany", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode insertSICompany(@RequestBody SICompany siCmp) throws ClassNotFoundException, IOException, SQLException {		
		ObjectNode flag = companyservice.insertSICompany(siCmp);	
		System.out.println("hi i am coming1");
		return flag;
	}
	@RequestMapping(value = "/updateSI", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ObjectNode updateSI(@RequestBody SICompany siCmp) {		
		ObjectNode flag = companyservice.updateSI(siCmp);	
		System.out.println("hi i am coming3");
		return flag;
	}
	@RequestMapping(value = "/inactivateSI", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ObjectNode inactivateSI(@RequestBody SICompany siCmp) {		
		ObjectNode flag = companyservice.inactivateSI(siCmp);	
		return flag;
	}

	@RequestMapping(value = "/activateSI", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ObjectNode activateSI(@RequestBody SICompany siCmp) {		
		ObjectNode flag = companyservice.activateSI(siCmp);	
		return flag;
	}
	
}
