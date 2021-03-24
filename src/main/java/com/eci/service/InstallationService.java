package com.eci.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mail.javamail.JavaMailSender;

import com.eci.bean.Installation;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

public interface InstallationService {
	
	
	ObjectNode getInstallationList(int userid);

	ObjectNode getInstallationCode();
	
	ObjectNode getSubContractorList(int userid);

	ObjectNode insertInstallationtask(Installation installation);

	ObjectNode updateremark(Installation installation);

	ObjectNode cancelInstallation(Installation installation);

	ObjectNode deleteInstallation(Installation installation);
	
	ObjectNode getUserWiseRegion(int id);
	
	ObjectNode generatePdfAction(Installation installation) throws DocumentException, IOException;

	ObjectNode generatePdfAction1() throws DocumentException, IOException;

	
}
