package com.eci.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.multipart.MultipartFile;

import com.eci.bean.Installation;
import com.eci.bean.InstallationAction;
import com.eci.bean.SI;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface TEService {

	ObjectNode getTERegionList(Installation inc);

	ObjectNode getTERegionWiseJobs(Installation inc);

	ObjectNode getTEJobWiseTasks(Installation inc);

	ObjectNode getTETaskTests(Installation inc);
	
	ObjectNode updateTETestStatus(String data,JavaMailSender javaMailSender);
	
	ObjectNode getTeTaskCategory(Installation inc);

	ObjectNode updateTEPhotoTests(MultipartFile file, InstallationAction IA);

	
	
	/*boolean SIRejectJob(SI si);

	boolean AssignTE(SI si);*/

}
