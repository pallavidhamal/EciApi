package com.eci.daoImp;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;

import com.eci.bean.CompleteJobReport;
import com.eci.bean.WipPendingReport;
import com.eci.dao.CompleteJobReportDao;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class CompleteJobReportDaoImp implements CompleteJobReportDao {

	GetDBConnection getConn = new GetDBConnection();
	TokenServiceImp tokService = new TokenServiceImp();

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ObjectNode getcompletejobReport(WipPendingReport wippendingreport) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode = objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		ArrayNode arrTempNode = objectMapper.createArrayNode();

		System.out.println("=====getDateTo========" + wippendingreport.getDateTo());
		System.out.println("=====getDateFrom========" + wippendingreport.getDateFrom());

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		/*
		 * String
		 * sql=" SELECT  inst.id AS id, inst.JobId AS JobId, cm.CustName AS CustomerName, um.Emp_Name AS SI, pm.Name AS ProductName,inst.Site AS Site, inst.Location AS Location, tm.TaskName as Stages,sic.Company, "
		 * +
		 * " inst.CurrentStage AS Stages,inst.Remark, isc.Status AS Status FROM     installation inst 	inner join customer_master cm on inst.CustomerId = cm.CustomerID "
		 * +
		 * " inner join product_master pm on inst.ProductId = pm.ProductId 	inner join user_master um on inst.SI = um.EmpId "
		 * +
		 * " inner join inc_status_codes isc on inst.StatusId = isc.Id  left join task_master tm on inst.CurrentStage = tm.TaskId   left join si_company sic on um.SICompany = sic.Id "
		 * + "  where inst.IsDeleted = 'N' and   StatusId IN (15)  " ;
		 */

		// query modify by ashwini shinde because client want all task done by that job,
		// in previous query shows last task of that job

		/*
		 * String
		 * sql=" SELECT     inst.id AS id,  GROUP_CONCAT(t.TaskName) AS Stages,  inst.JobId AS JobId, cm.CustName AS CustomerName,    um.Emp_Name AS SI,    pm.Name AS ProductName, "
		 * +
		 * " inst.Site AS Site,    inst.Location AS Location, sic.Company,    inst.CurrentStage AS Stages,    inst.Remark,    isc.Status AS Status "
		 * +
		 * " FROM    installation inst   INNER JOIN  customer_master cm ON inst.CustomerId = cm.CustomerID "
		 * +
		 * " INNER JOIN    product_master pm ON inst.ProductId = pm.ProductId   INNER JOIN     user_master um ON inst.SI = um.EmpId "
		 * +
		 * " INNER JOIN    inc_status_codes isc ON inst.StatusId = isc.Id  LEFT JOIN    task_master tm ON inst.CurrentStage = tm.TaskId "
		 * +
		 * " LEFT JOIN    si_company sic ON um.SICompany = sic.Id 	left join 	installation_task it on inst.id = it.IncId "
		 * + " left join task_master t on it.TaskId = t.TaskId " +
		 * "WHERE    inst.IsDeleted = 'N'  AND StatusId IN (15)  group by inst.id" ;
		 */

		// query modify by ashwini shinde because client want PO number and po line id
		// also
		String sql = " SELECT     inst.id AS id,  GROUP_CONCAT(t.TaskName) AS Stages,  inst.JobId AS JobId, cm.CustName AS CustomerName,    um.Emp_Name AS SI,    pm.Name AS ProductName, "
				+ " inst.Site AS Site,    inst.Location AS Location, inst.Purpose as Purpose , sic.Company,    inst.CurrentStage AS Stages,    inst.Remark,    isc.Status AS Status, po.PO_Number, po.LineId , "
				+ "rm.RegionName, um1.Emp_Name as PM , inst.CreatedDate  as AllocDt, ia.ActionDate as CompletionDt "
				+ " FROM    installation inst   INNER JOIN  customer_master cm ON inst.CustomerId = cm.CustomerID "
				+ " INNER JOIN    product_master pm ON inst.ProductId = pm.ProductId   INNER JOIN     user_master um ON inst.SI = um.EmpId "
				+ " INNER JOIN    inc_status_codes isc ON inst.StatusId = isc.Id  LEFT JOIN    task_master tm ON inst.CurrentStage = tm.TaskId "
				+ " LEFT JOIN    si_company sic ON um.SICompany = sic.Id 	left join 	installation_task it on inst.id = it.IncId "
				+ " left join task_master t on it.TaskId = t.TaskId  left join purchaseorder po ON inst.POLineId = po.Id  "
				+" INNER JOIN 	region_master rm on inst.RegionId = rm.RegionId "  //newly added 
				+" INNER JOIN user_master um1 ON inst.CreatedBy = um1.EmpId "		//newly added	
				+" INNER JOIN installation_actions ia ON inst.id=ia.IncId " //newly added
				+ "WHERE    inst.IsDeleted = 'N'  AND StatusId IN (15) and inst.CreatedDate between '"
				+ wippendingreport.getDateFrom() + "' and '" + wippendingreport.getDateTo() + "' and ia.Action=15 group by inst.id";

		try {
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();

			System.out.println("---getcompletejobReport sql---" + sql);

			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				ObjectNode objectInNode = objectMapper.createObjectNode();
				objectInNode.put("id", rs.getString("id"));
				objectInNode.put("JobId", rs.getString("JobId"));
				objectInNode.put("CustomerName", rs.getString("CustomerName"));
				objectInNode.put("ProductName", rs.getString("ProductName"));
				objectInNode.put("SI", rs.getString("SI"));
				objectInNode.put("Company", rs.getString("Company"));
				objectInNode.put("Site", rs.getString("Site"));
				objectInNode.put("Location", rs.getString("Location"));
				objectInNode.put("Purpose", rs.getString("Purpose"));
				objectInNode.put("Stages", rs.getString("Stages"));
				objectInNode.put("Remark", rs.getString("Remark"));
				objectInNode.put("Status", rs.getString("Status"));
				objectInNode.put("PO_Number", rs.getString("PO_Number"));
				objectInNode.put("LineId", rs.getString("LineId"));
				objectInNode.put("RegionName", rs.getString("RegionName"));
				objectInNode.put("PM", rs.getString("PM"));
				objectInNode.put("RegionName", rs.getString("RegionName"));
				objectInNode.put("PM", rs.getString("PM"));
				objectInNode.put("AllocDt", rs.getString("AllocDt"));
				objectInNode.put("CompletionDt", rs.getString("CompletionDt"));
				
				arrayOutNode.add(objectInNode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GetDBConnection.closeRS(rs);
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closeConn(conn);
		}
		objectOutNode.put("result", arrayOutNode);

		System.out.println("==getcompletejobReport===objectOutNode===11===" + objectOutNode);

		return objectOutNode;
	}

	@Override
	public ObjectNode getCompleteJobDetails(int id) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode = objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		ArrayNode arrTempNode = objectMapper.createArrayNode();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = " select tm.TaskName,it.TaskCompletionDate from installation_task it,task_master tm where it.TaskId=tm.TaskId and it.IncId = "
				+ id;

		try {
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();

			System.out.println("---getcompletejobReport sql---" + sql);

			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				ObjectNode objectInNode = objectMapper.createObjectNode();

				objectInNode.put("TaskName", rs.getString("TaskName"));
				objectInNode.put("TaskCompletionDate", rs.getString("TaskCompletionDate"));

				arrayOutNode.add(objectInNode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GetDBConnection.closeRS(rs);
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closeConn(conn);
		}
		objectOutNode.put("result", arrayOutNode);

		System.out.println("=getCompleteJobDetails====" + objectOutNode);

		return objectOutNode;
	}

	@Override
	public ObjectNode getAdHocReport(CompleteJobReport completejobreport) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode = objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		ArrayNode arrTempNode = objectMapper.createArrayNode();
		String update1 = "", wherecond = "", strWireLen = "" , productId ="";

		String strMode = completejobreport.getStrMode();

		if (strMode.equals("UPDATE")) {
			update1 = " INNER JOIN si_tejobupdates st on inst.id = st.IncId ";
			wherecond = " AND st.Action in (8,9) ";
		} else if (strMode.equals("ATP")) {
			wherecond = " AND inst.id in(select Incid from inctaskwiseatp st where st.TestId in (SELECT TestId FROM eci.atp_tests where parameter like 'Photo%')) ";
		} else if (strMode.equals("WIRE")) {
			wherecond = " AND inst.id in(select Incid from inctaskwiseatp st where st.TestId in (SELECT TestId FROM eci.atp_tests where parameter like 'wire used in (meter)%')) ";
		}

		System.out.println("-getAdHocReport--getProductId sql---" + completejobreport.getProductId());

		System.out.println("-getAdHocReport--getDateFrom sql---" + completejobreport.getDateFrom());
		System.out.println("-getAdHocReport--getDateTo sql---" + completejobreport.getDateTo());
		System.out.println("-getAdHocReport--mode sql---" + completejobreport.getStrMode());

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		
		if (completejobreport.getProductId() != 0) {
			
			productId = "and inst.ProductId = '" + completejobreport.getProductId()+"' " ;
		}

/* old 		
		String sql = " SELECT   inst.id AS id, inst.JobId AS JobId, inst.Site ,inst.Location , po.PO_Number,t.TaskId,  "
				+ "    po.LineId,cm.CustName AS CustomerName,  pm.Name AS ProductName,  GROUP_CONCAT(t.TaskName) AS Stages FROM  "
				+ "    installation inst INNER JOIN   customer_master cm ON inst.CustomerId = cm.CustomerID "
				+ "    INNER JOIN   product_master pm ON inst.ProductId = pm.ProductId "
				+ "    INNER JOIN   inc_status_codes isc ON inst.StatusId = isc.Id "
				+ "    LEFT JOIN  task_master tm ON inst.CurrentStage = tm.TaskId "
				+ "    LEFT JOIN  installation_task it ON inst.id = it.IncId "
				+ "    LEFT JOIN task_master t ON it.TaskId = t.TaskId  "
				+ "    LEFT JOIN  purchaseorder po ON inst.POLineId = po.Id  " + update1 +
				// " WHERE inst.IsDeleted = 'N' and inst.CustomerId = '1' and inst.ProductId =
				// '1' GROUP BY inst.id " ;

				"    WHERE inst.IsDeleted = 'N' and inst.CreatedDate between '" + completejobreport.getDateFrom()
				+ "' and '" + completejobreport.getDateTo() + "'  and " + "  inst.CustomerId = '"
				+ completejobreport.getCustomerId() + "' and inst.ProductId = '" + completejobreport.getProductId()+"' GROUP BY inst.id ";

*/		
		
// update on 21-10-2020
		
		String sql = " SELECT   inst.id AS id, inst.JobId AS JobId, inst.Site ,inst.Location , po.PO_Number,t.TaskId,  "
				+ "    po.LineId,cm.CustName AS CustomerName,  pm.Name AS ProductName,  GROUP_CONCAT(t.TaskName) AS Stages "
				+ " ,sic.Company ,um_pm.Emp_Name AS pm_name  , um.Emp_Name AS si_co_ordinator ,rm.RegionName , inst.StatusId , inc_st.Status FROM  "
				+ "    installation inst INNER JOIN   customer_master cm ON inst.CustomerId = cm.CustomerID "
				+ "    INNER JOIN   product_master pm ON inst.ProductId = pm.ProductId "
				+ "    INNER JOIN   inc_status_codes isc ON inst.StatusId = isc.Id "
				+ "    LEFT JOIN  task_master tm ON inst.CurrentStage = tm.TaskId "
				+ "    LEFT JOIN  installation_task it ON inst.id = it.IncId "
				+ "    LEFT JOIN task_master t ON it.TaskId = t.TaskId "
				+ "		 LEFT join user_master um on inst.SI = um.EmpId "
				+ "		LEFT JOIN si_company sic on um.SICompany = sic.Id " 
				+ "		LEFT JOIN user_master um_pm on inst.CreatedBy = um_pm.EmpId  "
				+ "		LEFT JOIN  region_master rm on inst.RegionId = rm.RegionId  "
				+ "		LEFT JOIN inc_status_codes  inc_st on inst.StatusId = inc_st.Id "
				+ "    LEFT JOIN  purchaseorder po ON inst.POLineId = po.Id  " + update1 +
				// " WHERE inst.IsDeleted = 'N' and inst.CustomerId = '1' and inst.ProductId =
				// '1' GROUP BY inst.id " ;

				"    WHERE inst.IsDeleted = 'N' and inst.CreatedDate between '" + completejobreport.getDateFrom()
				+ "' and '" + completejobreport.getDateTo() + "'  and " + "  inst.CustomerId = '"
				+ completejobreport.getCustomerId() + "' "+productId + wherecond + " GROUP BY inst.id ";

		try {
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();

			System.out.println("---getAdHocReport sql---" + sql);

			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				ObjectNode objectInNode = objectMapper.createObjectNode();
				objectInNode.put("id", rs.getString("id"));
				objectInNode.put("TaskId", rs.getString("TaskId"));
				objectInNode.put("JobId", rs.getString("JobId"));
				objectInNode.put("PO_Number", rs.getString("PO_Number"));
				objectInNode.put("LineId", rs.getString("LineId"));
				objectInNode.put("CustomerName", rs.getString("CustomerName"));
				objectInNode.put("ProductName", rs.getString("ProductName"));
				objectInNode.put("Stages", rs.getString("Stages"));
				objectInNode.put("Site", rs.getString("Site"));
				objectInNode.put("Location", rs.getString("Location"));
				
				objectInNode.put("si_Company", rs.getString("Company"));
				objectInNode.put("region_name", rs.getString("RegionName"));
				objectInNode.put("pm_name", rs.getString("pm_name"));
				objectInNode.put("si_co_ordinator", rs.getString("si_co_ordinator"));
				objectInNode.put("status_id", rs.getString("StatusId"));
				objectInNode.put("status_name", rs.getString("Status"));
				

				if (strMode.equals("WIRE")) {
					strWireLen = getWireLength(rs.getString("id"));
				}
				objectInNode.put("WireLength", strWireLen);

				arrayOutNode.add(objectInNode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GetDBConnection.closeRS(rs);
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closeConn(conn);
		}
		objectOutNode.put("result", arrayOutNode);

		System.out.println("==getcompletejobReport===objectOutNode===22===" + objectOutNode);

		return objectOutNode;
	}

	public String getWireLength(String id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String wireLen = "";

		String sql = " select iatp.IncId, iatp.TestValue, atp.parameter from inctaskwiseatp iatp, atp_tests atp where iatp.TestId = atp.TestId and atp.parameter like 'wire used in (meter)%' and iatp.IncId = '"
				+ id + "' ";

		try {
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();

		//	System.out.println("---getJobdetailsImg sql---" + sql);

			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				ObjectNode objectInNode = objectMapper.createObjectNode();
				objectInNode.put("IncId", rs.getString("IncId"));
				// objectInNode.put("TaskId",rs.getString("TaskId"));
				objectInNode.put("parameter", rs.getString("parameter"));
				objectInNode.put("TestValue", rs.getString("TestValue"));

				wireLen = rs.getString("TestValue");

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GetDBConnection.closeRS(rs);
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closeConn(conn);
		}

		return wireLen;
	}

	@Override
	public ObjectNode getJobdetailsImg(CompleteJobReport completejobreport) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode = objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		ArrayNode arrTempNode = objectMapper.createArrayNode();
		String sql = "";

		System.out.println("-getAdHocReport--getProductId sql---" + completejobreport.getId());
		System.out.println("-getAdHocReport--getProductId sql---" + completejobreport.getTaskId());

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String strMode = completejobreport.getStrMode();

		if (strMode.equals("UPDATE")) {
			sql = "SELECT st.IncId,st.ImagePath as TestValue,st.Remark as parameter FROM eci.si_tejobupdates st where st.Action in (8,9) and withVisit='Y' and st.IncId = '"
					+ completejobreport.getId() + "'";
		} else if (strMode.equals("ATP")) {
			sql = "select iatp.IncId, iatp.TestValue, atp.parameter from inctaskwiseatp iatp, atp_tests atp where iatp.TestId = atp.TestId and atp.parameter like '%Photo%' and iatp.IncId = '"
					+ completejobreport.getId() + "' ";
		} else if (strMode.equals("WIRE")) {
			sql = " select iatp.IncId, iatp.TestValue, atp.parameter from inctaskwiseatp iatp, atp_tests atp where iatp.TestId = atp.TestId and atp.parameter like 'wire used in (meter)%' and iatp.IncId = '"
					+ completejobreport.getId() + "' ";
		}

		/*
		 * String
		 * sql=" select iatp.IncId,iatp.TaskId, iatp.TestValue, atp.parameter from inctaskwiseatp iatp, atp_tests atp where iatp.TestId = atp.TestId and "
		 * + " iatp.IncId = '"+completejobreport.getId()+"' and iatp.TaskId = "
		 * +completejobreport.getTaskId() ;
		 */

		try {
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();

		//	System.out.println("---getJobdetailsImg sql---" + sql);

			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				ObjectNode objectInNode = objectMapper.createObjectNode();
				objectInNode.put("IncId", rs.getString("IncId"));
				// objectInNode.put("TaskId",rs.getString("TaskId"));
				objectInNode.put("parameter", rs.getString("parameter"));
				objectInNode.put("TestValue", rs.getString("TestValue"));

				arrayOutNode.add(objectInNode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GetDBConnection.closeRS(rs);
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closeConn(conn);
		}
		objectOutNode.put("result", arrayOutNode);

	//	System.out.println("==getJobdetailsImg===objectOutNode======" + objectOutNode);

		return objectOutNode;
	}

	@Override
	public ObjectNode getQcReport(CompleteJobReport completejobreport) {
		// TODO Auto-generated method stub

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String filename="";

		ObjectNode objectOutNode = objectMapper.createObjectNode();
		ObjectNode objectInNode = objectMapper.createObjectNode();

		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		//ArrayNode arrTempNode = objectMapper.createArrayNode();

		System.out.println("==test call======" + objectOutNode);
		System.out.println("-customer---" + completejobreport.getCustomerId());
		System.out.println("-to date---" + completejobreport.getDateTo());

		System.out.println("-from date---" + completejobreport.getDateFrom());

		int CustId = completejobreport.getCustomerId();
		String strFrmDt = completejobreport.getDateFrom();
		String strToDt = completejobreport.getDateTo();

		///
		XSSFWorkbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();
		XSSFCellStyle hlinkstyle = workbook.createCellStyle();
		XSSFFont hlinkfont = workbook.createFont();
		hlinkfont.setUnderline(XSSFFont.U_SINGLE);
		hlinkfont.setColor(HSSFColor.BLUE.index);
		hlinkstyle.setFont(hlinkfont);

		XSSFCellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints((short) 10);
		font.setBold(true);
		// font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		style.setFont(font);

		XSSFSheet sheet = workbook.createSheet("QCSurvey");

		int rowCnt = 1, cellCnt = 0, locCell = 2,testCnt=1;
		Row row = null;
		String strCustName="";
		//////////////////////////////////////////

		try {

			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();

			String query1 = "select inc.id inc_id,inc.Location ,cm.CustName custName from installation inc ,installation_task it ,customer_master cm"
					+ " where inc.id=it.IncId and inc.CustomerId=cm.CustomerID and inc.CustomerId="
					+ CustId + " and it.TaskId=8 and inc.IsDeleted = 'N' and inc.StatusId=15 and inc.CreatedDate between '" + strFrmDt
					+ "' and '" + strToDt + "' ";
			

			System.out.println("str026 : " + query1);
			
			
			ResultSet rs1 = stmt.executeQuery(query1);
			rowCnt = 1;
			int i = 0;

			while (rs1.next()) {
				i++;
				String inc_id = rs1.getString("inc_id");
				strCustName=rs1.getString("custName");
				strCustName=strCustName.replace(" ", "");
				
				System.out.println("rowCnt" + rowCnt + "locCell" + locCell + "cellCnt" + cellCnt + "i==" + i);

				if (locCell == 2) {
					sheet.createRow(rowCnt);
				}
				rowCnt = 1;
				row = sheet.getRow(rowCnt);

				row.createCell(locCell).setCellValue("Site Name:"+rs1.getString("Location"));
				row.getCell(locCell).setCellStyle(style);

				rowCnt++;

				if ((i == 2) && (cellCnt == 1)) {
					cellCnt = 3;
				} else {

					cellCnt++;
				}

				System.out.println("cellcnt" + cellCnt);

				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();

				stmt = conn.createStatement();

				String query2 = "select * from stagecategory  where StageId = 8 order by Id ";
				System.out.println("str02 : " + query2);

				ResultSet rs2 = stmt.executeQuery(query2);

				while (rs2.next()) {

					String strtblid = rs2.getString("Id");

					conn = GetDBConnection.getConnection();
					stmt = conn.createStatement();

					String str03 = "SELECT  inc.IncId, inc.TaskId, inc.TestId, atp.TaskCategoryId, atp.parameter, inc.TestValue FROM eci.inctaskwiseatp inc, atp_tests atp where inc.TestId = atp.TestId and inc.IncId = "
							+ inc_id + " and inc.TaskId =8  and atp.TaskCategoryId = " + strtblid + "  ";
					System.out.println("str03 : " + str03);

					ResultSet rs3 = stmt.executeQuery(str03);

					if (cellCnt == 1) {
						sheet.createRow(rowCnt);
						row = sheet.getRow(rowCnt);
						row.createCell(cellCnt).setCellValue(rs2.getString("Category"));
						row.getCell(cellCnt).setCellStyle(style);

					}
					rowCnt++;
					
					while (rs3.next()) {

						System.out.println("*************rowCnt=:" + rowCnt + "cellCnt" + cellCnt);

						if (i == 1)
							sheet.createRow(rowCnt);

						row = sheet.getRow(rowCnt);

						String strTestName = rs3.getString("parameter");

						if (cellCnt == 1) {
							System.out.println("test header" + rs3.getString("parameter"));
							row.createCell(0).setCellValue(testCnt);
							row.createCell(cellCnt).setCellValue(rs3.getString("parameter"));
							cellCnt++;
							testCnt++;
						}

						System.out.println("cellCnt after===" + cellCnt + "===test=" + rs3.getString("TestValue"));

						String strTestValue = rs3.getString("TestValue");

						if (strTestName.contains("Photo")||(strTestName.contains("PHOTO"))) 
						{
							strTestValue = strTestValue.replace(
									"C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0/webapps/",
									"https://proapp.co.in/");
						}

						row.createCell(cellCnt).setCellValue(strTestValue);

						if (strTestName.contains("Photo")||(strTestName.contains("PHOTO")))
						{
						  XSSFHyperlink link = (XSSFHyperlink)createHelper.createHyperlink(HyperlinkType.URL);
						  link.setAddress(strTestValue);
						  row.getCell(cellCnt).setHyperlink((XSSFHyperlink) link);
						  row.getCell(cellCnt).setCellStyle(hlinkstyle);
						}

						if ((cellCnt == 2) && (i == 1)) {
							cellCnt--;
							System.out.println("cellCnt after minus===" + cellCnt);
						}

						rowCnt++;
					}
				}

				locCell++;

			}
			
			if(i==0)
			{
			sheet.createRow(1);
			row = sheet.getRow(1);
			row.createCell(1).setCellValue("NO DATA FOUND");
			row.getCell(locCell).setCellStyle(style);
			rowCnt++;
			}
			
			

		} catch (Exception e) {
			System.out.println("exception" + e.toString());
		}

		////////////////////////////////////////

		String PATH = System.getProperty("user.dir");
		System.out.println("----UPLOAD_FOLDEdR----" + PATH);

		 String condir = PATH.concat("/webapps/ProApp"); //server

		 String directoryName = condir.concat("/QcSurvey"); //server

		// String directoryName = PATH.concat("/QcSurvey"); // local

		System.out.println("----UPLOAD_FOLDEdR--directoryName--" + directoryName);

		File directory = new File(directoryName);
		System.out.println("----UPLOAD_FOLDEdR--directory--" + directory);

		
		try {
		
		if (!directory.exists()) {
			System.out.println("creating folder");
			directory.mkdir();
		}
		else
		{
			      FileUtils.cleanDirectory(directory);
			      File[] files = directory.listFiles();
			      for (File file : files)
			      {
			    	  if (!file.delete())
			    		  System.out.println("Failed to delete "+file);
			      }
			      
		}
		}
		
		catch(Exception e)
		{
			System.out.println("==file delte exception======" + e.toString());
		}
		
		try 
		{
			
		System.out.println("creating folder directory " + directory);

		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
		String strDate = dateFormat.format(date);

		System.out.println("strDatestrDate " + strDate);
		filename = strDate +strCustName+strFrmDt+"_"+strToDt+"_QcSurvey.xlsx";

		String filepath = directory + "\\" + filename;

		System.out.println("filepath " + filepath);

		File file = new File(filepath);
		////

		FileOutputStream outputStream = new FileOutputStream(filepath);
			workbook.write(outputStream);
		}

		catch (Exception e) {
			System.out.println("==test call======" + e.toString());

		}
		finally {
			GetDBConnection.closeRS(rs);
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closeConn(conn);
		}
		///
		objectInNode.put("filepath", filename);
		arrayOutNode.add(objectInNode);
		objectOutNode.put("result", arrayOutNode);

		return objectOutNode;
	}
}
