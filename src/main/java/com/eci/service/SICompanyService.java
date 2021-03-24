package com.eci.service;

import java.io.IOException;
import java.sql.SQLException;

import com.eci.bean.SICompany;
import com.eci.bean.Task;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface SICompanyService {

	ObjectNode getSICompany(int userid);
	
	 ObjectNode getSIDetail(int id);
	
	  ObjectNode insertSICompany(SICompany siCmp) throws ClassNotFoundException, IOException,
	  SQLException;
	  
	  ObjectNode updateSI(SICompany siCmp);
	 
	
	  ObjectNode inactivateSI(SICompany siCmp);
	  
	  ObjectNode activateSI(SICompany siCmp);
	 
	
}
