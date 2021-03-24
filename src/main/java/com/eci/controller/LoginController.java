package com.eci.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eci.bean.Customer;
import com.eci.bean.User;
import com.eci.service.CustomerService;
import com.eci.service.LoginService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci") 
public class LoginController {
	
	@Autowired
	LoginService loginService;  

	
	//checks login for SI user
		@RequestMapping(value = "/checkSILoginCredential", method = RequestMethod.POST, headers = "Accept=application/json")
		public ObjectNode checkSILoginCredential(@RequestBody User user) {		
			ObjectNode list = loginService.checkSILoginCredential(user);	
			return list;
		}
	
	
		@RequestMapping(value = "/checkLoginCredential", method = RequestMethod.POST, headers = "Accept=application/json")
		public ObjectNode checkLoginCredential(@RequestBody User user,HttpServletRequest req) {		
			ObjectNode flag = loginService.checkLoginCredential(user,req);	
			return flag;
		}
		
		@RequestMapping(value = "/checkEmailExistance", method = RequestMethod.POST, headers = "Accept=application/json")
		public boolean checkEmailExistance(@RequestBody User user) {		
			boolean flag = loginService.checkEmailExistance(user);	
			return flag;
		}
		
		@RequestMapping(value = "/changePswd", method = RequestMethod.POST, headers = "Accept=application/json")
		public ObjectNode changePswd(@RequestBody User user) {		
			ObjectNode flag = loginService.changePswd(user);	
			return flag;
		}
		
		@RequestMapping(value = "/saveUserKey", method = RequestMethod.POST, headers = "Accept=application/json")
		public boolean saveUserKey(@RequestBody User user) {		
			boolean flag = loginService.saveUserKey(user);	
			return flag;
		}
		
}
