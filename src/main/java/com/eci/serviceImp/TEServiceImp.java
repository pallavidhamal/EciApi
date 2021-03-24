package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eci.bean.Installation;
import com.eci.bean.InstallationAction;
import com.eci.bean.SI;
import com.eci.bean.User;
import com.eci.dao.SIDao;
import com.eci.dao.TEDao;
import com.eci.dao.UserDao;
import com.eci.service.SIService;
import com.eci.service.TEService;
import com.eci.service.UserService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TEServiceImp implements TEService{

	@Autowired
//	UserDao userDao;
	TEDao teDao;
	
	@Override
	public ObjectNode getTERegionList(Installation inc) {
		// TODO Auto-generated method stub
	//	System.out.println("welcom to SIServiceImp getSIUsersList");
		ObjectNode siNode = teDao.getTERegionsList(inc);
		return siNode;
		
	}

	@Override
	public ObjectNode getTERegionWiseJobs(Installation inc) {
		// TODO Auto-generated method stub
	//	System.out.println("welcom to SIServiceImp getSIRegionWiseJobs");
		ObjectNode siRegwiseJobsNode = teDao.getTERegionWiseJobs(inc);
		return siRegwiseJobsNode;
	}

	@Override
	public ObjectNode getTEJobWiseTasks(Installation inc) {
		// TODO Auto-generated method stub
		ObjectNode siRegwiseJobsNode = teDao.getTEJobWiseTasks(inc);
		return siRegwiseJobsNode;
	}


	@Override
	public ObjectNode getTETaskTests(Installation inc) {
		// TODO Auto-generated method stub
		ObjectNode siRegwiseJobsNode = teDao.getTETaskTests(inc);
		return siRegwiseJobsNode;
	}

	@Override
	public ObjectNode updateTETestStatus(String data,JavaMailSender javaMailSender) {
		// TODO Auto-generated method stub
		ObjectNode siRegwiseJobsNode = teDao.updateTETestStatus(data, javaMailSender);
		return siRegwiseJobsNode;	
		}

	
	@Override
	public ObjectNode updateTEPhotoTests(MultipartFile file,InstallationAction IA) {
		// TODO Auto-generated method stub
		ObjectNode siRegwiseJobsNode = teDao.updateTEPhotoTests(file,IA);
		return siRegwiseJobsNode;	
		}
	
	
	@Override
	public ObjectNode getTeTaskCategory(Installation inc) {
		// TODO Auto-generated method stub
	//	System.out.println("welcom to SIServiceImp getSIUsersList");
		ObjectNode siNode = teDao.getTeTaskCategory(inc);
		return siNode;
		
	}
	
	/*@Override
	public boolean SIAcceptJob(SI si) {
		// TODO Auto-generated method stub
		boolean flag=siDao.SIAcceptJob(si);
		return flag;
	}

	@Override
	public boolean SIRejectJob(SI si) {
		// TODO Auto-generated method stub
		boolean flag=siDao.SIRejectJob(si);
		return flag;
	}

	@Override
	public boolean AssignTE(SI si) {
		// TODO Auto-generated method stub
		boolean flag=siDao.AssignTE(si);
		return flag;
	}
	
	@Override
	public boolean SIUpdateTask(SI si) {
		// TODO Auto-generated method stub
		boolean flag=siDao.SIUpdateTask(si);
		return flag;
	}
	*/
}
