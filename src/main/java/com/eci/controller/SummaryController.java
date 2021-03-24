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
import com.eci.service.SummaryService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class SummaryController {  

	@Autowired
	SummaryService summaryService;

		
		@RequestMapping(value = "/getPMsummary/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
		public ObjectNode getPMsummary(@PathVariable int id) {	
			
			ObjectNode list = summaryService.getPMsummary(id);	
			return list;
		}
		
		@RequestMapping(value = "/getWIPDetails", method = RequestMethod.POST, headers = "Accept=application/json")
		public ObjectNode getWIPDetails(@RequestBody Summary  summary) {	
			
			ObjectNode list = summaryService.getWIPDetails(summary);	
			return list;
		}
		
}
