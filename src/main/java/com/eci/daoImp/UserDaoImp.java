package com.eci.daoImp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eci.bean.Region;
import com.eci.bean.User;
import com.eci.dao.UserDao;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class UserDaoImp implements UserDao{

	/*private PreparedStatement preparedStmt = null;
	private Connection conn = null;
	private Statement stmt 	= null;
	private ResultSet rs	= null;*/
	TokenServiceImp tokService=new TokenServiceImp();
	GetDBConnection getConn = new GetDBConnection();
	ObjectMapper objectMapper = new ObjectMapper();
	
	RegionDaoImp regionDaoImp = new RegionDaoImp();
	
	
	@Override
	public ObjectNode getAllUsers() {
		
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;
	    
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		String result="error";
		
		//String sql = "select * from user_master where IsDeleted='N'";
		//String sql = "select *, rm.Id as RoleId, rm.Role as RoleName, rm.RoleType as RoleType from user_master um join role_master rm on um.EmpId = rm.Id where IsDeleted = 'N' ";
		
		/*
		 * String sql
		 * ="SELECT um.empid ,um.Emp_Name ,GROUP_CONCAT(rm.RegionName) as RegionName  ,um.Emp_Contact,um.Emp_Email, um.Emp_Address ,rl.Role, rt.RoleType, um.IsDeleted ,sc.Company "
		 * +
		 * "FROM user_master um , role_master rl,roletype_master rt ,user_region ur ,region_master rm ,si_company sc where "
		 * +
		 * "um.Role=rl.Id and um.EmpId = ur.UserId and ur.RegionId = rm.RegionId And um.RoleType = rt.Id  and um.SICompany=sc.Id "
		 * + "group by um.EmpId ";
		 */
		
		String sql="SELECT um.empid ,um.Emp_Name ,GROUP_CONCAT(rm.RegionName) as RegionName  ,um.Emp_Contact,um.Emp_Email, um.Emp_Address ,rl.Role, rt.RoleType, "
				+ "um.IsDeleted,um.UserName ,sc.Company FROM user_master um inner join  role_master rl on um.Role=rl.Id "
				+ "inner join roletype_master rt on um.RoleType = rt.Id "
				+ "inner join user_region ur on um.EmpId = ur.UserId "
				+ "inner join region_master rm on ur.RegionId = rm.RegionId "
				+ "left join si_company sc on um.SICompany=sc.Id "
				+ "group by um.EmpId  ";
		
		System.out.println("----sql getAll users----"+sql);
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement(); 
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("EmpId",rs.getString("empid"));
					objectInNode.put("Emp_Name", rs.getString("Emp_Name"));
					objectInNode.put("Emp_Address", rs.getString("Emp_Address"));
					objectInNode.put("Emp_Email", rs.getString("Emp_Email"));
					objectInNode.put("Emp_Contact", rs.getString("Emp_Contact"));
					objectInNode.put("Emp_Region", rs.getString("RegionName"));
					objectInNode.put("RoleName", rs.getString("Role"));
					objectInNode.put("RoleType", rs.getString("RoleType"));
					objectInNode.put("IsDeleted", rs.getString("IsDeleted"));
					objectInNode.put("Company", rs.getString("Company"));
					objectInNode.put("UserName", rs.getString("UserName"));

					arrayOutNode.add(objectInNode);
				
				}
				
					
					objectOutNode.put("result", arrayOutNode);
					//result= "success";
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
			//objectOutNode.put("result", result);
		
		return objectOutNode;
	}

	@Override
	public ObjectNode getUserDetail(int id) {

		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;
	    
		boolean flag = false;
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		String result="error";
		
		String sql = "SELECT *,GROUP_CONCAT(ur.RegionId) AS RegionStr FROM user_master um, user_region ur where um.EmpId = ur.UserId AND um.IsDeleted = 'N' and  um.EmpId =" +id+ " group by um.EmpId  ";
		

		System.out.println("----getUserDetail----"+sql);
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
					
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					ObjectNode objectInNode = objectMapper.createObjectNode();

					objectInNode.put("EmpId", rs.getString("EmpId"));
					objectInNode.put("Emp_Name", rs.getString("Emp_Name"));
					objectInNode.put("Emp_Address", rs.getString("Emp_Address"));
					objectInNode.put("Emp_Email", rs.getString("Emp_Email"));
					objectInNode.put("Emp_Contact", rs.getString("Emp_Contact"));
					objectInNode.put("RoleType", rs.getString("RoleType"));
					objectInNode.put("Role", rs.getString("Role"));
					objectInNode.put("SICompany", rs.getString("SICompany"));
					objectInNode.put("RegionStr", rs.getString("RegionStr"));
					objectInNode.put("Customer_Name", rs.getString("Customer_Name"));
					
					arrayOutNode.add(objectInNode);
					
					
				}
				objectOutNode.put("data", arrayOutNode.toString());
				result= "success";
				//result= "success";
				System.out.println("----getUserDetail--objectOutNode--"+objectOutNode);				
				
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
	public ObjectNode insertUser(User user) throws ClassNotFoundException, IOException, SQLException {
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		Connection conn 	= null;
		PreparedStatement preparedStmt = null;
	    ResultSet rs		= null;
	    Statement stmt = null;
		
		boolean flag = false,regionFlag=false;
		String strAuth="Authorised", userId;
		String userName = "";
		boolean existFlag=false;
		try{
			String strauth= user.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			
			existFlag=CheckPhoneNo(user.getContact(),"add",0);
			
			 System.out.println("existFlag--"+existFlag);
			 
			if(existFlag==false)
			{
			
				userName = createUsername(user.getName(), user.getContact());
				
				//check whether this usrename is already used for some other user
				String strQry="select UserName from user_master where IsDeleted='N' and UserName= '"+userName+"'";
				
				stmt = conn.createStatement(); 
				
				rs = stmt.executeQuery(strQry);
				
				while(rs.next()){
					//exists in table
					existFlag=true;
				}
		
			
					
					 if(existFlag==false) {
					 
			
						/*
						 * String SQL_QUERY =
						 * "INSERT INTO user_master(Emp_Name, Emp_Address, Emp_Email, Emp_Contact,RoleType, Role, SICompany, Emp_password, "
						 * + "Created_By, CreatedDate, IsDeleted, UserName, Customer_Name) "+
						 * " VALUES (?,?,?,?,?,?,?,'P@20Ap','"+userId+"',NOW(),'N', '"+userName+"', ?)";
						 */
						
						String SQL_QUERY = "INSERT INTO user_master(Emp_Name, Emp_Address, Emp_Email, Emp_Contact,RoleType, Role, SICompany, Emp_password , "
								+ "Created_By, CreatedDate, IsDeleted, UserName, Customer_Name) "+
								" VALUES (?,?,?,?,?,?,?,AES_ENCRYPT('P@20Ap', 'pr0@!!'),'"+userId+"',NOW(),'N', '"+userName+"', ?)";
						
						
						
						System.out.println("QUERY For insertPO "+SQL_QUERY);
						
						preparedStmt = conn.prepareStatement(SQL_QUERY,Statement.RETURN_GENERATED_KEYS);
			
						preparedStmt.setString(1, user.getName());
						preparedStmt.setString(2, user.getAddress());
						preparedStmt.setString(3, user.getEmail());
						preparedStmt.setString(4, user.getContact());
						preparedStmt.setInt(5,  user.getRoletype());
						preparedStmt.setInt(6,  user.getRole());
						//preparedStmt.setString(7,  user.getName());
						preparedStmt.setInt(7,  user.getsCompany());
						preparedStmt.setString(8,  user.getCustomerName());
						
						preparedStmt.executeUpdate();
						
						rs = preparedStmt.getGeneratedKeys();
						int generatedId = 0;
						if (rs.next()) {
							generatedId = rs.getInt(1);
						}
						 
						System.out.println("Inserted record's ID: " + generatedId);
						
						flag = true;
						
							if(flag==true)
							{
								regionFlag=insertUserRegions(user,generatedId);
							}
							
							
						}// user combi
						else
						{
							flag=false;
							strAuth="UserExists";
							
						}
							
						}//phone if
						else
						{
							flag=false;
							strAuth="DupPhone";
						}
			
			}//token if
			else
			{
				strAuth="UnAuthorised";
			}
			System.out.println("flag"+flag);
			System.out.println("strAuth"+strAuth);

			objectOutNode.put("result",flag);
			objectOutNode.put("message",strAuth);
			
		}catch (Exception e) { 
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeRS(rs);
			GetDBConnection.closeConn(conn);
		}
		// TODO Auto-generated method stub
		return objectOutNode;
		
		
	}

	
	private String createUsername(String name, String contact) {
		
		//System.out.println("QUERY For getUsername Name=== "+name);
		//System.out.println("QUERY For getUsername Contact======  "+contact);
		
		String firstChars = "", lastChars = "", completeUsername = "";
		
		if (name.length() > 4)
		{
			firstChars = name.substring(0, 4);
		}
//		System.out.println("+++++++++++++firstChars===="+firstChars);
		
		lastChars = contact.substring(contact.length()-4,contact.length());
		
		//System.out.println("+++++++++++++lastChars===="+lastChars);
		
		completeUsername = firstChars.concat(lastChars); 
		
		System.out.println("+++++++++++++completeUsername===="+completeUsername);
		
		// TODO Auto-generated method stub
		return completeUsername;
	}

	@Override
	public boolean insertUserRegions(User user,int generatedId) throws ClassNotFoundException, IOException, SQLException {
		
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		
		boolean flag = false;
		
		try{
			List<Region> reg=user.getReg();
			
			
			conn = GetDBConnection.getConnection();
			String SQL_QUERY = "INSERT INTO user_region(UserId, RegionId, CreatedBy, CreatedDate) "+
							   " VALUES (?,?,1,NOW())";
			
			System.out.println("QUERY For insertPO "+SQL_QUERY);
			preparedStmt = conn.prepareStatement(SQL_QUERY);
			
			
			 for(Region regn : reg) 
			 {
				 System.out.println("insertUserRegions getRegion_Id===== "+regn.getRegion_Id());
				 
				preparedStmt.setInt(1, generatedId);
				preparedStmt.setInt(2, Integer.parseInt(regn.getRegion_Id()));
				preparedStmt.executeUpdate();
			 }
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
		return flag;
		
	}
	
	public boolean CheckPhoneNo(String strPhone,String mode,int UserId)
	{
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs =null;
	    boolean Flag=false;
	    String strQry="";
	    try
		{
		conn = GetDBConnection.getConnection();

		if(mode.equals("add"))
		{
		 strQry="select * from user_master where IsDeleted='N' and Emp_Contact= "+strPhone;
		}
		else
		{
			strQry="select * from user_master where IsDeleted='N' and Emp_Contact= "+strPhone + " and EmpId != "+UserId ;
		}
		 
		stmt = conn.createStatement(); 
		
		rs = stmt.executeQuery(strQry);
		
		while(rs.next()){
			//exists in table
			Flag=true;
		}
		}
	    catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closeConn(conn);
		}
	    return Flag;
	}
	
	
	@Override
	public ObjectNode updateUser(User user) {
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		Connection conn 	= null;
		Statement stmt 		= null;
	    PreparedStatement preparedStmt = null;
		
	    ResultSet rs=null;
	    
		boolean flag = false,delFlag=false,existFlag=false;
		String strAuth="Authorised" , userId;
		String  userName="";
		try
		{
			String strauth= user.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			
			
			existFlag=CheckPhoneNo(user.getContact(), "update", user.getId());
			if(existFlag==false)//means not duplicate no 
			{
				userName = createUsername(user.getName(), user.getContact());
				
				//check whether this usrename is already used for some other user
				String strQry="select UserName from user_master where IsDeleted='N' and EmpId!= "+user.getId() +" and UserName= '"+userName+"'";
				
				stmt = conn.createStatement(); 
				
				rs = stmt.executeQuery(strQry);
				
				while(rs.next()){
					//exists in table
					existFlag=true;
				}
			
			
				if(existFlag==false)
				{
						String query = "Update user_master set Emp_Name = ? ,Emp_Address = ? , Emp_Email = ? ,Emp_Contact= ? ,RoleType = ? ,Role = ? , SICompany = ?, Customer_Name = ?,  "
								+ " UserName = '"+userName+"' , UpdatedBy = '"+userId+"' , UpdatedDate = NOW() where EmpId = " + user.getId();
						
						System.out.println("updateCustomer query"+query);
						
						preparedStmt = conn.prepareStatement(query);
				    
						preparedStmt.setString(1, user.getName());
						preparedStmt.setString(2, user.getAddress());
						preparedStmt.setString(3, user.getEmail());
						preparedStmt.setString(4, user.getContact());
						preparedStmt.setInt(5,  user.getRoletype());
						preparedStmt.setInt(6,  user.getRole());
						preparedStmt.setInt(7,  user.getsCompany());
						preparedStmt.setString(8,  user.getCustomerName());
						
					      // execute the java preparedstatement
						preparedStmt.executeUpdate();
						flag=true;
						
						if(flag==true)
						{
							flag=false;
							String sql = "delete from user_region where UserId = ?";
							preparedStmt = conn.prepareStatement(sql);
							preparedStmt.setInt(1, user.getId());
							preparedStmt.execute();
							
							insertUserRegions(user,user.getId());
							
							//delFlag=deleteUserRegions(user,user.getId());
						}
						flag=true;
				}
				else
				{
					flag=false;
					strAuth="UserExists";
				}
			
			}	
			else
			{
				//means duplicate no
				flag=false;
				strAuth="DupPhone";
			}	
			
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
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closeConn(conn);
		}
	return objectOutNode;
	}

	/*private boolean deleteUserRegions(User user, int id) {
		// TODO Auto-generated method stub
			boolean flag = false;
		   String sql = "delete from user_region where UserId = ?";
	       pst = conn.prepareStatement(sql);
		   pst.setInt(1, ChkEmptynullInt(SoId));
	       pst.execute();
		
		
		return flag;
	}*/

	@Override
	public ObjectNode inactivateUser(User user) {
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
			String strauth= user.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			String query = " Update user_master set  IsDeleted = 'Y', UpdatedBy = '"+userId+"' , UpdatedDate = NOW() where EmpId = " + user.getId();
			
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
/*		
		Connection conn 	= null;
		Statement stmt 		= null;
	    PreparedStatement preparedStmt = null;
	    
	    boolean flag = false;
		
		try
		{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			
			
			String query = " Update user_master set  IsDeleted = 'Y', UpdatedBy = 1 , UpdatedDate = NOW() where EmpId = " + user.getId();
						
			System.out.println("update User query"+query);
			
			preparedStmt = conn.prepareStatement(query);
		    preparedStmt.executeUpdate();
		    flag=true;
			
		}
		catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closeConn(conn);
		}
	return flag;*/
		
	}

	@Override
	public ObjectNode getTypeWiseRole(int roletype) {
		
		// TODO Auto-generated method stub

		Connection conn 	= null;
		Statement stmt 		= null;
		ResultSet rs		= null;
		
		boolean flag = false;
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		String result="error";
		
		String sql = " select * from role_master where IsDeleted = 'N' and RoleType = " +roletype ; 
			

		System.out.println("----getTypeWiseRole----"+sql);
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
					
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					ObjectNode objectInNode = objectMapper.createObjectNode();

					objectInNode.put("Id", rs.getString("Id"));
					objectInNode.put("RoleType", rs.getString("RoleType"));
					objectInNode.put("Role", rs.getString("Role"));
					
					arrayOutNode.add(objectInNode);
					
					
				}
				objectOutNode.put("data", arrayOutNode.toString());
				result= "success";
				//result= "success";
				System.out.println("----getUserDetail--objectOutNode--"+objectOutNode);				
				
			}
			catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
			finally {
				GetDBConnection.closeStmt(stmt);
				GetDBConnection.closeConn(conn);
				GetDBConnection.closeRS(rs);
			}
			objectOutNode.put("result", result);
			
		return objectOutNode;
	}

	@Override
	public boolean resetPassword(User user) {
		// TODO Auto-generated method stub
		boolean flag = false,delFlag=false;
		
		Connection conn = null;
		Statement stmt 		= null;
		PreparedStatement preparedStmt = null;
		
		try
		{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			
		//	String query = "update user_master set Emp_password =  'P@20Ap' , Emp_password1= AES_ENCRYPT('P@20Ap', 'pr0@!!') where EmpId = " + user.getId();
			
			String query = "update user_master set  Emp_password= AES_ENCRYPT('P@20Ap', 'pr0@!!') where EmpId = " + user.getId();
			
			System.out.println("resetPassword query"+query);
			
			preparedStmt = conn.prepareStatement(query);
	    		
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
			GetDBConnection.closeStmt(stmt);
		}
	return flag;
	}

	@Override
	public ObjectNode getSiCompanyList() {
		// TODO Auto-generated method stub

		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;
	    
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		String result="error";
		
		String sql ="SELECT * FROM si_company where IsDeleted = 'N' ";
		
		System.out.println("----sql  getSiCompanyList----"+sql);
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement(); 
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("Id",rs.getString("Id"));
					objectInNode.put("Company", rs.getString("Company"));
					
					arrayOutNode.add(objectInNode);
				
				}
				
				objectOutNode.put("result", arrayOutNode);
					//result= "success";
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
			//objectOutNode.put("result", result);
		
		return objectOutNode;
	}

	@Override
	public ObjectNode selectType() {
		// TODO Auto-generated method stub
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();     
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		String result="error";
		
		String sql = " SELECT * FROM roletype_master; ";
		
		System.out.println("----sql getAll users----"+sql);
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement(); 
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("Id",rs.getString("Id"));
					objectInNode.put("RoleType", rs.getString("RoleType"));
									
					arrayOutNode.add(objectInNode);
				
				}
				objectOutNode.put("result", arrayOutNode);
					//result= "success";
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
			//objectOutNode.put("result", result);
		
		return objectOutNode;
	}

	@Override
	public ObjectNode activateUser(User user) {
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
			String strauth= user.getAuthKey();
			userId = tokService.getUserIdFromToken(strauth);
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			String query = " Update user_master set  IsDeleted = 'N', UpdatedBy = '"+userId+"' , UpdatedDate = NOW() where EmpId = " + user.getId();
			
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
	public ObjectNode  apkversion(User user) {
		 Connection conn 	= null;
		 Statement stmt 	= null;
		 PreparedStatement preparedStmt = null;
		 ObjectNode objectOutNodenew =objectMapper.createObjectNode();     
		 ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		 boolean flag=false;
		 try
			{
	    
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				String query = " Update user_master set  UserApkVersion = '"+user.getApkVersion()+"' , Manufacturer='"+user.getManufacturer()+"' ,  "
						+ "   OS='"+user.getOS()+"' ,  MObileModel='"+user.getMObileModel()+"' ,  UpdatedDate = NOW() where EmpId = " + user.getId();			
				System.out.println("activateTask query"+query);
				preparedStmt = conn.prepareStatement(query);
				 int i= preparedStmt.executeUpdate();  
					ObjectNode objectInNode = objectMapper.createObjectNode();		
			        if (i > 0) {
			        	flag= true;
			        	objectInNode.put("flag", flag)	;
			        	arrayOutNode.add(objectInNode);
			        	} 
			        objectOutNodenew.put("Result", arrayOutNode);
			        
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
		
		return objectOutNodenew;
	}
	
	@Override
	public ObjectNode  getapkversion(User user) {
		 Connection conn 	= null;
		 Statement stmt 	= null,stmt1 	= null;
		 ResultSet rs		= null,rs1=null;
		 boolean flag=false;
		 PreparedStatement preparedStmt = null;
		 ObjectNode objectOutNodenew =objectMapper.createObjectNode();     
		 ArrayNode arrayOutNode = objectMapper.createArrayNode();
		 try
			{
	    
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				String sql = " Select * from user_master where EmpId =" +user.getId()+" ";			
				System.out.println(sql+"Querty");			
			/*
			 * stmt = conn.createStatement(); rs = stmt.executeQuery(sql);
			 * 
			 * while(rs.next()){ ObjectNode objectInNode = objectMapper.createObjectNode();
			 * flag=true; //objectInNode.put("result",rs.getString("UserApkVersion"));
			 * objectInNode.put("UserApkVersion",rs.getString("UserApkVersion"));
			 * objectInNode.put("Manufacturer", rs.getString("Manufacturer"));
			 * objectInNode.put("OS",rs.getString("OS")); objectInNode.put("MObileModel",
			 * rs.getString("MObileModel"));
			 * 
			 * arrayOutNode.add(objectInNode); //arrayOutNode.add(flag); }
			 */
					
					
					String sql1 = " Select * from activeapk";			
					System.out.println(sql+"Querty");			
					stmt1 = conn.createStatement(); 				
					rs1 = stmt1.executeQuery(sql1);
					
						while(rs1.next()){					
						ObjectNode objectInNode = objectMapper.createObjectNode();	
						flag=true;
						//objectInNode.put("result",rs.getString("UserApkVersion"));
						objectInNode.put("UserApkVersion"
								+ ""
								+ ""
								+ "",rs1.getString("activeapk"));
						arrayOutNode.add(objectInNode);
						//arrayOutNode.add(flag);
					}
					
					
					
					objectOutNodenew.put("data", arrayOutNode.toString());
					objectOutNodenew.put("result", flag);
					
				System.out.println("objectOutNode"+objectOutNodenew);
				
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
		
		return objectOutNodenew;
	}
	
	
	
	
	
	
}
