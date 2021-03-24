package com.eci.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eci.bean.Installation;
import com.eci.service.Apt_TestService;
import com.eci.service.InstallationService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class Apt_TestController {  
	@Autowired
	InstallationService installationService;
	@Autowired
	Apt_TestService Apt_TestService;
	
	@RequestMapping(value = "/getAptList", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getAptList() {		
		ObjectNode listOfProd = Apt_TestService.getAptList();	
		System.out.println("hi i am coming with getAptList");
		return listOfProd;
	}

	@RequestMapping(value = "/AptTestList", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode AptTestList(@RequestBody Installation  installation)  throws ClassNotFoundException, IOException, SQLException {
		System.out.println("hi i am coming step1");	
		ObjectNode flag = Apt_TestService.AptTestList(installation);	
		System.out.println("hi i am coming updateremark");
		return flag;
	}
	
	@RequestMapping(value = "/AptDataInsert", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode AptDataInsert(@RequestBody String checkboxD)  throws ClassNotFoundException, IOException, SQLException {
		ObjectNode node = Apt_TestService.AptDataInsert(checkboxD);	
		System.out.println("hi i am coming Insert Apt Data");
		return node;
	}
	
	@RequestMapping(value = "/AptTestListnew", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode AptTestListnew(@RequestBody String checkboxD)  throws ClassNotFoundException, IOException, SQLException {
		System.out.println("=====0====="+checkboxD);
		ObjectNode node = Apt_TestService.AptTestListnew(checkboxD);	
		System.out.println("hi i am coming Insert Apt list");
		return node;
	}
	
	@RequestMapping(value = "/DInsert", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode DInsert(@RequestBody String checkboxD)  throws ClassNotFoundException, IOException, SQLException {
		ObjectNode node = Apt_TestService.DInsert(checkboxD);	
		System.out.println("hi i am coming Ita");
		return node;
	}
	
	@RequestMapping(value = "/DeleteExist", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode DeleteExist(@RequestBody String checkboxD)  throws ClassNotFoundException, IOException, SQLException {
		ObjectNode node = Apt_TestService.DeleteExist(checkboxD);	
		System.out.println("hi i am coming to delete @");
		return node;
	}
	@RequestMapping(value = "/getTaskData", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode getTaskData(@RequestBody String TaskID)  throws ClassNotFoundException, IOException, SQLException {
		ObjectNode node = Apt_TestService.getTaskData(TaskID);	
		System.out.println("hi i am coming to delete");
		return node;
	}
	@RequestMapping(value = "/InsertAtpTaskData", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode InsertAtpTaskData(@RequestBody String TaskID)  throws ClassNotFoundException, IOException, SQLException {
		ObjectNode node = Apt_TestService.InsertAtpTaskData(TaskID);	
		System.out.println("hi i am coming to delete");
		return node;
	}
	@RequestMapping(value = "/getAptTest", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getAptTest() {		
		ObjectNode listOfProd = Apt_TestService.getAptTest();	
		System.out.println("hi i am coming with getAptList");
		return listOfProd;
	}
	
	@RequestMapping(value = "/updateatp", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode updateatp(@RequestBody String editid)  throws ClassNotFoundException, IOException, SQLException {
		ObjectNode node = Apt_TestService.updateatp(editid);	
		System.out.println("hi i am coming Insert Apt Data");
		return node;
	}
	@RequestMapping(value = "/updateAtpTaskData", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode updateAtpTaskData(@RequestBody String TaskID)  throws ClassNotFoundException, IOException, SQLException {
		ObjectNode node = Apt_TestService.updateAtpTaskData(TaskID);	
		System.out.println("hi i am coming to delete");
		return node;
	}
	@RequestMapping(value = "/deleteRecord", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode deleteRecord(@RequestBody String DelId)  throws ClassNotFoundException, IOException, SQLException {
		ObjectNode node = Apt_TestService.deleteRecord(DelId);	
		System.out.println("hi i am coming to delete");
		return node;
	}
	@RequestMapping(value = "/updateSeq", method = RequestMethod.POST, headers = "Accept=application/json")
	public boolean updateSeq(@RequestBody String TaskID)  throws ClassNotFoundException, IOException, SQLException {
		boolean node = Apt_TestService.updateSeq(TaskID);	
		System.out.println("hi i am coming to delete");
		return node;
	}
}
