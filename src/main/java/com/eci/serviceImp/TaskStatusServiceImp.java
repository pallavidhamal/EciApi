package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eci.bean.Installation;
import com.eci.bean.Task;
import com.eci.bean.TaskStatus;
import com.eci.dao.TaskStatusDao;
import com.eci.service.TaskStatusService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class TaskStatusServiceImp implements  TaskStatusService{
	
	@Autowired
	TaskStatusDao taskStatusDao;

	@Override
	public ObjectNode getTaskStatusList(int userid) {
		// TODO Auto-generated method stub
		
		ObjectNode orderNode = taskStatusDao.getTaskStatusList(userid);
		return orderNode;
	}


	@Override
	public ObjectNode findPoListByNo(String poNum) {
		// TODO Auto-generated method stub
		ObjectNode flag=taskStatusDao.findPoListByNo(poNum);
		return flag;
	}


	@Override
	public ObjectNode assignPo(Task task) {
		// TODO Auto-generated method stub
		ObjectNode flag=taskStatusDao.assignPo(task);
		return flag;
	}


	@Override
	public ObjectNode saveReopenJob(Task task) {
		// TODO Auto-generated method stub
		ObjectNode flag=taskStatusDao.saveReopenJob(task);
		return flag;
	}


	@Override
	public ObjectNode getTaskFrmAction(int userid) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = taskStatusDao.getTaskFrmAction(userid);
		return orderNode;
	}



		
}
