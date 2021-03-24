package com.eci.daoImp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;

import com.eci.bean.ApprovedTasksReport;
import com.eci.bean.Installation;
import com.eci.bean.Task;
import com.eci.dao.ApprovedTasksReportDao;
import com.eci.dao.InstallationDao;
import com.eci.service.ApprovedTasksReportService;
import com.eci.util.GenerateInvoicePdf;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@Repository
public class ApprovedTasksReportDaoImp implements ApprovedTasksReportDao {

	GetDBConnection getConn = new GetDBConnection();
	TokenServiceImp tokService = new TokenServiceImp();

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ObjectNode getApprovedTasksReport(ApprovedTasksReport approvedTasksReport) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ObjectNode objectOutNode = objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		ArrayNode arrTempNode = objectMapper.createArrayNode();
		String sql;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String remark = "", remark1 = "", typeFlag = "", recId = "";
		// System.out.println("=====userid========"+userid);
		String fromdt = approvedTasksReport.getDateFrom();
		String todt = approvedTasksReport.getDateTo();
		String tim = "00:00:00", tim1 = "23:59:59";
		String frmdttm = fromdt + " " + tim;
		String todttm = todt + " " + tim1;

		System.out.println("=====getAuthKey========" + approvedTasksReport.getAuthKey());
		System.out.println("=====getSiId==sisi======" + approvedTasksReport.getSiId());
		System.out.println("=====getDateTo========" + approvedTasksReport.getDateTo());
		System.out.println("=====getDateFrom========" + approvedTasksReport.getDateFrom());
		System.out.println("=====getDateFrom========" + frmdttm);

