package com.eci.dao;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.eci.bean.ApprovedTasksReport;
import com.eci.bean.Installation;
import com.eci.bean.User;
import com.eci.service.ApprovedTasksReportService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

public interface ApprovedTasksReportDao {

	ObjectNode getApprovedTasksReport(ApprovedTasksReport approvedTasksReport);

	ObjectNode getSICompanyList(int getSICompanyList);

	ObjectNode getAppJobDetails(int id);
	
	ObjectNode getHPMApprovedTasks(ApprovedTasksReport approvedTasksReport);
}
