package com.eci.dao;

import com.eci.bean.Unassigned;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface UnassignedDao {


	ObjectNode getInsToReassignList(int userid);

	ObjectNode getAssignSIList();

	ObjectNode updateReassignremark(Unassigned unassigned);


	ObjectNode updateReassignSI(Unassigned unassigned);

	ObjectNode getInsApprovalList(int userid);

	ObjectNode seekApproval(Unassigned unassigned);

	ArrayNode getLatestIncRemark(String strJobId,String statusId);

	
	
}
