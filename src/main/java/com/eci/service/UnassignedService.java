package com.eci.service;

import java.io.IOException;
import java.sql.SQLException;

import com.eci.bean.Installation;
import com.eci.bean.Unassigned;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface UnassignedService {

	ObjectNode getInsToReassignList(int userid);

	ObjectNode getAssignSIList();

	ObjectNode updateReassignremark(Unassigned unassigned);


	ObjectNode updateReassignSI(Unassigned unassigned);

	ObjectNode getInsApprovalList(int userid);

	ObjectNode seekApproval(Unassigned unassigned);
	
	
}
