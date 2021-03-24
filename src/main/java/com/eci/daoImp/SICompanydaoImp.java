package com.eci.daoImp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.stereotype.Repository;

import com.eci.bean.SICompany;
import com.eci.dao.SICompanyDao;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class SICompanydaoImp implements SICompanyDao {
	/*private PreparedStatement preparedStmt = null;
	private Connection conn = null;
	private Statement stmt 	= null;
	private ResultSet rs	= null;*/
	TokenServiceImp tokService=new TokenServiceImp();
	GetDBConnection getConn = new GetDBConnection();
	ObjectMapper objectMapper = new ObjectMapper();
	
	public ObjectNode getSICompany(int userid) {
		// TODO Auto-generated method stub
		 Connection conn 	= null;
		 Statement stmt 	= null;
		 ResultSet rs		= null;
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		//String sql = "Select * from task_master where IsDeleted = 'N' ";
		String sql = "Select * from si_company ";
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql getAllPorders----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("TaskId", rs.getString("Id"));
					objectInNode.put("TaskName", rs.getString("Company"));
					objectInNode.put("IsDeleted", rs.getString("IsDeleted"));
					
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

	@Override
	public ObjectNode getSIDetail(int id) {
		// TODO Auto-generated method stub
		 Connection conn 	= null;
		 Statement stmt 	= null;
		 ResultSet rs		= null;
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		String result="error";
		String sql = "SELECT * FROM si_company rm where Id = "+id;
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();

					objectInNode.put("TaskId",rs.getString("Id"));
					objectInNode.put("TaskName",rs.getString("Company"));
					
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
				GetDBConnection.closeStmt(stmt);
				GetDBConnection.closeRS(rs);
				GetDBConnection.closeConn(conn);
			}
			objectOutNode.put("result", result);
			
		return objectOutNode;
		
	}
	
	@Override
	public ObjectNode insertSICompany(SICompany siCmp) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		 Connection conn 	= null;
		 PreparedStatement preparedStmt = null;
		 
		boolean flag = false;
		
		String strAuth="Authorised", userId;
		
		try{
			
			String strauth =  siCmp.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			
			conn = GetDBConnection.getConnection();
			
			String SQL_QUERY = "INSERT INTO si_company(Company,CreatedBy,CreatedDate,IsDeleted) "+
					" VALUES (?,'"+userId+"',NOW(),'N')";
			
			System.out.println("QUERY For insertCompany "+SQL_QUERY);
			
			preparedStmt = conn.prepareStatement(SQL_QUERY);

			preparedStmt.setString(1, siCmp.getTask()); 
			
			preparedStmt.executeUpdate();
			flag = true;
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
	public ObjectNode updateSI(SICompany siCmp) {
		// TODO Auto-generated method stub
		boolean flag = false;
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		 Connection conn 	= null;
		 Statement stmt 	= null;
		 PreparedStatement preparedStmt = null;
		
		 String strAuth="Authorised",userId;
		 
		try
		{
			
			String strauth =  siCmp.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			
			String query = "Update si_company set  Company = ? ,"
					+ " UpdatedBy = '"+userId+"' , UpdatedDate = NOW() where Id = " +  siCmp.getId();
			
			System.out.println("updateCustomer query"+query);
			
			preparedStmt = conn.prepareStatement(query);
	    
		    preparedStmt.setString(1, siCmp.getTask());

		      // execute the java preparedstatement
		   preparedStmt.executeUpdate();
		      flag = true;
			}else
			{
				strAuth="UnAuthorised";
			}
			objectOutNode.put("result",flag);
			objectOutNode.put("message",strAuth);
			
			}
		
		catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		finally {
			
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
	return objectOutNode;
	}
	@Override
	public ObjectNode inactivateSI(SICompany siCmp) {
		// TODO Auto-generated method stub
		 boolean flag = false;
		 Connection conn 	= null;
		 Statement stmt 	= null;
		 PreparedStatement preparedStmt = null;
		 String userId;
		 String strAuth="Authorised";
		 ObjectNode objectOutNode =objectMapper.createObjectNode();
		try
		{
			String strauth=  siCmp.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			String query = "Update si_company set  IsDeleted = 'Y' ,"
					+ " UpdatedBy = '"+userId+"' , UpdatedDate = NOW() where Id = " + siCmp.getId();
			
			System.out.println("InactivateTask query = "+query);
			
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.executeUpdate();
		    flag = true;
			}else
			{
				strAuth="UnAuthorised";
			}
			objectOutNode.put("result",flag);
			objectOutNode.put("message",strAuth);
		}
		catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		finally {
			
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		return objectOutNode;
	}
	
	@Override
	public ObjectNode activateSI(SICompany siCmp) {
		// TODO Auto-generated method stub
		 boolean flag = false;
		 Connection conn 	= null;
		 Statement stmt 	= null;
		 PreparedStatement preparedStmt = null;
		 String userId;
		 String strAuth="Authorised";
		 ObjectNode objectOutNode =objectMapper.createObjectNode();
		try
		{
			String strauth= siCmp.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			String query = "Update si_company set  IsDeleted = 'N' ,"
					+ " UpdatedBy = '"+userId+"' , UpdatedDate = NOW() where Id = " +  siCmp.getId();
			System.out.println("activateTask query"+query);
			
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.executeUpdate();
		    flag = true;
			}else
			{
				strAuth="UnAuthorised";
			}
			objectOutNode.put("result",flag);
			objectOutNode.put("message",strAuth);
		}
		catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		finally {
			
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		return objectOutNode;
	}

	

}
