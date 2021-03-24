package com.eci.daoImp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;

import javax.servlet.ServletContext;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.eci.bean.Installation;
import com.eci.bean.SI;
import com.eci.bean.User;
import com.eci.dao.SIDao;
import com.eci.dao.UserDao;
import com.eci.bean.InstallationAction;
import com.eci.util.GetDBConnection;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;



@Repository
public class SIDaoImp implements SIDao{

	/*private PreparedStatement preparedStmt = null;
	private Connection conn = null;
	private Statement stmt 	= null;
	private ResultSet rs	= null;*/
	
	GetDBConnection getConn = new GetDBConnection();
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	ServletContext context;
	
	@Override
	public ObjectNode getSIRegionsList(Installation inc) {
		// TODO Auto-generated method stub
		
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		String result="error";
		
		String sql = "SELECT distinct(inc.RegionId) ,rm.RegionName   FROM eci.installation inc ,region_master rm  where inc.RegionId=rm.RegionId "
				+ " and SI = "+inc.getSiId() +"  order by inc.RegionId";
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement(); 
				
				String UPLOAD_FOLDEdR=context.getRealPath("/Uploads"); 
				System.out.println("----sql Si wise region list----"+sql);
				System.out.println("----UPLOAD_FOLDEdR----"+UPLOAD_FOLDEdR);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("RegionId", rs.getString("RegionId"));
					objectInNode.put("RegionName", rs.getString("RegionName"));
					
					arrayOutNode.add(objectInNode);
				
				}
					
					objectOutNode.put("data", arrayOutNode.toString());
					objectOutNode.put("result", result);

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
		
