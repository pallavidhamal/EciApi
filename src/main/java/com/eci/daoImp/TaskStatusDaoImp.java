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
import com.eci.bean.Task;
import com.eci.bean.TaskStatus;
import com.eci.dao.TaskStatusDao;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class TaskStatusDaoImp implements TaskStatusDao {
	GetDBConnection getConn = new GetDBConnection();
	ObjectMapper objectMapper = new ObjectMapper();
	TokenServiceImp tokService=new TokenServiceImp();
	public ObjectNode getTaskStatusList(int userid) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
				
			
		/*
		 * String
		 * sql="    SELECT  inst.id AS id,concat(po.PO_Number,'-',po.LineId) POLineId,  inst.JobId AS JobId, cm.CustName AS CustomerName, um.Emp_Name AS SI, sic.Company,"
		 * +
		 * " pm.Name AS ProductName,inst.Site AS Site, inst.Location AS Location, inst.Purpose as Purpose , tm.TaskName as Stages, "
		 * +
		 * " inst.Remark, isc.Status AS Status , inst.CreatedDate as CreatedDate FROM     installation inst "
		 * + " inner join customer_master cm on inst.CustomerId = cm.CustomerID " +
		 * " inner join product_master pm on inst.ProductId = pm.ProductId " +
		 * " inner join user_master um on inst.SI = um.EmpId " +
		 * " inner join inc_status_codes isc on inst.StatusId = isc.Id " +
		 * " left join task_master tm on inst.CurrentStage = tm.TaskId " +
		 * " left join purchaseorder po on inst.POLineId = po.Id  " +
		 * " left join si_company sic on um.SICompany = sic.Id" +
		 * " where inst.IsDeleted = 'N' and inst.CreatedBy = "+userid;
		 */
		
// update on 21-10-2020
		
		String sql="    SELECT  inst.id AS id,concat(po.PO_Number,'-',po.LineId) POLineId,  inst.JobId AS JobId, cm.CustName AS CustomerName, um.Emp_Name AS SI, sic.Company,"
				+ " pm.Name AS ProductName,inst.Site AS Site, inst.Location AS Location, inst.Purpose as Purpose , tm.TaskName as Stages, "
				+ " inst.Remark, isc.Status AS Status , inst.CreatedDate as CreatedDate , pm_um.Emp_Name as pm_name ,RM.RegionName as region FROM     installation inst "
				+ " inner join customer_master cm on inst.CustomerId = cm.CustomerID "
				+ " inner join product_master pm on inst.ProductId = pm.ProductId "
				+ " inner join user_master um on inst.SI = um.EmpId "
				+ " inner join inc_status_codes isc on inst.StatusId = isc.Id "
				+ " left join task_master tm on inst.CurrentStage = tm.TaskId "
				+ " left join purchaseorder po on inst.POLineId = po.Id  "
				+ " left join si_company sic on um.SICompany = sic.Id "
				+ " left join user_master pm_um on inst.CreatedBy = pm_um.EmpId "
				+ " left join region_master RM on inst.RegionId = RM.RegionId " 
				+ " where inst.IsDeleted = 'N' and inst.CreatedBy = "+userid;
		
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql Task status list----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("id",rs.getString("id"));
					objectInNode.put("POLineId",rs.getString("POLineId"));
					objectInNode.put("JobId",rs.getString("JobId"));
					objectInNode.put("Remark",rs.getString("Remark"));
					objectInNode.put("CustomerName",rs.getString("CustomerName"));
					objectInNode.put("SI",rs.getString("SI"));
					objectInNode.put("ProductName",rs.getString("ProductName"));
					objectInNode.put("Site",rs.getString("Site"));
					objectInNode.put("Purpose",rs.getString("Purpose"));
					objectInNode.put("Status",rs.getString("Status"));
					objectInNode.put("Stages",rs.getString("Stages"));
					objectInNode.put("Location",rs.getString("Location"));
					objectInNode.put("Company",rs.getString("Company"));
					objectInNode.put("AllocDt",rs.getString("CreatedDate"));
					
					objectInNode.put("RegionName",rs.getString("region"));
					
					String CompleteDt=getJobCompletionDate(rs.getString("id"));
					
					objectInNode.put("CompleteDt",CompleteDt);
					
					objectInNode.put("pm_name",rs.getString("pm_name"));
					
					
					
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
	
	
	
	private String getJobCompletionDate(String jobId) {
		// TODO Auto-generated method stub
		String actionDate = "";
		String sql = "SELECT ActionDate FROM eci.installation_actions where Action =15 and IncId = " + jobId ;
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		try
		{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			
			//System.out.println("----sql getJobCompletionDate----"+sql);
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				actionDate = rs.getString("ActionDate");
				
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
	
		return actionDate;
		
	}
	

	@Override
	public ObjectNode findPoListByNo(String poNum) {
		// TODO Auto-generated method stub
		String result="error";
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		
		String SQL_QUERY = "SELECT * from purchaseorder PO,region_master RM where  PO.RegionId=RM.RegionId and  PO.IsDeleted = 'N' and CURDATE() between PO.PO_Date and PO.PO_EndDate and  PO.PO_Number = '" +poNum+ "'  " ;
		
		System.out.println("----SQL_QUERY--findPoListByNo--"+SQL_QUERY);
		
		try
		{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(SQL_QUERY);
			
			while(rs.next()){
				ObjectNode objectInNode = objectMapper.createObjectNode();

				objectInNode.put("Id",rs.getString("Id"));
				objectInNode.put("PO_Number",rs.getString("PO_Number"));
				objectInNode.put("LineId",rs.getString("LineId"));
				objectInNode.put("PO_Qty",rs.getString("PO_Qty"));
				objectInNode.put("RegionName",rs.getString("RegionName"));
					
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
		objectOutNode.put("result", result);
		
	return objectOutNode;
	}



	@Override
	public ObjectNode assignPo(Task task) {
		// TODO Auto-generated method stub
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		PreparedStatement pStmt = null;
		PreparedStatement pStmt1 = null;

		Connection conn         = null;
		String userId = ""; 

		String strAuth="Authorised";
		boolean flag = false;
		try
		{
			
			String strauth= task.getAuthKey();
			
			if(strauth!=null)
			{
			userId=tokService.getUserIdFromToken(strauth);
			}
			System.out.println("strauth key userId == "+userId);
			
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			
			System.out.println("instMulId getId = "+task.getInstid());
			
			String instMulId = task.getInstid() ;
			
			/*System.out.println("instMulId = "+instMulId);
			*/
			String[] instMulIdArr = instMulId.split(",");
			
			for(int i = 0; i < instMulIdArr.length ; i++ ){
				
				System.out.println("QUERY For updateReassignSI i [] ==  "+instMulIdArr[i]);
				
				System.out.println("QUERY For assignPo=  po line id=  "+task.getpOLineId() );
				
				String SQL_QUERY = "update installation set POLineId = ? , UpdatedDate = now() , UpdateBY = '"+userId+"'  where Id= "+instMulIdArr[i] ; ;
				
				System.out.println("QUERY For assignPo==  "+SQL_QUERY);
				
				 pStmt = conn.prepareStatement(SQL_QUERY);
				
				pStmt.setInt(1, task.getpOLineId());

				pStmt.executeUpdate();
				
				
				String SQL_QUERY1 = "UPDATE installation SET StatusId= CASE WHEN StatusId=14 THEN 1 ELSE StatusId END where id= "+instMulIdArr[i] ; 
				
				System.out.println("QUERY For assignPo to update status==  "+SQL_QUERY1);
				
				 pStmt1 = conn.prepareStatement(SQL_QUERY1);
				
				
				pStmt1.executeUpdate();
				
				//UPDATE eci.installation SET StatusId= CASE WHEN StatusId=14 THEN 1 ELSE StatusId END where id=1587
				
				
				
				
			}
				
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
			GetDBConnection.closeConn(conn);
			GetDBConnection.closePstmt(pStmt);
			GetDBConnection.closePstmt(pStmt1);

		}
		// TODO Auto-generated method stub
		return objectOutNode;
	}



	@Override
	public ObjectNode saveReopenJob(Task task) {
		// TODO Auto-generated method stub
		String userId = ""; 

		String strAuth="Authorised";

		ObjectNode objectOutNode =objectMapper.createObjectNode();
		
		System.out.println("QUERY For assignPo==  "+task.getInstid());
		
		String tID = "";
		PreparedStatement pStmt = null;
		boolean flag = false, taskFlag = false,  taskactionFlag = false , inctaskwiseatpval = false ;
		Connection conn = null;
		try{
			
			System.out.println("getSiId getSiId===== "+task.getSiId());
			
			String strauth= task.getAuthKey();
			
			String incID= task.getInstid() ;
			int siId = task.getSiId() ; 
			if(strauth!=null)
			{
			userId=tokService.getUserIdFromToken(strauth);
			}
			System.out.println("strauth key userId == "+userId);
			
			List<Task> t = task.getTask1();
			
			System.out.println("saveReopenJob taskid===== "+t);
			
			
			conn = GetDBConnection.getConnection();
			String SQL_QUERY = "INSERT INTO installation_task (IncId , TaskId , SI, CreatedBy, CreatedDate ) VALUES ("+incID+" , ? ,"+task.getSiId()+", "+userId+" , now())";
			
			System.out.println("QUERY For insertInstallation Task "+SQL_QUERY);
			
			pStmt = conn.prepareStatement(SQL_QUERY);
			
			 for(Task tasklist : t ) 
			 {
				 
				 tID = tasklist.getTask();
				pStmt.setInt(1, Integer.parseInt(tasklist.getTask()));
				pStmt.executeUpdate();
				
				System.out.println("QUERY For tasklist.getTask() "+tasklist.getTask());
				
			 }
			 flag=true;
			 
			 taskactionFlag	= insertInstallationAction(incID , userId);
			 
			 taskFlag		= updateInstallation(incID, userId, siId);
			 
			 inctaskwiseatpval		= insertInstallationAtpTestValues(incID, userId, tID);
			 
			 
			 
			 objectOutNode.put("result",flag);
			 
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return objectOutNode;
	}

	
	private boolean insertInstallationAtpTestValues(String incID, String userId, String tID) {
		// TODO Auto-generated method stub
		boolean flag = false;
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs;
		int rs1;
		
		String ProductId = "", CustomerId="", SQL_QUERY ="";
		try{
		
			conn = GetDBConnection.getConnection();
			
			SQL_QUERY =  "SELECT ProductId, CustomerId FROM eci.installation where id =" +incID ;

			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL_QUERY);
			
			while(rs.next()){
				
				ProductId = rs.getString("ProductId");
				CustomerId = rs.getString("CustomerId");
				
			}
			
			 SQL_QUERY = "INSERT INTO inctaskwiseatp (IncId,	TaskId, TestId ,TestType,Status) SELECT "+incID+", "+tID+ ", at.TestId ,at.TestType , 'N' FROM atp_tests at, atp_tests_product atp " + 
							"	WHERE at.TestId = atp.TestId AND atp.ProductId = " +ProductId+
							"   AND atp.CustomerId = " +CustomerId+" AND at.TaskId = " +tID;
		
			System.out.println("QUERY For inctaskwiseatp "+SQL_QUERY);
			
			stmt = conn.createStatement();
			
			rs1 = stmt.executeUpdate(SQL_QUERY);
					
			flag = true;
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closeConn(conn);
		}
		
		
		// TODO Auto-generated method stub
		return flag;
	}



	private boolean updateInstallation(String incID , String userId, int siId) {
		// TODO Auto-generated method stub
		
		PreparedStatement pStmt = null;		   
		boolean flag = false;
		Connection conn = null;
		
		
		String SQL_QUERY = "Update installation set  StatusId = 17 ,   UpdateBY = '"+userId+"', SI = '"+siId+"'  UpdatedDate = now() where id =  "+incID ;

		try
		{
				conn = GetDBConnection.getConnection();
		
				System.out.println("----sql insertInstallationAction----"+SQL_QUERY);
		
				pStmt = conn.prepareStatement(SQL_QUERY);
		
				//pStmt.setInt(1, generatedId);
				pStmt.executeUpdate();
			
				flag=true;
	
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		return flag;
	}



	private boolean insertInstallationAction(String incID, String userId) {
		// TODO Auto-generated method stub
			PreparedStatement pStmt = null;		   
			boolean flag = false;
			
			String sql = " INSERT INTO installation_actions (IncId , Action , ActionDate , ActionBy ) VALUES("+incID+", 17 ,  Now(), "+userId+") " ;
			Connection conn = null;
	
			try
			{
					conn = GetDBConnection.getConnection();
			
					System.out.println("----sql insertInstallationAction----"+sql);
			
					pStmt = conn.prepareStatement(sql);
			
					//pStmt.setInt(1, generatedId);
					pStmt.executeUpdate();
				
					flag=true;
		
			}catch (Exception e) {
				// TODO: handle exception
		e.printStackTrace();
			}
			
			// TODO Auto-generated method stub
			return flag;
	}



	@Override
	public ObjectNode getTaskFrmAction(int userid) {
		// TODO Auto-generated method stub
		 Statement stmt 	= null;
		 ResultSet rs		= null;
		 Connection conn = null;
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		//String sql = "Select * from task_master where IsDeleted = 'N' ";
		String sql = "SELECT * FROM eci.task_master TM left join eci.installation_task IT on TM.TaskId = IT.TaskId where IT.IncId = "+userid ;
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql getTaskFrmAction----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("TaskId", rs.getString("TaskId"));
					objectInNode.put("TaskName", rs.getString("TaskName"));
					
					arrayOutNode.add(objectInNode);
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
			
			objectOutNode.put("result", arrayOutNode);
		
		return objectOutNode;
	}
	
	
}
