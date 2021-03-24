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
import com.eci.bean.PendingApproval;
import com.eci.service.PendingApprovalService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class PendingApprovalController {  

	@Autowired
	PendingApprovalService pendingApprService;
	
	@RequestMapping(value = "/getPendingApproveList/{userid}", method = RequestMethod.GET, headers = "Accept=application/json")
	
	public ObjectNode getPendingApproveList(@PathVariable int userid) {		
		
		ObjectNode listOfProd = pendingApprService.getPendingApproveList(userid);	
		
		System.out.println("hi i am coming with getPendingApproveList");
		
		return listOfProd;
	}
		
	@RequestMapping(value = "/insertIncClaimData", method = RequestMethod.POST, headers = "Accept=application/json")
	
	public ObjectNode insertUser(@RequestBody PendingApproval  pendingApproval) throws ClassNotFoundException, IOException, SQLException {	
		
		ObjectNode flag = pendingApprService.insertIncClaimData(pendingApproval);	
		
		System.out.println("hi i am coming pendingApprService");
		
		return flag;
	}

	
	@RequestMapping(value = "/showClaimDataByInstId/{instId}", method = RequestMethod.GET, headers = "Accept=application/json")
	
	public ObjectNode showClaimDataByInstId(@PathVariable int instId) {		
		
		ObjectNode listOfProd = pendingApprService.showClaimDataByInstId(instId);	
		
		System.out.println("hi i am coming with showClaimDataByInstId");
		
		return listOfProd;
	}
	
	@RequestMapping(value = "/getStatusListbyInstId/{instId}", method = RequestMethod.GET, headers = "Accept=application/json")
	
	public ObjectNode getStatusListbyInstId(@PathVariable int instId) {		
		
		ObjectNode listOfProd = pendingApprService.getStatusListbyInstId(instId);	
		
		System.out.println("hi i am coming with getStatusListbyInstId");
		
		return listOfProd;
	}
	
	@RequestMapping(value = "/saveRejectRemark", method = RequestMethod.POST, headers = "Accept=application/json")
	
	public ObjectNode saveRejectRemark(@RequestBody PendingApproval  pendingApproval) throws ClassNotFoundException, IOException, SQLException {	
		
		ObjectNode flag = pendingApprService.saveRejectRemark(pendingApproval);	
		
		System.out.println("hi i am coming saveRejectRemark");
		
		return flag;
	}

@RequestMapping(value = "/releasePOQty", method = RequestMethod.POST, headers = "Accept=application/json")
	
	public ObjectNode releasePOQty(@RequestBody PendingApproval  pendingApproval) throws ClassNotFoundException, IOException, SQLException {	
		
		ObjectNode flag = pendingApprService.releasePOQty(pendingApproval);	
		
		System.out.println("hi i am coming releasePOQty");
		
		return flag;
	}

	
 	

}