		if (approvedTasksReport.getSiId() == 0) {

			System.out.println("=====in if if if======" + frmdttm);

			/*
			 * sql="SELECT inc.id,inc.JobId,inc.Site, inc.Location,inc.RegionId ,inc.StatusId,inc.SI, icd.CreatedDate as DOA,rm.RegionName,um.Emp_Name  ,cm.CustName, pm.ProductId, pm.Name ,icd.VisitInc,  "
			 * +
			 * " po.PO_Number, po.LineId FROM eci.installation inc ,region_master rm,user_master um ,customer_master cm , product_master pm , inc_claim_data icd, purchaseorder po "
			 * +
			 * " where inc.RegionId=rm.RegionId and inc.SI=um.EmpId and inc.CustomerId=cm.CustomerID and inc.id=icd.IncId and inc.ProductId = pm.ProductId and inc.POLineId = po.Id "
			 * + " and SI in (SELECT EmpId FROM eci.user_master where IsDeleted='N'  ) " +
			 * " and icd.CreatedDate between '"+frmdttm+"'  and '"+todttm+"' ";
			 */

			/*
			 * sql =
			 * "SELECT inc.id,inc.JobId,inc.Site, inc.Location,inc.RegionId ,inc.StatusId,inc.SI, icd.CreatedDate as DOA,rm.RegionName,um.Emp_Name  ,cm.CustName, pm.ProductId, pm.Name ,icd.VisitInc,  	"
			 * + " po.PO_Number, po.LineId  FROM eci.installation inc 	" +
			 * " inner join  region_master rm on inc.RegionId=rm.RegionId   " +
			 * " inner join  user_master um on inc.SI=um.EmpId " +
			 * " inner join  customer_master cm on inc.CustomerId=cm.CustomerID " +
			 * " inner join  product_master pm on inc.ProductId = pm.ProductId   " +
			 * " inner join  inc_claim_data icd on inc.id=icd.IncId  " +
			 * "  left join   purchaseorder po on inc.POLineId = po.Id " +
			 * " where  SI in (SELECT EmpId FROM eci.user_master where IsDeleted='N' ) " +
			 * " and icd.CreatedDate between '" + frmdttm + "'  and '" + todttm + "' ";
			 */

// update on 21-10-2020 
			sql = "SELECT inc.id,inc.JobId,inc.Site, inc.Location,inc.RegionId ,inc.StatusId,inc.SI, icd.CreatedDate as DOA,rm.RegionName,um.Emp_Name  ,cm.CustName, pm.ProductId, pm.Name ,icd.VisitInc,  	"
					+ " po.PO_Number, po.LineId , um_pm.Emp_Name  as pm_name FROM eci.installation inc 	"
					+ " inner join  region_master rm on inc.RegionId=rm.RegionId   "
					+ " inner join  user_master um on inc.SI=um.EmpId "
					+ " inner join  customer_master cm on inc.CustomerId=cm.CustomerID "
					+ " inner join  product_master pm on inc.ProductId = pm.ProductId   "
					+ " inner join  inc_claim_data icd on inc.id=icd.IncId  "
					+ "  left join   purchaseorder po on inc.POLineId = po.Id "
					+ " LEFT join user_master um_pm on inc.CreatedBy = um_pm.EmpId "
					+ " where  SI in (SELECT EmpId FROM eci.user_master where IsDeleted='N' ) "
					+ " and icd.CreatedDate between '" + frmdttm + "'  and '" + todttm + "' ";

		} else {

			/*
			 * sql="SELECT inc.id,inc.JobId,inc.Site, inc.Location,inc.RegionId ,inc.StatusId,inc.SI, icd.CreatedDate as DOA,rm.RegionName,um.Emp_Name  ,cm.CustName, pm.ProductId, pm.Name ,icd.VisitInc, "
			 * +
			 * " po.PO_Number, po.LineId FROM eci.installation inc ,region_master rm,user_master um ,customer_master cm , product_master pm , inc_claim_data icd, purchaseorder po "
			 * +
			 * " where inc.RegionId=rm.RegionId and inc.SI=um.EmpId and inc.CustomerId=cm.CustomerID and inc.id=icd.IncId and inc.ProductId = pm.ProductId and inc.POLineId = po.Id "
			 * +
			 * " and SI in (SELECT EmpId FROM eci.user_master where IsDeleted='N' and SICompany  = '"
			 * +approvedTasksReport.getSiId()+"' ) " +
			 * " and icd.CreatedDate between '"+frmdttm+"'  and '"+todttm+"' ";
			 */

			/*
			 * sql =
			 * "SELECT inc.id,inc.JobId,inc.Site, inc.Location,inc.RegionId ,inc.StatusId,inc.SI, icd.CreatedDate as DOA,rm.RegionName,um.Emp_Name  ,cm.CustName, pm.ProductId, pm.Name ,icd.VisitInc,  	"
			 * + " po.PO_Number, po.LineId FROM eci.installation inc 	" +
			 * " inner join  region_master rm on inc.RegionId=rm.RegionId   " +
			 * " inner join  user_master um on inc.SI=um.EmpId " +
			 * " inner join  customer_master cm on inc.CustomerId=cm.CustomerID " +
			 * " inner join  product_master pm on inc.ProductId = pm.ProductId   " +
			 * " inner join  inc_claim_data icd on inc.id=icd.IncId  " +
			 * "  left join   purchaseorder po on inc.POLineId = po.Id " +
			 * " where  SI in (SELECT EmpId FROM eci.user_master where IsDeleted='N' and SICompany  = '"
			 * + +approvedTasksReport.getSiId() + "' )  " + " and icd.CreatedDate between '"
			 * + frmdttm + "'  and '" + todttm + "' ";
			 */

// update on 21-10-2020
			sql = "SELECT inc.id,inc.JobId,inc.Site, inc.Location,inc.RegionId ,inc.StatusId,inc.SI, icd.CreatedDate as DOA,rm.RegionName,um.Emp_Name  ,cm.CustName, pm.ProductId, pm.Name ,icd.VisitInc,  	"
					+ " po.PO_Number, po.LineId  , um_pm.Emp_Name  as pm_name FROM eci.installation inc 	"
					+ " inner join  region_master rm on inc.RegionId=rm.RegionId   "
					+ " inner join  user_master um on inc.SI=um.EmpId "
					+ " inner join  customer_master cm on inc.CustomerId=cm.CustomerID "
					+ " inner join  product_master pm on inc.ProductId = pm.ProductId   "
					+ " inner join  inc_claim_data icd on inc.id=icd.IncId  "
					+ "  left join   purchaseorder po on inc.POLineId = po.Id "
					+ "	LEFT join user_master um_pm on inc.CreatedBy = um_pm.EmpId  "
					+ " where  SI in (SELECT EmpId FROM eci.user_master where IsDeleted='N' and SICompany  = '"
					+ +approvedTasksReport.getSiId() + "' )  " + " and icd.CreatedDate between '" + frmdttm + "'  and '"
					+ todttm + "' ";
			
		}

