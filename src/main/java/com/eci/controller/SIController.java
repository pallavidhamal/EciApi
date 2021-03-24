package com.eci.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eci.bean.Customer;
import com.eci.bean.Installation;
import com.eci.bean.InstallationAction;
import com.eci.bean.SI;
import com.eci.bean.User;
import com.eci.service.SIService;
import com.eci.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class SIController {

	@Autowired
	 SIService siService;  
	 //UserService userService;  
	
//		list all SI regions
		@RequestMapping(value = "/getSIRegionsList", method = RequestMethod.POST, headers = "Accept=application/json")		
		public ObjectNode getSIRegionsList(@RequestBody Installation inc) {
						
			System.out.println("welcom to SIController getSIUsersList");
			ObjectNode listOfSI = siService.getSIRegionsList(inc);	
			return listOfSI;
		
		}
	
//	get details of SI's region wise jobs
	@RequestMapping(value = "/getSIRegionWiseJobs", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode getSIRegionWiseJobs(@RequestBody Installation inc) {
		
		System.out.println("welcom to SIController getSIRegionWiseJobs");
		ObjectNode listOfSIRegWiseJobs = siService.getSIRegionWiseJobs(inc);	
		return listOfSIRegWiseJobs;
	
	}

	@RequestMapping(value = "/takeIncAction", method = RequestMethod.POST, headers = "Accept=application/json")
	public boolean takeIncAction(@RequestBody InstallationAction IA) {	
		
		boolean flag = siService.takeIncAction(IA);	
		return flag;
	}
	
	@RequestMapping(value = "/getTEList", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode getTEList(@RequestBody SI si) {	
		
		ObjectNode list = siService.getTEList(si);	
		return list;
	}
	
	@RequestMapping(value = "/getSIsummary", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode getSIsummary(@RequestBody SI si) {	
		
		ObjectNode list = siService.getSIsummary(si);	
		return list;
	}
	@RequestMapping(value = "getReasonList", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode getRegionList(@RequestBody SI si) {	
		
		ObjectNode list = siService.getReasonList(si);	
		return list;
	}
	
	
/*	@RequestMapping(value = "/SIAcceptJob", method = RequestMethod.POST, headers = "Accept=application/json")
	public boolean SIAcceptJob(@RequestBody InstallationAction IA) {	
		
		boolean flag = siService.SIAcceptJob(IA);	
		return flag;
	}
	
	@RequestMapping(value = "/SIRejectJob", method = RequestMethod.POST, headers = "Accept=application/json")
	public boolean SIRejectJob(@RequestBody SI si) {	
		
		//System.out.println("hi i am in SIRejectJob"+si.getSI_Id());
		
		boolean flag = siService.SIRejectJob(si);	
		return flag;
	}
	
	@RequestMapping(value = "/AssignTE", method = RequestMethod.POST, headers = "Accept=application/json")
	public boolean AssignTE(@RequestBody SI si) {	
		
		//System.out.println("hi i am in SIRejectJob"+si.getSI_Id());
		
		boolean flag = siService.AssignTE(si);	
		return flag;
	}*/
	
	
	@RequestMapping(value = "/SIUpdateTask", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public boolean SIUpdateTask(@RequestParam(name="file" ,required=false) MultipartFile file,InstallationAction IA)throws IOException 
	{
	//public ResponseEntity<Object> SIUpdateTask(@RequestParam("file") MultipartFile file,SI si)throws IOException {		
		
		//System.out.println("hhh"+si.getName());
		boolean flag = siService.SIUpdateTask(file,IA);
		
		return flag;
	}
	
	

	
	
}
