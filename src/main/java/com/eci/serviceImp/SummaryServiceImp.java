package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eci.bean.Installation;
import com.eci.bean.Summary;
import com.eci.dao.InstallationDao;
import com.eci.dao.SummaryDao;
import com.eci.service.InstallationService;
import com.eci.service.ProductService;
import com.eci.service.SummaryService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@Repository
public class SummaryServiceImp implements SummaryService {
	
	@Autowired
	SummaryDao summaryDao;
	
	@Override
	public ObjectNode getPMsummary(int id) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = summaryDao.getPMsummary(id);
		return orderNode;
	}

	@Override
	public ObjectNode getWIPDetails(Summary summary) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = summaryDao.getWIPDetails(summary);
		return orderNode;
	}

		
}
