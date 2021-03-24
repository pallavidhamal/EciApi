package com.eci.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.eci.bean.Installation;
import com.eci.bean.Summary;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

public interface POReportService {
	
	
	ObjectNode getPOReportData(int id);

	ObjectNode getClosedPOReportData(int id);


	
}
