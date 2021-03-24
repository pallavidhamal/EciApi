package com.eci.service;

import java.io.IOException;
import java.sql.SQLException;

import com.eci.bean.Task;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface TaskService {

	ObjectNode getAllTasks(int userid);
	
	ObjectNode getTaskDetail(int id);
	
	ObjectNode insertTask(Task task) throws ClassNotFoundException, IOException, SQLException;
	
	ObjectNode updateTask(Task task);
	
	ObjectNode inactivateTask(Task task);

	ObjectNode activateTask(Task task);
	
}
