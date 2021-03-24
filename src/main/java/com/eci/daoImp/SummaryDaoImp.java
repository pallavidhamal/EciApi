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

import com.eci.bean.Installation;
import com.eci.bean.Summary;
import com.eci.bean.Task;
import com.eci.dao.InstallationDao;
import com.eci.dao.SummaryDao;
import com.eci.util.GenerateInvoicePdf;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@Repository
public class SummaryDaoImp implements SummaryDao {

		GetDBConnection getConn = new GetDBConnection();
	TokenServiceImp tokService=new TokenServiceImp();

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ObjectNode getPMsummary(int id) {
		boolean flag = false;
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		String result="error";
		
		String sql = "SELECT inc.RegionId ,inc.SI,rm.RegionName,um.Emp_Name, SUM(if(inc.StatusId = 2, 1, 0)) as SIAccepted,"
				+ " SUM(if(inc.StatusId = 5, 1, 0)) as TaskTOStart, "
				+ "SUM(if(inc.StatusId = 16, 1, 0)) as WIP, "
				+ "SUM(if(inc.StatusId in(3,4,7,9),1,0)) as Unassigned "
				+ "FROM eci.installation inc ,region_master rm,user_master um "
				+ "where inc.RegionId=rm.RegionId and inc.SI=um.EmpId and inc.CreatedBy=  "+id
				+ " group by RegionId,SI  order by inc.RegionId ,inc.SI; ";
		
		System.out.println("----getPMsummary id----"+id);
		
		System.out.println("----getPMsummary----"+sql);
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
					
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					ObjectNode objectInNode = objectMapper.createObjectNode();

					objectInNode.put("Emp_Name", rs.getString("Emp_Name"));
					objectInNode.put("RegionName", rs.getString("RegionName"));
					objectInNode.put("TaskTOStart", rs.getString("TaskTOStart"));//
					objectInNode.put("SIAccepted", rs.getString("SIAccepted"));
					
					objectInNode.put("WIP", rs.getString("WIP"));//
					objectInNode.put("Unassigned", rs.getString("Unassigned"));//
					
					objectInNode.put("RegionId", rs.getString("RegionId"));
					objectInNode.put("SI", rs.getString("SI"));
					
					arrayOutNode.add(objectInNode);
					
					
				}
				objectOutNode.put("data", arrayOutNode.toString());
				result= "success";
				System.out.println("----getPMsummary--objectOutNode--"+objectOutNode);				
				
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
			objectOutNode.put("result", result);
			
