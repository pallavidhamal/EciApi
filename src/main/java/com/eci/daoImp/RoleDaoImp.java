package com.eci.daoImp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.eci.bean.Role;
import com.eci.bean.Task;
import com.eci.dao.RoleDao;
import com.eci.dao.TaskDao;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class RoleDaoImp implements RoleDao {
	/*private PreparedStatement preparedStmt = null;
	private Connection conn = null;
	private Statement stmt 	= null;
	private ResultSet rs	= null;
	*/
	GetDBConnection getConn = new GetDBConnection();
	ObjectMapper objectMapper = new ObjectMapper();
	TokenServiceImp tokService=new TokenServiceImp();
	
	public ObjectNode getRoles() {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		
		String sql = "Select * from role_master where IsDeleted = 'N'";
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql getAll Roles----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("Id", rs.getString("Id"));
					objectInNode.put("RoleType", rs.getString("RoleType"));
					objectInNode.put("Role", rs.getString("Role"));

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
	public boolean insertRole(Role role) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		
		boolean flag = false;
		
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		String strAuth="Authorised";
		
		try{
			String strauth= role.getAuthKey();
			
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			
			String SQL_QUERY = "INSERT INTO role_master(RoleType,Role,CreatedBy,CreatedDate,IsDeleted) VALUES (?,?,1,NOW(),'N')";
			
			System.out.println("QUERY For insertRole "+SQL_QUERY);
			
			System.out.println("QUERY For getRoleType "+role.getRoleType());
			
			preparedStmt = conn.prepareStatement(SQL_QUERY);

			preparedStmt.setString(1, role.getRoleType()); 
			
			preparedStmt.setString(2, role.getRole()); 
			preparedStmt.executeUpdate();
			flag = true;
			
			System.out.println("QUERY For flag "+flag);
			}else
			{
				strAuth="UnAuthorised";
			}	
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		System.out.println("QUERY For flag out "+flag);
		// TODO Auto-generated method stub
		return flag;
		
	}

	@Override
	public ObjectNode getRoleDetail(int id) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		String result="error";
		
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;
		
		String sql = "SELECT * FROM role_master where Id = "+id ;
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql-role_master---"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();

					objectInNode.put("Id",rs.getString("Id"));
					objectInNode.put("RoleType",rs.getString("RoleType"));
					objectInNode.put("Role",rs.getString("Role"));
					
					arrayOutNode.add(objectInNode);
				}
				
				System.out.println("----sql-role_master-arrayOutNode--"+arrayOutNode);
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
	public boolean updateRole(Role role) {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		
		try
		{
			conn = GetDBConnection.getConnection();
		//	stmt = conn.createStatement();
			
			System.out.println("updateCustomer id "+role.getId());
			
			String query = "Update role_master set RoleType = ?,   Role = ? , UpdatedBy = 1 , UpdatedDate = NOW() where Id = " + role.getId();
			
		
			preparedStmt = conn.prepareStatement(query);	    
		    preparedStmt.setString(1, role.getRoleType());
		    preparedStmt.setString(2, role.getRole());
		      
		   
		      // execute the java preparedstatement
		      preparedStmt.executeUpdate();
		      flag=true;
			
		}
		catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
	return flag;
	}

	@Override
	public boolean inactivateRole(Role role) {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		Connection conn = null;
		Statement stmt 	= null;
		PreparedStatement preparedStmt = null;
		
		
		try
		{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			
			String query = "Update role_master set  IsDeleted = 'Y', "
					+ " UpdatedBy = 1 , UpdatedDate = NOW() where Id = " + role.getId() ;
			
			System.out.println("inactivateRole query"+query);
			
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
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		
	return flag;
	}

/*
	@Override
	public ObjectNode getTaskDetail(int id) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		String result="error";
		String sql = "SELECT * FROM task_master rm where TaskId = "+id;
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();

					objectInNode.put("TaskId",rs.getString("TaskId"));
					objectInNode.put("TaskName",rs.getString("TaskName"));
					
					arrayOutNode.add(objectInNode);
				}
				objectOutNode.put("data", arrayOutNode.toString());
				result= "success";
			}
			catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
			objectOutNode.put("result", result);
			
		return objectOutNode;
		
	}

	@Override
	public boolean insertTask(Task task) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		
		boolean flag = false;
		
		try{
			
			conn = GetDBConnection.getConnection();
			
			String SQL_QUERY = "INSERT INTO task_master(TaskName,CreatedBy,CreatedDate,IsDeleted) "+
					" VALUES (?,1,NOW(),'N')";
			
			System.out.println("QUERY For insertTask "+SQL_QUERY);
			
			preparedStmt = conn.prepareStatement(SQL_QUERY);

			preparedStmt.setString(1, task.getTask()); 
			
			preparedStmt.executeUpdate();
			flag = true;
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return flag;
		
	}

	@Override
	public boolean updateTask(Task task) {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		try
		{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			
			String query = "Update task_master set  TaskName = ? ,"
					+ " UpdatedBy = 1 , UpdatedDate = NOW() where TaskId = " + task.getId();
			
			System.out.println("updateCustomer query"+query);
			
			preparedStmt = conn.prepareStatement(query);
	    
		    preparedStmt.setString(1, task.getTask());

		      // execute the java preparedstatement
		   preparedStmt.executeUpdate();
		      flag = true;
		}
		catch(Exception e)
    	{
    		e.printStackTrace();
    	}
	return flag;
	}
	
	
	public boolean inactivateTask(Task task) {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		try
		{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			String query = "Update task_master set  IsDeleted = 'Y' ,"
					+ " UpdatedBy = 1 , UpdatedDate = NOW() where TaskId = " + task.getId();
			
			System.out.println("inactivateTask query"+query);
			
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.executeUpdate();
		    flag = true;
		    
		}
		catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		return flag;
	}*/
}
