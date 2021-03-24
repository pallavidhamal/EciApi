package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eci.bean.PoKitty;
import com.eci.dao.PoKittyDao;
import com.eci.service.PoKittyrService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class PoKittyServiceImp implements PoKittyrService {

	@Autowired
	PoKittyDao kittyDao;
	
	public ObjectNode getPOKittyList(int userid) {
		
		System.out.println("----PoKittyServiceImp getPOKittyList----");
		
		ObjectNode kittyList = kittyDao.getPOKittyList(userid);
		
		System.out.println("----PoKittyServiceImp getPOKittyList--kittyList--"+kittyList);
		
		return kittyList;
	}

	@Override
	public ObjectNode updatePOremark(PoKitty poKitty) {
		// TODO Auto-generated method stub
		
		ObjectNode flag=kittyDao.updatePOremark(poKitty);
		return flag;
	}

	
	
	

	
}