		return objectOutNode;
	}

	@Override
	public ObjectNode getWIPDetails(Summary summary) {
		// TODO Auto-generated method stub
		boolean flag = false;
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		String result="error",qry="";
		
		System.out.println("----user id----"+summary.getId());
		System.out.println("----getSiId---"+summary.getSiId());
		System.out.println("----getRegionId---"+summary.getRegionId());
		System.out.println("----getcurrentStatus---"+summary.getCurrentStatus());

		int strId=summary.getId();
		
		if(strId!=0)
		{
			qry=" and inc.CreatedBy=  "+strId;
		}
		
		
		/*
		 * String sql =
		 * "SELECT inc.RegionId ,inc.SI,rm.RegionName,um.Emp_Name, SUM(if(inc.StatusId = 2, 1, 0)) as SIAccepted,"
		 * + " SUM(if(inc.StatusId = 5, 1, 0)) as TaskTOStart, " +
		 * " SUM(if(inc.StatusId = 16, 1, 0)) as WIP, " +
		 * " SUM(if(inc.StatusId in(3,4,7,9),1,0)) as Unassigned " +
		 * " FROM eci.installation inc ,region_master rm,user_master um " +
		 * " where inc.RegionId=rm.RegionId and inc.SI=um.EmpId and inc.CreatedBy=  "
		 * +summary.getId()+"  and inc.SI = "+summary.getSiId()+" and inc.RegionId = "
		 * +summary.getRegionId()+" " +
		 * " group by RegionId,SI  order by inc.RegionId ,inc.SI; ";
		 */
		
		
		String sql = "SELECT inc.id,inc.JobId,inc.RegionId ,inc.SI,rm.RegionName,um.Emp_Name "
				+ " FROM eci.installation inc ,region_master rm,user_master um "
				+ " where inc.RegionId=rm.RegionId and inc.SI=um.EmpId   and inc.SI = "+summary.getSiId()+" and inc.RegionId = "+summary.getRegionId()+" "
				+ " and inc.StatusId in ("+summary.getCurrentStatus()+") "+ qry+ " order by inc.RegionId ,inc.SI ";
		
		
		
		
		System.out.println("----getWIPDetails----"+sql);
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
					
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					ObjectNode objectInNode = objectMapper.createObjectNode();
					objectInNode.put("IncId", rs.getString("id"));

					objectInNode.put("JobId", rs.getString("JobId"));
					objectInNode.put("Emp_Name", rs.getString("Emp_Name"));
					//objectInNode.put("Emp_Name", rs.getString("Emp_Name"));
					objectInNode.put("RegionName", rs.getString("RegionName"));
				//	objectInNode.put("TaskTOStart", rs.getString("TaskTOStart"));
				//	objectInNode.put("SIAccepted", rs.getString("SIAccepted"));
					
				//	objectInNode.put("WIP", rs.getString("WIP"));
				//	objectInNode.put("Unassigned", rs.getString("Unassigned"));
					
					arrayOutNode.add(objectInNode);
					
					String TEdate=getInstallationTEDate( rs.getString("id"));
					String TEname=getInstallationTEName( rs.getString("id"));
					
					objectInNode.put("TEAssDate", TEdate);
					objectInNode.put("TEname", TEname);

				}
				objectOutNode.put("data", arrayOutNode.toString());
				result= "success";
				System.out.println("----getPMsummary--objectOutNode--"+objectOutNode);				
				
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
			objectOutNode.put("result", result);
			
		return objectOutNode;
	}


	
	
	public String getInstallationTEDate(String strJobId) {
	//public String getLatestIncRemark(String strJobId) {
		// TODO Auto-generated method stub
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;

		String sql ="",strTEassDt="";
				
		sql = "SELECT ActionDate FROM eci.installation_actions where IncId= "+strJobId+" and Action=5" ;
				
				try
				{
						conn = GetDBConnection.getConnection();
						stmt = conn.createStatement(); 
						System.out.println("----getInstallationTE----"+sql);
						rs = stmt.executeQuery(sql);
						while(rs.next())
						{
							strTEassDt=rs.getString("ActionDate");
						}
						
				}
					
				catch(Exception e)
		    	{
		    		e.printStackTrace();
		    	}
				finally {
					GetDBConnection.closeStmt(stmt);
					GetDBConnection.closeRS(rs);
					GetDBConnection.closeConn(conn);
				}
				
				System.out.println("----getInstallationTE TE name----"+strTEassDt);
				
				return strTEassDt;
				
	}
	
	public String getInstallationTEName(String strJobId) {
		//public String getLatestIncRemark(String strJobId) {
			// TODO Auto-generated method stub
			Connection conn 	= null;
			Statement stmt 		= null;
		    ResultSet rs		= null;

			String sql ="",strTEname="";
					
			sql = "select Emp_Name from user_master where EmpId= (select TE from installation where id=  "+strJobId+" )" ;
					
					try
					{
							conn = GetDBConnection.getConnection();
							stmt = conn.createStatement(); 
							System.out.println("----getInstallationTE----"+sql);
							rs = stmt.executeQuery(sql);
							while(rs.next())
							{
								strTEname=rs.getString("Emp_Name");
							}
							
					}
						
					catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}
					finally {
						GetDBConnection.closeStmt(stmt);
						GetDBConnection.closeRS(rs);
						GetDBConnection.closeConn(conn);
					}
					
					System.out.println("----getInstallationTE TE name----"+strTEname);
					
					return strTEname;
					
		}

	
}