		/*System.out.println("welcom to SI  regions list");
				   
				ObjectNode objectOutNode =objectMapper.createObjectNode();
				ArrayNode arrayOutNode = objectMapper.createArrayNode();
				
				int i = 0;
				String result="error";
				
						ObjectNode objectInNode1 = objectMapper.createObjectNode();
						objectInNode1.put("RegionId","1");
						objectInNode1.put("RegionName", "Thane");
						arrayOutNode.add(objectInNode1);
						
						ObjectNode objectInNode2 = objectMapper.createObjectNode();
						objectInNode2.put("RegionId","2");
						objectInNode2.put("RegionName", "Navi Mumbai");
						arrayOutNode.add(objectInNode2);
						
						
						ObjectNode objectInNode3 = objectMapper.createObjectNode();
						objectInNode3.put("RegionId","3");
						objectInNode3.put("RegionName", "Bhandup");
						arrayOutNode.add(objectInNode3);
						
						
						ObjectNode objectInNode4 = objectMapper.createObjectNode();
						objectInNode4.put("RegionId","4");
						objectInNode4.put("RegionName", "Ghatkoper");
						arrayOutNode.add(objectInNode4);
						
						
						ObjectNode objectInNode5 = objectMapper.createObjectNode();
						objectInNode5.put("RegionId","5");
						objectInNode5.put("RegionName", "Kurla");
						arrayOutNode.add(objectInNode5);
		

						objectOutNode.put("data", arrayOutNode.toString());
						result= "success";
						
				System.out.println("regions list=====objectOutNode========"+objectOutNode);
				return objectOutNode;*/
	}



	@Override
	public ObjectNode getSIRegionWiseJobs(Installation inc) {
		// TODO Auto-generated method stub
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		String result="error";
		
		 Connection conn 	= null;
		 Statement stmt 	= null;
		 ResultSet rs		= null;
		
		String sql ="select inc.id,inc.JobId,cm.CustName,pm.Name,inc.site,inc.Location ,inc.Site_To,inc.Location_To, inc.StatusId , "
				+ " inc.FromEngineer,inc.ToEngineer from installation inc, "
				+ "customer_master cm , product_master pm where inc.CustomerId = cm.CustomerID and inc.ProductId=pm.ProductId "
				+ "and inc.StatusId in (1,2,13,17) and  SI = "+inc.getSiId() +" and RegionId ="+inc.getRegionId();
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement(); 
				
				System.out.println("----sql Si wise region list----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{
					ObjectNode objectInNode1 = objectMapper.createObjectNode();
					objectInNode1.put("IncId", rs.getString("id"));
					objectInNode1.put("CustName",rs.getString("CustName"));
					objectInNode1.put("JobId", rs.getString("JobId"));
					objectInNode1.put("Site",rs.getString("site"));
					objectInNode1.put("Location", rs.getString("Location"));
					objectInNode1.put("Product",rs.getString("Name"));
					objectInNode1.put("Status", rs.getString("StatusId"));
					objectInNode1.put("SiteTo",rs.getString("Site_To"));
					objectInNode1.put("LocationTo", rs.getString("Location_To"));
					objectInNode1.put("FromEngineer",rs.getString("FromEngineer"));
					objectInNode1.put("ToEngineer", rs.getString("ToEngineer"));
					
					arrayOutNode.add(objectInNode1);
				
				}
				result= "success";
				objectOutNode.put("data", arrayOutNode.toString());
				objectOutNode.put("result", result);

					
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
		
		/*
		
		System.out.println("welcom to SI RegionWise Jobs");
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		int i = 0;
		String result="error";
		
		
		
				ObjectNode objectInNode1 = objectMapper.createObjectNode();
				
				objectInNode1.put("CustName","Rohit Shah");
				objectInNode1.put("JobId", "RS101");
				objectInNode1.put("Site","Thane/Cadbury");
				objectInNode1.put("Location", "Thane");
				objectInNode1.put("Product","Abc");
				objectInNode1.put("Status", "New");
				arrayOutNode.add(objectInNode1);
				
				ObjectNode objectInNode2 = objectMapper.createObjectNode();
				objectInNode2.put("CustName","Kranti Sharma");
				objectInNode2.put("JobId", "KS101");
				objectInNode2.put("Site","Andhri/swipz");
				objectInNode2.put("Location", "Andhri");
				objectInNode2.put("Product","PQR");
				objectInNode2.put("Status", "Accept");
				arrayOutNode.add(objectInNode2);
				
				ObjectNode objectInNode3 = objectMapper.createObjectNode();
				objectInNode3.put("CustName","Ravi Varma");
				objectInNode3.put("JobId", "RV101");
				objectInNode3.put("Site","Thane/Bhandup");
				objectInNode3.put("Location", "Thane");
				objectInNode3.put("Product","XYZ");
				objectInNode3.put("Status", "ATE");
				arrayOutNode.add(objectInNode3);
				
				
				objectOutNode.put("result", arrayOutNode.toString());*/

			
		
		
		/*System.out.println("getSIRegionWiseJobs=====objectOutNode========"+objectOutNode);
		return objectOutNode;*/
	
	}

	

	@Override
	public boolean takeIncAction(InstallationAction IA) {
		boolean flag = false;
		boolean flag1 = false;
		
		PreparedStatement preparedStmt = null;
		Connection conn 	= null;
		
		
		try{
			
			System.out.println("inc id"+IA.getIncId());
			System.out.println("inc action"+IA.getAction());
			System.out.println("inc getActionBy"+IA.getActionBy());

			conn = GetDBConnection.getConnection();
			
			String SQL_QUERY = "INSERT INTO installation_actions(IncId,Action,ActionDate,ActionBy,Remark) "+
					" VALUES (?,?,NOW(),?,?)";
			
			System.out.println("QUERY For SIAcceptJob "+SQL_QUERY);
			
			preparedStmt = conn.prepareStatement(SQL_QUERY);

			preparedStmt.setInt(1, IA.getIncId());
			preparedStmt.setString(2, IA.getAction());
			preparedStmt.setInt(3, IA.getActionBy());
			preparedStmt.setString(4, IA.getRemark());
			
			preparedStmt.executeUpdate();
			
			flag = true;
			
			if(flag==true)
				flag1=UpdateInstallationStatus(IA);
			
			
		}catch (Exception e) { 
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		// TODO Auto-generated method stub
		return flag1;
	}

	@Override
	public boolean UpdateInstallationStatus(InstallationAction IA) 
	{
		boolean flag = false;
		
		 PreparedStatement preparedStmt = null;
		 Connection conn 	= null;
		 Statement stmt 	= null;
			ResultSet rs		= null;
			int userId = 0;//here it is SI
			InstallationDaoImp insdaoimp=new InstallationDaoImp();

		try
		{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			
			String query ="";
			
			if(IA.getAction().equals("5")) // assign TE
			{
				query = "Update installation set  StatusId = "+IA.getAction()+" , TE = "+IA.getTeId()
						+ ", UpdatedDate = NOW() ,UpdateBY = '"+IA.getActionBy()+"' where id = "+ IA.getIncId() ;
			}
			else
			{
			query = "Update installation set  StatusId = "+IA.getAction()
					+ ",UpdatedDate = NOW() ,UpdateBY = '"+IA.getActionBy()+"' where id = "+ IA.getIncId() ;
			}
			
			System.out.println("UpdateInstallationStatus query"+query);
			
			preparedStmt = conn.prepareStatement(query);
		      // execute the java preparedstatement
		    preparedStmt.executeUpdate();
		    flag=true;
		    
		    if((flag==true)&&(IA.getAction().equals("5"))) //when SI assigns TE send notification to TE
		    {
		    	
		    	String sql = "SELECT TE FROM installation where id = "+IA.getIncId();
	  			
				stmt = conn.createStatement(); 
  				rs = stmt.executeQuery(sql);
  				
  				while(rs.next())
  				{
  					userId=rs.getInt("TE");
  				}
  				System.out.println("Sendng notification to TE - TE id"+sql+"==="+userId);

				boolean flg=insdaoimp.sendNotification(userId);
		    	
		    }
		    
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			GetDBConnection.closeConn(conn);
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeStmt(stmt);
			
		}
		return flag;
	}
	

	@Override
	public boolean SIRejectJob(SI si) {
		// TODO Auto-generated method stub
		boolean flag = true;
		System.out.println("SIRejectJob=====si id======="+si.getSi_Id());		
		System.out.println("SIRejectJob=====job id======="+si.getJob_Id());
		
		System.out.println("return flag======="+flag);		

		
		return flag;
	}



	@Override
	public boolean AssignTE(SI si) {
		
		boolean flag = true;
		
		
		System.out.println("AssignTE=====si id======="+si.getSi_Id());	
		System.out.println("AssignTE=====job id======="+si.getJob_Id());
		System.out.println("AssignTE=====TE id======="+si.getTe_Id());
	
		
		return flag;
	}



	@Override
	public boolean SIUpdateTask(MultipartFile file,InstallationAction IA) {
		// TODO Auto-generated method stub
		boolean flag = false;
		boolean flag1 = false;
		
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		
		String fpath="";
		try
		{
			System.out.println("File-------"+file);
	    //    Path path = Paths.get(context.getRealPath("Uploads") + file.getOriginalFilename());
			if(file!=null)
			{
			if(!file.isEmpty())
			{				
				String PATH=System.getProperty("user.dir");
				System.out.println("----UPLOAD_FOLDEdR si si----"+PATH); //D:\SpringJavaAsh1\Eci\Eci
				
				PATH=PATH.replace("\\","\\\\");
				
			  String condir = PATH.concat("/webapps"); //server
				
			//	String condir = PATH.concat("/webapps1"); //local 
				
				System.out.println("----UPLOAD_FOLDEdR--condir--"+condir);
				
				 String directoryName = condir.concat("/UploadPhoto"); //server
			    
			//    String directoryName = PATH.concat("/UploadPhoto"); //local

			   // String directoryName = PATH.concat("/UploadPhoto");
			    
			 //    directoryName ="C:\\Users\\admin\\Documents\\workspace-spring-tool-suite-4-4.1.0.RELEASE\\Eci\\UploadPhoto";
			  //   directoryName ="C:\\Users\\admin\\workspace\\ECI_WEB\\UploadPhoto";
			
			    File directory = new File(directoryName);
			    if (! directory.exists()){
			    	
			    	System.out.println("creating folder");
			    	
			        directory.mkdir();
			        // If you require it to make the entire directory path including parents,
			        // use directory.mkdirs(); here instead.
			    }
				
			    DateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); // Just the year, with 2 digits
				String formattedDate = df.format(Calendar.getInstance().getTime());			    			    
			    String filename=IA.getIncId()+"_"+formattedDate+"_"+file.getOriginalFilename();
				
			    
				File convertFile=new File(directoryName+"/"+filename);
				convertFile.createNewFile();
				FileOutputStream fout=new FileOutputStream(convertFile);
				fout.write(file.getBytes());
				fout.close();
				fpath=directoryName+"/"+filename;

			}
			}
		//return new ResponseEntity<>("hurray uploaded",HttpStatus.OK);
		
		System.out.println("SIUpdateTask=====inc id======="+IA.getIncId());			
		System.out.println("SIUpdateTask=====action by======="+IA.getActionBy());
		System.out.println("SIUpdateTask=====visit flag======="+IA.getWithVisit());
		System.out.println("SIUpdateTask=====remark======="+IA.getRemark());
		System.out.println("SIUpdateTask=====status code======="+IA.getAction());
		System.out.println("SIUpdateTask=====getReason======"+IA.getReason());
		
		System.out.println("SIUpdateTask=====image fpath======="+fpath);
		
		conn = GetDBConnection.getConnection();
		
		String SQL_QUERY = "INSERT INTO si_tejobupdates(IncId,SI_TE,withVisit,Remark,Date,Action,ImagePath,Latitude,Longitude,Reason ) "+
				" VALUES (?,?,?,?,NOW(),?,?,?,?,? )";
		
		System.out.println("QUERY For SIUpdateTask "+SQL_QUERY);
		
		preparedStmt = conn.prepareStatement(SQL_QUERY);

		preparedStmt.setInt(1, IA.getIncId());
		preparedStmt.setInt(2, IA.getActionBy());
		preparedStmt.setString(3, IA.getWithVisit());
		preparedStmt.setString(4, IA.getRemark());		
		preparedStmt.setString(5, IA.getAction());
		preparedStmt.setString(6, fpath);
		preparedStmt.setString(7, IA.getLatitude());
		preparedStmt.setString(8, IA.getLongitude());
		preparedStmt.setString(9, IA.getReason());
		
		preparedStmt.executeUpdate();
		
		flag=true;
		
		if(flag==true)
		{	
			// 6 means SI update and save and 7 means SI update & chkout 
			// 8 means SI update and save and 9 means SI update & chkout
			// so ststus to be be modified in installation table for check out i.e 7 & 9
			
			if((IA.getAction().equals("7"))||(IA.getAction().equals("9")))
			{
				flag1=UpdateInstallationStatus(IA);
			}
		}
		
		}
		catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		finally {
			GetDBConnection.closeConn(conn);
			GetDBConnection.closePstmt(preparedStmt);
			
		}
		return flag;
	}
	
	@Override
	public boolean SIAcceptJob(InstallationAction IA) {
		// TODO Auto-generated method stub
		return true;	
				
		
	}


	@Override
	public ObjectNode getTEList(SI si) {
		
		Statement stmt 	= null;
		ResultSet rs	= null;
		Connection conn = null;
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		String result="error";
		
		String sql = "SELECT * FROM eci.user_master where siCompany = (select siCompany from user_master where EmpId = "+si.getSi_Id()+")  and Role = 2";
		try
		{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement(); 
			System.out.println("----getTEList----"+sql);
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				ObjectNode objectInNode = objectMapper.createObjectNode();
				
				objectInNode.put("TEId", rs.getString("EmpId"));
				objectInNode.put("TEName", rs.getString("Emp_Name"));
				
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
	public ObjectNode getSIsummary(SI si) {
		// TODO Auto-generated method stub
		Statement stmt 	= null;
		ResultSet rs	= null;
		Connection conn = null;
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		String result="error";
		
		String sql = "SELECT inc.RegionId ,inc.SI,rm.RegionName,um.Emp_Name, SUM(if(inc.StatusId = 2, 1, 0)) as SIAccepted, "
				+ "SUM(if(inc.StatusId = 5, 1, 0)) as Assigned, "
				+ "SUM(if(inc.StatusId = 16, 1, 0)) as WIP, "
				+ "SUM(if(inc.StatusId in(3,4,7,9),1,0)) as ExitReject "
				+ "FROM eci.installation inc ,region_master rm,user_master um "
				+ "where inc.RegionId=rm.RegionId and inc.SI=um.EmpId and SI  ="+si.getSi_Id()+" group by RegionId  order by RegionId ; ";
		try
		{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement(); 
			System.out.println("----getTEList----"+sql);
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				ObjectNode objectInNode = objectMapper.createObjectNode();
				
				objectInNode.put("RegionId", rs.getString("RegionId"));
				objectInNode.put("RegionName", rs.getString("RegionName"));
				objectInNode.put("SIAccepted", rs.getString("SIAccepted"));
				objectInNode.put("WIP", rs.getString("WIP"));
				objectInNode.put("ExitReject", rs.getString("ExitReject"));
				objectInNode.put("Assigned", rs.getString("Assigned"));
				
				
				
				
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
	public ObjectNode getReasonList(SI si) {
		// TODO Auto-generated method stub
		
			
			Connection conn 	= null;
			Statement stmt 		= null;
		    ResultSet rs		= null;
			
			ObjectNode objectOutNode =objectMapper.createObjectNode();
			ArrayNode arrayOutNode = objectMapper.createArrayNode();
			
			String result="error";
			
			String sql = "SELECT * FROM reason_list;";
			
				try
				{
					conn = GetDBConnection.getConnection();
					stmt = conn.createStatement(); 
					
					System.out.println("----sql Si wise region list----"+sql);
					
					rs = stmt.executeQuery(sql);
					
					while(rs.next())
					{
						ObjectNode objectInNode = objectMapper.createObjectNode();
						
						objectInNode.put("id", rs.getString("id"));
						objectInNode.put("reason_name", rs.getString("reason_name"));
						
						arrayOutNode.add(objectInNode);
					
					}
						
						objectOutNode.put("data", arrayOutNode.toString());
						objectOutNode.put("result", result);

						result= "success";
						System.out.println("----arrayOutNode.toString()----"+arrayOutNode.toString());
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
