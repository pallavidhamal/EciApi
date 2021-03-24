package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eci.bean.User;

import com.eci.dao.UserDao;
import com.eci.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class UserServiceImp implements UserService{

	@Autowired
	UserDao userDao;
	
	public ObjectNode getAllUsers() 
	{
		ObjectNode orderNode = userDao.getAllUsers();
		return orderNode;
	}
	
	public ObjectNode getUserDetail(int id) 
	{
		ObjectNode orderNode = userDao.getUserDetail(id);
		return orderNode;
	}
	
	
	public ObjectNode updateUser(User user) {
		ObjectNode flag = userDao.updateUser(user);
		return flag;
	}


	@Override
	public ObjectNode insertUser(User user) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		ObjectNode flag=userDao.insertUser(user);
		return flag;
	}

	@Override
	public ObjectNode inactivateUser(User user) {
		// TODO Auto-generated method stub
		ObjectNode flag=userDao.inactivateUser(user);
		return flag;
	}

	@Override
	public ObjectNode getTypeWiseRole(int roletype) {
		// TODO Auto-generated method stub
		ObjectNode userNode = userDao.getTypeWiseRole(roletype);
		return userNode;
	}

	@Override
	public boolean resetPassword(User user) {
		// TODO Auto-generated method stub
		boolean flag = userDao.resetPassword(user);
		return flag;
	}

	@Override
	public ObjectNode getSiCompanyList() {
		// TODO Auto-generated method stub
		ObjectNode orderNode = userDao.getSiCompanyList();
		return orderNode;
	}

	@Override
	public ObjectNode selectType() {
		// TODO Auto-generated method stub
		ObjectNode orderNode = userDao.selectType();
		return orderNode;
	}

	@Override
	public ObjectNode activateUser(User user) {
		// TODO Auto-generated method stub
		ObjectNode flag=userDao.activateUser(user);
		return flag;
	}

	@Override
	public ObjectNode apkversion(User user) {
		// TODO Auto-generated method stub
		ObjectNode flag=userDao.apkversion(user);
		return flag;
	}
	@Override
	public ObjectNode getapkversion(User user) {
		// TODO Auto-generated method stub
		ObjectNode version=userDao.getapkversion(user);
		return version;
	}
}
