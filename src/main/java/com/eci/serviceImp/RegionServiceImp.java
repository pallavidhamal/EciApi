package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eci.bean.Region;
import com.eci.dao.RegionDao;
import com.eci.service.RegionService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class RegionServiceImp implements RegionService {

	@Autowired
	RegionDao regionDao;
	
	public ObjectNode getAllRegions() {
		ObjectNode orderNode = regionDao.getRegions();
		return orderNode;
	}

	@Override
	public ObjectNode getRegionDetail(int id) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = regionDao.getRegionDetail(id);
		return orderNode;
	}

	@Override
	public ObjectNode insertRegion(Region region) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		ObjectNode flag=regionDao.insertRegion(region);
		return flag;
	}

	@Override
	public ObjectNode updateRegion(Region region) {
		// TODO Auto-generated method stub
		ObjectNode flag = regionDao.updateRegion(region);
		return flag;
	}

	@Override
	public ObjectNode inactivateRegion(Region region) {
		// TODO Auto-generated method stub
		ObjectNode flag = regionDao.inactivateRegion(region); 
		return flag;	
		}

	@Override
	public ObjectNode activateRegion(Region region) {
		// TODO Auto-generated method stub
		ObjectNode flag = regionDao.activateRegion(region); 
		return flag;	
	}
	
}
