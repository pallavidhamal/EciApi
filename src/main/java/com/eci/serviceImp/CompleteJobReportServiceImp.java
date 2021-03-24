package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eci.bean.CompleteJobReport;
import com.eci.bean.WipPendingReport;
import com.eci.dao.CompleteJobReportDao;
import com.eci.service.CompleteJobReportService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@Repository
public class CompleteJobReportServiceImp implements CompleteJobReportService {
	
	@Autowired
	CompleteJobReportDao completejobreportDao;

	@Override
	public ObjectNode getcompletejobReport(WipPendingReport  wippendingreport) {
		// TODO Auto-generated method stub
		ObjectNode flag = completejobreportDao.getcompletejobReport(wippendingreport);		
		System.out.println(" getcompletejobReport===== ");
		return flag;
	}

	@Override
	public ObjectNode getCompleteJobDetails(int id) {
		// TODO Auto-generated method stub
		ObjectNode flag = completejobreportDao.getCompleteJobDetails(id);
		
		System.out.println(" getCompleteJobDetails===== ");
		return flag;
	}

	@Override
	public ObjectNode getAdHocReport(CompleteJobReport completejobreport) {
		// TODO Auto-generated method stub
		ObjectNode flag = completejobreportDao.getAdHocReport(completejobreport);
		
		System.out.println(" getAdHocReport===== ");
		return flag;
	}

	@Override
	public ObjectNode getJobdetailsImg(CompleteJobReport completejobreport) {
		// TODO Auto-generated method stub
		ObjectNode flag = completejobreportDao.getJobdetailsImg(completejobreport);
		
		//System.out.println(" getJobdetailsImg===== ");
		return flag;
	}

	@Override
	public ObjectNode getQcReport(CompleteJobReport completejobreport) {
		// TODO Auto-generated method stub
		ObjectNode flag = completejobreportDao.getQcReport(completejobreport);
		
		//System.out.println(" getJobdetailsImg===== ");
		return flag;	}

	
		
}
