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

import com.eci.bean.Customer;
import com.eci.bean.Product;
import com.eci.service.ProductService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class ProductController {

	@Autowired
	ProductService proService;
//	list all Products for select box
	
	@RequestMapping(value = "/getProducts", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getProducts() {		
		ObjectNode listOfProd = proService.getAllProducts();	
		System.out.println("hi i am coming");
		return listOfProd;
	}
		
//	get details of Products for edit
	@RequestMapping(value = "/getProductDetail/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getProductDetail(@PathVariable int id) {	
		ObjectNode listOfProducts = proService.getProductDetail(id);	
		return listOfProducts;
	}
	
//	insert Product
	@RequestMapping(value = "/insertProduct", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode insertProduct(@RequestBody Product product) throws ClassNotFoundException, IOException, SQLException {		
		ObjectNode flag = proService.insertProduct(product);	
		System.out.println("hi i am coming1");
		return flag;
	}
	
	
	//edit Product
	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode updateProduct(@RequestBody Product product) {		
		ObjectNode flag = proService.updateProduct(product);	
		System.out.println("hi i am coming3");
		return flag;
	}
	
	//inactivate product
	@RequestMapping(value = "/inactivateProduct", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ObjectNode inactivateProduct(@RequestBody Product product) {		
		ObjectNode flag = proService.inactivateProduct(product);	
		System.out.println("hi i am coming3");
		return flag;
	}
	@RequestMapping(value = "/activateProduct", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ObjectNode activateProduct(@RequestBody Product product) {		
		ObjectNode flag = proService.activateProduct(product);	
		System.out.println("hi i am coming3");
		return flag;
	}
}
