package com.eci.dao;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

public interface POReportDao {


	ObjectNode getPOReportData(int id);

	ObjectNode getClosedPOReportData(int id);

		
	
}
