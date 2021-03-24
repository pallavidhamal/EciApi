package com.eci.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eci.bean.Installation;
import com.eci.bean.SI;
import com.eci.service.InstallationService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class InstallationController {  

	@Autowired
	InstallationService installationService;
	
	
	@RequestMapping(value = "/getInstallationList/{userid}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getInstallationList(@PathVariable int userid) {		
		ObjectNode listOfProd = installationService.getInstallationList(userid);	
		System.out.println("hi i am coming with getInstallationList");
		return listOfProd;
	}
		
	
	@RequestMapping(value = "/getInstallationCode", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getInstallationCode() {		
		ObjectNode listOfProd = installationService.getInstallationCode();	
		System.out.println("hi i am coming with getInstallationCode");
		return listOfProd;
	}
	
	@RequestMapping(value = "/getSubContractorList/{userid}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getSunContractorList(@PathVariable int userid) {		
		ObjectNode listOfProd = installationService.getSubContractorList(userid);	
		System.out.println("hi i am coming with getSubContractorList 11");
		return listOfProd;
	}
	
//	insert 
	@RequestMapping(value = "/insertInstallationtask", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode insertUser(@RequestBody Installation  installation) throws ClassNotFoundException, IOException, SQLException {	
		
		ObjectNode flag = installationService.insertInstallationtask(installation);
		
		System.out.println("hi i am coming insertInstallationtask");
		
		return flag;
	}
	
		@RequestMapping(value = "/updateremark", method = RequestMethod.POST, headers = "Accept=application/json")
		public ObjectNode updateremark(@RequestBody Installation  installation) {		
			ObjectNode flag = installationService.updateremark(installation);	
			System.out.println("hi i am coming updateremark");
			return flag;
		}
	
		
		@RequestMapping(value = "/cancelInstallation", method = RequestMethod.POST, headers = "Accept=application/json")
		public ObjectNode cancelInstallation(@RequestBody Installation  installation) {		
			ObjectNode flag = installationService.cancelInstallation(installation);	
			System.out.println("hi i am coming cancelInstallation");
			return flag;
		}
		
		@RequestMapping(value = "/deleteInstallation", method = RequestMethod.POST, headers = "Accept=application/json")
		public ObjectNode deleteInstallation(@RequestBody Installation  installation) {		
			ObjectNode flag = installationService.deleteInstallation(installation);	
			System.out.println("hi i am coming cancelInstallation");
			return flag;
		}
				
		
		@RequestMapping(value = "/getUserWiseRegion/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
		public ObjectNode getUserWiseRegion(@PathVariable int id) {	
			System.out.println("id========="+id);
			ObjectNode listOfUsers = installationService.getUserWiseRegion(id);	
			return listOfUsers;
		}
		
		
		@RequestMapping(value = "/generatePdfAction", method = RequestMethod.GET, headers = "Accept=application/json")
		public ObjectNode generatePdfAction(Installation installation) throws DocumentException, IOException {	
			
		
			ObjectNode list = installationService.generatePdfAction(installation);	
			return list;
		}
		
		@RequestMapping(value = "/generatePdfAction1", method = RequestMethod.GET, headers = "Accept=application/json")
		public ObjectNode generatePdfAction1() throws DocumentException, IOException {	
			
			ObjectNode list = installationService.generatePdfAction1();	
			return list;
		}
		
		
}
