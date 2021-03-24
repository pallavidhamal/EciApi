package com.eci.daoImp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.eci.bean.Installation;
import com.eci.bean.InstallationAction;
import com.eci.dao.TEDao;
import com.eci.util.GetDBConnection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class TEDaoImp implements TEDao{

	/*private PreparedStatement preparedStmt = null;
	private Connection conn = null;
	private Statement stmt 	= null;
	private ResultSet rs	= null;*/
	
	GetDBConnection getConn = new GetDBConnection();
	ObjectMapper objectMapper = new ObjectMapper();
	
	
	/*
	 * @Autowired JavaMailSender javaMailSender;
	 */
	JsonNode rootNode;
	JsonNode rootNode1;
	@Override
	public ObjectNode getTERegionsList(Installation inc) {
		// TODO Auto-generated method stub
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;
		
				int i = 0;
				String result="error";
				
				ObjectNode objectOutNode =objectMapper.createObjectNode();
				ArrayNode arrayOutNode = objectMapper.createArrayNode();
				
				String sql = "SELECT distinct(inc.RegionId) ,rm.RegionName   FROM eci.installation inc ,region_master rm  where inc.RegionId=rm.RegionId "
						+ " and TE = "+inc.getTeId() +"  order by inc.RegionId";
				
				try
				{
						conn = GetDBConnection.getConnection();
						stmt = conn.createStatement(); 
						
						System.out.println("----sql TE wise region list----"+sql);
						
						rs = stmt.executeQuery(sql);
						
						while(rs.next())
						{
							ObjectNode objectInNode = objectMapper.createObjectNode();
							
							objectInNode.put("RegionId", rs.getString("RegionId"));
							objectInNode.put("RegionName", rs.getString("RegionName"));
							
							arrayOutNode.add(objectInNode);
						
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
				
					/*	ObjectNode objectInNode1 = objectMapper.createObjectNode();
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
						arrayOutNode.add(objectInNode5);*/
						
				//System.out.println("getSIUsersList=====objectOutNode========"+objectOutNode);
	}



	@Override
	public ObjectNode getTERegionWiseJobs(Installation inc) {
		// TODO Auto-generated method stub
		
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;	
	    
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		String result="error";
		
		String sql ="select inc.id,inc.JobId,cm.CustName,pm.Name,inc.site,inc.Location,inc.Type_of_Migration, inc.StatusId ,inc.Site_To,inc.Location_To, pm.Type , "
				+ "inc.FromEngineer,inc.ToEngineer from installation inc, customer_master cm , product_master pm where inc.CustomerId = cm.CustomerID and inc.ProductId=pm.ProductId "
				+ "and inc.StatusId in (5,16) and  TE = "+inc.getTeId() +" and RegionId ="+inc.getRegionId();
		
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
					objectInNode1.put("Type", rs.getString("Type"));
					objectInNode1.put("Type_of_Migration", rs.getString("Type_of_Migration"));
					
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
		
		
	/*	ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		int i = 0;
		String result="error";
		
					
				ObjectNode objectInNode1 = objectMapper.createObjectNode();
				
				objectInNode1.put("CustName","Rohit Shah");
				objectInNode1.put("JobId", "RS101");
				objectInNode1.put("Site","Thane/Cadbury");
				objectInNode1.put("Location", "Thane");
				objectInNode1.put("Product","Abc");
			//	objectInNode1.put("Status", "New");
				arrayOutNode.add(objectInNode1);
				
				ObjectNode objectInNode2 = objectMapper.createObjectNode();
				objectInNode2.put("CustName","Kranti Sharma");
				objectInNode2.put("JobId", "KS101");
				objectInNode2.put("Site","Andhri/swipz");
				objectInNode2.put("Location", "Andhri");
				objectInNode2.put("Product","PQR");
			//	objectInNode2.put("Status", "N");
				arrayOutNode.add(objectInNode2);
				
				ObjectNode objectInNode3 = objectMapper.createObjectNode();
				objectInNode3.put("CustName","Ravi Varma");
				objectInNode3.put("JobId", "RV101");
				objectInNode3.put("Site","Thane/Bhandup");
				objectInNode3.put("Location", "Thane");
				objectInNode3.put("Product","XYZ");
			//	objectInNode3.put("Status", "ATE");
				arrayOutNode.add(objectInNode3);
				
				
				objectOutNode.put("result", arrayOutNode.toString());

			
				result= "success";
		
		
		System.out.println("getSIRegionWiseJobs=====objectOutNode========"+objectOutNode);
		return objectOutNode;*/
	
	}



	@Override
	public ObjectNode getTEJobWiseTasks(Installation inc) {
		// TODO Auto-generated method stub
		
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;	
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		String result="error";
		
		String sql ="SELECT it.IncId,it.TaskId,tm.TaskName,it.TaskComplete,inc.ProductId,inc.CustomerId FROM eci.installation_task it ,installation inc, task_master tm where "
				+ " it.TaskId = tm.TaskId and it.IncId=inc.id and IncId = "+inc.getJobId() ;
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement(); 
				
				System.out.println("----sql Si wise region list----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{
					ObjectNode objectInNode1 = objectMapper.createObjectNode();
					objectInNode1.put("JobId", rs.getString("IncId"));
					objectInNode1.put("TaskId",rs.getString("TaskId"));
					objectInNode1.put("TaskName", rs.getString("TaskName"));
					objectInNode1.put("TaskStatus",rs.getString("TaskComplete"));
					objectInNode1.put("ProductId",rs.getString("ProductId"));
					objectInNode1.put("CustomerId",rs.getString("CustomerId"));

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
				
				
	/*	System.out.println("welcome to TE task list getTEJobWiseTasks");
		
		System.out.println("TE id"+si.getTid());
		
		System.out.println("TE id"+si.getJob_Id());
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		int i = 0;
		String result="error";
		
				ObjectNode objectInNode1 = objectMapper.createObjectNode();
				objectInNode1.put("TaskId","1");
				objectInNode1.put("JobId","1");
				objectInNode1.put("TaskName", "Pre-Installation Survey");
				objectInNode1.put("TaskStatus", "NEW");
				arrayOutNode.add(objectInNode1);
				
				ObjectNode objectInNode2 = objectMapper.createObjectNode();
				objectInNode2.put("TaskId","2");
				objectInNode2.put("JobId","1");
				objectInNode2.put("TaskName", "Power On");
				objectInNode2.put("TaskStatus", "NEW");
				arrayOutNode.add(objectInNode2);
				
				ObjectNode objectInNode3 = objectMapper.createObjectNode();
				objectInNode3.put("TaskId","3");
				objectInNode3.put("JobId","1");
				objectInNode3.put("TaskName", "Commisioning");
				objectInNode3.put("TaskStatus", "NEW");
				arrayOutNode.add(objectInNode3);
				
				ObjectNode objectInNode4 = objectMapper.createObjectNode();
				objectInNode4.put("TaskId","4");
				objectInNode4.put("JobId","1");
				objectInNode4.put("TaskName", "Standalone accptance"); 
				objectInNode4.put("TaskStatus", "NEW");
				arrayOutNode.add(objectInNode4);
				
				
				ObjectNode objectInNode5 = objectMapper.createObjectNode();
				objectInNode5.put("TaskId","5");
				objectInNode5.put("JobId","1");
				objectInNode5.put("TaskName", "Integration");   
				objectInNode5.put("TaskStatus", "NEW");
				arrayOutNode.add(objectInNode5);

				ObjectNode objectInNode6 = objectMapper.createObjectNode();
				objectInNode6.put("TaskId","6");
				objectInNode6.put("JobId","1");
				objectInNode6.put("TaskName", "CS-Survey");   
				objectInNode6.put("TaskStatus", "NEW");

				arrayOutNode.add(objectInNode6);

				objectOutNode.put("data", arrayOutNode.toString());
				result= "success";
				
		System.out.println("getTEJobWiseTasks============="+objectOutNode);
		return objectOutNode;*/
		
	}



	@Override 
	public ObjectNode getTETaskTests(Installation inc) 
	{
		// TODO Auto-generated method stub
		
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;	
	    
	    		System.out.println("getTypeofMigration====== "+inc.getType_of_Migration());
				System.out.println("getTETaskTests job id "+inc.getJobId());		
				System.out.println("getTETaskTests task id "+inc.getTaskId());
				System.out.println("getTETaskTests getStagecategory id "+inc.getStagecategoryId());
				
				
				
				ObjectNode objectOutNode =objectMapper.createObjectNode();
				// ObjectNode objectInNode2 =objectMapper.createObjectNode();

				
				ArrayNode arrayOutNode = objectMapper.createArrayNode();
				ArrayNode arrayOutNode1 = objectMapper.createArrayNode();
				
				ObjectNode objectSlotNode =objectMapper.createObjectNode();
				ArrayNode arraySlotNode = objectMapper.createArrayNode();
								
				String strValues="",noOfSlots="",grpId="0";
				String result="error";
				String sql;
				
				/*String sql ="SELECT  ita.IncId,ita.TaskId,ita.TestId ,ita.Status ,ita.TestValue as tval,ats.parameter ,ats.ParamType,ats.ReferenceValue ,atp.ExpectOP ,ats.TestType,"
						+ "ats.ValidationType,ats.TestValueType,ita.GroupId FROM eci.inctaskwiseatp ita "
						+ "INNER JOIN atp_tests ats ON ita.TestId = ats.TestId  "						
						+ "inner join atp_tests_product atp on atp.TestId = ita.TestId WHERE "
						+ "ita.IncId = "+inc.getJobId()+" AND ita.TaskId = "+ inc.getTaskId() +" and atp.ProductId="+inc.getProductId() + " and atp.customerId="+inc.getCustomerId();*/
				
				if(inc.getTaskId()==7) {
					
				/*	 sql ="select * from (SELECT  ats.Seq, ita.IncId,ita.TaskId,ita.TestId ,ita.Status , sc.Id as stageCatid, ita.TestValue as tval,ats.parameter ,ats.ParamType,ats.ReferenceValue ,atp.ExpectOP ,ats.TestType, " + 
					 		"	ats.ValidationType,ats.TestValueType,ita.GroupId FROM eci.inctaskwiseatp ita" + 
					 		" INNER JOIN atp_tests ats ON ita.TestId = ats.TestId  					" + 
					 		" inner join atp_tests_product atp on atp.TestId = ita.TestId " + 
					 		" INNER JOIN stagecategory sc ON sc.Id = ats.TaskCategoryId  " + 
					 		" WHERE ita.IncId = "+inc.getJobId()+" AND ita.TaskId = "+ inc.getTaskId() +" and atp.ProductId=  "+inc.getProductId() +"  and  ISNULL(NULLIF(ats.TestValueType,'')) " + 
					 		" and atp.customerId= "+inc.getCustomerId()+"  union all " + 
					 		" SELECT  ats.Seq, ita.IncId,ita.TaskId,ita.TestId ,ita.Status , sc.Id as stageCatid, ita.TestValue as tval,ats.parameter ,ats.ParamType,ats.ReferenceValue ,atp.ExpectOP ,ats.TestType, " + 
					 		" ats.ValidationType,ats.TestValueType,ita.GroupId FROM eci.inctaskwiseatp ita " + 
					 		" INNER JOIN atp_tests ats ON ita.TestId = ats.TestId  					" + 
					 		" inner join atp_tests_product atp on atp.TestId = ita.TestId " + 
					 		" INNER JOIN stagecategory sc ON sc.Id = ats.TaskCategoryId  " + 
					 		"  WHERE ita.IncId = "+inc.getJobId()+" AND ita.TaskId = "+ inc.getTaskId() +" and atp.ProductId=  "+inc.getProductId()+ " and  ats.MIGType = '"+inc.getType_of_Migration()+"' and atp.customerId= "+inc.getCustomerId()+" )" + 
					 		"  mig order by GroupId, Seq ";*/
					 
					 
				sql="SELECT  ita.id,ats.Seq, ita.IncId,ita.TaskId,ita.TestId ,ita.Status , sc.Id as stageCatid, ita.TestValue as tval,ats.parameter ,ats.ParamType,ats.ReferenceValue ,atp.ExpectOP ,ats.TestType, " + 
			 		" ats.ValidationType,ats.TestValueType,ita.GroupId FROM eci.inctaskwiseatp ita " + 
			 		" INNER JOIN atp_tests ats ON ita.TestId = ats.TestId  					" + 
			 		" inner join atp_tests_product atp on atp.TestId = ita.TestId " + 
			 		" INNER JOIN stagecategory sc ON sc.Id = ats.TaskCategoryId  " + 
			 		"  WHERE ita.IncId = "+inc.getJobId()+" AND ita.TaskId = "+ inc.getTaskId() +" and atp.ProductId=  "+inc.getProductId()+ "  and atp.customerId= "+inc.getCustomerId()+" " + 
			 		"  and ( ats.MIGType = '"+inc.getType_of_Migration()+"' or ats.MIGType is null ) order by GroupId, Seq ";
					
								 
				//union query removed as new column for migration type used ...pallavi
					//ats.TestValueType = '"+inc.getType_of_Migration()+"' changed to MIGType as migration type moved from testvalue type to MIGType
					
				}else {
					
					 sql ="SELECT  ita.id,ita.IncId,ita.TaskId,ita.TestId ,ita.Status , sc.Id as stageCatid, ita.TestValue as tval,ats.parameter ,ats.ParamType,ats.ReferenceValue ,atp.ExpectOP ,ats.TestType,"
							+ "ats.ValidationType,ats.TestValueType,ats.Seq,ita.GroupId FROM eci.inctaskwiseatp ita "
							+ "INNER JOIN atp_tests ats ON ita.TestId = ats.TestId  "						
							+ "inner join atp_tests_product atp on atp.TestId = ita.TestId  INNER JOIN stagecategory sc ON sc.Id = ats.TaskCategoryId   WHERE "
							+ "ita.IncId = "+inc.getJobId()+" AND ita.TaskId = "+ inc.getTaskId() +" and atp.ProductId="+inc.getProductId() + " and atp.customerId="+inc.getCustomerId()+"  order by ita.GroupId,  ats.Seq";
					
				}
						//ats.sequence  group by ita.GroupId
				//+ " and  sc.Id = "+ inc.getStagecategoryId();
						
				System.out.println("----getTETaskTests--here--"+sql);
					try
					{
						conn = GetDBConnection.getConnection();
						stmt = conn.createStatement(); 
						
						System.out.println("----getTETaskTests--hr--"+sql);
						
						rs = stmt.executeQuery(sql);
						
						while(rs.next())
						{
							ObjectNode objectInNode1 = objectMapper.createObjectNode();
							
							ObjectNode objectInNode2 = objectMapper.createObjectNode();
							
							String stemp=rs.getString("tval");
							
							//System.out.println("--stemp-------------"+stemp);
							//System.out.println("--TestValueType-------------"+rs.getString("TestValueType"));
							//System.out.println("--TestType-------------"+rs.getString("TestType"));
							
							if(stemp!=null)
								stemp=stemp.trim();
	
							String strTestValueType="";
							if (rs.getString("TestValueType") != null && !(rs.getString("TestValueType").isEmpty()))
							{
								strTestValueType=rs.getString("TestValueType");
							}
							
							String strTestType="";
							
							if (rs.getString("TestType") != null && !(rs.getString("TestType").isEmpty()))
							{
								strTestType=rs.getString("TestType");
							
							objectInNode1.put("JobId", rs.getString("IncId"));
							objectInNode1.put("TaskId",rs.getString("TaskId"));
							objectInNode1.put("TestId",rs.getString("TestId"));
							objectInNode1.put("TestValue",stemp);
							
							objectInNode1.put("TestName", rs.getString("parameter"));
							objectInNode1.put("ParamType",rs.getString("ParamType"));
							
							objectInNode1.put("ReferenceValue",rs.getString("ReferenceValue"));
							objectInNode1.put("ExpectOP",rs.getString("ExpectOP"));
							objectInNode1.put("Status",rs.getString("Status"));
							objectInNode1.put("TestType",strTestType);
							objectInNode1.put("ValidationType",rs.getString("ValidationType")); 
							objectInNode1.put("TestCatgory",rs.getString("stageCatid"));
							objectInNode1.put("TestValueType",rs.getString("TestValueType"));
							objectInNode1.put("Id",rs.getString("id"));
							objectInNode1.put("SeqId",rs.getString("Seq"));

							
							 grpId=rs.getString("GroupId");
						//	System.out.println("grpId "+grpId);
							
							if(grpId==null)
								grpId="1";
							
							objectInNode1.put("GroupId",grpId);
							noOfSlots=rs.getString("GroupId");
							
							strValues=getTestValues(rs.getString("TestId"),inc.getProductId(),strTestType,strTestValueType);
							
							//System.out.println( "test values======= "+strValues);
							objectInNode1.put("TestValues",strValues);
							
							arrayOutNode.add(objectInNode1);
							
							}
							else
							{
								objectInNode2.put("JobId", rs.getString("IncId"));
								objectInNode2.put("TaskId",rs.getString("TaskId"));
								objectInNode2.put("TestId",rs.getString("TestId"));
								objectInNode2.put("TestValue",stemp);

								objectInNode2.put("TestName", rs.getString("parameter"));
								objectInNode2.put("ParamType",rs.getString("ParamType"));
								
								objectInNode2.put("ReferenceValue",rs.getString("ReferenceValue"));
								objectInNode2.put("ExpectOP",rs.getString("ExpectOP"));
								objectInNode2.put("Status",rs.getString("Status"));
								objectInNode2.put("Status",rs.getString("Status"));
								objectInNode2.put("ValidationType",rs.getString("ValidationType"));
								objectInNode2.put("TestCatgory",rs.getString("stageCatid"));
								objectInNode1.put("GroupId",rs.getString("GroupId"));
								objectInNode1.put("SeqId",rs.getString("Seq"));

								objectInNode2.put("TestType",strTestType);
								
								strValues=getTestValues(rs.getString("TestId"),inc.getProductId(),strTestType,strTestValueType);
								
								objectInNode2.put("TestValues",strValues);
								arrayOutNode1.add(objectInNode2);
									
								//objectOutNode.put("data", arrayOutNode1.toString());
							}
							
						//	System.out.println("arrayOutNode ==== "+arrayOutNode);
						//	System.out.println("arrayOutNode1 ===== "+arrayOutNode1);
							
						}
						result= "success";
						
						objectOutNode.put("Slots", arrayOutNode.toString());
						objectOutNode.put("All", arrayOutNode1.toString());
						objectOutNode.put("noOfSlots", grpId);
	
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
		
				ObjectNode objectInNode1 = objectMapper.createObjectNode();
				objectInNode1.put("TestId","1");
				objectInNode1.put("JobId","1");
				objectInNode1.put("TestCat", "General Requirement");
				objectInNode1.put("Test", "Site Type");
				objectInNode1.put("TestStatus", "NDone");				
				objectInNode1.put("TestType", "DD");
				arrayOutNode.add(objectInNode1);
								
				ObjectNode objectInNode2 = objectMapper.createObjectNode();
				objectInNode2.put("TestId","2");
				objectInNode2.put("JobId","1");
				objectInNode1.put("TestCat", "General Requirement");
				objectInNode2.put("Test", "Rack Type");
				objectInNode2.put("TestStatus", "NDone");
				objectInNode2.put("TestType", "DD");			
				arrayOutNode.add(objectInNode2);
				
				ObjectNode objectInNode3 = objectMapper.createObjectNode();
				objectInNode3.put("TestId","3");
				objectInNode3.put("JobId","1");
				objectInNode1.put("TestCat", "General Requirement");				
				objectInNode3.put("Test", "Outdoor Cabinet Make");
				objectInNode3.put("TestStatus", "NDone");
				objectInNode3.put("TestType", "DD");				
				arrayOutNode.add(objectInNode3);
				
				
				ObjectNode objectInNode4 = objectMapper.createObjectNode();
				objectInNode4.put("TestId","4");
				objectInNode4.put("JobId","1");
				objectInNode4.put("TestCat", "General Requirement");				
				objectInNode4.put("Test", "Ambient Temperature");
				objectInNode4.put("TestStatus", "Done");
				objectInNode4.put("TestType", "TXT");				
				arrayOutNode.add(objectInNode4);
				
				
			//	*******************
				
				
				ObjectNode objectInNode5 = objectMapper.createObjectNode();
				objectInNode5.put("TestId","5");
				objectInNode5.put("JobId","1");
				objectInNode5.put("TestCat", "Gauge Requirement");
				objectInNode5.put("Test", "XRAP Grounding cable  guage sqmm");
				objectInNode5.put("TestStatus", "NDone");				
				objectInNode5.put("TestType", "DD");
				arrayOutNode.add(objectInNode5);
								
				ObjectNode objectInNode6 = objectMapper.createObjectNode();
				objectInNode6.put("TestId","6");
				objectInNode6.put("JobId","1");
				objectInNode6.put("TestCat", "Gauge Requirement");
				objectInNode6.put("Test", "Mux Grounding cable  guage sqmm");
				objectInNode6.put("TestStatus", "NDone");
				objectInNode6.put("TestType", "DD");			
				arrayOutNode.add(objectInNode6);
				
				ObjectNode objectInNode7 = objectMapper.createObjectNode();
				objectInNode7.put("TestId","7");
				objectInNode7.put("JobId","1");
				objectInNode7.put("TestCat", "Gauge Requirement");				
				objectInNode7.put("Test", "Main Grounding cable  guage sqmm");
				objectInNode7.put("TestStatus", "NDone");
				objectInNode7.put("TestType", "DD");				
				arrayOutNode.add(objectInNode7);
				
				
				ObjectNode objectInNode8 = objectMapper.createObjectNode();
				objectInNode8.put("TestId","8");
				objectInNode8.put("JobId","1");
				objectInNode8.put("TestCat", "Gauge Requirement");				
				objectInNode8.put("Test", "Earth pit grounding resistance");
				objectInNode8.put("TestStatus", "Done");
				objectInNode8.put("TestType", "TXT");				
				arrayOutNode.add(objectInNode8);*/
				
			

			/*	objectOutNode.put("data", arrayOutNode.toString());
				result= "success";
				
				System.out.println("getTETaskWiseTests============="+objectOutNode);
				return objectOutNode;*/
		
	}


//MultipartFile file,MultipartFile file1,
	@SuppressWarnings("resource")
	@Override
	public ObjectNode updateTETestStatus(String data,JavaMailSender javaMailSender) {  
		// TODO Auto-generated method stub
		
		Connection conn 	= null;
		Statement stmt 		= null;
	    PreparedStatement preparedStmt = null;
		ResultSet rs=null;
	    
		System.out.println("welcome to update TE TestStatus");
		boolean flag=false;
		String testId="",msg="";
		String query1="";
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		
		String fpath1="",fpath2="";
		
		try 
		{
			
			//fpath1=uploadPhoto(file);
			//fpath2=uploadPhoto(file1);
			
		rootNode = objectMapper.readTree(data);
		System.out.println("\n---------rootNode ---------data----------"+rootNode);
		
	    System.out.println("-------job_Id-------------------"+rootNode.path("job_Id").asText());
	    System.out.println("--------taskid-------------------"+rootNode.path("taskid").asText());
	    System.out.println("--------testData-------------------"+rootNode.path("testData").asText());
	    
	    String strJobId=rootNode.path("job_Id").asText();
	    String strTaskId=rootNode.path("taskid").asText();
	    String action = rootNode.path("action").asText();  // if U then update & continue is clicked , if E then update & exit
	    String remark = rootNode.path("remark").asText();
	    
	    
	    JsonNode testData = rootNode.get("testData");	    	    
	    ArrayNode testData1 = (ArrayNode) testData;
	    
	    
	 
	    
		System.out.println("-----phoneNumbersNode1-----home------"+testData1.size());
		
		conn = GetDBConnection.getConnection();
		stmt = conn.createStatement();
		String query="",actionCode="";
		
		switch(action){    
		case "UH":    msg="ATP test data saved";
		 break;   
		case "UF":     msg="ATP test data saved,Stage Completed ,Can go to next test";
		 break;  
		case "EH":     msg="Data removed for this ATP test ,Job closed";
			 break;  
		case "EF":   msg="ATP test data saved,Stage Completed ,Job closed";  
		 break;  
		case "RH":     msg="Additional day requested";
			 break;  
	
		default:     
		}    
		
		
		if((action.equals("EF"))||((action.equals("EH"))))
				actionCode="10";
		else if(action.equals("RH"))
				actionCode="11";
		else if(action.equals("UH"))
			actionCode="16";
				
				
/*	s&C	half data    UH	only save the data             ===
		full          UF	u have completed the stage ,will not be able to change the values,  now you can go to next
			
s&E		half          EH	u have not filled complete ATP, if you exit it will delete data and this job will be closed, exiting a job
		 full          EF	save data , update stage , don’t go to next step
			
req add day		half           RH	save the data , with remark, will be seen in pending approvals
		full            FF	you have filled whole atp , so you cant go for requesting additional day
*/
		
		if(action.equals("EH"))
		{
			query = "Update inctaskwiseatp set  Status = '' , TestValue = NULL , "
					+ " UpdateBy = 1 , UpdatedDate = NOW() where IncId = " + strJobId + " and TaskId=" +strTaskId ;   
			
			//blank testvalue was creating pro
			
			System.out.println("EH query"+query);
			
			preparedStmt = conn.prepareStatement(query);			
	        preparedStmt.executeUpdate();
			
		}
		
		if(action.equals("UH")||action.equals("UF")||(action.equals("EF"))||(action.equals("RH")))
		{	
			for(int i=0;i<testData1.size();i++)
			{
				testId=	testData1.get(i).path("testid").asText();
					
				System.out.println("test id"+testData1.get(i).path("testid").asText());
				System.out.println("value"+testData1.get(i).path("Value").asText());
				System.out.println("checked flag"+testData1.get(i).path("chked").asText());
				
				
				String testValue=testData1.get(i).path("Value").asText().trim();
				
			/*	if(testValue.equals("photo1"))
				{
					testValue=fpath1;
				}else if(testValue.equals("photo2"))
				{
					testValue=fpath2;
				}*/
				
				/*if((strTaskId.equals("7"))||(strTaskId.equals("9")))
				{
					System.out.println("taskid---"+strTaskId);
					System.out.println("testValue---"+testValue+"---");

					if(!testValue.equals(""))
					{						
					query = "Update inctaskwiseatp set  Status = ? ,TestValue = ? ,"
							+ " UpdateBy = 1 , UpdatedDate = NOW() where IncId = " + strJobId + " and TaskId=" +strTaskId +" and TestId="+testId;
					
					System.out.println("update te test status migration/channel test value not blank query"+query);
					
					preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, testData1.get(i).path("chked").asText());
					preparedStmt.setString(2, testValue);
			        preparedStmt.executeUpdate();
					}
					
				}
				else
				{*/				
				query = "Update inctaskwiseatp set  Status = ? ,TestValue = ? ,"
						+ " UpdateBy = 1 , UpdatedDate = NOW() where IncId = " + strJobId + " and TaskId=" +strTaskId +" and TestId="+testId;
				
				System.out.println("update te test status query"+query);
				
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, testData1.get(i).path("chked").asText());
				preparedStmt.setString(2, testValue);
			//	preparedStmt.setString(3, testData1.get(i).path("Value").asText());
				

				
		        preparedStmt.executeUpdate();
				//}
			
			}
			
			
			//new addition 
			if(rootNode.has("slotsRemovedData"))
			{
				JsonNode slotsRemovedData = rootNode.get("slotsRemovedData");
	    		System.out.println("---slotsRemovedData---"+slotsRemovedData);

				
			    ArrayNode slotRemData = (ArrayNode) slotsRemovedData;
			    for(int i=0;i<slotRemData.size();i++) 
				{
			    	ArrayNode tempNode = (ArrayNode)slotRemData.get(i);
			    	for(int j=0;j<tempNode.size();j++)
					{
			    		String Id=tempNode.get(j).path("Id").asText();
			    		
			    		System.out.println("---deleting Id---"+Id);

						  String sql = "delete from inctaskwiseatp where Id = " + Id ;
						  preparedStmt = conn.prepareStatement(sql);
						  preparedStmt.execute();
						 
					}
				}
			}
			//new addition
			
			if(rootNode.has("slotData"))
			{	    
			    
					/*
					 * String sql = "delete from inctaskwiseatp where IncId = " + strJobId +
					 * " and TaskId=" +strTaskId +" and TestType='S'"; preparedStmt =
					 * conn.prepareStatement(sql); preparedStmt.execute();
					 */
				
				
				JsonNode slotData = rootNode.get("slotData");
			    ArrayNode slotData1 = (ArrayNode) slotData;
			
				System.out.println("slotData1 size"+slotData1.size());
				System.out.println("slotData1 size"+slotData1);
				String strId="", strDbGrpId="";
				int groupId=1,dbGrpId=0;
				for(int i=0;i<slotData1.size();i++) //full slot outer array
				{
					ArrayNode tempNode = (ArrayNode)slotData1.get(i);
					System.out.println("tempNOde--"+tempNode);
					
					
					query="select max(GroupId) groupId from inctaskwiseatp where IncId= "+strJobId;
					stmt = conn.createStatement(); 
					
					System.out.println("----sql take max group id----"+query);
					
					rs = stmt.executeQuery(query);
					
					while(rs.next())
					{
						groupId=rs.getInt("groupId");						
						System.out.println("----groupId MAX----"+groupId);
					}
					
					
					for(int j=0;j<tempNode.size();j++)// one slot wise data
					{
					//System.out.println("xxxx"+tempNode.get(j).path("testid").asText());
					strId=tempNode.get(j).path("Id").asText();
					System.out.println("Id--"+strId+"---");

						
					testId=	tempNode.get(j).path("testid").asText();
				
					if(j==0)
					{
						  
						strDbGrpId=tempNode.get(j).path("GroupId").asText();	//////////					
						System.out.println("strDbGrpId--"+strDbGrpId+"--");
						
						if(strDbGrpId.equalsIgnoreCase("null") || strDbGrpId.isEmpty())
						{
							groupId++;
							System.out.println("groupId plus plus--"+groupId);
						}
						
								
					}	 
					
					System.out.println("tempNode test id"+tempNode.get(j).path("testid").asText());
					System.out.println("tempNode value"+tempNode.get(j).path("Value").asText());
					System.out.println("tempNode checked flag"+tempNode.get(j).path("chked").asText());
					
					
					if(strId != null && !strId.isEmpty())
					{
						System.out.println("333");
						String strTestValue=tempNode.get(j).path("Value").asText();
						
						if(strTestValue != null && !strTestValue.isEmpty())
						{
								query = "Update inctaskwiseatp set  Status = ? ,TestValue = ? , GroupId = ? , "
										+ " UpdateBy = 1 , UpdatedDate = NOW() where IncId = " + strJobId + " and TaskId=" +strTaskId +" and TestId="+testId + " and Id="+strId;
								
								System.out.println("update te test status query111"+query);
								
								preparedStmt = conn.prepareStatement(query);
								preparedStmt.setString(1,tempNode.get(j).path("chked").asText());
								preparedStmt.setString(2,strTestValue);
								preparedStmt.setInt(3, Integer.parseInt(tempNode.get(j).path("GroupId").asText()));
						        preparedStmt.executeUpdate();
						        
						}
						else
						{
						        query = "Update inctaskwiseatp set  Status = ? , GroupId = ? ,"
										+ " UpdateBy = 1 , UpdatedDate = NOW() where IncId = " + strJobId + " and TaskId=" +strTaskId +" and TestId="+testId + " and Id="+strId;
								
								System.out.println("update te test status query222"+query);
								
								preparedStmt = conn.prepareStatement(query);
								preparedStmt.setString(1,tempNode.get(j).path("chked").asText());
								preparedStmt.setInt(2, Integer.parseInt(tempNode.get(j).path("GroupId").asText()));
						        preparedStmt.executeUpdate();
						}
					
					}
					else
					{
						  System.out.println("444");
						//this means it is a new slot
						query = "insert into inctaskwiseatp (IncId,TaskId, TestId ,TestType,TestValue,Status,GroupId) values (?,?,?,?,?,?,?)";
						
						System.out.println("insert into inctaskwiseatp for slot"+query);
						
						preparedStmt = conn.prepareStatement(query);
						preparedStmt.setString(1, strJobId);
						preparedStmt.setString(2,strTaskId);
						preparedStmt.setString(3,testId);
						preparedStmt.setString(4,"S");
						preparedStmt.setString(5, tempNode.get(j).path("Value").asText());
						preparedStmt.setString(6, tempNode.get(j).path("chked").asText());
						preparedStmt.setString(7, Integer.toString(groupId));
				        preparedStmt.executeUpdate();
						
					}
			        
					}
			       // groupId++;
				}
			
			}
		
		}
		flag=true;
		
		
		if(flag==true)
		{
			flag=false;
			preparedStmt=null;
			
			if(action.equals("UH"))
			{
				query1 = "Update installation set  StatusId= ? "
						+ " , UpdatedDate = NOW()  where id = " + strJobId  ;
				System.out.println("update installation stage query"+query1);

				preparedStmt = conn.prepareStatement(query1);
				preparedStmt.setString(1, "16");				
		        preparedStmt.executeUpdate();				
				
			}
			
			
			
			if((action.equals("UF"))||(action.equals("EF"))) //if save & exit or save & continue with full data i.e task is completed , so update status in installation_task
			{
				 query = "Update installation_task set  TaskComplete = 'Y' ,TaskCompletionDate = NOW(), "
						+ " UpdatedBy = 1 , UpdatedDate = NOW() where IncId = " + strJobId + " and TaskId=" +strTaskId ;
				
				System.out.println("update te test status query"+query);
				
				preparedStmt = conn.prepareStatement(query);
		        preparedStmt.executeUpdate();
		        
		        
		        //check if all tasks done/or not accdly update CurrentStatus in installation
		        String taskStatus=CheckTasksStatus(strJobId);
		        
		        
		        //similarly needs to update in installation table for current stage. 
		        
		        if(action.equals("UF"))
		        {   //for te exits code is 10 
		        	
		        	String strApendQry="";
		        	if(taskStatus.equals("1"))//i.e if job is completed 
        			{
		        		strApendQry=", StatusId= '15' ";
        			}		        	
		        	else
		        	{
		        		strApendQry=", StatusId= '16' ";
		        	}
		        	
		        		query1 = "Update installation set  CurrentStage = ? ,CurrentStatus= ? "
								+ " , UpdatedDate = NOW() "+strApendQry+  " where id = " + strJobId  ;
						System.out.println("update installation stage query"+query1);

						preparedStmt = conn.prepareStatement(query1);
						preparedStmt.setString(1, strTaskId);
						preparedStmt.setString(2, taskStatus);
						
				        preparedStmt.executeUpdate();
				        
				      
				        if(taskStatus.equals("1")) //i.e job completed
				        {
				         String SQL_QUERY = "INSERT INTO installation_actions(IncId,Action,ActionDate) "+
				        		 			" VALUES (?,?,NOW())";
						
						System.out.println("QUERY For SIAcceptJob "+SQL_QUERY);
						
						preparedStmt = conn.prepareStatement(SQL_QUERY);

						preparedStmt.setInt(1, Integer.parseInt(strJobId));
						preparedStmt.setString(2, "15");//15 for job completes
						//preparedStmt.setInt(3, 1);// need to give te id
						preparedStmt.executeUpdate();
				        
				        }      
				        
				        
				        
		        }
		        
			}
			
			
			
			
			if((action.equals("EF"))||(action.equals("RH"))||(action.equals("EH")))
	        {
				String SQL_QUERY="";
				//check if all tasks done/or not accdly update CurrentStatus in installation
		        String taskStatus=CheckTasksStatus(strJobId);
		        
		        
		        if(taskStatus.equals("1")) //i.e job completed
		        {
		         SQL_QUERY = "INSERT INTO installation_actions(IncId,Action,ActionDate) "+
						" VALUES (?,?,NOW())";
				
				System.out.println("QUERY For SIAcceptJob "+SQL_QUERY);
				
				preparedStmt = conn.prepareStatement(SQL_QUERY);

				preparedStmt.setInt(1, Integer.parseInt(strJobId));
				preparedStmt.setString(2, "15");//15 for job completes
				//preparedStmt.setInt(3, 1);// need to give te id
				preparedStmt.executeUpdate();
		        
		        }
		        else
		        {
		        	SQL_QUERY = "INSERT INTO installation_actions(IncId,Action,ActionDate,ActionBy,Remark) "+
							" VALUES (?,?,NOW(),?,?)";
					
					System.out.println("QUERY For SIAcceptJob "+SQL_QUERY);
					
					preparedStmt = conn.prepareStatement(SQL_QUERY);

					preparedStmt.setInt(1, Integer.parseInt(strJobId));
					preparedStmt.setString(2, actionCode);//10 for te exits
					preparedStmt.setInt(3, 1);// need to give te id
					preparedStmt.setString(4, remark);
					preparedStmt.executeUpdate();
		        }
		        
		        
		        
		        
				
				if((action.equals("EF"))||(action.equals("EH")))  //do not update for (action.equals("RH"))
				{
					if(taskStatus.equals("1"))
						actionCode="15";  // this means te exits with full job completion
					
				query1 = "Update installation set  CurrentStage = ? ,CurrentStatus= ? "
						+ " ,StatusId="+actionCode+", UpdatedDate = NOW() where id = " + strJobId  ;
				System.out.println("update installation stage query"+query1);

				preparedStmt = conn.prepareStatement(query1);
				preparedStmt.setString(1, strTaskId);
				preparedStmt.setString(2, taskStatus);
		        preparedStmt.executeUpdate();
		        
				}
	        
	        }
			
			if(action.equals("EF"))
	        {
				
				String qry="select SI,JobId from installation where id= "+strJobId;
				stmt = conn.createStatement(); 
				
				System.out.println("----sql take max group id----"+query);
				
				ResultSet rs1 = stmt.executeQuery(qry);
				int SiD = 0;
				String jobId="";
				while(rs1.next())
				{
					 SiD=rs1.getInt("SI");	
					 jobId=rs1.getString("JobId");	
					System.out.println("----groupId MAX----"+SiD);
				}
				boolean flg=sendNotification(SiD,jobId);
				
				System.out.println("pm notification flg"+flg);
				
				
				String qry2="select Emp_Email from user_master where EmpId= "+SiD;
				
				System.out.println("----sql take max group id----"+query);
				
				ResultSet rs2 = stmt.executeQuery(qry2);
				
				String siMailId="";
				while(rs2.next())
				{
					siMailId=rs2.getString("Emp_Email");	
					System.out.println("----to siMailId----"+siMailId);
				}
				
				flg=sendMail(siMailId,jobId,javaMailSender);
				
				
	        }
			
			objectOutNode.put("result","success");
			objectOutNode.put("message",msg);
			
		}
		flag=true;
		
	  }
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		finally {
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		
		return objectOutNode;
	}
	




	/*@Override
	public boolean SIUpdateTask(SI si) {
		// TODO Auto-generated method stub
		return false;
	}*/

	//sends notification to SI when TE completes the job
	boolean sendNotification(int userId,String jobId)
	{
		 ObjectNode objectOutNode =objectMapper.createObjectNode();
			// TODO Auto-generated method stub
			  String SERVER_KEY = "AAAAaoYBGNM:APA91bHAkqJXvRgBVWxRqRjzrOhJHKu-u3YQapcOG5zOh-H5IjxSg6ztAuo6VhidxqC3Nd9TTs6Z7CEBiW08Zx8c3Bdd5MqkmYywh1So4Hh1iDvZ19pJLCGTC4ow2xHbsAx55HYZX4hq";
		
			  boolean flag=false;
			  
			//String DEVICE_TOKEN = "dwl8bXiIc6Q:APA91bHeq-5-Z6Ku7qXsLEnMsjDK4j13kXkxM_jfTMR2iIKIAPmrNmCOw9qdnSn9ttO18ticC4KSe7mAffOFeH7V2dbGpQYP37aIaioE__SVnOHLPJy7j5_SAImESV-mdH-Xv69W8ZkE";
			  String DEVICE_TOKEN = "";
			  
			  String title = "ProApp Notification";
		      String message = jobId + " Completed ";
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
	
	
	
	
	
	
	
	
	@Override
	public ObjectNode getTeTaskCategory(Installation inc) {
		// TODO Auto-generated method stub
		
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;
	    
				int i = 0;
				String result="error";
				
				ObjectNode objectOutNode =objectMapper.createObjectNode();
				ArrayNode arrayOutNode = objectMapper.createArrayNode();
				
				System.out.println("----sql getTeTaskCategory-type---"+inc.getType());
				
				//String sql = "select * from stagecategory where StageId =  "+inc.getTaskId();
				
				String sql = "select * from stagecategory where StageId =  '"+inc.getTaskId()+"' and CatFor in( 'B' , '"+inc.getType()+"' ) " ;
				try
				{
						conn = GetDBConnection.getConnection();
						stmt = conn.createStatement(); 
						
						System.out.println("----sql getTeTaskCategory----"+sql);
						
						rs = stmt.executeQuery(sql);
						
						while(rs.next())
						{
							ObjectNode objectInNode = objectMapper.createObjectNode();
							
							objectInNode.put("CategoryId", rs.getString("Id"));
							objectInNode.put("Category", rs.getString("Category"));
							objectInNode.put("TaskId", rs.getString("StageId"));
							objectInNode.put("CatFor", rs.getString("CatFor"));

							arrayOutNode.add(objectInNode);
						
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
				
	}				

	@Override
	public String getTestValues(String testId,int ProductId,String TestType,String strTestValueType) {
		// TODO Auto-generated method stub
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;

				int i = 0;
				String result="error";
				String strString="--Select--,";
				
				ObjectNode objectOutNode =objectMapper.createObjectNode();
				ArrayNode arrayOutNode = objectMapper.createArrayNode();
				String sql ="";
				
				//condition removed as slot tests values in slottesvalues table
				
				if((TestType.equals("S"))&&(strTestValueType.equals("P")) )    //||	(TestType.equals("S"))&&(strTestValueType.equals("SDH-DWDM Migration")))			
				{
					 sql = "select * from atp_slottestvalues where TestId =  "+testId + " and ProductId="+ProductId ;
				}
				else
				{
					 sql = "select * from atp_testvalues where TestId =  "+testId;
				}
				
				
				try
				{
						conn = GetDBConnection.getConnection();
						stmt = conn.createStatement(); 
						
						System.out.println("----getTestValues----"+sql);
						
						rs = stmt.executeQuery(sql);
						
						while(rs.next())
						{
							arrayOutNode.add(rs.getString("TestValue"));
							
							strString=strString+rs.getString("TestValue")+",";
							
						}
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
				
				return strString;
				
	}
	
	@Override
	public String CheckTasksStatus(String strJobId) {
		// TODO Auto-generated method stub
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;

		int i = 0;
		String sql ="",isComplete="",flag="";
				
					 sql = "SELECT * FROM installation_task where IncId="+strJobId ;
				
				try
				{
						conn = GetDBConnection.getConnection();
						stmt = conn.createStatement(); 
						
						System.out.println("----CheckTasksStatus----"+sql);
						
						rs = stmt.executeQuery(sql);
						
						while(rs.next())
						{
							isComplete=rs.getString("TaskComplete");
							if(isComplete.equals("N"))
							{
								flag="0";
								break;
							}
							else
							{
								flag="1";
							}
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
	public void updateTaskStarted(String strJobId) {
		//this function is called when te fills atp for the first time
		
		// TODO Auto-generated method stub
		Connection conn 	= null;
		Statement stmt 		= null;
	    ResultSet rs		= null;
		String flag="true";
		String sql="";
		PreparedStatement preparedStmt = null;
		
		
		try
		{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement(); 
			
			sql = "SELECT * FROM installation_actions where IncId="+strJobId +"and action = '16' ";
			rs = stmt.executeQuery(sql);
			int i1=0;
			while(rs.next())
			{
				i1=1;
			}
			if(i1==0)
			{
				//insert if first atp update is done 
				 sql = "INSERT INTO installation_actions(IncId,Action,ActionDate) VALUES (?,?,NOW())";
 				 preparedStmt = conn.prepareStatement(sql);
 				 preparedStmt.setInt(1, Integer.parseInt(strJobId));
				 preparedStmt.setString(2, "16");
 				 
			}
			
			/*{
				//update
				sql = "Update installation set  CurrentStage = ? ,CurrentStatus= ? "
						+ " ,StatusId='16', UpdatedDate = NOW() where id = " + strJobId  ;
				System.out.println("updateTaskStarted"+sql);
				preparedStmt = conn.prepareStatement(sql);
		        preparedStmt.executeUpdate();
			}
			else*/
			
			
			
					
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
				
				
	}
	
	
	/*public String uploadPhoto(MultipartFile file) {
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
			//	String root = getServletContext().getRealPath("/"); 
				
				
				String PATH=System.getProperty("user.dir");
				System.out.println("----UPLOAD_FOLDEdR----"+PATH);
			    String directoryName = PATH.concat("/ATPPhoto");
			
			    File directory = new File(directoryName);
			    if (! directory.exists()){
			    	
			    	System.out.println("creating folder");
			    	
			        directory.mkdir();
			        // If you require it to make the entire directory path including parents,
			        // use directory.mkdirs(); here instead.
			    }
				
			    DateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); // Just the year, with 2 digits
				String formattedDate = df.format(Calendar.getInstance().getTime());			    			    
			    String filename="_"+formattedDate+"_"+file.getOriginalFilename();
				
			    
				File convertFile=new File(directoryName+"/"+filename);
				convertFile.createNewFile();
				FileOutputStream fout=new FileOutputStream(convertFile);
				fout.write(file.getBytes());
				fout.close();
				fpath=directoryName+"/"+filename;

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
		return fpath;
		
		}*/



	@Override
	public ObjectNode updateTEPhotoTests(MultipartFile file, InstallationAction IA) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();

	//	boolean flag = false;
	//	boolean flag1 = false;
		String result="Error";
		
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		
		String fpath=""; int strJobId = 0,strTaskId=0,testid=0;
		try
		{
			  strJobId=IA.getIncId();
			     strTaskId=IA.getTaskId();
			     testid = IA.getTestId();
			
			System.out.println("File-------"+file);
	    //    Path path = Paths.get(context.getRealPath("Uploads") + file.getOriginalFilename());
			if(file!=null)
			{
			if(!file.isEmpty())
			{				
					
				
				String PATH=System.getProperty("user.dir");   //D:\SpringJavaAsh1\Eci\Eci c:\prg fil\tomcat90.\
				
				PATH=PATH.replace("\\","\\\\");
				
				
				System.out.println("----UPLOAD_FOLDEdR111-herre---"+PATH);
				
				//String condir = PATH.concat("/webapps/ProApp"); //server
			
				 String condir = PATH.concat("/webapps"); //server  c:\prg fil\tomcat90./wenapps 
				
			//	String condir = PATH.concat("/webapps1"); //local
			
				System.out.println("----UPLOAD_FOLDEdR--condir--"+condir); //c:\prg fil\tomcat90./wenapps
				
			//	condir=condir.replace("\\","\\\\");
				
			    //String directoryName = condir.concat("/ATPPhoto"); //sever       
			    
			    String directoryName = condir.concat("/ATPPhoto"); //local
			    
			    
			  //   directoryName ="C:\\Users\\admin\\Documents\\workspace-spring-tool-suite-4-4.1.0.RELEASE\\Eci\\ATPPhoto";
			    // directoryName=directoryName.replace("\\","\\\\");
					
			     
			  //   directoryName ="C:\\Users\\admin\\workspace\\ECI_WEB\\UploadPhoto";
			    System.out.println("----UPLOAD_FOLDEdR--condir-directoryName-"+directoryName);
			    			
			    File directory = new File(directoryName);
			    if (! directory.exists()){
			    	
			    	System.out.println("creating folder");
			    	
			        directory.mkdir();
			        // If you require it to make the entire directory path including parents,
			        // use directory.mkdirs(); here instead.
			    }
				
			    DateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); // Just the year, with 2 digits
				String formattedDate = df.format(Calendar.getInstance().getTime());			    			    
			    String filename=strJobId+"_"+formattedDate+"_"+file.getOriginalFilename();
				
			    
				File convertFile=new File(directoryName+"/"+filename);
				convertFile.createNewFile();
				FileOutputStream fout=new FileOutputStream(convertFile);
				fout.write(file.getBytes());
				fout.close();
				fpath=directoryName+"/"+filename;

			}
			}
			
			
			/*rootNode1 = objectMapper.readTree(data);
			System.out.println("\n---------rootNode ---------data----------"+rootNode1);
			
		    System.out.println("-------job_Id-------------------"+rootNode1.path("job_Id").asText());
		    System.out.println("--------taskid-------------------"+rootNode1.path("taskid").asText());
		    System.out.println("--------testData-------------------"+rootNode1.path("testData").asText());
		    
		    String strJobId=rootNode1.path("jobId").asText();
		    String strTaskId=rootNode1.path("taskid").asText();
		    String testid = rootNode1.path("testid").asText();  
		  // String remark = rootNode.path("remark").asText();
*/			
			 		
			
			String query = "Update inctaskwiseatp set  Status = '' ,TestValue = '"+fpath+"'"
					+ " , UpdateBy = 1 , UpdatedDate = NOW() where IncId = " + strJobId + " and TaskId=" +strTaskId + " and TestId= " + testid;   
			
			
			System.out.println("EH query"+query);
			
			conn = GetDBConnection.getConnection();
			
			preparedStmt = conn.prepareStatement(query);			
	        preparedStmt.executeUpdate();
	        result="success";
			
		}
		catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		finally {
			GetDBConnection.closeConn(conn);
			GetDBConnection.closePstmt(preparedStmt);
			
		}
		objectOutNode.put("result",result);
		
		objectOutNode.put("TestValue",fpath);
		objectOutNode.put("IncId",strJobId);
		objectOutNode.put("TaskId",strTaskId);
		objectOutNode.put("TestId",testid);
		
		
		
		//objectOutNode.put("message","");
		
		return objectOutNode;
	}
	
	
	public boolean sendMail(String to,String jobId,JavaMailSender javaMailSender){
		
		  SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo(to);

	        msg.setSubject("Job Completed");
	        msg.setText("Hi PM, \n Job with id - "+jobId+ " completed \n Team ProApp, \n Thanks");

	        javaMailSender.send(msg);
	        
	        System.out.println("mail sent to PM");
	        return true;

	}
	
	
	/*
	 * // sends mail to PM after job is completed public boolean sendMail(String
	 * to,String jobId){
	 * 
	 * final String user="scube.usr@gmail.com";//change accordingly final String
	 * pass="scube@1234";
	 * 
	 * String msg="p>Dear PM&nbsp;</p>" + "<p> Job completed for -"+jobId + "</p>"+
	 * "<p> ProApp </p>";
	 * 
	 * // MailServer="scube.usr@gmail.com"; // MailPwd="scube@1234";
	 * 
	 * // final String user=MailServer; // final String pass=MailPwd;
	 * 
	 * //1st step) Get the session object Properties props = new Properties();
	 * 
	 * props.put("mail.smtp.host", "smtp.gmail.com"); props.put("mail.smtp.auth",
	 * "true"); props.put("mail.debug", "false"); props.put("mail.smtp.ssl.enable",
	 * "true");
	 * 
	 * Session session = Session.getDefaultInstance(props, new
	 * javax.mail.Authenticator() { protected PasswordAuthentication
	 * getPasswordAuthentication() { return new PasswordAuthentication(user,pass); }
	 * }); //2nd step)compose message try { MimeMessage message = new
	 * MimeMessage(session); message.setFrom(new InternetAddress(user)); //
	 * message.setReplyTo(new InternetAddress[] {new InternetAddress(replyTo)});
	 * 
	 * message.addRecipient(Message.RecipientType.TO,new InternetAddress(to)); //
	 * message.addRecipient(Message.RecipientType.BCC,new
	 * InternetAddress("duplicates@geebeeworld.com"));
	 * 
	 * message.setSubject("ProApp Job Completed for " + jobId); //
	 * message.setText(msg);
	 * 
	 * 
	 * message.setContent(msg, "text/html; charset=UTF-8"); //
	 * message.setContent(msg,"Transfer-Encoding: 7bit");
	 * 
	 * //attachment BodyPart messageBodyPart = new MimeBodyPart();
	 * 
	 * // Now set the actual message messageBodyPart.setText("This is message body"
	 * + msg);
	 * 
	 * // Create a multipar message Multipart multipart = new MimeMultipart();
	 * 
	 * // Set text message part multipart.addBodyPart(messageBodyPart);
	 * 
	 * //3rd step)send message Transport.send(message);
	 * 
	 * System.out.println("Mail sent"); return true;
	 * 
	 * } catch (MessagingException e) { throw new RuntimeException(e); }
	 * 
	 * }
	 */
	
	
}