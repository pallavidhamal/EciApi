package com.eci.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.eci.bean.ApprovedTasksReport;
import com.eci.bean.Installation;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

public interface ApprovedTasksReportService {

	ObjectNode getApprovedTasksReport(ApprovedTasksReport approvedTasksReport);

	ObjectNode getSICompanyList(int userid);
	
	ObjectNode getAppJobDetails(int userid);
	
	ObjectNode getHPMApprovedTasks(ApprovedTasksReport approvedTasksReport);

}