		// date_format('"+approvedTasksReport.getDateFrom()+"', '%Y-%m-%d %H:%i:%s') and
		// date_format('"+approvedTasksReport.getDateTo()+"', '%Y-%m-%d %H:%i:%s')
		// icd.CreatedDate put this
		try {
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();

			System.out.println("----sql getApprovedTasksReport--11--" + sql);

			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				ObjectNode objectInNode = objectMapper.createObjectNode();

				objectInNode.put("IncId", rs.getString("id"));
				objectInNode.put("JobId", rs.getString("JobId"));
				objectInNode.put("RegionId", rs.getString("RegionId"));
				objectInNode.put("RegionName", rs.getString("RegionName"));
				objectInNode.put("Emp_Name", rs.getString("Emp_Name"));
				objectInNode.put("CustName", rs.getString("CustName"));
				objectInNode.put("VisitInc", rs.getString("VisitInc"));
				objectInNode.put("Name", rs.getString("Name"));
				objectInNode.put("DOA", rs.getString("DOA"));
				objectInNode.put("Location", rs.getString("Location"));
				objectInNode.put("Name", rs.getString("Name"));
				objectInNode.put("PO_Number", rs.getString("PO_Number"));
				objectInNode.put("LineId", rs.getString("LineId"));
// add on 21-10-2020				
				objectInNode.put("pm_name", rs.getString("pm_name"));

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

		System.out.println("					objectOutNode======" + objectOutNode);

		return objectOutNode;
	}

	@Override
	public ObjectNode getSICompanyList(int userid) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode = objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();

		// String sql = " SELECT EmpId, Emp_Name, RoleType FROM user_master where Role =
		// '1' " ;
		// String sql = " SELECT distinct(um.EmpId),um.Emp_Name, um.RoleType FROM
		// user_master um ,user_region ur where Role = '1' and um.EmpId=ur.UserId and
		// regionId in (select regionId from user_region where userId= "+userid+" )" ;
		// change this query for get user according to region
		String sql = "SELECT * FROM eci.si_company where IsDeleted = 'N' ";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();

			System.out.println("----sql getSICompanyList-111---" + sql);

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				ObjectNode objectInNode = objectMapper.createObjectNode();

				objectInNode.put("Id", rs.getString("Id"));
				objectInNode.put("Company", rs.getString("Company"));

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

