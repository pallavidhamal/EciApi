package com.eci.daoImp;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import com.eci.bean.Installation;
import com.eci.bean.Task;
import com.eci.dao.InstallationDao;
import com.eci.util.GenerateInvoicePdf;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@Repository
public class InstallationDaoImp implements InstallationDao {

	/*private PreparedStatement preparedStmt = null;
	private Connection conn = null;
	private Statement stmt 	= null;
	private ResultSet rs	= null;*/
	
	GetDBConnection getConn = new GetDBConnection();
	TokenServiceImp tokService=new TokenServiceImp();

	ObjectMapper objectMapper = new ObjectMapper();
	
	public ObjectNode getInstallationList(int userid) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		ArrayNode arrTempNode = objectMapper.createArrayNode();

		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		String remark="",remark1="",typeFlag="",recId="";
		System.out.println("=====userid========"+userid);
		
					
		String sql="    SELECT  inst.id AS id, inst.JobId AS JobId, cm.CustName AS CustomerName, um.Emp_Name AS SI, pm.Name AS ProductName,"
				+ "inst.Site AS Site, inst.Location AS Location, inst.TE, inst.CreatedDate as CreatedDate , "
				+ " tm.TaskName as Stages,sic.Company, "
				+ " inst.CurrentStage AS Stages,inst.Remark, isc.Status AS Status FROM     installation inst "
				+ "	inner join customer_master cm on inst.CustomerId = cm.CustomerID "
				+ " inner join product_master pm on inst.ProductId = pm.ProductId "
				+ "	inner join user_master um on inst.SI = um.EmpId "
				+ "	inner join inc_status_codes isc on inst.StatusId = isc.Id "
				+ " left join task_master tm on inst.CurrentStage = tm.TaskId  "
				+ " left join si_company sic on um.SICompany = sic.Id "
				+ " where inst.IsDeleted = 'N' and inst.CreatedBy = "+userid+" and  StatusId IN (1 , 2, 5, 13,16)  ";
		
		//or inst.CurrentStatus =1) ...this was for completed jobs but not reqd , as installation shows only WIP jobs
		
		System.out.println("----sql getInstallationList----"+sql);
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql getInstallationList----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					remark="";
					
					ObjectNode objecttempNode =objectMapper.createObjectNode();
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
					objectInNode.put("Company",rs.getString("Company"));
					objectInNode.put("AllocDt",rs.getString("CreatedDate"));
										
					String CompleteDt=getJobCompletionDate(rs.getString("id"));
					
					objectInNode.put("CompleteDt",CompleteDt);
					
					//objectInNode.put("TE",rs.getString("TE"));
										
					arrTempNode=getLatestIncRemark(rs.getString("id"));
					System.out.println("arrTempNode upper"+arrTempNode);
					
					System.out.println("TE======"+rs.getString("TE"));
					
					String teName = getTEName(rs.getString("TE"));
					System.out.println("teName======="+teName);
					
					if(teName==null)
						teName="";
					objectInNode.put("TE",teName);
					
					
					
					if(arrTempNode.size()!=0)
					{
					remark=arrTempNode.get(0).asText();
					typeFlag=arrTempNode.get(1).asText();
					recId=arrTempNode.get(2).asText();
					
					System.out.println("arrTempNode remark"+remark);
					System.out.println("arrTempNode typeFlag"+typeFlag);
					System.out.println("arrTempNode recId"+recId);
					}
					
					if(remark.equals(""))
					remark = rs.getString("Remark");
					
					if(remark==null)
					remark="";
					
								
					
					objectInNode.put("Remark",remark);
					objectInNode.put("typeFlag",typeFlag);
					objectInNode.put("recId",recId);
					
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

