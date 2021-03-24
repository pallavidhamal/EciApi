package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eci.dao.POReportDao;
import com.eci.service.POReportService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@Repository
public class POReportServiceImp implements POReportService {
	
	@Autowired
	POReportDao poreportDao;
	
	@Override
	public ObjectNode getPOReportData(int id) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = poreportDao.getPOReportData(id);
		return orderNode;
	}

	@Override
	public ObjectNode getClosedPOReportData(int id) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = poreportDao.getClosedPOReportData(id);
		return orderNode;
	}


}
