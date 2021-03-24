package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eci.bean.WipPendingReport;
import com.eci.dao.WipPendingReportDao;
import com.eci.service.WipPendingReportService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@Repository
public class WipPendingReportServiceImp implements WipPendingReportService {
	
	@Autowired
	WipPendingReportDao wippendingreportDao;
	
	

	@Override
	public ObjectNode getWippendingReport(WipPendingReport wippendingreport) {
		// TODO Auto-generated method stub
		ObjectNode flag = wippendingreportDao.getWippendingReport(wippendingreport);
		
		System.out.println(" approvedtasksreportService===== ");
		return flag;
	}

	
		
}
