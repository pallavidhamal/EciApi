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

import com.eci.bean.CompleteJobReport;
import com.eci.service.CompleteJobReportService;
import com.eci.service.CustomerHReportService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")

public class CustomerHReportController {  

	@Autowired
	CustomerHReportService customerhreportservice;
		
	@RequestMapping(value = "/getCustHReportList/{custId}", method = RequestMethod.GET, headers = "Accept=application/json")
	
	public ObjectNode getCustHReportList(@PathVariable int custId) throws ClassNotFoundException, IOException, SQLException {	
		
		ObjectNode flag = customerhreportservice.getCustHReportList(custId);
		
		System.out.println("hi i am coming getCustHReportList");
		
		return flag;
	}
	
		
}
