package com.eci.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eci.bean.Customer;
import com.eci.bean.User;

import com.eci.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class UserController {

	@Autowired
	 UserService userService;  
	
	@RequestMapping(value = "/getapkversion", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode getapkversion(@RequestBody User user) throws ClassNotFoundException, IOException, SQLException {		
		ObjectNode versiondata = userService.getapkversion(user);	
		System.out.println("hi i am get Apk Version"+versiondata);
		return versiondata;
	}	
	@RequestMapping(value = "/ApkVersion", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode ApkVersion(@RequestBody User user) throws ClassNotFoundException, IOException, SQLException {		
		ObjectNode flag = userService.apkversion(user);	
		System.out.println("hi i am Apk Version");
		return flag;
	}
//	list all useromers for select box
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getUsers() {
		System.out.println("hi in getUsers");
		ObjectNode listOfCust = userService.getAllUsers();	
		return listOfCust;
	}
	
//	get details of Users for edit
	@RequestMapping(value = "/getUserDetail/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getUserDetail(@PathVariable int id) {	
		System.out.println("id========="+id);
		ObjectNode listOfUsers = userService.getUserDetail(id);	
		return listOfUsers;
	}
	
//	insert User
	@RequestMapping(value = "/insertUser", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode insertUser(@RequestBody User user) throws ClassNotFoundException, IOException, SQLException {		
		ObjectNode flag = userService.insertUser(user);	
		System.out.println("hi i am coming1");
		return flag;
	}
	
	
	//edit User
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode updateUser(@RequestBody User user) {		
		ObjectNode flag = userService.updateUser(user);	
		System.out.println("hi i am coming3");
		return flag;
	}
	
	@RequestMapping(value = "/inactivateUser", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ObjectNode inactivateCustomer(@RequestBody User user) {		
		ObjectNode flag = userService.inactivateUser(user);	
		System.out.println("hi i am coming3");
		return flag;
	}
	@RequestMapping(value = "/activateUser", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ObjectNode activateCustomer(@RequestBody User user) {		
		ObjectNode flag = userService.activateUser(user);	
		System.out.println("hi i am coming3");
		return flag;
	}
		
	@RequestMapping(value = "/getTypeWiseRole/{roletype}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getTypeWiseRole(@PathVariable int roletype) {	
		System.out.println("idroletype========="+roletype);
		ObjectNode listOfUsers = userService.getTypeWiseRole(roletype);	
		return listOfUsers;
	}
	
		
	@RequestMapping(value = "/uploadSIupdateImage", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadSIupdateImage(@RequestParam("file") MultipartFile file)throws IOException {		
		
		File convertFile=new File("E:\\ECI\\Uploads\\"+file.getOriginalFilename());
		convertFile.createNewFile();
		FileOutputStream fout=new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		return new ResponseEntity<>("hurray uploaded",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST, headers = "Accept=application/json")
	public boolean resetPassword(@RequestBody User user) {		
		boolean flag = userService.resetPassword(user);	
		System.out.println("hi i am coming resetPassword");
		return flag;
	}
	
	@RequestMapping(value = "/getSiCompanyList", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getSiCompanyList() {
		System.out.println("hi in getSiCompanyList");
		ObjectNode listOfCompany = userService.getSiCompanyList();	
		return listOfCompany;
	}
	@RequestMapping(value = "/selectType", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode selectType() {
		System.out.println("hi in selectType");
		ObjectNode listOfCompany = userService.selectType();	
		return listOfCompany;
	}
	
	
}
