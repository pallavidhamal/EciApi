package com.eci.daoImp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.eci.bean.Region;
import com.eci.dao.RegionDao;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class RegionDaoImp implements RegionDao {
	/*private PreparedStatement preparedStmt = null;
	private Connection conn = null;
	private Statement stmt 	= null;
	private ResultSet rs	= null;*/
	TokenServiceImp tokService=new TokenServiceImp();
	GetDBConnection getConn = new GetDBConnection();
	ObjectMapper objectMapper = new ObjectMapper();
	
	public ObjectNode getRegions() {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();

		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		
		
		//String sql = "Select * from region_master where IsDeleted = 'N' ";
		String sql = "Select * from region_master ";
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql getAllPorders----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("RegionId", rs.getString("RegionId"));
					objectInNode.put("IsDeleted", rs.getString("IsDeleted"));
					objectInNode.put("RegionName", rs.getString("RegionName"));
					
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
	public ObjectNode getRegionDetail(int id) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		String result="error";
		
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		
		String sql = "SELECT * FROM region_master rm where RegionId = "+id;
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();

					objectInNode.put("RegionId",rs.getString("RegionId"));
					objectInNode.put("RegionName",rs.getString("RegionName"));
					objectInNode.put("Description",rs.getString("Description"));
					
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
	public ObjectNode insertRegion(Region region) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		Connection conn 		= null;
		PreparedStatement preparedStmt = null;
		
		boolean flag = false;
		
		String strAuth="Authorised", userId;
		
		try{
			
			String strauth = region.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			
			String SQL_QUERY = "INSERT INTO region_master(RegionName,CreatedBy,CreatedDate,IsDeleted) "+
					" VALUES (?,'"+userId+"',NOW(),'N')";
			
			System.out.println("QUERY For insertRegion "+SQL_QUERY);
			
			preparedStmt = conn.prepareStatement(SQL_QUERY);

			preparedStmt.setString(1, region.getRegion()); 
			//preparedStmt.setString(2, region.getDesc());
			
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
			GetDBConnection.closeConn(conn);
			GetDBConnection.closePstmt(preparedStmt);
		}
		// TODO Auto-generated method stub
		return objectOutNode;
		
	}

	@Override
	public ObjectNode updateRegion(Region region) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		boolean flag = false;
		
		Statement stmt 	= null;
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		
		String strAuth="Authorised", userId;
		
		try
		{
			String strauth= region.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			
			String query = "Update region_master set  RegionName = ? ,"
					+ " UpdatedBy = '"+userId+"' , UpdatedDate = NOW() where RegionId = " + region.getId();
			
			System.out.println("updateCustomer query"+query);
			
			preparedStmt = conn.prepareStatement(query);
	    
		    preparedStmt.setString(1, region.getRegion());
		    //preparedStmt.setString(2, region.getDesc());

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
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
			GetDBConnection.closeStmt(stmt);
		}
	return objectOutNode;
	}
	
	
	public ObjectNode inactivateRegion(Region region) {
		// TODO Auto-generated method stub
		boolean flag = false;
		Statement stmt 	= null;
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		String userId;
		String strAuth="Authorised";
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		
		try
		{
			String strauth= region.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			String query = "Update region_master set  IsDeleted = 'Y' ,"
					+ " UpdatedBy ='"+userId+"'  , UpdatedDate = NOW() where RegionId = " + region.getId();
			
			System.out.println("inactivateRegion query"+query);
			
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
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
			GetDBConnection.closeStmt(stmt);
		}
		return objectOutNode;
	}


	@Override
	public ObjectNode activateRegion(Region region) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				boolean flag = false;
				Statement stmt 	= null;
				Connection conn = null;
				PreparedStatement preparedStmt = null;
				String userId;
				String strAuth="Authorised";
				ObjectNode objectOutNode =objectMapper.createObjectNode();
				
				try
				{
					String strauth= region.getAuthKey();
					userId = tokService.getUserIdFromToken(strauth);
					if(tokService.isTokenValid(strauth))
					{
					conn = GetDBConnection.getConnection();
					stmt = conn.createStatement();
					String query = "Update region_master set  IsDeleted = 'N' ,"
							+ " UpdatedBy = '"+userId+"'  , UpdatedDate = NOW() where RegionId = " + region.getId();
					
					System.out.println("activateRegion query"+query);
					
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
					GetDBConnection.closePstmt(preparedStmt);
					GetDBConnection.closeConn(conn);
					GetDBConnection.closeStmt(stmt);
				}
				return objectOutNode;
	}
}
