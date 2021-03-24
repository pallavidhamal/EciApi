package com.eci.service;

import org.springframework.web.multipart.MultipartFile;

import com.eci.bean.Installation;
import com.eci.bean.InstallationAction;
import com.eci.bean.SI;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface SIService {

	ObjectNode getSIRegionsList(Installation inc);

	ObjectNode getSIRegionWiseJobs(Installation inc);

	boolean SIAcceptJob(InstallationAction IA);

	boolean SIRejectJob(SI si);

	boolean AssignTE(SI si);

	boolean SIUpdateTask(MultipartFile file,InstallationAction IA);

	boolean takeIncAction(InstallationAction iA);

	ObjectNode getTEList(SI si);
	
	ObjectNode getSIsummary(SI si);

	ObjectNode getReasonList(SI si);

	

}
