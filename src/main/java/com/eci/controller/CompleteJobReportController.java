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
import com.eci.bean.CompleteJobReport;
import com.eci.bean.Installation;
import com.eci.bean.WipPendingReport;
import com.eci.service.CompleteJobReportService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class CompleteJobReportController {  

	@Autowired
	CompleteJobReportService completejobreportservice;
		
	@RequestMapping(value = "/getcompletejobReport", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode getcompletejobReport(@RequestBody WipPendingReport  wippendingreport) throws ClassNotFoundException, IOException, SQLException {	
		
		ObjectNode flag = completejobreportservice.getcompletejobReport(wippendingreport);
		
		System.out.println("hi i am coming getcompletejobReport");
		
		return flag;
	}
	
	
	@RequestMapping(value = "/getCompleteJobDetails/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getCompleteJobDetails(@PathVariable int id) {		
		ObjectNode listOfProd = completejobreportservice.getCompleteJobDetails(id);
		
		System.out.println("hi i am coming with getCompleteJobDetails======");
		return listOfProd;
	}

	@RequestMapping(value = "/getAdHocReport", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode getAdHocReport(@RequestBody CompleteJobReport completejobreport) throws ClassNotFoundException, IOException, SQLException {	
		
		ObjectNode flag = completejobreportservice.getAdHocReport(completejobreport);
		
		System.out.println("hi i am coming getAdHocReport");
		
		return flag;
	}
	
	@RequestMapping(value = "/getJobdetailsImg", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode getJobdetailsImg(@RequestBody CompleteJobReport completejobreport) throws ClassNotFoundException, IOException, SQLException {	
		
		ObjectNode flag = completejobreportservice.getJobdetailsImg(completejobreport);
		
		System.out.println("hi i am coming getJobdetailsImg");
		
		return flag;
	}
	
	@RequestMapping(value = "/getQcReport", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode getQcReport(@RequestBody CompleteJobReport completejobreport) throws ClassNotFoundException, IOException, SQLException {	
		
		ObjectNode flag = completejobreportservice.getQcReport(completejobreport);
		
		System.out.println("hi i am coming getAdHocReport");
		
		return flag;
	}
	
		
		
}
