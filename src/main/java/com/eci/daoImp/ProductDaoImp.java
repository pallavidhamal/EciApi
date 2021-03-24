package com.eci.daoImp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.eci.bean.Customer;
import com.eci.bean.Product;
import com.eci.dao.ProductDao;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class ProductDaoImp implements ProductDao {

	/*private PreparedStatement preparedStmt = null;
	private Connection conn = null;
	private Statement stmt 	= null;
	private ResultSet rs	= null;
	*/
	TokenServiceImp tokService=new TokenServiceImp();
	GetDBConnection getConn = new GetDBConnection();
	ObjectMapper objectMapper = new ObjectMapper();
	
	public ObjectNode getProducts() {
		// TODO Auto-generated method stub
		
		 Connection conn 	= null;
		 Statement stmt 	= null;
		 ResultSet rs		= null;
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		//String sql = "SELECT * FROM product_master pm ,customer_master cm where pm.CustomerID=cm.CustomerID and  pm.IsDeleted = 'N' ";
		//String sql = "SELECT * FROM product_master pm ,customer_master cm where pm.CustomerID=cm.CustomerID ";
		String sql = "SELECT pm.ProductId, pm.Name, pm.CatCode, pm.Description, cm.CustName, pm.IsDeleted  FROM product_master pm ,customer_master cm where pm.CustomerID=cm.CustomerID ";
		
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql getAllPorders----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("ProdId",rs.getString("ProductId"));
					objectInNode.put("Name",rs.getString("Name"));
					objectInNode.put("CatCode",rs.getString("CatCode"));
					objectInNode.put("Desc",rs.getString("Description"));
					objectInNode.put("CustName",rs.getString("CustName"));
					objectInNode.put("IsDeleted",rs.getString("IsDeleted"));
					
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
	public ObjectNode getProductDetail(int id) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		String result="error";
		
		 Connection conn 	= null;
		 Statement stmt 	= null;
		 ResultSet rs		= null;
		
		String sql = "SELECT * FROM product_master where ProductId = "+id;
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();

					objectInNode.put("ProductId",rs.getString("ProductId"));
					objectInNode.put("Name",rs.getString("Name"));
					objectInNode.put("CatCode",rs.getString("CatCode"));
					objectInNode.put("CustomerId",rs.getString("CustomerId"));
					objectInNode.put("Desc",rs.getString("Description"));
					
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
	public ObjectNode insertProduct(Product product) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		Connection conn = null;
		PreparedStatement preparedStmt = null;

		boolean flag = false;
		String strAuth="Authorised", userId;
		
		try{
			String strauth= product.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			
			String SQL_QUERY = "INSERT INTO product_master(Name,CatCode,Description,CustomerId,CreatedBy,CreatedDate,IsDeleted) "+
					" VALUES (?,?,?,?,'"+userId+"',NOW(),'N')";
			
			
			System.out.println("QUERY For insertPO "+SQL_QUERY);
			
			preparedStmt = conn.prepareStatement(SQL_QUERY);

			preparedStmt.setString(1, product.getProduct());
			preparedStmt.setString(2, product.getCatCode());
			preparedStmt.setString(3, product.getDesc());
			preparedStmt.setInt(4, product.getCustomerId());
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
			GetDBConnection.closeStmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		// TODO Auto-generated method stub
		return objectOutNode;
	}

	@Override
	public ObjectNode updateProduct(Product product) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		PreparedStatement preparedStmt = null;
		Connection conn 	= null;
		Statement stmt 		= null;
		
		boolean flag = false;
		String strAuth="Authorised", userId;
		
		try
		{
			String strauth= product.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			
			String query = "Update product_master set  Name = ? ,CatCode = ? ,Description= ?  ,"
					+ " UpdatedBy = '"+userId+"' , UpdatedDate = NOW() where ProductId = " + product.getId();
			
			System.out.println("updateCustomer query"+query);
			
			preparedStmt = conn.prepareStatement(query);
	    
		    preparedStmt.setString(1, product.getProduct());
		    preparedStmt.setString(2, product.getCatCode());;
		    preparedStmt.setString(3, product.getDesc());

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
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closeStmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
	return objectOutNode;
	}
	
	@Override
	public ObjectNode inactivateProduct(Product product) {
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
			String strauth= product.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			String query = "Update product_master set  IsDeleted = 'Y', "
					+ " UpdatedBy = '"+userId+"' , UpdatedDate = NOW() where ProductId = " + product.getId();
			
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
		/*boolean flag = false;
		PreparedStatement preparedStmt = null;
		Connection conn 	= null;
		Statement stmt 		= null;
		
		
		try
		{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			
			String query = "Update product_master set  IsDeleted = 'Y', "
					+ " UpdatedBy = 1 , UpdatedDate = NOW() where ProductId = " + product.getId();
			
			System.out.println("updateCustomer query"+query);
			
			preparedStmt = conn.prepareStatement(query);
		      // execute the java preparedstatement
		    preparedStmt.executeUpdate();
		    flag=true;
			
		}
		catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		finally {
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closeStmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
	return flag;
		*/
	}

	@Override
	public ObjectNode activateProduct(Product product) {
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
			String strauth= product.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			String query = "Update product_master set  IsDeleted = 'N', "
					+ " UpdatedBy = '"+userId+"' , UpdatedDate = NOW() where ProductId = " + product.getId();
			
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
