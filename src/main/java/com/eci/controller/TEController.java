package com.eci.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eci.bean.Installation;
import com.eci.bean.InstallationAction;
import com.eci.bean.SI;
import com.eci.service.TEService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class TEController {

	@Autowired
	 TEService teService;  
	
	@Autowired
	  JavaMailSender javaMailSender;
	 //UserService userService;  
	
//		list all SI users List
		@RequestMapping(value = "/getTERegionList", method = RequestMethod.POST, headers = "Accept=application/json")		
		public ObjectNode getTERegionList(@RequestBody Installation inc) {					
		//	System.out.println("welcom to TEController getSIUsersList");
			ObjectNode listOfTEReg = teService.getTERegionList(inc);	
			return listOfTEReg;		
		}
	
//	get details of SIUsersRegionwise job
	@RequestMapping(value = "/getTERegionWiseJobs", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode getTERegionWiseJobs(@RequestBody Installation inc) {
		
	//	System.out.println("welcom to TEController getSIRegionWiseJobs");
		ObjectNode listOfTERegWiseJobs = teService.getTERegionWiseJobs(inc);	
		return listOfTERegWiseJobs;
	
	}

//	get details of SIUsersRegionwise job
	@RequestMapping(value = "/getTEJobWiseTasks", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode getTEJobWiseTasks(@RequestBody Installation inc) { 
		
	//	System.out.println("welcom to TEController getTEJobWiseTasks");
		ObjectNode listOfTERegWiseJobs = teService.getTEJobWiseTasks(inc);	
		return listOfTERegWiseJobs;
	
	}
	
//	get details of SIUsersRegionwise job
	@RequestMapping(value = "/getTETaskTests", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode getTETaskTests(@RequestBody Installation inc) { 
		
	//	System.out.println("welcom to TEController getTEJobWiseTasks");
		ObjectNode listOfTERegWiseJobs = teService.getTETaskTests(inc);	
		return listOfTERegWiseJobs;
	
	}
	
//	get details of SIUsersRegionwise job
	@RequestMapping(value = "/updateTETestStatus", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode updateTETestStatus(@RequestBody String data) { 
		
	//	System.out.println("welcom to TEController getTEJobWiseTasks");
		ObjectNode node = teService.updateTETestStatus(data, javaMailSender);	
		return node;
	}
	
	@RequestMapping(value = "/updateTEPhotoTests", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ObjectNode updateTEPhotoTests(@RequestParam(name="file" ,required=false) MultipartFile file,InstallationAction IA) { 
		
	//	System.out.println("welcom to TEController getTEJobWiseTasks");
		ObjectNode node = teService.updateTEPhotoTests(file,IA);	
		return node;
	}
	
/*	@RequestMapping(value = "/updateTETestStatus", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ObjectNode updateTETestStatus(@RequestParam(name="file" ,required=false) MultipartFile file,@RequestParam(name="file" ,required=false) MultipartFile file1, String data) { 
		
	//	System.out.println("welcom to TEController getTEJobWiseTasks");
		ObjectNode node = teService.updateTETestStatus(file,file1,data);	
		return node;
	}*/
	
	
	
/*	@RequestMapping(value = "/SIUpdateTask", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public boolean SIUpdateTask(@RequestParam(name="file" ,required=false) MultipartFile file,InstallationAction IA)throws IOException 
	{
	//public ResponseEntity<Object> SIUpdateTask(@RequestParam("file") MultipartFile file,SI si)throws IOException {		
		
		//System.out.println("hhh"+si.getName());
		boolean flag = siService.SIUpdateTask(file,IA);
		
		return flag;
	}*/
	
	
	
	
	
//	list all SI users List
	@RequestMapping(value = "/getTeTaskCategory", method = RequestMethod.POST, headers = "Accept=application/json")		
	public ObjectNode getTeTaskCategory(@RequestBody Installation inc) {					
	//	System.out.println("welcom to TEController getSIUsersList");
		ObjectNode listOfTEReg = teService.getTeTaskCategory(inc);	
		return listOfTEReg;		
	}
	
	
/*	@RequestMapping(value = "/SIAcceptJob", method = RequestMethod.POST, headers = "Accept=application/json")
	public boolean SIAcceptJob(@RequestBody SI si) {	
		
		//System.out.println("hi i am in SIAcceptJob"+si.getSI_Id());
		
		boolean flag = TEService.SIAcceptJob(si);	
		return flag;
	}
	
	@RequestMapping(value = "/SIRejectJob", method = RequestMethod.POST, headers = "Accept=application/json")
	public boolean SIRejectJob(@RequestBody SI si) {	
		
		//System.out.println("hi i am in SIRejectJob"+si.getSI_Id());
		
		boolean flag = SIService.SIRejectJob(si);	
		return flag;
	}
	
	@RequestMapping(value = "/AssignTE", method = RequestMethod.POST, headers = "Accept=application/json")
	public boolean AssignTE(@RequestBody SI si) {	
		
		//System.out.println("hi i am in SIRejectJob"+si.getSI_Id());
		
		boolean flag = SIService.AssignTE(si);	
		return flag;
	}
	
	*/

	
	
}
