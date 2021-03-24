package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eci.bean.Installation;
import com.eci.bean.Unassigned;
import com.eci.dao.InstallationDao;
import com.eci.dao.UnassignedDao;
import com.eci.service.InstallationService;
import com.eci.service.ProductService;
import com.eci.service.UnassignedService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class UnassignedServiceImp implements UnassignedService {
	
	@Autowired
	UnassignedDao unassignedDao;

	@Override
	public ObjectNode getInsToReassignList(int userid) {
		// TODO Auto-generated method stub
		
		ObjectNode orderNode = unassignedDao.getInsToReassignList(userid);
		
		return orderNode;
	}

	@Override
	public ObjectNode getAssignSIList() {
		// TODO Auto-generated method stub
		
		ObjectNode orderNode = unassignedDao.getAssignSIList();
		
		return orderNode;
	}

	@Override
	public ObjectNode updateReassignremark(Unassigned unassigned) {
		// TODO Auto-generated method stub
		
		ObjectNode flag=unassignedDao.updateReassignremark(unassigned);
		
		return flag;
	}

	
	@Override
	public ObjectNode updateReassignSI(Unassigned unassigned) {
		// TODO Auto-generated method stub
		ObjectNode flag = unassignedDao.updateReassignSI(unassigned);
		return flag;
	}

	@Override
	public ObjectNode getInsApprovalList(int userid) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = unassignedDao.getInsApprovalList(userid);
		
		return orderNode;
	}

	@Override
	public ObjectNode seekApproval(Unassigned unassigned) {
		// TODO Auto-generated method stub
		ObjectNode flag = unassignedDao.seekApproval(unassigned);
		
		System.out.println("hi i am coming3");
		return flag;
	}

		
}
