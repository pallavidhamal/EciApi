
package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eci.bean.SICompany;
import com.eci.bean.Task;
import com.eci.dao.SICompanyDao;
import com.eci.dao.TaskDao;
import com.eci.service.SICompanyService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class SICompanyServiceImp implements SICompanyService {

	@Autowired
	SICompanyDao cmpdao;
	
	public ObjectNode getSICompany(int userid) {
		ObjectNode orderNode = cmpdao.getSICompany(userid);
		return orderNode;
	}

	@Override
	public ObjectNode getSIDetail(int id) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = cmpdao.getSIDetail(id);
		return orderNode;
	}
	@Override
	public ObjectNode insertSICompany(SICompany siCmp) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		ObjectNode flag=cmpdao.insertSICompany(siCmp);
		return flag;
	}
	@Override
	public ObjectNode updateSI(SICompany siCmp) {
		// TODO Auto-generated method stub
		ObjectNode flag = cmpdao.updateSI(siCmp);
		return flag;
	}
	@Override
	public ObjectNode inactivateSI(SICompany siCmp) {
		// TODO Auto-generated method stub
		ObjectNode flag = cmpdao.inactivateSI(siCmp); 
		return flag;	
		}

	@Override
	public ObjectNode activateSI(SICompany siCmp) {
		// TODO Auto-generated method stub
		ObjectNode flag = cmpdao.activateSI(siCmp); 
		return flag;	
	}
}
