package com.eci.controller;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eci.bean.Customer;
import com.eci.bean.PurchaseOrder;
import com.eci.bean.Site;
import com.eci.service.CustomerService;
import com.eci.service.PurchaseOrderService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class CustomerController {

	@Autowired
	 CustomerService custService;  
	
//	list all customers for select box
	@RequestMapping(value = "/getCustomers", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getCustomers() {
		System.out.println("hi in getCustomers");
		ObjectNode listOfCust = custService.getAllCustomers();	
		return listOfCust;
	}
	
//	get details of Customers for edit
	@RequestMapping(value = "/getCustomerDetail/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getCustomerDetail(@PathVariable int id) {	
		ObjectNode listOfCustomers = custService.getCustomerDetail(id);	
		return listOfCustomers;
	}
	
//	insert Customer
	@RequestMapping(value = "/insertCustomer", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode insertCustomer(@RequestBody Customer customer) throws ClassNotFoundException, IOException, SQLException {		
		ObjectNode flag = custService.insertCustomer(customer);	
		System.out.println("hi i am coming1");
		return flag;
	}
	
	
	//edit Customer
	@RequestMapping(value = "/updateCustomer", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ObjectNode updateCustomer(@RequestBody Customer customer) {		
		ObjectNode flag = custService.updateCustomer(customer);	
		System.out.println("hi i am coming3");
		return flag;
	}
	
	//inactivate Customer
		@RequestMapping(value = "/inactivateCustomer", method = RequestMethod.PUT, headers = "Accept=application/json")
		public ObjectNode inactivateCustomer(@RequestBody Customer customer) {		
			ObjectNode flag = custService.inactivateCustomer(customer);	
			System.out.println("hi i am coming3");
			return flag;
		}
		
		@RequestMapping(value = "/activateCustomer", method = RequestMethod.PUT, headers = "Accept=application/json")
		public ObjectNode activateCustomer(@RequestBody Customer customer) {		
			ObjectNode flag = custService.activateCustomer(customer);	
			System.out.println("hi i am coming3");
			return flag;
		}
		
		@RequestMapping(value = "/saveSite", method = RequestMethod.POST, headers = "Accept=application/json")
		public ObjectNode saveSite(@RequestBody Site customer) throws ClassNotFoundException, IOException, SQLException {		
			ObjectNode list = custService.saveSite(customer);	 
			System.out.println("hi i am coming3");
			return list;
		}
		
		@RequestMapping(value = "/getSiteDetail/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
		public ObjectNode getSiteDetail(@PathVariable int id) {	
			ObjectNode listOfCustomers = custService.getSiteDetail(id);	
			return listOfCustomers;
		}
		
		/*@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public ObjectNode getSiteDetail(@PathVariable int id) {	
			ObjectNode listOfCustomers = custService.getSiteDetail(id);	
			return listOfCustomers;
		}*/
		
}
