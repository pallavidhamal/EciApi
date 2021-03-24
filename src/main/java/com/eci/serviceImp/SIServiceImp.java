package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eci.bean.Installation;
import com.eci.bean.SI;
import com.eci.bean.User;
import com.eci.dao.SIDao;
import com.eci.dao.UserDao;
import com.eci.bean.InstallationAction;
import com.eci.service.SIService;
import com.eci.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class SIServiceImp implements SIService{

	@Autowired
//	UserDao userDao;
	SIDao siDao;

	@Override
	public ObjectNode getSIRegionsList(Installation inc) {
		// TODO Auto-generated method stub
		System.out.println("welcom to SIServiceImp getSIUsersList");
		ObjectNode siNode = siDao.getSIRegionsList(inc);
		return siNode;
		
	}

	@Override
	public ObjectNode getSIRegionWiseJobs(Installation inc) {
		// TODO Auto-generated method stub
		System.out.println("welcom to SIServiceImp getSIRegionWiseJobs");
		ObjectNode siRegwiseJobsNode = siDao.getSIRegionWiseJobs(inc);
		return siRegwiseJobsNode;
	}

	@Override
	public boolean takeIncAction(InstallationAction IA){
		// TODO Auto-generated method stub
		boolean flag=siDao.takeIncAction(IA);
		return flag;
	}
	
	@Override
	public boolean SIAcceptJob(InstallationAction IA) {
		// TODO Auto-generated method stub
		boolean flag=siDao.SIAcceptJob(IA);
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
	public boolean SIUpdateTask(MultipartFile file,InstallationAction IA) {
		// TODO Auto-generated method stub
		boolean flag=siDao.SIUpdateTask(file,IA);
		return flag;
	}

	@Override
	public ObjectNode getTEList(SI si) {
		// TODO Auto-generated method stub
		ObjectNode node = siDao.getTEList(si);
		return node;
	}
	@Override
	public ObjectNode getSIsummary(SI si) {
		// TODO Auto-generated method stub
		ObjectNode node = siDao.getSIsummary(si);
		return node;
	}

	@Override
	public ObjectNode getReasonList(SI si) {
		// TODO Auto-generated method stub
		ObjectNode node = siDao.getReasonList(si);
		return node;
	}
	
	
	
}
