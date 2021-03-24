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

import com.eci.bean.WipPendingReport;
import com.eci.dao.WipPendingReportDao;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@Repository
public class WipPendingReportDaoImp implements WipPendingReportDao {

	GetDBConnection getConn = new GetDBConnection();
	TokenServiceImp tokService=new TokenServiceImp();

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ObjectNode getWippendingReport(WipPendingReport wippendingreport) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		ArrayNode arrTempNode = objectMapper.createArrayNode();

		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		
		System.out.println("=====getSiId========"+wippendingreport.getSiId());
		System.out.println("=====getDateTo========"+wippendingreport.getDateTo());
		System.out.println("=====getDateFrom========"+wippendingreport.getDateFrom());

		/*pending

		Si accepted but not assigned to TE
		2
		assigned to te but te not started 
		5

		WIP - te started
		16*/
		
		String sql="";
		
		if (wippendingreport.getSiId() == 0) {
			
			/*
			 * sql=" SELECT inc.JobId,inc.Site, inc.Location,inc.RegionId ,inc.StatusId,inc.SI, inc.CreatedDate,rm.RegionName,um.Emp_Name  ,cm.CustName ,"
			 * +
			 * "	CASE when inc.StatusId in(16) then 'WIP' when inc.StatusId in(2,5)  then 'Pending' end as stt "
			 * +
			 * "	FROM eci.installation inc ,region_master rm,user_master um ,customer_master cm "
			 * +
			 * "   where inc.RegionId=rm.RegionId and inc.SI=um.EmpId and inc.CustomerId=cm.CustomerID "
			 * +
			 * "and SI in (SELECT EmpId FROM eci.user_master where IsDeleted='N' and Role=1 ) "
			 * + "  and  inc.StatusId in(2,5,16) and inc.CreatedDate between '"
			 * +wippendingreport.getDateFrom()+"' and '"+wippendingreport.getDateTo()+"' " ;
			 */
			
		
//	update  on 	21-10-2020		
			sql=" SELECT inc.JobId,inc.Site, inc.Location,inc.RegionId ,inc.StatusId,inc.SI, inc.CreatedDate,rm.RegionName,um.Emp_Name ,"
					+ " cm.CustName ,	CASE when inc.StatusId in(16) then 'WIP' when inc.StatusId in(2,5)  then 'Pending' end as stt , "
					+ " um_pm.Emp_Name as pm_name , prm.Name as product_name ,sic.Company ,inc.CurrentStage ,tm.TaskName as Stages ,DATE_FORMAT(inc.CreatedDate, '%Y-%m-%d') as allocation_on "
					+ " FROM installation inc "
					+ " left join region_master rm on inc.RegionId=rm.RegionId "
					+ " left join user_master um  on inc.SI=um.EmpId "
					+ "	left join customer_master cm  on inc.CustomerId=cm.CustomerID"
					+ " left join user_master um_pm  on inc.CreatedBy = um_pm.EmpId "
					+ " left join product_master prm on  inc.ProductId = prm.ProductId "
					+ " left join si_company sic on um.SICompany = sic.Id "
					+ " left join task_master tm on inc.CurrentStage = tm.TaskId "
					+ " where SI in (SELECT EmpId FROM eci.user_master where IsDeleted='N' and Role=1 ) " 
					+ " and  inc.StatusId in(2,5,16) and inc.CreatedDate between '"+wippendingreport.getDateFrom()+"' and '"+wippendingreport.getDateTo()+"'" ; 
			
		}else {
		
			/*
			 * sql=" SELECT inc.JobId,inc.Site, inc.Location,inc.RegionId ,inc.StatusId,inc.SI, inc.CreatedDate,rm.RegionName,um.Emp_Name  ,cm.CustName ,"
			 * +
			 * "	CASE when inc.StatusId in(16) then 'WIP' when inc.StatusId in(2,5)  then 'Pending' end as stt "
			 * +
			 * "	FROM eci.installation inc ,region_master rm,user_master um ,customer_master cm "
			 * +
			 * "   where inc.RegionId=rm.RegionId and inc.SI=um.EmpId and inc.CustomerId=cm.CustomerID "
			 * +
			 * "and SI in (SELECT EmpId FROM eci.user_master where IsDeleted='N' and Role=1 and SICompany  = '"
			 * +wippendingreport.getSiId()+"' ) "+
			 * "  and  inc.StatusId in(2,5,16) and inc.CreatedDate between '"
			 * +wippendingreport.getDateFrom()+"' and '"+wippendingreport.getDateTo()+"' " ;
			 */
			

//			update  on 	21-10-2020		
					sql=" SELECT inc.JobId,inc.Site, inc.Location,inc.RegionId ,inc.StatusId,inc.SI, inc.CreatedDate,rm.RegionName,um.Emp_Name ,"
							+ " cm.CustName ,	CASE when inc.StatusId in(16) then 'WIP' when inc.StatusId in(2,5)  then 'Pending' end as stt , "
							+ " um_pm.Emp_Name as pm_name, prm.Name as product_name ,sic.Company ,inc.CurrentStage ,tm.TaskName as Stages ,DATE_FORMAT(inc.CreatedDate, '%Y-%m-%d') as allocation_on "
							+ " FROM installation inc "
							+ " left join region_master rm on inc.RegionId=rm.RegionId "
							+ " left join user_master um  on inc.SI=um.EmpId "
							+ "	left join customer_master cm  on inc.CustomerId=cm.CustomerID"
							+ " left join user_master um_pm  on inc.CreatedBy = um_pm.EmpId "
							+ " left join product_master prm on  inc.ProductId = prm.ProductId "
							+ " left join si_company sic on um.SICompany = sic.Id "
							+ " left join task_master tm on inc.CurrentStage = tm.TaskId "
							+ " where SI in (SELECT EmpId FROM eci.user_master where IsDeleted='N' and Role=1 and SICompany  = '"+wippendingreport.getSiId()+"' ) " 
							+ " and  inc.StatusId in(2,5,16) and inc.CreatedDate between '"+wippendingreport.getDateFrom()+"' and '"+wippendingreport.getDateTo()+"'" ; 

			
			
		}
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql getWippendingReport----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("JobId",rs.getString("JobId"));
					objectInNode.put("CustName",rs.getString("CustName"));
					objectInNode.put("RegionName",rs.getString("RegionName"));
					objectInNode.put("Site",rs.getString("Site"));
					objectInNode.put("Emp_Name",rs.getString("Emp_Name"));
					objectInNode.put("stt",rs.getString("stt"));
					
					
					objectInNode.put("pm_name",rs.getString("pm_name"));
					
				/*
				 * objectInNode.put("product",rs.getString("product"));
				 * objectInNode.put("Stages",rs.getString("stages"));
				 * objectInNode.put("si_company",rs.getString("SI_company"));
				 * objectInNode.put("allocation_on",rs.getString("allocation_on"));
				 */
					
					objectInNode.put("product",rs.getString("product_name"));
					objectInNode.put("stages",rs.getString("Stages"));
					objectInNode.put("si_company",rs.getString("Company"));
					objectInNode.put("allocation_on",rs.getString("allocation_on"));
				
					
					
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
