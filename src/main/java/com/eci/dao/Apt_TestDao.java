package com.eci.dao;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.eci.bean.Installation;
import com.eci.bean.User;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

public interface Apt_TestDao {

	ObjectNode getAptList();
	ObjectNode AptTestList(Installation installation);
	ObjectNode AptDataInsert(String checkboxD);
	ObjectNode DInsert(String checkboxD);
	ObjectNode DeleteExist(String checkboxD);
	ObjectNode getTaskData(String taskID);
	ObjectNode InsertAtpTaskData(String TaskID);
	ObjectNode getAptTest();
	ObjectNode updateatp(String editid);
	ObjectNode updateAtpTaskData(String TaskID);
	ObjectNode deleteRecord(String DelId);
	boolean updateSeq(String TaskID);
	ObjectNode AptTestListnew(String checkboxD); 

}
