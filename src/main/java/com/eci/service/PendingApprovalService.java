package com.eci.service;

import java.io.IOException;
import java.sql.SQLException;

import com.eci.bean.Installation;
import com.eci.bean.PendingApproval;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface PendingApprovalService {
	


	ObjectNode getPendingApproveList(int userid);

	ObjectNode insertIncClaimData(PendingApproval pendingApproval);

	ObjectNode showClaimDataByInstId(int instId);

	ObjectNode getStatusListbyInstId(int instId);

	ObjectNode saveRejectRemark(PendingApproval pendingApproval);

	ObjectNode releasePOQty(PendingApproval pendingApproval);

		
	
	
}
