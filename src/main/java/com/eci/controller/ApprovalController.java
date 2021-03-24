package com.eci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eci.bean.Approval;
import com.eci.service.ApprovalService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class ApprovalController {  

	@Autowired
	ApprovalService approvalService;
	
	@RequestMapping(value = "/getApprovalHPMList", method = RequestMethod.GET, headers = "Accept=application/json")
	
	public ObjectNode getApprovalHPMList() {		
		
		ObjectNode listOfProd = approvalService.getApprovalHPMList();	
		
		System.out.println("hi i am coming with getApprovalHPMList");
		
		return listOfProd;
	}
	
	@RequestMapping(value = "/approvalStatusId", method = RequestMethod.POST, headers = "Accept=application/json")
	public boolean approvalStatusId(@RequestBody Approval  approval) {		
		
		boolean flag = approvalService.approvalStatusId(approval);	
		
		System.out.println("hi i am coming3 approvalStatusId");
		
		return flag;
	}
	
		
	

}
