package com.eci.daoImp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.eci.bean.Installation;
import com.eci.bean.PendingApproval;
import com.eci.bean.Task;
import com.eci.dao.InstallationDao;
import com.eci.dao.PendingApprovalDao;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class PendingApprovalDaoImp implements PendingApprovalDao {

	/*private PreparedStatement preparedStmt = null;
	private Connection conn = null;
	private Statement stmt 	= null;
	private ResultSet rs	= null;*/
	TokenServiceImp tokService=new TokenServiceImp();
	GetDBConnection getConn = new GetDBConnection();
	ObjectMapper objectMapper = new ObjectMapper();
	
	public ObjectNode getPendingApproveList(int userid) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
					
		/*	String sql="    SELECT  inst.id AS id, inst.JobId AS JobId, cm.CustName AS CustomerName, um.Emp_Name AS SI, pm.Name AS ProductName,inst.Site AS Site, inst.Location AS Location, tm.TaskName as Stages, "
		+ "inst.CurrentStage as StageId,  inst.CurrentStage AS Stages,inst.Remark, isc.Status AS Status FROM installation inst "
		+ "inner join customer_master cm on inst.CustomerId = cm.CustomerID "
		+ " inner join product_master pm on inst.ProductId = pm.ProductId "
		+ "inner join user_master um on inst.SI = um.EmpId "
		+ "inner join inc_status_codes isc on inst.StatusId = isc.Id "
		+ "left join task_master tm on inst.CurrentStage = tm.TaskId  where inst.IsDeleted = 'N'";
*/
		//if TE checkout it will be show into Pending approval thats why this change is done(and union query with 9 status code as TEchecksOut) 
		String sql=" SELECT " 
		+ "inst.id AS id, POLineId as POLineId, inst.JobId AS JobId, cm.CustName AS CustomerName,inst.StatusId,inst.CurrentStatus,inst.CreatedBy, um.Emp_Name AS SI, pm.Name AS ProductName,inst.Site AS Site, "
		+ "inst.Location AS Location, tm.TaskName as Stages,inst.CurrentStage AS StageId, isc.Status AS Status ,ia.Action,ia.Remark,ia.withVisit ,ia.Claim,ia.id as UA_Id, sic.Company "
		+ "FROM installation inst "
		+ "left join installation_actions ia on inst.id=ia.IncId "
		+ "inner join customer_master cm on inst.CustomerId = cm.CustomerID "
		+ "inner join product_master pm on inst.ProductId = pm.ProductId "
		+ "inner join user_master um on inst.SI = um.EmpId "
		+ "inner join inc_status_codes isc on inst.StatusId = isc.Id "
		+ "left join task_master tm on inst.CurrentStage = tm.TaskId "
		+ "left join si_company sic on um.SICompany = sic.Id "
		+ "where  inst.CreatedBy="+userid+" and ia.Action in (11,15) "
		+ "union "
			+ "SELECT "
			+ "inst.id AS id, POLineId as POLineId, inst.JobId AS JobId, cm.CustName AS CustomerName,inst.StatusId,inst.CurrentStatus,inst.CreatedBy, um.Emp_Name AS SI, pm.Name AS ProductName,inst.Site AS Site, "
			+ "inst.Location AS Location, tm.TaskName as Stages,inst.CurrentStage AS StageId, isc.Status AS Status ,st.Action,st.Remark,st.withVisit,st.claim,st.id as UA_Id, sic.Company "
			+ "FROM installation inst "
			+ "left join si_tejobupdates st on inst.id=st.IncId "  
			+ "inner join customer_master cm on inst.CustomerId = cm.CustomerID " 
			+ "inner join product_master pm on inst.ProductId = pm.ProductId "
			+ "inner join user_master um on inst.SI = um.EmpId "
			+ "inner join inc_status_codes isc on inst.StatusId = isc.Id " 
			+ "left join task_master tm on inst.CurrentStage = tm.TaskId  "
			+ " left join si_company sic on um.SICompany = sic.Id "
			 + "where   inst.CreatedBy="+userid+" and st.Action in (9) and st.Claim is NULL " ;
		
		// + "where   inst.CreatedBy="+userid+" and ia.Action in (11,15) and ia.Claim is NULL  ";
		//status 10 removed from above filter -old was ia.Action in (10,11,15)
		//claim condition removed as for approved they want to show in list now with no buttons
				
		//below part commented as It should only be reflected in pending approval if Additional Day is requested or Job is completed .
		//Only if Details is selected all updates sequentially must be seen with Time Stamp
		 
	/*	 + "union "
		+ "SELECT "
		+ "inst.id AS id, POLineId as POLineId, inst.JobId AS JobId, cm.CustName AS CustomerName,inst.StatusId,inst.CurrentStatus,inst.CreatedBy, um.Emp_Name AS SI, pm.Name AS ProductName,inst.Site AS Site, "
		+ "inst.Location AS Location, tm.TaskName as Stages,inst.CurrentStage AS StageId, isc.Status AS Status ,st.Action,st.Remark,st.withVisit,st.claim,st.id as UA_Id, sic.Company "
		+ "FROM installation inst "
		+ "left join si_tejobupdates st on inst.id=st.IncId "  
		+ "inner join customer_master cm on inst.CustomerId = cm.CustomerID " 
		+ "inner join product_master pm on inst.ProductId = pm.ProductId "
		+ "inner join user_master um on inst.SI = um.EmpId "
		+ "inner join inc_status_codes isc on inst.StatusId = isc.Id " 
		+ "left join task_master tm on inst.CurrentStage = tm.TaskId  "
		+ " left join si_company sic on um.SICompany = sic.Id "
		 + "where   inst.CreatedBy="+userid+" and st.Action in (6,7,8,9) and st.Claim is NULL ";*/
		
		
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql PendingApprovalDaoImp----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("id",rs.getString("id"));
					objectInNode.put("JobId",rs.getString("JobId"));
					objectInNode.put("Remark",rs.getString("Remark"));
					objectInNode.put("CustomerName",rs.getString("CustomerName"));
					objectInNode.put("SI",rs.getString("SI"));
					objectInNode.put("ProductName",rs.getString("ProductName"));
					objectInNode.put("Site",rs.getString("Site"));
					objectInNode.put("Status",rs.getString("Status"));
					objectInNode.put("Stages",rs.getString("Stages"));
					objectInNode.put("Location",rs.getString("Location"));
					objectInNode.put("StageId",rs.getString("StageId"));
					
					objectInNode.put("UA_Id",rs.getString("UA_Id"));
					objectInNode.put("withVisit",rs.getString("withVisit"));
					objectInNode.put("Claim",rs.getString("Claim"));
					objectInNode.put("POLineId",rs.getString("POLineId"));
					
					
					objectInNode.put("Company",rs.getString("Company"));
					
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
		
		return objectOutNode;
	}

	@Override
	public ObjectNode insertIncClaimData(PendingApproval pendingApproval) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		boolean flag = false;
		String strAuth="Authorised";
		String userId = ""; 
			
			Connection conn = null;
			PreparedStatement pStmt = null;
			conn=null;
			
			try{
				String strauth= pendingApproval.getAuthKey();
				if(strauth!=null)
				{
				userId=tokService.getUserIdFromToken(strauth);
				}
				System.out.println("strauth key userId 111== "+userId);
				if(tokService.isTokenValid(strauth))
				{
				conn = GetDBConnection.getConnection();
				
				String SQL_QUERY = "insert into inc_claim_data(IncId, StageId,VisitInc,Remark,CreatedDate) values(?, ?, ?,?,NOW())";
				
				System.out.println("QUERY For insertIncClaimData== "+SQL_QUERY);
				
				pStmt = conn.prepareStatement(SQL_QUERY,Statement.RETURN_GENERATED_KEYS);

				pStmt.setInt(1, pendingApproval.getIncId());
				pStmt.setInt(2, pendingApproval.getStageId());
				pStmt.setString(3, pendingApproval.getVisitInc());
				pStmt.setString(4, pendingApproval.getRemark());
				
				pStmt.executeUpdate();
				
				flag = true;
				
				System.out.println("QUERY For pendingApproval.getuA_Id()== "+pendingApproval.getuA_Id());
				System.out.println("QUERY For pendingApproval.getWithVisit()== "+pendingApproval.getWithVisit());
				
				String uA_Id = pendingApproval.getuA_Id();
				String withVisit = pendingApproval.getWithVisit();
				
				System.out.println("uA_Id== "+uA_Id);
				System.out.println("withVisit= "+withVisit);
				
				
				String teclaimdata = updateTEClaimData(uA_Id,withVisit);
				
			}else
			{
				strAuth="UnAuthorised";
			}

			objectOutNode.put("result",flag);
			objectOutNode.put("message",strAuth);
			
			}catch (Exception e) { 
				// TODO: handle exception
				e.printStackTrace();
			}
			finally {
				GetDBConnection.closePstmt(pStmt);
				GetDBConnection.closeConn(conn);
			}
			// TODO Auto-generated method stub
			return objectOutNode;
	}

	private String updateTEClaimData(String uA_Id, String withVisit) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		
		String flag = "false";
		Connection conn = null;
		PreparedStatement pStmt = null;
		String SQL_QUERY;
		
		System.out.println("updateTEClaimData==  "+uA_Id);
		System.out.println("updateTEClaimData===  "+withVisit);
		

		try {
			conn = GetDBConnection.getConnection();
			
			System.out.println("updateTEClaimData===  "+withVisit);
			
			
			//if(withVisit.equals("null")) {
			if(withVisit.equals("null")) { 
				System.out.println("aaaaa===  "+withVisit);

				
				SQL_QUERY = "update installation_actions set Claim = 'Y' where id = "+uA_Id ;
					
						
			}else {
				
				System.out.println("bbbb===  "+withVisit);

				 SQL_QUERY = "update si_tejobupdates set Claim = 'Y' where id = " +uA_Id ;

			}
			
			System.out.println("QUERY For installation_actions==  "+SQL_QUERY);
			pStmt = conn.prepareStatement(SQL_QUERY);
			
			pStmt.executeUpdate();
			
			flag = "true";
	
		}catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			GetDBConnection.closePstmt(pStmt);
			GetDBConnection.closeConn(conn);
		}
		return flag;
		
	}

	@Override
	public ObjectNode showClaimDataByInstId(int instId) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		
		String result="error";
		String visitIncVal ="";
		System.out.println("instId======"+instId);
		
		String sql="   SELECT  inst.id AS id, inst.Site, inst.JobId AS JobId, cm.CustName AS CustomerName, um.Emp_Name AS SI, pm.Name AS ProductName,tm.TaskName as Stages,  inst.CurrentStage AS Stages, icd.VisitInc  FROM installation inst " + 
				"inner join customer_master cm on inst.CustomerId = cm.CustomerID  " + 
				"inner join product_master pm on inst.ProductId = pm.ProductId " + 
				"inner join user_master um on inst.SI = um.EmpId " + 
				"inner join inc_status_codes isc on inst.StatusId = isc.Id " + 
				"left join inc_claim_data icd on inst.id = icd.IncId " + 
				"left join task_master tm on inst.CurrentStage = tm.TaskId  where inst.IsDeleted = 'N' and  inst.id = "+instId;
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql PendingApprovalDaoImp----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("id",rs.getString("id"));
					objectInNode.put("JobId",rs.getString("JobId"));
					objectInNode.put("CustomerName",rs.getString("CustomerName"));
					objectInNode.put("SI",rs.getString("SI"));
					objectInNode.put("ProductName",rs.getString("ProductName"));
					objectInNode.put("Stages",rs.getString("Stages"));
					objectInNode.put("Site",rs.getString("Site"));
					
					visitIncVal = rs.getString("VisitInc");
					if(visitIncVal==null)
						visitIncVal="";
					
					objectInNode.put("VisitInc",visitIncVal);
					
					arrayOutNode.add(objectInNode);
				}
				objectOutNode.put("data", arrayOutNode.toString());
				result= "success";
				
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
		
		return objectOutNode;
	}

	@Override
	public ObjectNode getStatusListbyInstId(int instId) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		
		String result="error";
		System.out.println("instId======"+instId);
		
		String sql=" select TM.TaskName,IT.TaskCompletionDate from installation_task IT ,task_master TM where IT.TaskId=TM.TaskId and IT.IncId = "+instId;
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql PendingApprovalDaoImp----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("TaskName",rs.getString("TaskName"));
					objectInNode.put("TaskCompletionDate",rs.getString("TaskCompletionDate"));
					
					
					arrayOutNode.add(objectInNode);
				}
				objectOutNode.put("data", arrayOutNode.toString());
				result= "success";
				
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
			//objectOutNode.put("result", arrayOutNode);
		
		return objectOutNode;
	}

	@Override
	public ObjectNode saveRejectRemark(PendingApproval pendingApproval) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		String strAuth="Authorised";
		String teclaimREgdata;
		PreparedStatement pStmt = null;
		Connection conn 	= null;
		
		boolean flag = false;
		try
		{
			String strauth= pendingApproval.getAuthKey();
			
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			
			System.out.println("QUERY For saveRejectRemark=  "+pendingApproval.getIncId());
			
			//String SQL_QUERY = "update inc_claim_data set VisitInc = 'Rejected' , Remark = '"+pendingApproval.getRemark() +"' where IncId = "+pendingApproval.getIncId() ;
			
			String SQL_QUERY = "insert into inc_claim_data(IncId, VisitInc,Remark,CreatedDate) values(?, ?, ?, NOW())";
			
			System.out.println("QUERY For saveRejectRemark== "+SQL_QUERY);
			
			pStmt = conn.prepareStatement(SQL_QUERY,Statement.RETURN_GENERATED_KEYS);

			pStmt.setInt(1, pendingApproval.getIncId());
			pStmt.setString(2, "Rejected");
			pStmt.setString(3, pendingApproval.getRemark());
			
			
			pStmt.executeUpdate();
		
			teclaimREgdata = rejectClaimData(pendingApproval.getuA_Id(),pendingApproval.getWithVisit());
			
			flag=true;
			
			
			}else
			{
				strAuth="UnAuthorised";
			}

			objectOutNode.put("result",flag);
			objectOutNode.put("message",strAuth);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			GetDBConnection.closeStmt(pStmt);
			GetDBConnection.closeConn(conn);
		}
		
		
		// TODO Auto-generated method stub
		return objectOutNode;
	}

	private String rejectClaimData(String uA_Id, String withVisit) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		
		String flag = "false";
		Connection conn = null;
		PreparedStatement pStmt = null;
		String SQL_QUERY;
		
		System.out.println("updateTEClaimData==  "+uA_Id);
		System.out.println("updateTEClaimData===  "+withVisit);
		

		try {
			conn = GetDBConnection.getConnection();
			
			if(withVisit.equals("null")) {
				
				 SQL_QUERY = "update installation_actions set Claim = 'R' where id = "+uA_Id ;
					
						
			}else {
				
				 SQL_QUERY = "update si_tejobupdates set Claim = 'R' where id = " +uA_Id ;
			}
			
			
			System.out.println("QUERY For installation_actions==  "+SQL_QUERY);
			pStmt = conn.prepareStatement(SQL_QUERY);
			
			pStmt.executeUpdate();
			
			flag = "true";
	
		}catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			GetDBConnection.closePstmt(pStmt);
			GetDBConnection.closeConn(conn);
		}
		return flag;
		
	}

	@Override
	public ObjectNode releasePOQty(PendingApproval pendingApproval) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		boolean flag = false;
		String result="error";
		Statement stmt 	= null;
		ResultSet rs	= null;
		Connection conn = null;
		PreparedStatement preparedStmt = null , preparedStmt1 = null;
		conn=null;
		String balcqty = " ", poId = "";
		String SQL_QUERY,  SQL_QUERY1;
		
		try
		{
			 SQL_QUERY = "select * from purchaseorder po left join installation inc on inc.POLineId = po.Id where inc.id  = "+pendingApproval.getIncId();
			
			 System.out.println("QUERY For releasePOQty====  "+SQL_QUERY);
			
			 conn = GetDBConnection.getConnection();
			 stmt = conn.createStatement();
			
			 rs = stmt.executeQuery(SQL_QUERY);
			
			 while(rs.next()){
				
			 balcqty  = rs.getString("Balace_Qty");	 
			 poId  = rs.getString("Id");	 
			 System.out.println("----sql --balcqty-balcqty-----"+balcqty);
				
			 }
			
			 SQL_QUERY = "update purchaseorder set Balace_Qty = "+balcqty+" + 1 where Id = "+poId;
			 
			 SQL_QUERY1 = "update installation set POLineId = NULL where id = "+pendingApproval.getIncId()+" and POLineId = "+poId;  
			 
			
			 System.out.println("QUERY For purchaseorder====  "+SQL_QUERY);
			 
			 System.out.println("QUERY For purchaseorder  SQL_QUERY1====  "+ SQL_QUERY1);
			
			 preparedStmt = conn.prepareStatement(SQL_QUERY);
			 
			 preparedStmt1 = conn.prepareStatement(SQL_QUERY1);
				
			 preparedStmt.executeUpdate();
			 preparedStmt1.executeUpdate();
				
			 flag=true;
			 			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		
		// TODO Auto-generated method stub
		objectOutNode.put("result", flag);
		objectOutNode.put("data", arrayOutNode);
	
	return objectOutNode;
	}


	
	
	
	
}
