package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eci.bean.ApprovedTasksReport;
import com.eci.bean.Installation;
import com.eci.dao.ApprovedTasksReportDao;
import com.eci.dao.InstallationDao;
import com.eci.service.ApprovedTasksReportService;
import com.eci.service.InstallationService;
import com.eci.service.ProductService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@Repository
public class ApprovedTasksReportServiceImp implements ApprovedTasksReportService {
	
	@Autowired
	ApprovedTasksReportDao approvedtasksreportDao;

	@Override
	public ObjectNode getApprovedTasksReport(ApprovedTasksReport  approvedTasksReport) {
		// TODO Auto-generated method stub
		
		ObjectNode flag = approvedtasksreportDao.getApprovedTasksReport(approvedTasksReport);
		
		System.out.println(" approvedtasksreportService===== ");
		return flag;
	}

	@Override
	public ObjectNode getSICompanyList(int getSICompanyList) {
		// TODO Auto-generated method stub
		ObjectNode flag = approvedtasksreportDao.getSICompanyList(getSICompanyList);
		
		System.out.println(" approvedtasksreportService===== ");
		return flag;
	}

	
	@Override
	public ObjectNode getAppJobDetails(int id) {
		// TODO Auto-generated method stub
		ObjectNode flag = approvedtasksreportDao.getAppJobDetails(id);
		
		System.out.println(" getCompleteJobDetails===== ");
		return flag;
	}

	@Override
	public ObjectNode getHPMApprovedTasks(ApprovedTasksReport approvedTasksReport) {
		// TODO Auto-generated method stub
		ObjectNode flag = approvedtasksreportDao.getHPMApprovedTasks(approvedTasksReport);
		
		System.out.println(" getHPMApprovedTasks===== ");
		return flag;
	}
	
		
}
