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

import com.eci.bean.ApprovedTasksReport;
import com.eci.service.ApprovedTasksReportService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class ApprovedTasksReportController {  

	@Autowired
	ApprovedTasksReportService approvedtasksreportService;
		
	@RequestMapping(value = "/getApprovedTasksReport", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode insertUser(@RequestBody ApprovedTasksReport  approvedTasksReport) throws ClassNotFoundException, IOException, SQLException {	
		
		ObjectNode flag = approvedtasksreportService.getApprovedTasksReport(approvedTasksReport);
		
		System.out.println("hi i am coming getApprovedTasksReport");
		
		return flag;
	}

	
	
	@RequestMapping(value = "/getSICompanyList/{userid}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getSICompanyList(@PathVariable int userid) {		
		ObjectNode listOfProd = approvedtasksreportService.getSICompanyList(userid);	
		System.out.println("hi i am coming with getSICompanyList");
		return listOfProd;
	}
		
	@RequestMapping(value = "/getAppJobDetails/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getAppJobDetails(@PathVariable int id) {		
		ObjectNode listOfProd = approvedtasksreportService.getAppJobDetails(id);
		
		System.out.println("hi i am coming with getCompleteJobDetails======");
		return listOfProd;
	}
	
	
	@RequestMapping(value = "/getHPMApprovedTasks", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode getHPMApprovedTasks(@RequestBody ApprovedTasksReport  approvedTasksReport) throws ClassNotFoundException, IOException, SQLException {	
		
		ObjectNode flag = approvedtasksreportService.getHPMApprovedTasks(approvedTasksReport);
		
		System.out.println("hi i am coming getApprovedTasksReport");
		
		return flag;
	}
	
}
