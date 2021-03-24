package com.eci.dao;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.eci.bean.Installation;
import com.eci.bean.Summary;
import com.eci.bean.User;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

public interface SummaryDao {

	
	ObjectNode getPMsummary(int id);

	ObjectNode getWIPDetails(Summary summary);

		
	
}
