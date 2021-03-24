package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eci.bean.PendingApproval;
import com.eci.dao.PendingApprovalDao;
import com.eci.service.PendingApprovalService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class PendingApprovalServiceImp implements PendingApprovalService {
	
	@Autowired
	PendingApprovalDao pendingApprDao;
	
	@Override
	public ObjectNode getPendingApproveList(int userid) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = pendingApprDao.getPendingApproveList(userid);
		
		return orderNode;
	}

	@Override
	public ObjectNode insertIncClaimData(PendingApproval pendingApproval) {
		// TODO Auto-generated method stub
		ObjectNode flag=pendingApprDao.insertIncClaimData(pendingApproval);
		
		return flag;
	}

	@Override
	public ObjectNode showClaimDataByInstId(int instId) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = pendingApprDao.showClaimDataByInstId(instId);
		
		return orderNode;
	}

	@Override
	public ObjectNode getStatusListbyInstId(int instId) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = pendingApprDao.getStatusListbyInstId(instId);
		
		return orderNode;
	}

	@Override
	public ObjectNode saveRejectRemark(PendingApproval pendingApproval) {
		// TODO Auto-generated method stub
		ObjectNode flag=pendingApprDao.saveRejectRemark(pendingApproval);
		
		return flag;
	}

	@Override
	public ObjectNode releasePOQty(PendingApproval pendingApproval) {
		// TODO Auto-generated method stub
		ObjectNode flag=pendingApprDao.releasePOQty(pendingApproval);
		
		return flag;	
	}

	


		
}
