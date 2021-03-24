package com.eci.service;

import java.io.IOException;
import java.sql.SQLException;

import com.eci.bean.Approval;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ApprovalService {
	
	ObjectNode getApprovalHPMList();

	boolean approvalStatusId(Approval approval);
	
	
	
}
