package com.eci.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eci.bean.PoKitty;
import com.eci.service.PoKittyrService;
import com.fasterxml.jackson.databind.node.ObjectNode;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")

public class PoKittyController {
	
	@Autowired
	PoKittyrService kittyService;
	
	@RequestMapping(value = "/getPOKittyList/{userid}", method = RequestMethod.GET, headers = "Accept=application/json")
	
	public ObjectNode getPOKittyList(@PathVariable int userid) {	
		
		ObjectNode listOfPOKitty = kittyService.getPOKittyList(userid);	
		
		System.out.println("hi i am getPOKittyList");
		
		return listOfPOKitty;
		
	}
	
	@RequestMapping(value = "/updatePOremark", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode updatePOremark(@RequestBody PoKitty poKitty) {	
		
		ObjectNode flag = kittyService.updatePOremark(poKitty);	
		
		System.out.println("hi i am coming updateremark");
		
		return flag;
	}
	
	
}
