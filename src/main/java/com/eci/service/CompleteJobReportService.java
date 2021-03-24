package com.eci.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.eci.bean.CompleteJobReport;
import com.eci.bean.WipPendingReport;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

public interface CompleteJobReportService {


	ObjectNode getcompletejobReport(WipPendingReport  wippendingreport);

	ObjectNode getCompleteJobDetails(int id);

	ObjectNode getAdHocReport(CompleteJobReport completejobreport);

	ObjectNode getQcReport(CompleteJobReport completejobreport);
	
	ObjectNode getJobdetailsImg(CompleteJobReport completejobreport);
	
}
