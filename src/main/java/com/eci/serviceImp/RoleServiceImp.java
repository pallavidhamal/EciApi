package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eci.bean.Role;
import com.eci.bean.Task;
import com.eci.dao.RoleDao;
import com.eci.dao.TaskDao;
import com.eci.service.RoleService;
import com.eci.service.TaskService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class RoleServiceImp implements RoleService {

	@Autowired
	RoleDao roleDao;
	
	public ObjectNode getRoles() {
		ObjectNode orderNode = roleDao.getRoles();
		return orderNode;
	}

	@Override
	public boolean insertRole(Role role) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		boolean flag=roleDao.insertRole(role);
		return flag;
	}

	@Override
	public ObjectNode getRoleDetail(int id) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = roleDao.getRoleDetail(id);
		
		return orderNode;
	}

	@Override
	public boolean updateRole(Role role) {
		// TODO Auto-generated method stub
		
		boolean flag = roleDao.updateRole(role);
		return flag;
	}

	@Override
	public boolean inactivateRole(Role role) {
		// TODO Auto-generated method stub
		boolean flag=roleDao.inactivateRole(role);
		
		return flag;
	}
	
	
	
	
	
/*
	
	@Override
	public boolean insertTask(Task task) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		boolean flag=taskDao.insertTask(task);
		return flag;
	}

	@Override
	public boolean updateTask(Task task) {
		// TODO Auto-generated method stub
		boolean flag = taskDao.updateTask(task);
		return flag;
	}

	@Override
	public boolean inactivateTask(Task task) {
		// TODO Auto-generated method stub
		boolean flag = taskDao.inactivateTask(task); 
		return flag;	
		}*/
	
}
