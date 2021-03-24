package com.eci.daoImp;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eci.bean.User;
import com.eci.dao.LoginDao;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class LoginDaoImp implements LoginDao{

	/*private PreparedStatement preparedStmt = null;
	private Connection conn = null;
	private Statement stmt 	= null;
	private ResultSet rs	= null;*/
	
	GetDBConnection getConn = new GetDBConnection();
	TokenServiceImp tokService=new TokenServiceImp();
	ObjectMapper objectMapper = new ObjectMapper();
	public static final String TOKEN_SECRET = "s4T2zOIWHMM1sxq";


	@Override
	public ObjectNode checkLoginCredential(User user,HttpServletRequest req) {
		
		 Connection conn 	= null;
		 Statement stmt 	= null;
		 ResultSet rs		= null,rs1=null;
		
		boolean flag = false;
		String strToken="";
		HttpSession 		httpSession 	= 	null;
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		String SQL_QUERY="";
		
		System.out.println("----sql check user UserName----"+user.getUserName());
		
		System.out.println("----sql check user getPassword----"+user.getPassword());
		
		
		//String sql = "SELECT um.EmpId, rm.role as role FROM user_master um, role_master rm where  um.role=rm.Id and  um.Emp_Email = '"+user.getEmail()+"' and um.Emp_password = '"+user.getPassword()+"' and um.IsDeleted = 'N' ";
		//String sql = "SELECT um.EmpId, rm.role as role, um.UserName FROM user_master um, role_master rm where  um.role=rm.Id and um.UserName = '"+user.getUserName()+"' and um.Emp_password = '"+user.getPassword()+"' and um.IsDeleted = 'N' ";
		
		String sql = "SELECT um.EmpId, rm.role as role, um.UserName FROM user_master um, role_master rm where  um.role=rm.Id and um.UserName = '"+user.getUserName()+"'"
				+ " and   CAST(AES_DECRYPT(um.Emp_password,'pr0@!!') AS CHAR) = '"+user.getPassword()+"' and um.IsDeleted = 'N' ";

		
			try
			{
				httpSession = req.getSession();
				PreparedStatement pStmt = null;
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement(); 
				
				System.out.println("----sql check user credentials----"+sql);
				String userId="",userRole="",uname="";
				rs = stmt.executeQuery(sql);
				
				
				
				while(rs.next()){
					
					//httpSession.setAttribute("EmpId",rs.getString("EmpId"));	
					userId=rs.getString("EmpId");
					userRole=rs.getString("role");
					uname=rs.getString("UserName");
					
					System.out.println("--UserName--"+uname);
					flag = true;
				}
			//	rs1 = stmt.executeQuery(sql1);
				
				
				
				if(flag==true)
				{
					 SQL_QUERY = "update user_auth_token set IsActive='N' where UserId = " +userId; 
					 System.out.println("inactivate old tokens "+SQL_QUERY);
					 pStmt = conn.prepareStatement(SQL_QUERY);
					 pStmt.executeUpdate();
					
					 strToken=tokService.createToken(userId);
					 System.out.println("strToken==="+strToken);
					
					 conn = GetDBConnection.getConnection();
					
					 SQL_QUERY = "INSERT INTO user_auth_token ( UserId ,  AuthToken ,  IsActive  ) " + 
							"VALUES (  ? , ? , 'Y')";
					
					 System.out.println("insert token query"+SQL_QUERY);
					
					 pStmt = conn.prepareStatement(SQL_QUERY);
					 pStmt.setString(1, userId);
					 pStmt.setString(2,strToken);					
						
					 pStmt.executeUpdate();
				}
				System.out.println("getUserIdFromToken---"+tokService.getUserIdFromToken(strToken));
				
				objectOutNode.put("result", flag);
				objectOutNode.put("authkey", strToken);
				objectOutNode.put("role", userRole);
				objectOutNode.put("uname", uname);
				objectOutNode.put("userId", userId);
				
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

	
	public boolean checkEmailExistance(User user) {
		
		 Connection conn 	= null;
		 Statement stmt 	= null;
		 ResultSet rs		= null;
		
		boolean flag = false;
		
		//String sql = "SELECT * FROM user_master where Emp_Email = '"+user.getEmail()+"' and Emp_password = '"+user.getPassword()+"' and IsDeleted = 'N' ";
		
		String sql = "SELECT * FROM user_master where Emp_Email = '"+user.getEmail()+"' and CAST(AES_DECRYPT(um.Emp_password,'pr0@!!') AS CHAR) = '"+user.getPassword()+"' and IsDeleted = 'N' ";
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement(); 
				
				System.out.println("----sql checkEmailExistance----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					flag = true;
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
		
		return flag;
	}


	@Override
	public ObjectNode checkSILoginCredential(User user) 
	{
		// TODO Auto-generated method stub
		 Connection conn 	= null;
		 Statement stmt 	= null;
		 ResultSet rs		= null;
		
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		boolean result = false;

		/*
		 * String sql
		 * ="SELECT um.EmpId,rm.Role as RoleName FROM user_master um,role_master rm where um.Role = rm.Id  and"
		 * + " um.UserName = '"+user.getEmail()+"' and um.Emp_password = '"+user.
		 * getPassword()+"' " +
		 * "and rm.Role in('SI Co-Ordinator','TE') and um.IsDeleted = 'N'";
		 */
		
		String sql ="SELECT um.EmpId,rm.Role as RoleName FROM user_master um,role_master rm where um.Role = rm.Id  and"
				+ " um.UserName = '"+user.getEmail()+"' and CAST(AES_DECRYPT(um.Emp_password,'pr0@!!') AS CHAR) = '"+user.getPassword()+"' "
				+ " and rm.Role in('SI Co-Ordinator','TE') and um.IsDeleted = 'N'";
		
		
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement(); 
				
				System.out.println("----sql checkSILoginCredential----"+sql);
				rs = stmt.executeQuery(sql);
				
				
				while(rs.next())
				{
					ObjectNode objectInNode = objectMapper.createObjectNode();
					objectInNode.put("EmpId", rs.getString("EmpId"));
					objectInNode.put("EmpRole", rs.getString("RoleName"));
					result = true;
					arrayOutNode.add(objectInNode);
				}
				
				
				if(result==false)
				{
					objectOutNode.put("message", "User does not exist");

				}
				
				
				objectOutNode.put("result", result);
				objectOutNode.put("data", arrayOutNode.toString());

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


	@Override
	public ObjectNode changePswd(User user) {
		// TODO Auto-generated method stub
		
		boolean flag=false;
		
		 Connection conn 	= null;
		 Statement stmt 	= null;
		 PreparedStatement pStmt = null;
		 ResultSet rs		= null,rs1=null;
		
		 String userId="", SQL_QUERY,SQL_QUERY1;
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		boolean result = false;	
		
		String strAuth="Authorised";
		
		 	try
		 	{
			System.out.println("-------In action class of password------- ");
			
			conn = GetDBConnection.getConnection();
			
			
			String strauth= user.getAuthKey();
			
			System.out.println("------strauthstrauth------- "+strauth);

			if(strauth!=null)
			{
				if(!strauth.equals(""))
				{
					userId = tokService.getUserIdFromToken(strauth);
				}
			}
			else
			{
				userId = Integer.toString(user.getId());
			}
			
			
			System.out.println("-------In action class of userId------- "+userId);
			
		//	SQL_QUERY 	= "select * from user_master where UserName='" +user.getUserName()+ "' and Emp_password='" + user.getOldpassword() + "' and IsDeleted='N'";
			
			SQL_QUERY 	= "select * from user_master where UserName='" +user.getUserName()+ "' and CAST(AES_DECRYPT(Emp_password,'pr0@!!') AS CHAR)='" + user.getOldpassword() + "' and IsDeleted='N'";
			
			
			System.out.println("SQL_QUERY=========="+SQL_QUERY);
			
			pStmt = conn.prepareStatement(SQL_QUERY);
			rs 	  = pStmt.executeQuery();
		    
			/*
			 * pStmt = conn.prepareStatement(SQL_QUERY1); rs1 = pStmt.executeQuery();
			 */
			
			while(rs.next())
			{
				System.out.println("In while loop");
				flag=true;
			}
			
			/*
			 * while(rs1.next()) { System.out.println("In while loop"); flag=true; }
			 */
			
			System.out.println("flag : "+flag);
			
			if(flag==true)
			{
				System.out.println("emp password is : "+user.getEmppassword());
				
				//SQL_QUERY = "update employee_master set Emp_password = '"+request.getParameter("Emp_password")+"'  where EmpId = '"+httpSession.getAttribute("Login_Id").toString()+"'";
					
					SQL_QUERY = "update user_master set Emp_password = AES_ENCRYPT('"+user.getEmppassword()+"', 'pr0@!!')    where EmpId = '"+userId+"'";
				
					stmt = conn.createStatement();
					stmt.execute(SQL_QUERY);
					
				    System.out.println("password updated  successfully=== "+SQL_QUERY);

				    objectOutNode.put("result","success");
				    
				    }else{
				    	objectOutNode.put("result","NoData");
				
				    }
		 		}
		 		catch(Exception e){
					e.printStackTrace();
				}
		 	return objectOutNode;
		
		
	}


	@Override
	public boolean saveUserKey(User user) {
		
		
		boolean flag = false;
		
		PreparedStatement preparedStmt = null;
		Connection conn 	= null;
		
		
		try{

			System.out.println("user id--"+user.getId());
			System.out.println("user  key--"+user.getUserKey());
			
			conn = GetDBConnection.getConnection();
			
			//inactivate old keys for the user
			String query = "Update userkey set  isActive = 'N'  "
			+ ", UpdatedDate = NOW()  where UserId = "+ user.getId() ;
	
			preparedStmt = conn.prepareStatement(query);
		      // execute the java preparedstatement
		    preparedStmt.executeUpdate();
		    
			System.out.println("user's old key inactivated "+query);
			
			
			String SQL_QUERY = "INSERT INTO userkey(UserId,UserKey,isActive,CreatedDate) "+
					" VALUES (?,?,?,NOW())";
			
			System.out.println("QUERY For saveUserKey "+SQL_QUERY);
			
			preparedStmt = conn.prepareStatement(SQL_QUERY);

			preparedStmt.setInt(1, user.getId());
			preparedStmt.setString(2, user.getUserKey());
			preparedStmt.setString(3, "Y");
			preparedStmt.executeUpdate();
			
			System.out.println("user's new key inactivated "+query);

			
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
	
	
	
/*
	
	 public String createToken(String userId) {            // public String createToken(ObjectId userId
	        try {
	            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
	            String token = JWT.create()
	                    .withClaim("userId", userId.toString())
	                    .withClaim("createdAt", new Date())
	                    .sign(algorithm);
	            return token;
	        } catch (UnsupportedEncodingException exception) {
	            exception.printStackTrace();
	            //log WRONG Encoding message
	        } catch (JWTCreationException exception) {
	            exception.printStackTrace();
	            //log Token Signing Failed
	        }
	        return null;
	    }

	    public String getUserIdFromToken(String token) {
	        try {
	            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
	            JWTVerifier verifier = JWT.require(algorithm)
	                    .build();
	            DecodedJWT jwt = verifier.verify(token);
	            return jwt.getClaim("userId").asString();
	        } catch (UnsupportedEncodingException exception) {
	            exception.printStackTrace();
	            //log WRONG Encoding message
	            return null;
	        } catch (JWTVerificationException exception) {
	            exception.printStackTrace();
	            //log Token Verification Failed
	            return null;
	        }
	    }

	    public boolean isTokenValid(String token) {
	        String userId = this.getUserIdFromToken(token);
	        return userId != null;
	    }
	
	
	*/
	
	
	
	
	
	
	
}
