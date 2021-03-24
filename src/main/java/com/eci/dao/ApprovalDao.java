package com.eci.dao;

import java.io.IOException;
import java.sql.SQLException;

import com.eci.bean.Approval;
import com.eci.bean.Installation;
import com.eci.bean.User;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ApprovalDao {

	ObjectNode getApprovalHPMList();

	boolean approvalStatusId(Approval approval);

	
	
	
}
