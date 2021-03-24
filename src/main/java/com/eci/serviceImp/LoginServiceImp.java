package com.eci.serviceImp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eci.bean.User;
import com.eci.dao.CustomerDao;
import com.eci.dao.LoginDao;
import com.eci.service.LoginService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.servlet.http.HttpServletRequest;

@Service
public class LoginServiceImp implements LoginService {

	@Autowired
	LoginDao loginDao;
	
	@Override
	public ObjectNode checkLoginCredential(User user,HttpServletRequest req) {
		
		ObjectNode flag=loginDao.checkLoginCredential(user, req);
		return flag;
	}

	@Override
	public boolean checkEmailExistance(User user) {


		boolean flag=loginDao.checkEmailExistance(user);
		return flag;
	}

	@Override
	public ObjectNode checkSILoginCredential(User user) {
		// TODO Auto-generated method stub
		ObjectNode list=loginDao.checkSILoginCredential(user);
		return list;
	}

	@Override
	public ObjectNode changePswd(User user) {
		// TODO Auto-generated method stub
		ObjectNode list=loginDao.changePswd(user);
		return list;
	}

	@Override
	public boolean saveUserKey(User user) {
		// TODO Auto-generated method stub
		boolean list=loginDao.saveUserKey(user);
		return list;
	}

}
