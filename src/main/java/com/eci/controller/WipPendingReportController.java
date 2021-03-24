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
import com.eci.bean.WipPendingReport;
import com.eci.service.ApprovedTasksReportService;
import com.eci.service.WipPendingReportService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class WipPendingReportController {  

	@Autowired
	WipPendingReportService wippendingreportservice;
		
	@RequestMapping(value = "/getWippendingReport", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode insertUser(@RequestBody WipPendingReport  wippendingreport) throws ClassNotFoundException, IOException, SQLException {	
		
		ObjectNode flag = wippendingreportservice.getWippendingReport(wippendingreport);
		
		System.out.println("hi i am coming getWippendingReport");
		
		return flag;
		
		
		
		
		
	}

		
}
