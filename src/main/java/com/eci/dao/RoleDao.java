package com.eci.dao;

import java.io.IOException;
import java.sql.SQLException;

import com.eci.bean.Role;
import com.eci.bean.Task;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface RoleDao {
	
	ObjectNode getRoles();
	
	boolean insertRole(Role role) throws ClassNotFoundException, IOException, SQLException;

	ObjectNode getRoleDetail(int id);

	boolean updateRole(Role role);

	boolean inactivateRole(Role role);
	
	
/*	ObjectNode getTaskDetail(int id);
	
	boolean insertTask(Task task) throws ClassNotFoundException, IOException, SQLException;
	
	boolean updateTask(Task task);
	
	boolean inactivateTask(Task task);*/
	
}
