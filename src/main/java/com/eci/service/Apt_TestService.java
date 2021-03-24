package com.eci.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.eci.bean.Installation;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

public interface Apt_TestService {
	
	
	ObjectNode getAptList();
	ObjectNode AptTestList(Installation installation);
	ObjectNode AptDataInsert(String checkboxD);
	ObjectNode DInsert(String checkboxD);
	ObjectNode DeleteExist(String checkboxD);
	ObjectNode getTaskData(String TaskID);
	ObjectNode InsertAtpTaskData(String TaskID);
	ObjectNode getAptTest();
	ObjectNode updateatp(String editid);
	ObjectNode updateAtpTaskData(String TaskID);
	ObjectNode deleteRecord(String DelId);
	boolean updateSeq(String TaskID);
	ObjectNode AptTestListnew(String checkboxD);
	
	
}
