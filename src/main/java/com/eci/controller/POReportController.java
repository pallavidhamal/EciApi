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
import com.eci.bean.SI;
import com.eci.bean.Summary;
import com.eci.service.InstallationService;
import com.eci.service.POReportService;
import com.eci.service.SummaryService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class POReportController {  

	@Autowired
	POReportService poreportService;
		
		@RequestMapping(value = "/getPOReportData/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
		public ObjectNode getPOReportData(@PathVariable int id) {	
			
			ObjectNode list = poreportService.getPOReportData(id);	
			return list;
		}
			
		@RequestMapping(value = "/getClosedPOReportData/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
		public ObjectNode getClosedPOReportData(@PathVariable int id) {	
			
			ObjectNode list = poreportService.getClosedPOReportData(id);	
			return list;
		}
}
