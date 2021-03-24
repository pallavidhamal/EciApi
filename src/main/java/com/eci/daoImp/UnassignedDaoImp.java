package com.eci.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.eci.bean.Unassigned;
import com.eci.dao.UnassignedDao;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class UnassignedDaoImp implements UnassignedDao {

	TokenServiceImp tokService=new TokenServiceImp();
	GetDBConnection getConn = new GetDBConnection();
	ObjectMapper objectMapper = new ObjectMapper();
	
	public ObjectNode getInsToReassignList(int userid) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		ArrayNode arrTempNode = objectMapper.createArrayNode();

		String remark="",typeFlag="",recId="";
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		
		//show install with 3,4,7,9,10(with half tasks done)
		
		String sql="    SELECT  inst.id AS id, inst.JobId AS JobId, cm.CustName AS CustomerName, um.Emp_Name AS SI, pm.Name AS ProductName,"
				+ " inst.Site AS Site, inst.Location AS Location,inst.StatusId, tm.TaskName as Stages, "
				+ " inst.CurrentStage AS Stages,inst.Remark, isc.Status AS Status FROM  installation inst "
				+ "inner join customer_master cm on inst.CustomerId = cm.CustomerID "
				+ " inner join product_master pm on inst.ProductId = pm.ProductId "
				+ "inner join user_master um on inst.SI = um.EmpId "
				+ "inner join inc_status_codes isc on inst.StatusId = isc.Id "
				+ "left join task_master tm on inst.CurrentStage = tm.TaskId  where inst.IsDeleted = 'N' "
				+ "and inst.CreatedBy = "+userid+" and inst.StatusId in(3,4,7,9) or case when inst.StatusId =10 and CurrentStatus=0 "
				+ " and inst.CreatedBy = "+userid+" then  1 end ";
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql getInsToReassignList----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					remark="";
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("id",rs.getString("id"));
					objectInNode.put("JobId",rs.getString("JobId"));
					objectInNode.put("CustomerName",rs.getString("CustomerName"));
					objectInNode.put("SI",rs.getString("SI"));
					objectInNode.put("ProductName",rs.getString("ProductName"));
					objectInNode.put("Site",rs.getString("Site"));
					objectInNode.put("Status",rs.getString("Status"));
					objectInNode.put("Stages",rs.getString("Stages"));
					objectInNode.put("Location",rs.getString("Location"));
					
					arrTempNode=getLatestIncRemark(rs.getString("id"),rs.getString("StatusId"));
					
					if(arrTempNode.size()!=0)
					{
					remark=arrTempNode.get(0).asText();
					typeFlag=arrTempNode.get(1).asText();
					recId=arrTempNode.get(2).asText();
					}
					
					if(remark.equals(""))
					remark = rs.getString("Remark");
					
					if(remark==null)
					remark="";
						
					/*if(remark.length()>1)
						remark=remark.substring(1, remark.length()-1);
						
					if(typeFlag.length()>1)
						typeFlag=typeFlag.substring(1, typeFlag.length()-1);
					
					if(recId.length()>1)
						recId=recId.substring(1, recId.length()-1);
				*/
					
					
					objectInNode.put("Remark",remark);
					objectInNode.put("typeFlag",typeFlag);
					objectInNode.put("recId",recId);
					
					//remark.subString(1,remark.length()-1);
					
					System.out.println("----sql --remark--"+remark);
					System.out.println("----sql --typeFlag--"+typeFlag);
					System.out.println("----sql --recId--"+recId);	
					
								
					
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
	public ObjectNode getAssignSIList() {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;	
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		String sql = " SELECT EmpId, Emp_Name, RoleType FROM user_master where Role = '1' " ;
			
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql getInstallationList----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("EmpId",rs.getString("EmpId"));
					objectInNode.put("Emp_Name",rs.getString("Emp_Name"));
					objectInNode.put("RoleType",rs.getString("RoleType"));
					
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
	public ObjectNode updateReassignremark(Unassigned unassigned) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		boolean flag = false;
		String strAuth="Authorised";
		String userId = "",TypeFlag="", sql=""; 
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		
		try
		{
			String strauth= unassigned.getAuthKey();
			
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			String SQL_QUERY = "UPDATE installation SET Remark  = '"+unassigned.getRemark()+"'  where  id = " +unassigned.getId() ;
			
			System.out.println("QUERY For getRemark==  "+unassigned.getRemark());
			System.out.println("QUERY For getRecId==  "+unassigned.getRecId());
			
			System.out.println("QUERY For unassigned==  "+SQL_QUERY);
			
			preparedStmt = conn.prepareStatement(SQL_QUERY);
			
			preparedStmt.executeUpdate();
			
			TypeFlag = unassigned.getTypeFlag();
			
			System.out.println("QUERY For TypeFlagTypeFlagTypeFlagTypeFlagTypeFlag==  "+TypeFlag);
			
			if(TypeFlag!=null && TypeFlag!="" ) {
				
				if(TypeFlag.equalsIgnoreCase("SI")) {
					
					System.out.println("SI SI SI==  ");
					sql = "update si_tejobupdates set Remark = '"+unassigned.getRemark()+"'  where id = " +unassigned.getRecId() ;
					
				}else if(TypeFlag.equalsIgnoreCase("IA")) {
					
					
					sql = "update installation_actions set Remark = '"+unassigned.getRemark()+"' where id = " +unassigned.getRecId() ;
				}
				
				System.out.println("QUERY For updateremark==SQL_QUERY:  "+sql);
				
				preparedStmt = conn.prepareStatement(sql);
				
				preparedStmt.executeUpdate();
				
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
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		
		// TODO Auto-generated method stub
		return objectOutNode;
	}


	@Override
	public ObjectNode updateReassignSI(Unassigned unassigned) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		boolean flag = false;
		String strAuth="Authorised";
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		try
		{
			String strauth= unassigned.getAuthKey();
			
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			String instMulId = unassigned.getInstid();
			
			String[] instMulIdArr = instMulId.split(",");
			
			
			for(int i = 0; i < instMulIdArr.length ; i++ ){
				
			System.out.println("QUERY For updateReassignSI i [] ==  "+instMulIdArr[i]);
				
			String SQL_QUERY = "update installation set SI = ? , StatusId = '1' where id =  "+instMulIdArr[i];
			
			System.out.println("QUERY For getRemark==  "+unassigned.getSiId());
			
			System.out.println("QUERY For updateremark==  "+SQL_QUERY);
			
			preparedStmt = conn.prepareStatement(SQL_QUERY);
			
			preparedStmt.setInt(1, unassigned.getSiId());
			

			preparedStmt.executeUpdate();
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
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		
		// TODO Auto-generated method stub
		return objectOutNode;
	}


	@Override
	public ObjectNode getInsApprovalList(int userid) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
				
		//created by user and for whom no po line id assigned
		String sql="    SELECT  inst.id AS id, inst.JobId AS JobId, cm.CustName AS CustomerName, um.Emp_Name AS SI, pm.Name AS ProductName,inst.Site AS Site, inst.Location AS Location, tm.TaskName as Stages, "
				+ " inst.CurrentStage AS Stages,inst.StatusId,inst.Remark, isc.Status AS Status FROM installation inst "
				+ "inner join customer_master cm on inst.CustomerId = cm.CustomerID "
				+ " inner join product_master pm on inst.ProductId = pm.ProductId "
				+ "inner join user_master um on inst.SI = um.EmpId "
				+ "inner join inc_status_codes isc on inst.StatusId = isc.Id "
				+ "left join task_master tm on inst.CurrentStage = tm.TaskId  where inst.IsDeleted = 'N' and inst.CreatedBy = "+userid +" and inst.StatusId in(14) ";
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql getInstallationList----"+sql);
				
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
	public ObjectNode seekApproval(Unassigned unassigned) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		boolean flag = false;
		String strAuth="Authorised";
		
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		try
		{
			String strauth= unassigned.getAuthKey();
			
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			String instId = unassigned.getInstid();
			
			String[] instIdArr = instId.split(",");
			
			
			for(int i = 0; i < instIdArr.length ; i++ ){
				
			System.out.println("QUERY For seekApproval i [] ==  "+instIdArr[i]);
				
			String SQL_QUERY = "update installation set StatusId = 12 where id = "+instIdArr[i];
			
			System.out.println("QUERY For seekApproval==  "+SQL_QUERY);
			
			preparedStmt = conn.prepareStatement(SQL_QUERY);
			
			preparedStmt.executeUpdate();
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
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		
		// TODO Auto-generated method stub
		return objectOutNode;
	}

	@Override
	public ArrayNode getLatestIncRemark(String strJobId,String statusId) {
		// TODO Auto-generated method stub
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;
		ArrayNode objectOutNode =objectMapper.createArrayNode();

		int i = 0;
		String sql ="",strRmrk="",flg="",id="";
	//reamaing for status 10 i.e te exits			
		
		if((statusId.equals("3"))||(statusId.equals("4"))||(statusId.equals("10")))
		{
		
		sql = "select ia.id,ia.Action,ia.Remark,ia.ActionDate as ADate, 'IA' as flag from installation_actions ia where ia.IncId ="+strJobId +" and "
				+ " action="+statusId +" and ia.Remark is not null order by ADate desc limit 1" ;
				
		}
		else if((statusId.equals("7"))||(statusId.equals("9")))
		{
			sql = "SELECT ste.id,ste.Action,ste.Remark,ste.Date as ADate, 'SI' as flag FROM si_tejobupdates ste where ste.IncId = "+strJobId +" and action="+statusId +
					 " and ste.Remark is not null order by ADate desc limit 1";
		}
		
		
		
				try
				{
						conn = GetDBConnection.getConnection();
						stmt = conn.createStatement(); 
						System.out.println("----getLatestIncRemark----"+sql);
						rs = stmt.executeQuery(sql);
						while(rs.next())
						{
							strRmrk=rs.getString("Remark");
							flg=rs.getString("flag");
							id=rs.getString("id");
							
							objectOutNode.add(strRmrk);
							objectOutNode.add(flg);
							objectOutNode.add(id);
							
						}
						
						System.out.println("----getLatestIncRemark----"+objectOutNode);
						
						
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
				
				return objectOutNode;
				
	}
	

}
