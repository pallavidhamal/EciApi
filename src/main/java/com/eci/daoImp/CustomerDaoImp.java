package com.eci.daoImp; 

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.eci.bean.Customer;
import com.eci.bean.Site;
import com.eci.dao.CustomerDao;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class CustomerDaoImp implements CustomerDao {
	/*private PreparedStatement preparedStmt = null;
	private Connection conn = null;
	private Statement stmt 	= null;
	private ResultSet rs	= null;*/
	TokenServiceImp tokService=new TokenServiceImp();
	GetDBConnection getConn = new GetDBConnection();
	ObjectMapper objectMapper = new ObjectMapper();
	
	public ObjectNode getCustomers() {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		 Connection conn 	= null;
		 Statement stmt 	= null;
		 ResultSet rs		= null;
		
		 String result="error";
		
		// String sql = "select * from customer_master  where IsDeleted='N'";
		 String sql = "select * from customer_master ";
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement(); 
				
				System.out.println("----sql getAllPorders----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("CustomerID", rs.getString("CustomerID"));
					objectInNode.put("Cust_Name", rs.getString("CustName"));
					objectInNode.put("IsDeleted", rs.getString("IsDeleted"));
					
					arrayOutNode.add(objectInNode);
				
				}
					objectOutNode.put("result", arrayOutNode);
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
			
			//objectOutNode.put("result", result);
		
		return objectOutNode;
	
	}
	
	///

	public ObjectNode getCustomerDetail(int id) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;
		
		String result="error";
		String sql = "SELECT * FROM customer_master where CustomerID = "+id;
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();

					objectInNode.put("CustomerID",rs.getString("CustomerID"));
					objectInNode.put("CustName",rs.getString("CustName"));
										
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
	//
	public ObjectNode updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		boolean flag = false;
		String strAuth="Authorised", userId;
		PreparedStatement preparedStmt = null;
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;
	    
			try
			{
				
				String strauth= customer.getAuthKey();
				userId = tokService.getUserIdFromToken(strauth);
				if(tokService.isTokenValid(strauth))
				{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				String query = "Update customer_master set  CustName = ? ,"
						+ " UpdatedBy = '"+userId+"' , UpdatedDate = NOW() where CustomerID = " + customer.getCustomerId();
				
				//,Site = ? ,Email = ? ,Contact = ? ,CustRegion= ? ,Address= ? ,
				
				System.out.println("updateCustomer query"+query);
				
				preparedStmt = conn.prepareStatement(query);
		    
			      preparedStmt.setString(1, customer.getCustomerName());
			            // execute the java preparedstatement
			      preparedStmt.executeUpdate();
			      flag=true;
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
				GetDBConnection.closeRS(rs);
				GetDBConnection.closeStmt(stmt);
				GetDBConnection.closeStmt(preparedStmt);
				GetDBConnection.closeConn(conn);
			}
			
		return objectOutNode;
	
	}


	@Override
	public ObjectNode insertCustomer(Customer customer) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		
		boolean flag = false;
		String strAuth="Authorised";
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		
		PreparedStatement preparedStmt = null;
		Connection conn 			   = null;
		
		try{
			
			String strauth= customer.getAuthKey();
			System.out.println("strauth key customer"+strauth);
			
			System.out.println("user id customer"+tokService.getUserIdFromToken(strauth));
			
			
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			
			String SQL_QUERY = "INSERT INTO customer_master(CustName,Site,Email,Contact,CustRegion,Address,CreatedBy,CreatedDate,IsDeleted) "+
					" VALUES (?,?,?,?,?,?,1,NOW(),'N')";
			
			System.out.println("QUERY For insertPO "+SQL_QUERY);
			
			preparedStmt = conn.prepareStatement(SQL_QUERY);

			preparedStmt.setString(1, customer.getCustomerName());
			preparedStmt.setString(2, customer.getSite());
			preparedStmt.setString(3, customer.getEmail());
			preparedStmt.setString(4, customer.getContact());
			preparedStmt.setInt(5,  customer.getRegionId());
			preparedStmt.setString(6,  customer.getAddress());
			
			flag = true;
			preparedStmt.executeUpdate();
			
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
			GetDBConnection.closeStmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
			
		return objectOutNode;
	
	}

	@Override
	public ObjectNode inactivateCustomer(Customer customer) {
	
		 boolean flag = false;
		 Connection conn 	= null;
		 Statement stmt 	= null;
		 PreparedStatement preparedStmt = null;
		 String userId;
		 String strAuth="Authorised";
		 ObjectNode objectOutNode =objectMapper.createObjectNode();
		try
		{
			String strauth= customer.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			String query = "Update customer_master set  IsDeleted = 'Y', "
					+ " UpdatedBy = '"+userId+"' , UpdatedDate = NOW() where CustomerID = " + customer.getCustomerId();
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

	@Override
	public ObjectNode activateCustomer(Customer customer) {
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
			String strauth= customer.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			String query = "Update customer_master set  IsDeleted = 'N', "
					+ " UpdatedBy = '"+userId+"' , UpdatedDate = NOW() where CustomerID = " + customer.getCustomerId();
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
	
	
	@Override
	public ObjectNode saveSite(Site customer) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		PreparedStatement preparedStmt = null;
		Connection conn 	= null;
		
		String flag = "error";
		ObjectNode objectOutNode =objectMapper.createObjectNode();

		try{
			
			conn = GetDBConnection.getConnection();
			
			String SQL_QUERY = "INSERT INTO site_master(imageName,lat,longitude,CreatedBy,CreatedDate,IsDeleted) "+
					" VALUES (?,?,?,5,NOW(),'N')";
			
			System.out.println("QUERY For insertPO "+SQL_QUERY);
			
			preparedStmt = conn.prepareStatement(SQL_QUERY);

			preparedStmt.setString(1, customer.getImageName());
			preparedStmt.setDouble(2, customer.getLat());
			preparedStmt.setDouble(3, customer.getLongitude());
			
			preparedStmt.executeUpdate();
			flag = "success";
							
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			GetDBConnection.closeStmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		// TODO Auto-generated method stub
		objectOutNode.put("result", flag);
		
		return objectOutNode;
	}

	@Override
	public ObjectNode getSiteDetail(int id) {
		// TODO Auto-generated method stub
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;
	    
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		String result="error";
		String sql = "SELECT * FROM site_master where ID = "+id;
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();

					objectInNode.put("imageName",rs.getString("imageName"));
					objectInNode.put("lat",rs.getString("lat"));
					objectInNode.put("longitude",rs.getString("longitude"));
					/*objectInNode.put("Site",rs.getString("Site"));
					objectInNode.put("Email",rs.getString("Email"));
					objectInNode.put("Contact",rs.getString("Contact"));
					objectInNode.put("CustRegion",rs.getString("RegionName"));
					objectInNode.put("Address",rs.getString("Address"));				
					objectInNode.put("Address",rs.getString("Address"));*/
					
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

	
	
	
}