	private String getTEName(String id) {
		// TODO Auto-generated method stub

		String flag = "false";
		Connection conn = null;
		ResultSet rs	= null;	
		PreparedStatement Stmt = null;
		String SQL_QUERY;
		String teNames = null;
		
		System.out.println("update teid==  "+id);
		

		try {
			
			//String strauth= pendingApproval.getAuthKey();
			conn = GetDBConnection.getConnection();
			
			SQL_QUERY = " SELECT um.EmpId,inc.id, inc.TE, um.Emp_Name as TEName FROM eci.installation inc left join  user_master um on inc.TE = um.EmpId where inc.TE = " +id ;
						
			System.out.println("QUERY For getTEName==  "+SQL_QUERY);
			
			Stmt = conn.prepareStatement(SQL_QUERY);
			
			
		      rs = Stmt.executeQuery(SQL_QUERY);
				
				while(rs.next()){

					 teNames = rs.getString("TEName") ;
					
				}
				
			}
			catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
			finally {
				GetDBConnection.closeRS(rs);
				GetDBConnection.closeStmt(Stmt);
				GetDBConnection.closeConn(conn);
			}
		System.out.println("QUERY For teNames==  "+teNames);
		return teNames;
	}
	@Override
	public ObjectNode getSubContractorList(int userid) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		//String sql = " SELECT EmpId, Emp_Name, RoleType FROM user_master where Role = '1' " ;
		//String sql = " SELECT distinct(um.EmpId),um.Emp_Name, um.RoleType FROM user_master um ,user_region ur where Role = '1' and um.EmpId=ur.UserId and regionId in (select regionId from user_region where userId= "+userid+" )" ;
		//change this query for get user according to region
		String sql = "SELECT distinct(um.EmpId),um.Emp_Name, um.RoleType, sic.Company FROM user_master um ,user_region ur, si_company sic where um.SICompany = sic.Id   and um.EmpId=ur.UserId  and "
				+ " regionId in (select regionId from user_region where userId= "+userid+" ) and Role = 1 ";
		
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;		
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql getSubContractorList-111---"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("EmpId",rs.getString("EmpId"));
					objectInNode.put("Emp_Name",rs.getString("Emp_Name"));
					objectInNode.put("Company",rs.getString("Company"));
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
	public ObjectNode insertInstallationtask(Installation installation) {
		// TODO Auto-generated method stub
		boolean flag = false,taskFlag=false, taskactionFlag = false, reduceBalcQtyflag = false,authFlg=false,flg=false;
		String StatusId, jobId,newjobId, POID, userId ;
		int siId;
		String strAuth="Authorised";
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		PreparedStatement pStmt = null;
		conn=null;
		
		jobId = getJobId();
		
		newjobId = "JOB"+jobId;
		
		POID		= getPoId(installation); //search for combination PO
		POID = POID.trim();
		
		
		if(!POID.isEmpty()) {
			
			StatusId = "1";
			reduceBalcQtyflag	= reduceBalcQty(installation,POID); //here if get status id 1, 2 etc then call and balc qty
			
		}else {
			
			StatusId = "14";
			System.out.println("StatusId is empty==== ");
			
		}
		
		try{
			
			siId = installation.getSiId(); 
			String strauth= installation.getAuthKey();
			System.out.println("strauth key installation"+strauth);
			
			System.out.println("user id customer"+tokService.getUserIdFromToken(strauth));
			System.out.println("getLocation_To====== "+installation.getLocation_To());
			
			System.out.println("getTypeofMigration====== "+installation.getType_of_Migration());
			System.out.println("getReleaseDt====== "+installation.getReleaseDt());
			
			userId = tokService.getUserIdFromToken(strauth);
			
			if(tokService.isTokenValid(strauth))
			{
			
			conn = GetDBConnection.getConnection();
			strauth= installation.getAuthKey();
			
			/*String SQL_QUERY = "INSERT INTO installation (JobId ,  CustomerId ,  ProductId ,  SI , Quantity ,  Site ,  Location ,  RegionId ,  CreatedBy ,  CreatedDate , StatusId , IsDeleted, POLineId, Site_To, Location_To, Type_of_Migration ) " + 
					"VALUES (  '"+newjobId+"' , ? , ? , ? , ? , ? , ? , ? ,'"+userId+"',now() , '"+StatusId+"' , 'N', '"+POID+"' ,? , ? , ? )";
			*/
			String SQL_QUERY = "INSERT INTO installation (JobId ,  CustomerId ,  ProductId ,  SI , Quantity ,  Site ,  Location ,  RegionId ,  CreatedBy ,CreatedDate , StatusId , IsDeleted,  "+
					 "  POLineId, Site_To, Location_To, Type_of_Migration, Ring_name, Plan_Id, IR_Number, SR_Number, Circle_Name, Release_Date, RFI_Date ,FromEngineer ,ToEngineer,Purpose) " + 
					"VALUES (  '"+newjobId+"' , ? , ? , ? , ? , ? , ? , ? ,'"+userId+"',now() , '"+StatusId+"' , 'N', '"+POID+"' ,? , ? , ?, ?, ? , ? , ? , ? , ? , ? ,?,?,?)";
			
			System.out.println("QUERY For insertPO "+SQL_QUERY);
			
			pStmt = conn.prepareStatement(SQL_QUERY,Statement.RETURN_GENERATED_KEYS);

		//	preparedStmt.setString(1, installation.getJobId());
			pStmt.setInt(1, installation.getCustomerId());
			pStmt.setInt(2, installation.getProductId());
			pStmt.setInt(3, installation.getSiId());
			pStmt.setInt(4,  installation.getQuantity());
			pStmt.setString(5,  installation.getSite());
			pStmt.setString(6, installation.getLocation());
			pStmt.setInt(7,  installation.getRegionId());
			pStmt.setString(8, installation.getSite_To());
			pStmt.setString(9,  installation.getLocation_To());
			pStmt.setString(10,  installation.getType_of_Migration());
			
			pStmt.setString(11,  installation.getRingname());
			pStmt.setString(12,  installation.getPlanId());
			pStmt.setString(13,  installation.getIrNumber());
			pStmt.setString(14,  installation.getSrnumber());
			pStmt.setString(15,  installation.getCirclename());
			pStmt.setString(16, installation.getReleaseDt());
			pStmt.setString(17,  installation.getRfiDt());
			pStmt.setString(18, installation.getFromEngineer());
			pStmt.setString(19,  installation.getToEngineer());
			pStmt.setString(20,  installation.getPurpose());
			
			pStmt.executeUpdate();
			
			rs = pStmt.getGeneratedKeys();
			int generatedId = 0;
			if (rs.next()) {
				generatedId = rs.getInt(1);
			}
			 
			System.out.println("Inserted task ID: " + generatedId);
			
			flag = true;
			if(flag==true)
			{
				taskFlag		= insertInstallationTask1(installation,generatedId, userId, siId);
				taskactionFlag	= insertInstallationAction(installation, generatedId, StatusId, userId);
				
			}
			}else
			{
				strAuth="UnAuthorised";
			}
			
			objectOutNode.put("result",flag);
			objectOutNode.put("message",strAuth);
			objectOutNode.put("PoFlag",StatusId); 
			
			//send notification to SI
			if(StatusId.equals("1"))
			{
  				System.out.println("Sendng notification to SI after ins creation- SI id==="+installation.getSiId());
				flg=sendNotification(installation.getSiId());
			}
			
			System.out.println("Notification sent flag"+flg);
			

		}catch (Exception e) { 
			// TODO: handle exception
			e.printStackTrace();
		}
		/*finally {
			GetDBConnection.closeRS(rs);
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}*/
		
		// TODO Auto-generated method stub
		return objectOutNode;
	}
	
	
	boolean sendNotification(int userId)
	{
		 ObjectNode objectOutNode =objectMapper.createObjectNode();
			// TODO Auto-generated method stub
			  String SERVER_KEY = "AAAAaoYBGNM:APA91bHAkqJXvRgBVWxRqRjzrOhJHKu-u3YQapcOG5zOh-H5IjxSg6ztAuo6VhidxqC3Nd9TTs6Z7CEBiW08Zx8c3Bdd5MqkmYywh1So4Hh1iDvZ19pJLCGTC4ow2xHbsAx55HYZX4hq";
		
			  boolean flag=false;
			  
			//String DEVICE_TOKEN = "dwl8bXiIc6Q:APA91bHeq-5-Z6Ku7qXsLEnMsjDK4j13kXkxM_jfTMR2iIKIAPmrNmCOw9qdnSn9ttO18ticC4KSe7mAffOFeH7V2dbGpQYP37aIaioE__SVnOHLPJy7j5_SAImESV-mdH-Xv69W8ZkE";
			  String DEVICE_TOKEN = "";
			  
			  String title = "ProApp Notification";
		      String message = "You got a new task";
		      Connection conn1 	= null;
			  Statement stmt 		= null;
			  ResultSet rs		= null;
			    
		      try 
		      {
		    	  
		    	String sql = "SELECT UserKey FROM userkey where UserId= " + userId  +" And isActive='Y' ";
			  		
		  			
  				conn1 = GetDBConnection.getConnection();
  				stmt = conn1.createStatement(); 
  				rs = stmt.executeQuery(sql);
  				
  				while(rs.next())
  				{
  					DEVICE_TOKEN=rs.getString("UserKey");
  				}
	  					
		      System.out.println("DEVICE_TOKEN--"+DEVICE_TOKEN);

		    	  
		      String pushMessage = "{\"data\":{\"title\":\"" +
		                title +
		                "\",\"message\":\"" +
		                message +
		                "\"},\"to\":\"" +
		                DEVICE_TOKEN +
		                "\"}";
			  
		        // Create connection to send FCM Message request.
		        URL url = new URL("https://fcm.googleapis.com/fcm/send");
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		        conn.setRequestProperty("Authorization", "key=" + SERVER_KEY);
		        conn.setRequestProperty("Content-Type", "application/json");
		        conn.setRequestMethod("POST");
		        conn.setDoOutput(true);

		        // Send FCM message content.
		        OutputStream outputStream = conn.getOutputStream();
		        outputStream.write(pushMessage.getBytes());
		        
		        System.out.println("sendNotification res code--"+conn.getResponseCode());
		        System.out.println("sendNotification res msg--"+conn.getResponseMessage());
		        
		        objectOutNode.put("resCode", conn.getResponseCode());
		        objectOutNode.put("resMessage", conn.getResponseMessage());
		        flag=true;
		        
		      }
		      catch(Exception e)
		      {
		    	  System.out.println("exception occured"+e.toString());
		    	  e.printStackTrace();
		      }
				
			 return flag;
	}

