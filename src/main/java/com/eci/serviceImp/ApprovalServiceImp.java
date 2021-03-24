package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eci.bean.Approval;
import com.eci.dao.ApprovalDao;
import com.eci.service.ApprovalService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class ApprovalServiceImp implements  ApprovalService{
	
	@Autowired
	ApprovalDao approvalDao;
	

	@Override
	public ObjectNode getApprovalHPMList() {
		// TODO Auto-generated method stub
		ObjectNode orderNode = approvalDao.getApprovalHPMList();
		
		return orderNode;
	}


	@Override
	public boolean approvalStatusId(Approval approval) {
		// TODO Auto-generated method stub
		boolean flag = approvalDao.approvalStatusId(approval);
		
		System.out.println("hi i am coming3");
		
		return flag;
	}

	

		
}
