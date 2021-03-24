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
import com.eci.bean.Task;
import com.eci.bean.Unassigned;
import com.eci.service.InstallationService;
import com.eci.service.UnassignedService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")

public class UnassignedController {  

	@Autowired
	UnassignedService unassignedService;
	
	@RequestMapping(value = "/getInsToReassignList/{userid}", method = RequestMethod.GET, headers = "Accept=application/json")
	
	public ObjectNode getInsToReassignList(@PathVariable int userid) {	
		
		ObjectNode listOfProd = unassignedService.getInsToReassignList(userid);	
		
		System.out.println("hi i am coming with getInsToReassignList");
		
		return listOfProd;
	}
		
	
		
	@RequestMapping(value = "/getAssignSIList", method = RequestMethod.GET, headers = "Accept=application/json")
	
	public ObjectNode getAssignSIList() {		
		
		ObjectNode listOfProd = unassignedService.getAssignSIList();
		
		System.out.println("hi i am coming with getAssignSIList");
		
		return listOfProd;
	}
	
		@RequestMapping(value = "/updateReassignremark", method = RequestMethod.POST, headers = "Accept=application/json")
		
		public ObjectNode updateremark(@RequestBody Unassigned  unassigned) {		
			
			ObjectNode flag = unassignedService.updateReassignremark(unassigned);	
			
			System.out.println("hi i am coming updateReassignremark");
			return flag;
		}
	
		
		@RequestMapping(value = "/updateReassignSI", method = RequestMethod.POST, headers = "Accept=application/json")
		public ObjectNode updateReassignSI(@RequestBody Unassigned  unassigned) {		
			
			ObjectNode flag = unassignedService.updateReassignSI(unassigned);	
			
			System.out.println("hi i am coming3");
			
			return flag;
		}
		
		@RequestMapping(value = "/getInsApprovalList/{userid}", method = RequestMethod.GET, headers = "Accept=application/json")
		
		public ObjectNode getInsApprovalList(@PathVariable int userid) {	
			
			ObjectNode listOfProd = unassignedService.getInsApprovalList(userid);	
			
			System.out.println("hi i am coming with getInsApprovalList");
			
			return listOfProd;
		}
		
		@RequestMapping(value = "/seekApproval", method = RequestMethod.POST, headers = "Accept=application/json")
		public ObjectNode seekApproval(@RequestBody Unassigned  unassigned) {		
			
			ObjectNode flag = unassignedService.seekApproval(unassigned);	
			
			System.out.println("hi i am coming3");
			
			return flag;
		}
		
		
}
