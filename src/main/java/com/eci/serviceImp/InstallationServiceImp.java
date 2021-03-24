package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import com.eci.bean.Installation;

import com.eci.dao.InstallationDao;
import com.eci.service.InstallationService;
import com.eci.service.ProductService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@Repository
public class InstallationServiceImp implements InstallationService {
	
	@Autowired
	InstallationDao installationDao;

	@Override
	public ObjectNode getInstallationList(int userid) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = installationDao.getInstallationList(userid);
		return orderNode;
	}

	@Override
	public ObjectNode getInstallationCode()
	{
		ObjectNode orderNode = installationDao.getInstallationCode();
		return orderNode;
	}
	
	@Override
	public ObjectNode getSubContractorList(int userid) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = installationDao.getSubContractorList(userid);
		return orderNode;
	}

	@Override
	public ObjectNode insertInstallationtask(Installation installation) {
		// TODO Auto-generated method stub
		ObjectNode flag=installationDao.insertInstallationtask(installation);
		
		System.out.println(" insertInstallationTask ser===== ");
		return flag;
	}

	@Override
	public ObjectNode updateremark(Installation installation) {
		// TODO Auto-generated method stub
		ObjectNode flag=installationDao.updateremark(installation);
		return flag;
	}

	@Override
	public ObjectNode cancelInstallation(Installation installation) {
		// TODO Auto-generated method stub
		ObjectNode flag=installationDao.cancelInstallation(installation);
		return flag;
	}

	@Override
	public ObjectNode getUserWiseRegion(int id) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = installationDao.getUserWiseRegion(id);
		return orderNode;
	}


	@Override
	public ObjectNode generatePdfAction(Installation installation) throws DocumentException, IOException {
		// TODO Auto-generated method stub
		ObjectNode orderNode = installationDao.generatePdfAction(installation);
		return orderNode;
	}
	
	@Override
	public ObjectNode generatePdfAction1() throws DocumentException, IOException {
		// TODO Auto-generated method stub
		ObjectNode orderNode = installationDao.generatePdfAction1();
		return orderNode;
	}

	@Override
	public ObjectNode deleteInstallation(Installation installation) {
		// TODO Auto-generated method stub
		ObjectNode flag=installationDao.deleteInstallation(installation);
		return flag;
	}
	
}
