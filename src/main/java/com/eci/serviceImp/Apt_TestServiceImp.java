package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eci.bean.Installation;

import com.eci.dao.Apt_TestDao;
import com.eci.dao.InstallationDao;
import com.eci.service.Apt_TestService;
import com.eci.service.ProductService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@Repository
public class Apt_TestServiceImp implements Apt_TestService {
	
	@Autowired
	Apt_TestDao Apt_TestDao;
	@Autowired
	InstallationDao installationDao;
	
	@Override
	public ObjectNode getAptList() {
		// TODO Auto-generated method stub
		ObjectNode orderNode = Apt_TestDao.getAptList();
		return orderNode;
	}
	@Override
	public ObjectNode AptTestList(Installation installation) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = Apt_TestDao.AptTestList( installation);
		return orderNode;
	}
	@Override
	public ObjectNode AptDataInsert(String checkboxD) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = Apt_TestDao.AptDataInsert(checkboxD);
		return orderNode;
	}
	@Override
	public ObjectNode  DInsert(String checkboxD) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = Apt_TestDao.DInsert(checkboxD);
		return orderNode;
	}
	@Override
	public ObjectNode  DeleteExist(String checkboxD) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = Apt_TestDao.DeleteExist(checkboxD);
		return orderNode;
	}
	@Override
	public ObjectNode  getTaskData(String TaskID) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = Apt_TestDao.getTaskData(TaskID);
		return orderNode;
	}
	@Override
	public ObjectNode  InsertAtpTaskData(String TaskID) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = Apt_TestDao.InsertAtpTaskData(TaskID);
		return orderNode;
	}
	@Override
	public ObjectNode getAptTest() {
		// TODO Auto-generated method stub
		ObjectNode orderNode = Apt_TestDao.getAptTest();
		return orderNode;
	}
	@Override
	public ObjectNode  updateatp(String editid) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = Apt_TestDao.updateatp(editid);
		return orderNode;
	}
	@Override
	public ObjectNode  updateAtpTaskData(String TaskID) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = Apt_TestDao.updateAtpTaskData(TaskID);
		return orderNode;
	}
	@Override
	public ObjectNode  deleteRecord(String DelId) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = Apt_TestDao.deleteRecord(DelId);
		return orderNode;
	}
	@Override
	public boolean  updateSeq(String TaskID) {
		// TODO Auto-generated method stub
		boolean orderNode = Apt_TestDao.updateSeq(TaskID);
		return orderNode;
	}
	
	@Override
	public ObjectNode AptTestListnew(String checkboxD) {
		// TODO Auto-generated method stub
		System.out.println("====1==="+checkboxD);
		ObjectNode orderNode = Apt_TestDao.AptTestListnew(checkboxD);
		return orderNode;
	}
	
	}