		return objectOutNode;
	}

	@Override
	public ObjectNode getAppJobDetails(int id) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode = objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		ArrayNode arrTempNode = objectMapper.createArrayNode();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "SELECT IncId,Remark,ActionDate FROM eci.installation_actions where IncId =" + id
				+ "  and action in (11,15) union " + " SELECT IncId,Remark,Date FROM eci.si_tejobupdates where IncId = "
				+ id + "  and action in (9) ";

		// String sql=" select tm.TaskName,it.TaskCompletionDate from installation_task
		// it,task_master tm where it.TaskId=tm.TaskId and it.IncId = "+id ;

		try {
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();

			System.out.println("---getcompletejobReport sql---" + sql);

			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				ObjectNode objectInNode = objectMapper.createObjectNode();

				objectInNode.put("Remark", rs.getString("Remark"));
				objectInNode.put("ActionDate", rs.getString("ActionDate"));

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
	public ObjectNode getHPMApprovedTasks(ApprovedTasksReport approvedTasksReport) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		ArrayNode arrTempNode = objectMapper.createArrayNode();
		String sql;
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		String remark="",remark1="",typeFlag="",recId="";
	//	System.out.println("=====userid========"+userid);
		String fromdt = approvedTasksReport.getDateFrom() ;
		String todt = approvedTasksReport.getDateTo();
		String tim = "00:00:00", tim1 = "23:59:59";
		String frmdttm = fromdt +" "+ tim ;
		String todttm = todt +" "+ tim1 ;
		
		System.out.println("=====getAuthKey========"+approvedTasksReport.getAuthKey());
		System.out.println("=====getSiId==sisi======"+approvedTasksReport.getSiId());
		System.out.println("=====getDateTo========"+approvedTasksReport.getDateTo());
		System.out.println("=====getDateFrom========"+approvedTasksReport.getDateFrom());
		System.out.println("=====getDateFrom========"+frmdttm);
	
	/*	if(approvedTasksReport.getSiId()==0) {
			
			System.out.println("=====in if if if======"+frmdttm);
			
			sql= "SELECT inc.id,inc.JobId,inc.Site, inc.Location,inc.RegionId ,inc.StatusId,inc.SI, icd.CreatedDate as DOA,rm.RegionName,um.Emp_Name  ,cm.CustName, pm.ProductId, pm.Name ,icd.VisitInc,  	"
			 		+ " po.PO_Number, po.LineId FROM eci.installation inc 	"
			 		+ " inner join  region_master rm on inc.RegionId=rm.RegionId   "
			 		+ " inner join  user_master um on inc.SI=um.EmpId "
			 		+ " inner join  customer_master cm on inc.CustomerId=cm.CustomerID "
			 		+ " inner join  product_master pm on inc.ProductId = pm.ProductId   "
			 		+ " inner join  inc_claim_data icd on inc.id=icd.IncId  "
			 		+ "  left join   purchaseorder po on inc.POLineId = po.Id "
			 		+ " where  SI in (SELECT EmpId FROM eci.user_master where IsDeleted='N' ) "
					+ " and icd.CreatedDate between '"+frmdttm+"'  and '"+todttm+"' ";
			 
			 
			
		}else {*/
			
			
			
		/*	 sql= "SELECT inc.id,inc.JobId,inc.Site, inc.Location,inc.RegionId ,inc.StatusId,inc.SI, ,rm.RegionName,um.Emp_Name  ,cm.CustName, pm.ProductId, pm.Name   	"
				 		+ " FROM eci.installation inc 	"
				 		+ " inner join  region_master rm on inc.RegionId=rm.RegionId   "
				 		+ " inner join  user_master um on inc.SI=um.EmpId "
				 		+ " inner join  customer_master cm on inc.CustomerId=cm.CustomerID "
				 		+ " inner join  product_master pm on inc.ProductId = pm.ProductId   "
				 		+ " inner join  inc_claim_data icd on inc.id=icd.IncId  "
				 		+ "  left join   purchaseorder po on inc.POLineId = po.Id "
				
				  +
				  " where  SI in (SELECT EmpId FROM eci.user_master where IsDeleted='N' and SICompany  = '"
				  + + approvedTasksReport.getSiId()+ "' )  "
				 
						+ " and inc.CreatedDate between '"+frmdttm+"'  and '"+todttm+"' ";
			*/
			 
			 sql= " SELECT inc.id,inc.JobId,inc.Site, inc.Location,inc.RegionId ,inc.StatusId,inc.SI,rm.RegionName,um.Emp_Name as SI_NAME,um1.Emp_Name as PM,cm.CustName, pm.ProductId, pm.Name ,"
			 		+ "ic.Status ,date_format(ia.ActionDate , '%Y-%m-%d') as ActionDate  FROM eci.installation inc "
			 		+ "inner join  region_master rm on inc.RegionId=rm.RegionId "
			 		+ "inner join  user_master um on inc.SI=um.EmpId "
			 		+" INNER JOIN user_master um1 ON inc.CreatedBy = um1.EmpId "		//newly added
			 		+ " inner join  customer_master cm on inc.CustomerId=cm.CustomerID "
			 		+ " inner join  product_master pm on inc.ProductId = pm.ProductId   "
			 		+ " inner join installation_actions ia on inc.id=ia.IncId "
			 		+ " inner join inc_status_codes ic on ic.Id = inc.StatusId "
			 		+ " and ia.Action=13 "
			 		+ " and ia.ActionBy = " + approvedTasksReport.getAuthKey()
			 		+ " and inc.CreatedDate between '"+frmdttm+"'  and '"+todttm+"'";
			 
	
		//}
		
		//date_format('"+approvedTasksReport.getDateFrom()+"', '%Y-%m-%d %H:%i:%s')  and date_format('"+approvedTasksReport.getDateTo()+"', '%Y-%m-%d %H:%i:%s')  
		//	icd.CreatedDate put this 
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql getApprovedTasksReport--22--"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("IncId",rs.getString("id"));
					objectInNode.put("JobId",rs.getString("JobId"));
					objectInNode.put("RegionId",rs.getString("RegionId"));
					objectInNode.put("RegionName",rs.getString("RegionName"));
					objectInNode.put("SI_NAME",rs.getString("SI_NAME"));
					objectInNode.put("PM",rs.getString("PM"));
					objectInNode.put("CustName",rs.getString("CustName"));
					objectInNode.put("Site",rs.getString("Site"));
					objectInNode.put("Name",rs.getString("Name"));
				//	objectInNode.put("DOA",rs.getString("DOA"));
					objectInNode.put("Location",rs.getString("Location"));
					//objectInNode.put("Name",rs.getString("Name"));
					//objectInNode.put("PO_Number",rs.getString("PO_Number"));
					//objectInNode.put("LineId",rs.getString("LineId"));
					objectInNode.put("Status",rs.getString("Status"));
					objectInNode.put("ActionDate",rs.getString("ActionDate"));

					
					arrayOutNode.add(objectInNode);
				}
				
			}
			catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
			finally {
				GetDBConnection.closeRS(rs);
				GetDBConnection.closeStmt(stmt);
				GetDBConnection.closeConn(conn);
			}
			objectOutNode.put("result", arrayOutNode);
		
			System.out.println("					objectOutNode======"+objectOutNode);
			
		return objectOutNode;
	}

}
