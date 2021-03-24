package com.eci.service;

import java.io.IOException;
import java.sql.SQLException;

import com.eci.bean.User;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface UserService {

	ObjectNode getAllUsers();
	
	ObjectNode getUserDetail(int id);
	
	ObjectNode insertUser(User user) throws ClassNotFoundException, IOException, SQLException;
	
	ObjectNode updateUser(User user);

	ObjectNode inactivateUser(User user);

	ObjectNode getTypeWiseRole(int roletype);

	boolean resetPassword(User user);

	ObjectNode getSiCompanyList();

	ObjectNode selectType();

	ObjectNode activateUser(User user);

	ObjectNode apkversion(User user);

	ObjectNode getapkversion(User user);


	
}
