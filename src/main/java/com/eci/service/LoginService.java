package com.eci.service;

import javax.servlet.http.HttpServletRequest;

import com.eci.bean.User;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface LoginService {

	ObjectNode checkLoginCredential(User user,HttpServletRequest req);

	boolean checkEmailExistance (User user);
	
	ObjectNode checkSILoginCredential (User user);

	ObjectNode changePswd(User user);

	boolean saveUserKey(User user);
		
}