	private boolean reduceBalcQty(Installation installation, String pOID) {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		Statement stmt 	= null;
		ResultSet rs	= null;
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		conn=null;
		String balcqty = " ";
		String SQL_QUERY;
		
		try
		{
			 SQL_QUERY = "select Balace_Qty from purchaseorder where id = "+pOID.trim();
			
			 System.out.println("QUERY For SQL_QUERY=SQL_QUERY====  "+SQL_QUERY);
			
			 conn = GetDBConnection.getConnection();
			 stmt = conn.createStatement();
			
			 rs = stmt.executeQuery(SQL_QUERY);
			
			 while(rs.next()){
				
			 balcqty  = rs.getString("Balace_Qty");	 
			 System.out.println("----sql --balcqty-balcqty-----"+balcqty);
				
			 }
			
			 SQL_QUERY = "update purchaseorder set Balace_Qty = "+balcqty+" - 1 where id = "+pOID;
			
			 System.out.println("QUERY For purchaseorder====  "+SQL_QUERY);
			
			 			 preparedStmt = conn.prepareStatement(SQL_QUERY);
				
			 preparedStmt.executeUpdate();
				
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

	private String getPoId(Installation installation) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		PreparedStatement pStmt = null;
		conn=null;
		
		String poId = " ";
		
		System.out.println("QUERY For getCustomerId===  "+installation.getCustomerId() );
		System.out.println("QUERY For getProductId===  "+installation.getProductId() );
		System.out.println("QUERY For getRegionId===  "+installation.getRegionId() );
		
		/*String sql = "select POM.Id from purchaseorder POM where POM.IsDeleted='N' and POM.CustomerId= '"+installation.getCustomerId()+"' and  "
				+ " POM.ProductId = '"+installation.getProductId()+"' and POM.RegionId = '"+installation.getRegionId()+"' and CURDATE() between POM.PO_Date and POM.PO_EndDate "
				+ " order by Id limit 1 " ;*/
		// add this filter Balace_Qty != '0' for negative PO 
		String sql = "select POM.Id from purchaseorder POM where POM.IsDeleted='N' and POM.CustomerId= '"+installation.getCustomerId()+"' and  "
				+ " POM.ProductId = '"+installation.getProductId()+"' and POM.RegionId = '"+installation.getRegionId()+"' and Balace_Qty != '0' and "
			//			+ "CURDATE() >= POM.PO_Date AND CURDATE() <= POM.PO_EndDate    "
				+ "  CURDATE() between POM.PO_Date and POM.PO_EndDate "
				+ " order by Id limit 1 " ;
		
		System.out.println("----sql getPoId----"+sql);
		try {
			
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);

			while(rs.next()){
			
			//poId  = Integer.parseInt(rs.getString("Id"));	 
			poId  = rs.getString("Id");	 
				
			System.out.println("----sql --poId--"+poId);
			
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			GetDBConnection.closeRS(rs);
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closeConn(conn);
		}
		return poId;
		
		
	}
	

	private boolean insertInstallationAction(Installation installation, int generatedId, String statusId, String userId) {
		// TODO Auto-generated method stub
			PreparedStatement pStmt = null;		   
			boolean flag = false;
			
			String sql = " INSERT INTO installation_actions (IncId , Action , ActionDate , ActionBy ) VALUES(?, "+statusId+", Now(), "+userId+") " ;
			Connection conn = null;
	
			try
			{
					conn = GetDBConnection.getConnection();
			
					System.out.println("----sql insertInstallationAction----"+sql);
			
					pStmt = conn.prepareStatement(sql);
			
					pStmt.setInt(1, generatedId);
					pStmt.executeUpdate();
				
					flag=true;
		
			}catch (Exception e) {
				// TODO: handle exception
		e.printStackTrace();
			}
			/*finally {
				GetDBConnection.closeRS(rs);
				GetDBConnection.closePstmt(pStmt);
				GetDBConnection.closeConn(conn);
			}*/
			
			
			
			// TODO Auto-generated method stub
			return flag;
	}
	
	//delete installation
	@Override
	public ObjectNode deleteInstallation(Installation installation) {
		// TODO Auto-generated method stub
			PreparedStatement pStmt = null;		   
			boolean flag = false;
			Connection conn = null;
			 ObjectNode objectOutNode =objectMapper.createObjectNode();

			try
			{

					System.out.println("installation id===  "+installation.getId() );
					conn = GetDBConnection.getConnection();
					String  SQL_Delete = "DELETE FROM installation WHERE id="+installation.getId();
					System.out.println(SQL_Delete);
					pStmt = conn.prepareStatement(SQL_Delete);
					pStmt.execute();
						
					flag=true;
					objectOutNode.put("result",flag);
			}catch (Exception e) {
				// TODO: handle exception
					e.printStackTrace();
			}
			// TODO Auto-generated method stub
			return objectOutNode;
	}
	//
	
	
	

	private String getJobId() {
		// TODO Auto-generated method stub
		String jid = "";
		String sql = " select  max(id) as id FROM installation; " ;
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		try
		{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			
			System.out.println("----sql getJobId----"+sql);
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				jid = rs.getString("id");
				
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
	
		return jid;
		
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
			
			System.out.println("----sql getJobCompletionDate----"+sql);
			
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
	
	public boolean insertInstallationTask1(Installation installation,int generatedId, String userId, int siId) throws ClassNotFoundException, IOException, SQLException {
		
		PreparedStatement pStmt = null;
		boolean flag = false, atptestvalues = false;
		Connection conn = null;
		try{
			
			System.out.println("userId userId  userId===== "+userId);
			
			List<Task> task=installation.getTask();
			
			System.out.println("insertInstallationTask taskid===== "+task);
			
			
			conn = GetDBConnection.getConnection();
			String SQL_QUERY = "INSERT INTO installation_task (IncId , TaskId,TaskComplete, CreatedBy, CreatedDate, SI) VALUES (? , ? ,'N', "+userId+", now(), "+siId+")";
			
			System.out.println("QUERY For insertInstallation Task "+SQL_QUERY);
			pStmt = conn.prepareStatement(SQL_QUERY);
			
			 for(Task tasklist : task) 
			 {
				pStmt.setInt(1, generatedId);
				pStmt.setInt(2, Integer.parseInt(tasklist.getTask()));
				pStmt.executeUpdate();
				
				System.out.println("QUERY For tasklist.getTask() "+tasklist.getTask());
				
				
				atptestvalues	= insertInstallationAtpTestValues(installation,generatedId, tasklist.getTask());
				
				/*if(tasklist.getTask().equals("1")) {
					
					System.out.println("entered here ");
					
				}
				if(tasklist.getTask().equals("2")) {
					
					System.out.println("entered here ");
					atptestvalues	= insertInstallationAtpTestValues(installation,generatedId, tasklist.getTask());
				}	*/	
				
				
			 }
			 flag=true;
			 
			 
			 
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		/*finally {
			GetDBConnection.closePstmt(pStmt);
			GetDBConnection.closeConn(conn);
		}*/
		
		
		// TODO Auto-generated method stub
		return flag;
		
	}
	
	private boolean insertInstallationAtpTestValues(Installation installation, int generatedId, String taskId) {
		// TODO Auto-generated method stub
		boolean flag = false;
		Connection conn = null;
		Statement stmt 	= null;
		System.out.println("insertInstallationTask generatedId===== "+generatedId);
		
		System.out.println("insertInstallationTask ProductId===== "+installation.getProductId());
		
		System.out.println("insertInstallationTask getType_of_Migration==== "+installation.getType_of_Migration());
		
		
		System.out.println("insertInstallationTask getTask=getTask==== "+taskId);
		int rs;
		
		try{
		
			conn = GetDBConnection.getConnection();
			
			/*
			 * String SQL_QUERY =
			 * " INSERT INTO inctaskwiseatp (IncId,	TaskId, TestId ,TestType,Status) SELECT "
			 * +generatedId+", "+taskId+
			 * ", at.TestId ,at.TestType , 'N' FROM atp_tests at, atp_tests_product atp " +
			 * "	WHERE at.TestId = atp.TestId  AND atp.ProductId = "
			 * +installation.getProductId()+ "   AND atp.CustomerId = "
			 * +installation.getCustomerId()+" AND at.TaskId = " +taskId;
			 */
			
			String SQL_QUERY = " INSERT INTO inctaskwiseatp (IncId,	TaskId, TestId ,TestType,Status) SELECT "+generatedId+", "+taskId+ ", at.TestId ,at.TestType , 'N' FROM atp_tests at, atp_tests_product atp " + 
							"	WHERE at.TestId = atp.TestId AND atp.ProductId = " +installation.getProductId()+
							"   AND atp.CustomerId = " +installation.getCustomerId()+" AND at.TaskId = " +taskId;
		
			System.out.println("QUERY For insertInstallationAtpTestValues "+SQL_QUERY);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeUpdate(SQL_QUERY);
					
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

	@Override
	public ObjectNode getInstallationCode() {
		
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		String result="error";
		String sql = "select * from inc_Status_Codes" ;
				
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql getInstallationList----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("StatusCode",rs.getString("Id"));
					objectInNode.put("Status",rs.getString("Status"));
					
					
					arrayOutNode.add(objectInNode);
				}
				
				
				result="success";
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
			objectOutNode.put("data", arrayOutNode);
		
		return objectOutNode;
	}
	
	@Override
	public ObjectNode updateremark(Installation installation) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		boolean flag = false;
		Connection conn = null;
		ResultSet rs	= null;
		
		String userId = "",TypeFlag="", sql=""; 
		String strAuth="Authorised";
		PreparedStatement preparedStmt= null;
		try
		{
			
			String strauth= installation.getAuthKey();
			System.out.println("strauth key customer strauth"+strauth);
			
			if(strauth!=null)
			{
			userId=tokService.getUserIdFromToken(strauth);
			}
			System.out.println("strauth key userId == "+userId);
			
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			
			String SQL_QUERY = "UPDATE installation SET Remark  = '"+installation.getRemark()+"'  where  id = " +installation.getId() ;
			
			System.out.println("QUERY For getRemark==  "+installation.getRemark());
			
			System.out.println("QUERY For getTypeFlag=="+installation.getTypeFlag());
			
			System.out.println("QUERY For getRecId==  "+installation.getRecId());
			
			System.out.println("QUERY For updateremark==  "+SQL_QUERY);
			preparedStmt = conn.prepareStatement(SQL_QUERY);
			
			preparedStmt.executeUpdate();
			
			TypeFlag = installation.getTypeFlag();
			
			if(TypeFlag.equalsIgnoreCase("SI")) {
				
				System.out.println("SI SI SI==  ");
				sql = "update si_tejobupdates set Remark = '"+installation.getRemark()+"'  where id = " +installation.getRecId() ;
				
			}else if(TypeFlag.equalsIgnoreCase("IA")) {
				
				
				sql = "update installation_actions set Remark = '"+installation.getRemark()+"' where id = " +installation.getRecId() ;
			}
			
			System.out.println("QUERY For updateremark==SQL_QUERY:  "+sql);
			
			preparedStmt = conn.prepareStatement(sql);
			
			preparedStmt.executeUpdate();
			
			
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
	public ObjectNode cancelInstallation(Installation installation) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		boolean flag = false;
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		String userId = ""; 
		String strAuth="Authorised", saveRemark;
		
		try
		{
			String strauth= installation.getAuthKey();
			System.out.println("strauth key cancelInstallation"+strauth);
			
			if(strauth!=null)
			{
			userId=tokService.getUserIdFromToken(strauth);
			}
			System.out.println("strauth cancelInstallation key getId == "+installation.getId());
			System.out.println("strauth cancelInstallation key getRemark == "+installation.getRemark());
			
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			String SQL_QUERY = "UPDATE installation SET StatusId  = '4' where  id = " +installation.getId() ;
			
			
			System.out.println("QUERY For cancelInstallation==  "+SQL_QUERY);
			
			preparedStmt = conn.prepareStatement(SQL_QUERY);
			
			preparedStmt.executeUpdate();
			
			saveRemark = saveRemark(installation.getId(), installation.getRemark(), userId);
			
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
		finally 
		{
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		// TODO Auto-generated method stub
		return objectOutNode;
	}

	private String saveRemark(int id, String remark, String userId) {
		// TODO Auto-generated method stub

		String flag = "false";
		Connection conn = null;
		PreparedStatement pStmt = null;
		String SQL_QUERY;
		
		System.out.println("update id==  "+id);
		System.out.println("update remark===  "+remark);
		

		try {
			
			//String strauth= pendingApproval.getAuthKey();
			conn = GetDBConnection.getConnection();
			
			SQL_QUERY = "Insert into installation_actions(IncId, Remark, ActionDate, ActionBy, Action) VALUES ( '"+id+"' , '"+remark+"' , now(), '"+userId+"' , 4) ";
						
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
	public ObjectNode getUserWiseRegion(int id) {
		boolean flag = false;
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		String result="error";
		
		String sql = "select ur.UserId as id, ur.RegionId, rm.RegionName from  user_region ur, region_master rm where rm.RegionId = ur.RegionId and rm.IsDeleted = 'N' and ur.UserId =" +id+ "  ";
		
		System.out.println("----installationService id----"+id);
		
		System.out.println("----installationService----"+sql);
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
					
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					ObjectNode objectInNode = objectMapper.createObjectNode();

					objectInNode.put("RegionId", rs.getString("RegionId"));
					objectInNode.put("RegionName", rs.getString("RegionName"));
					
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
				GetDBConnection.closeRS(rs);
				GetDBConnection.closeStmt(stmt);
				GetDBConnection.closeConn(conn);
			}
			objectOutNode.put("result", result);
			
		return objectOutNode;
	}
	
	public static String replaceNullString(String input) {
		if(input.equals(""))
			return "";
		else
		  return input == null ? "" : input;
		}

	@Override
	public boolean insertInstallationTask(Installation installation, int generatedId)
			throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	

	
	
	
	
	
	@Override
	public ArrayNode getLatestIncRemark(String strJobId) {
	//public String getLatestIncRemark(String strJobId) {
		// TODO Auto-generated method stub
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;
		ArrayNode objectOutNode =objectMapper.createArrayNode();

		int i = 0;
		String sql ="",strRmrk="",flg="",id="", outPut="";
				
		sql = "SELECT ste.id,ste.Action,ste.Remark,ste.Date as ADate, 'SI' as flag FROM si_tejobupdates ste where ste.IncId = "+strJobId +" and ste.Remark is not null "
				+ "union select ia.id,ia.Action,ia.Remark,ia.ActionDate as ADate, 'IA' as flag from installation_actions ia where ia.IncId = "+strJobId +" and ia.Remark "
				+ "is not null order by ADate desc limit 1" ;
				
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
							
							
							outPut=strRmrk+"#$#"+flg+"#$#"+id;
							
							objectOutNode.add(strRmrk);
							objectOutNode.add(flg);
							objectOutNode.add(id);
							
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
				
				System.out.println("----objectOutNode lower----"+objectOutNode);
				
				return objectOutNode;
			//	return outPut;
				
	}

	
	@Override
	  public ObjectNode generatePdfAction(Installation installation)
			    throws DocumentException, IOException
			  {
			    HttpServletRequest request = null;
			    PrintWriter out = null;
			    
			    GenerateInvoicePdf generateInvoicePdf = new GenerateInvoicePdf();
			    System.out.println("---In -generatePdfAction----");
			    String incid = installation.getPdfid();
			    
			    String Cname = installation.getCustomerName();
			    
			    String Pname = installation.getProductName();
			    System.out.println("---In -generatePdfAction-Cname-incid--" + incid);
			    
			    System.out.println("---In -generatePdfAction--Pname--" + Pname + "" + Cname);
			    
			    
			    String filePath = generateInvoicePdf.generateInvoice(incid, Pname, Cname);
			    
			    System.out.println(incid);
			    return null;
			  }

	@Override
	public ObjectNode generatePdfAction1() throws DocumentException, IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
