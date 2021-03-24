package com.eci.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eci.bean.Customer;
import com.eci.bean.Region;
import com.eci.service.RegionService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class RegionControlller {

	@Autowired
	RegionService regService;
	
//	list all regions for select box
	@RequestMapping(value = "/getRegions", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getRegions() {		
		ObjectNode listOfRegion = regService.getAllRegions();	
		System.out.println("hi i am coming");
		return listOfRegion;
	}
	
//	get details of regions for edit
	@RequestMapping(value = "/getRegionDetail/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getRegionDetail(@PathVariable int id) {	
		System.out.println("id========="+id);
		ObjectNode listOfRegions = regService.getRegionDetail(id);	
		return listOfRegions;
	}
	
//	insert regions
	@RequestMapping(value = "/insertRegion", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode insertRegion(@RequestBody Region region) throws ClassNotFoundException, IOException, SQLException {		
		ObjectNode flag = regService.insertRegion(region);	
		System.out.println("hi i am coming1");
		return flag;
	}
	
	
	//edit regions 
	@RequestMapping(value = "/updateRegion", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ObjectNode updateRegion(@RequestBody Region region) {		
		ObjectNode flag = regService.updateRegion(region);	
		System.out.println("hi i am coming3");
		return flag;
	}
	
	//edit regions
	@RequestMapping(value = "/inactivateRegion", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ObjectNode inactivateRegion(@RequestBody Region region) {		
		ObjectNode flag = regService.inactivateRegion(region);	
		return flag;
	}
	@RequestMapping(value = "/activateRegion", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ObjectNode activateRegion(@RequestBody Region region) {		
		ObjectNode flag = regService.activateRegion(region);	
		return flag;
	}
	
	
}
