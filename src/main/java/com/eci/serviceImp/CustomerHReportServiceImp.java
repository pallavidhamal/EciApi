package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eci.dao.CustomerHReportDao;
import com.eci.service.CustomerHReportService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@Repository
public class CustomerHReportServiceImp implements CustomerHReportService {
	
	@Autowired
	CustomerHReportDao customerhreportDao ;
	
	@Override
	public ObjectNode getCustHReportList(int custId) {
		// TODO Auto-generated method stub
		ObjectNode flag = customerhreportDao.getCustHReportList(custId);
		
		System.out.println(" getCustHReportList===== ");
		return flag;
	}

}
