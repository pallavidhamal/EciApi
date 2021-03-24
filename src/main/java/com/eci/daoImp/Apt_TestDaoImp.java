package com.eci.daoImp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;

import com.eci.bean.Installation;
import com.eci.bean.Task;
import com.eci.dao.Apt_TestDao;
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
public class Apt_TestDaoImp implements Apt_TestDao {

	/*private PreparedStatement preparedStmt = null;
	private Connection conn = null;
	private Statement stmt 	= null;
	private ResultSet rs	= null;*/
	
	GetDBConnection getConn = new GetDBConnection();
	TokenServiceImp tokService=new TokenServiceImp();

	ObjectMapper objectMapper = new ObjectMapper();
	
	public ObjectNode getAptList() {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		ArrayNode arrTempNode = objectMapper.createArrayNode();

		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;

					
		String sql="SELECT cm.CustName, pm.Name, tm.TaskName, sc.Category, at.parameter FROM  atp_tests_product atp,atp_tests at,product_master pm,customer_master cm,task_master tm,stagecategory sc WHERE atp.TestId = at.TestId AND atp.ProductId = pm.ProductId AND atp.CustomerId = cm.CustomerID AND pm.CustomerId = cm.CustomerID AND at.TaskId = tm.TaskId AND at.TaskCategoryId = sc.Id;";
		
		
		System.out.println("----sql# getInstallationList----"+sql);
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
			
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					
					
					ObjectNode objecttempNode =objectMapper.createObjectNode();
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("CustName",rs.getString("CustName"));
					objectInNode.put("Name",rs.getString("Name"));
					objectInNode.put("TaskName",rs.getString("TaskName"));
					objectInNode.put("Category",rs.getString("Category"));
					objectInNode.put("parameter",rs.getString("parameter"));
					
	
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
	public ObjectNode AptTestList(Installation installation) {
		// TODO Auto-generated method stub
		
		 System.out.println(installation.getTaskId()+"@@@@@");
		 System.out.println(installation.getCustomerId()+"@@@@");
		 System.out.println(installation.getProductId()+"@@@");
		 
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		ArrayNode arrTempNode = objectMapper.createArrayNode();

		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;

					
		String sql="SELECT at.TestId,tm.TaskName,sc.Category,at.parameter,at.TestType,at.TestValueType,at.ParamType FROM atp_tests at,task_master tm,stagecategory sc WHERE at.TaskId = tm.TaskId AND tm.TaskId = sc.StageId AND at.TaskCategoryId = sc.Id and at.taskID="+installation.getTaskId()+" ";
		
		
		
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
			
				
				rs = stmt.executeQuery(sql);
				ObjectNode objecttempNode =objectMapper.createObjectNode();
			
				while(rs.next()){
					
					
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					
					objectInNode.put("TestId",rs.getString("TestId"));
					objectInNode.put("TaskName",rs.getString("TaskName"));
					objectInNode.put("Category",rs.getString("Category"));
					objectInNode.put("parameter",rs.getString("parameter"));
					objectInNode.put("TestType",rs.getString("TestType"));
					objectInNode.put("TestValueType",rs.getString("TestValueType"));
					String paramtype =rs.getString("ParamType");
					
					if(paramtype.equals("TXT")) {
						paramtype="Text";
					}else if(paramtype.equals("DD")) {
						paramtype="Drop Down";
					}else if(paramtype.equals("PHT")) {
						paramtype="Photo Test";
					}else if(paramtype.equals("AC")) {
						paramtype="Auto Complete Dropdown";
					}else if(paramtype.equals("LBL")) {
						paramtype="label";
					}
					else {
						paramtype=rs.getString("ParamType");
						
					}
					objectInNode.put("ParamType",paramtype);
					
					
					boolean flag=AptCheckExist(objectInNode,installation);
					objectInNode.put("flag",flag);
				System.out.println(flag+"!!!!!!!!!!!!!");
					
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
	public ObjectNode AptDataInsert(String checkboxD) {
		
		PreparedStatement preparedStmt = null;
		Connection conn 			   = null;
		
		
		System.out.println("update id==  "+checkboxD);
		JsonNode rootNode;
		

		try {
			
			rootNode = objectMapper.readTree(checkboxD);
			System.out.println("\n---------rootNode ---------data----------"+rootNode);
			
		    System.out.println("-------job_Id-------------------"+rootNode.path("CustomerId").asText());
		    System.out.println("--------taskid-------------------"+rootNode.path("ProductId").asText());
		    System.out.println("--------testData-------------------"+rootNode.path("taskId").asText());
		    System.out.println("--------testData-------------------"+rootNode.path("checkboxD").asText());
		    System.out.println("--------testData-------------------"+rootNode.path("AptValue").asText());
		    
		   
		  		    
		    
		    
		 
			conn = GetDBConnection.getConnection();
		    String  SQL_Insert = "INSERT INTO atp_tests_product(TestId,ProductId,CustomerId) VALUES (?,?,?)";
			System.out.println("QUERY For installation_actions==  "+SQL_Insert);
			
			
			
		String str1 = 	rootNode.path("checkboxD").asText();
		String str2 = 	rootNode.path("ProductId").asText();
		String str3 = 	rootNode.path("CustomerId").asText();
		String strtskid = 	rootNode.path("taskId").asText();
					
			/*
			 * String Aptxtvalue = rootNode.path("AptValue").asText(); String slotArray[] =
			 * null;
			 */
		
		
			/*
			 * slotArray= Aptxtvalue.split("#");
			 * 
			 * 
			 * System.out.println(slotArray+"--slotArray"+slotArray.length);
			 */
		
			
			String[] descVal = str1.split(",");
			System.out.println(descVal+"--descVal"+descVal.length);
			System.out.println(descVal+"Testing");
			String[] amtVal = str2.split(",");
			String[] custid = str3.split(",");
		//	pStmt = conn.prepareStatement(SQL_QUERY);
			 preparedStmt = conn.prepareStatement(SQL_Insert);
			for(int it=0;it<descVal.length;it++)
			{
				preparedStmt.setString(1,descVal[it].trim());
				if(!str2.equals("")) {
					preparedStmt.setString(2,str2);
				}else {
					preparedStmt.setString(2,amtVal[it].trim());
				}
				if(!str3.equals("")) {
					preparedStmt.setString(3,str3);
				}else {
					preparedStmt.setString(3,custid[it].trim());
				}
				
				
				
				

			/*	pst.setBigDecimal(4, ChkEmptynullDec(prsVal[it].trim()));					
				pst.setInt(5, ChkEmptynullInt(strLoginid));*/
				
				preparedStmt.executeUpdate();
				
				//get hidden value here
			
			
			
				/*
				 * if(!Aptxtvalue.equals("")) {
				 * AptTestDataInsert(descVal[it],str2,slotArray[it]); }
				 */
				
				
				//string hiden
				//slot insert function (prodid, test,id ,concat string)
				
			
			}
		 
		}catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		
		finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		
				
			return null;
		
	}
	

	
	public boolean AptCheckExist(ObjectNode objectInNode , Installation installation) throws IOException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		ArrayNode arrTempNode = objectMapper.createArrayNode();
		boolean flag = false;
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
				JsonNode str    =   objectInNode.get("TestId");
		
	
	    String  SQL_Check = "select * from  atp_tests_product where TestId="+str+"  and ProductId="+installation.getProductId()+" and CustomerId="+installation.getCustomerId()+" ";
				
		
		System.out.println(SQL_Check);
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
			
				
				rs = stmt.executeQuery(SQL_Check);
				
				while(rs.next()){
		          
			             System.out.print("true");   
			             flag =true;
					
			        }
					
					
					
					
				
					
	
					arrayOutNode.add(objectInNode);
				
				
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
			objectOutNode.put("result", flag);
			
		return flag;
	}
	
	
	
	@Override
	public ObjectNode DInsert(String checkboxD) {   // chnge parameter
		
		PreparedStatement preparedStmt = null;
		Connection conn 			   = null;
		
		
		
		JsonNode rootNode1;
		
		System.out.println("%%%%%%%%%%"+checkboxD);
		try {
			
			rootNode1 = objectMapper.readTree(checkboxD);
			    System.out.println("--------product-------------------"+rootNode1.path("ProductId").asText());
			    System.out.println("--------checkboxId-------------------"+rootNode1.path("checkboxD").asText());
			    System.out.println("--------Apt test-------------------"+rootNode1.path("AptValue").asText());
			    
			
			 String testID = 	rootNode1.path("checkboxD").asText();
				String prodtid = 	rootNode1.path("ProductId").asText();
				String ApttxtValue = rootNode1.path("AptValue").asText();
				String checkboxid = rootNode1.path("checkboxD").asText();
				deleteslotValue(prodtid,testID);
			
			
			
			conn = GetDBConnection.getConnection();
		    String  SQL_Insert = "INSERT INTO atp_slottestvalues(TestId,TestValue,ProductId)  VALUES (?,?,?)";

			System.out.println(checkboxid+"--checkboxid"+prodtid+"--prodtid"+ApttxtValue+"--ApttxtValue");
			
			
		    preparedStmt = conn.prepareStatement(SQL_Insert);
		    String[] descVal;

				
				 descVal = ApttxtValue.split("#");
				String[] checkid = checkboxid.split(",");
				
				for(int i=0;i<descVal.length;i++)
				{
					System.out.println("i"+i+"------"+descVal[i]);
					String str[]= descVal[i].split(",");
					for(int i1=0;i1<str.length;i1++)
					{
						System.out.println("i1"+i1+"------"+str[i1]);	
						
						preparedStmt.setString(1,checkid[i].trim());
						preparedStmt.setString(2,str[i1].trim());
						preparedStmt.setString(3,prodtid);
						preparedStmt.executeUpdate(); 
						
						
					}
					
				}
				
				
				
			/*
			 * for(int i=0; i<checkid.length;i++) {
			 * 
			 * preparedStmt.setString(1,checkid[i].trim());
			 * 
			 * 
			 * descVal = ApttxtValue.split(","); for(int k=0; k<descVal.length; k++) {
			 * 
			 * preparedStmt.setString(2,descVal[i].trim());
			 * 
			 * 
			 * preparedStmt.setString(3,prodtid);
			 * 
			 * preparedStmt.executeUpdate(); } }
			 */
			 
		
				
				
				
				
				
			
		 
		}catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		
		
		return null;
	}

	
	private void deleteslotValue(String prodtid, String testID) {
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		try {
			conn = GetDBConnection.getConnection();
			 String  SQL_Delete = "Delete  from  atp_slottestvalues where ProductId="+prodtid+" and TestId="+testID+"";
			  System.out.println(SQL_Delete);
			  preparedStmt = conn.prepareStatement(SQL_Delete);
				preparedStmt.execute();
		}catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			GetDBConnection.closeStmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
			
			
		}
	
	
	@Override
	public ObjectNode DeleteExist(String checkboxD) {
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		
			JsonNode deletenode;
		
		
		try {
			conn = GetDBConnection.getConnection();
			deletenode = objectMapper.readTree(checkboxD);
			String strdel = 	deletenode.path("checkboxD").asText();
			String strp = 	deletenode.path("ProductId").asText();
			String strc = 	deletenode.path("CustomerId").asText();
			System.out.println(deletenode.path("CustomerId").asText()+"------Customer--------------");
			System.out.println(deletenode.path("ProductId").asText()+"------Prodect--------------");
			 String[] chkid = strdel.split(",");
			 
			 System.out.println(strdel+"***"+strp+"##"+strc+"--------------------"+chkid.length);
				for(int i=0;i<chkid.length;i++)
				{			 
					System.out.println(chkid+"++++"+chkid[i]);
			  String  SQL_Delete = "DELETE FROM atp_tests_product WHERE TestId="+chkid[i]+" and ProductId="+strp+" and CustomerId="+strc+" ";
			  System.out.println(SQL_Delete);
			  preparedStmt = conn.prepareStatement(SQL_Delete);
				preparedStmt.execute();
				}
	}catch (Exception e) {
		
		// TODO: handle exception
		e.printStackTrace();
	}
	finally {
		GetDBConnection.closeStmt(preparedStmt);
		GetDBConnection.closeConn(conn);
	}
		
		return null;
	}

	@Override
	public ObjectNode getTaskData(String taskID) {
				Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		JsonNode taskidnode;
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
	
		

			try
			{
				taskidnode = objectMapper.readTree(taskID);
				String taskId = 	taskidnode.path("Task").asText();
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				String sql="select * from stagecategory where StageId="+taskId+";";
				
				rs = stmt.executeQuery(sql);
				System.out.println(sql);
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();	
				
					
					objectInNode.put("Id",rs.getString("Id"));
					objectInNode.put("Category",rs.getString("Category"));
					
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
	public ObjectNode InsertAtpTaskData(String TaskID) {
		PreparedStatement preparedStmt = null;
		Connection conn 			   = null;

		JsonNode rootNode1;
		
		
		try {
			
			rootNode1 = objectMapper.readTree(TaskID);
			String Migtypestr = rootNode1.path("MIGType").asText();
			String DDVAluestr = rootNode1.path("Tvaldrp").asText();
			String reqstr = rootNode1.path("Task").asText();
			ArrayNode  Seqno = getSeq(reqstr);
			
	         String str =  Seqno.get(0).toString();
			
			
			System.out.println("######"+ Seqno.get(0));
			conn = GetDBConnection.getConnection();
		    String  SQL_Insert = "INSERT INTO atp_tests(TaskId,TaskCategoryId,parameter,ParamType,TestValueType,TestType,MIGType,Seq)  VALUES (?,?,?,?,?,?,?,?)";
			
		    preparedStmt = conn.prepareStatement(SQL_Insert,Statement.RETURN_GENERATED_KEYS);
		    preparedStmt.setString(1,rootNode1.path("Task").asText());
			preparedStmt.setString(2,rootNode1.path("CatgId").asText());
			preparedStmt.setString(3,rootNode1.path("Taskdrp").asText());
			preparedStmt.setString(4,rootNode1.path("ParamType").asText());
			 if(DDVAluestr.equals("")) {
				 preparedStmt.setString(5,"");	
				 preparedStmt.setString(6,"");	
				} else if(DDVAluestr.equals("S")){	
					 preparedStmt.setString(5,"");	
					 preparedStmt.setString(6,"S");	
					 
				}else if(DDVAluestr.equals("S1")) {
					preparedStmt.setString(5,"P");	
					 preparedStmt.setString(6,"S1");	
				}
								
					
			 if(Migtypestr.isEmpty()) {
				 preparedStmt.setString(7,"");	
				} else {
					 preparedStmt.setString(7,Migtypestr);	
				}

			 preparedStmt.setString(8,str);	
			System.out.println(SQL_Insert);
			
			
		   
		    preparedStmt.executeUpdate(); 
		    
		    ResultSet rs = preparedStmt.getGeneratedKeys();
			int generatedSoId = 0;
			if (rs.next()) {
				generatedSoId = rs.getInt(1);
			}
		    
			
		    System.out.println(generatedSoId);
		    
		    if(DDVAluestr.equals("S")) {
		    	System.out.println("insertapt val callinggggg");
		    	InserAtpVal(TaskID,generatedSoId);
			}   else {
				System.out.println("insertapt val callinggggg fail");
			}
		}catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		
		
		return null;
	}
		
		
	

	public ObjectNode InserAtpVal(String TaskID , int generatedSoId) {
		PreparedStatement preparedStmt = null;
		Connection conn 			   = null;
		
		
		
		JsonNode rootNode1;
		
		
		try {
			
			rootNode1 = objectMapper.readTree(TaskID);
			   
			    
			
			
			 System.out.println("--------product-------------------"+rootNode1.path("Task").asText());
			    System.out.println("--------checkboxId-------------------"+rootNode1.path("strTaskiD").asText());
			
			
			conn = GetDBConnection.getConnection();
		    String  SQL_Insert = "INSERT INTO atp_testvalues(TestId,TestValue)  VALUES (?,?)";
			
		    String DDVAluestr = rootNode1.path("DropDownVal").asText();
			int str2 = generatedSoId;
			String[] DDVal = DDVAluestr.split(",");
		    
			preparedStmt = conn.prepareStatement(SQL_Insert);
		    for(int it=0;it<DDVal.length;it++)
			{
				preparedStmt.setInt(1,str2);
				preparedStmt.setString(2,DDVal[it].trim());
				  preparedStmt.executeUpdate(); 
			}		
			System.out.println(SQL_Insert+"APPPPPPPPTTTTTTT");
		}catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		
		
		return null;
	}

	@Override
	public ObjectNode getAptTest() {
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		ArrayNode arrTempNode = objectMapper.createArrayNode();

		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;

					
	//	String sql="select * from atp_tests ";
		String sql="SELECT * FROM atp_tests at,task_master tm, stagecategory sc WHERE at.TaskId = tm.TaskId AND tm.TaskId = sc.StageId AND at.TaskCategoryId = sc.Id";
		
		System.out.println("runnung");
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
			
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					
					
					ObjectNode objecttempNode =objectMapper.createObjectNode();
					ObjectNode objectInNode = objectMapper.createObjectNode();
					objectInNode.put("TestId",rs.getString("TestId"));					
					objectInNode.put("TaskId",rs.getString("TaskId"));
					objectInNode.put("parameter",rs.getString("parameter"));
					objectInNode.put("Seq",rs.getString("Seq"));
					objectInNode.put("TaskName",rs.getString("TaskName"));
					objectInNode.put("Category",rs.getString("Category"));
					
				  String paramtype =rs.getString("ParamType");
				  if(paramtype =="" ||	 paramtype == null ) {
					  paramtype="";
					  } else if(paramtype.equals("TXT")) {
				  paramtype="Text"; 
				  }else if(paramtype.equals("DD")) {
					  paramtype="Drop Down";
				  }else if(paramtype.equals("PHT")) {
					  paramtype="Photo Test"; 
				  }  else if(paramtype.equals("AC")) { 
					  paramtype="Auto Complete Dropdown"; }else
				  if(paramtype.equals("LBL")) { 
					  paramtype="label"; } 
				  else {
				  paramtype=rs.getString("ParamType");
				 	  } 
				  objectInNode.put("ParamType",paramtype);
				 
					
					
	
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
	public ObjectNode updateatp(String editid) {
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		ArrayNode arrTempNode = objectMapper.createArrayNode();

		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		
			try
			{
				 JsonNode	rootNode1 = objectMapper.readTree(editid);
				 String str =rootNode1.path("edit").asText();
				 System.out.println(str+"_______"+rootNode1.path("edit").asText());
				 String sql="select * from atp_tests where TestId="+rootNode1.path("edit").asText()+" ";

				 System.out.println(sql);
				
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
			
				
				rs = stmt.executeQuery(sql);
				ObjectNode objecttempNode =objectMapper.createObjectNode();
			
				while(rs.next()){
					
					
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					
					objectInNode.put("TestId",rs.getString("TestId"));
					objectInNode.put("TaskId",rs.getString("TaskId"));
					objectInNode.put("TaskCategoryId",rs.getString("TaskCategoryId"));
					objectInNode.put("parameter",rs.getString("parameter"));
					objectInNode.put("ParamType",rs.getString("ParamType"));
					objectInNode.put("TestType",rs.getString("TestType"));			
					objectInNode.put("TestValueType",rs.getString("TestValueType"));
					objectInNode.put("Seq",rs.getString("Seq"));
					objectInNode.put("TestValue",getAtpTestValues(editid));
					System.out.println(rs.getString("TestType")+"*************");
						
					arrayOutNode.add(objectInNode);
					
				}
				
			/*
			 * ObjectNode Objnode = getAtpTestValues(editid); arrayOutNode.add(Objnode);
			 */
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

	public ObjectNode getAtpTestValues(String editid) {
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		

		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		
			try
			{
				 JsonNode	rootNode1 = objectMapper.readTree(editid);
				 String str =rootNode1.path("edit").asText();
				 System.out.println(str+"_______"+rootNode1.path("edit").asText());
				 String sql="select * from atp_testvalues where TestId="+str+" ";

				 System.out.println(sql);
				
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
			
				
				rs = stmt.executeQuery(sql);
				
			
				while(rs.next()){
					
					
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					
				//	objectInNode.put("TestValue",rs.getString("TestValue"));
				
					
				
					arrayOutNode.add(rs.getString("TestValue"));
					System.out.println(arrayOutNode+"###############");
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
	public ObjectNode updateAtpTaskData(String TaskID) {
		PreparedStatement preparedStmt =null;
		Connection conn 			   = null;
		
		
		
		JsonNode rootNode1;
		
		
		try {
			
			rootNode1 = objectMapper.readTree(TaskID);
			String DDVAluestr = rootNode1.path("Tvaldrp").asText();
			String editddstr  = rootNode1.path("editDropDownVal").asText();
			System.out.println(editddstr+"EdittttttttId");
			System.out.println(TaskID+"######"+ rootNode1.path("EditSeq").asText());
			conn = GetDBConnection.getConnection();
		    String  SQL_Insert = "update atp_tests set TaskId="+rootNode1.path("editTask").asText()+",TaskCategoryId="+rootNode1.path("editCatgId").asText()+",parameter='"+rootNode1.path("editTaskdrp").asText()+"',ParamType='"+rootNode1.path("editParamType").asText()+"',TestType='"+rootNode1.path("editTvaldrp").asText()+"',Seq='"+rootNode1.path("EditSeq").asText()+"' where TestId="+rootNode1.path("updateid").asText()+" ";
			
			
			
			System.out.println(SQL_Insert);
			
			 preparedStmt = conn.prepareStatement(SQL_Insert);
		   
		    preparedStmt.executeUpdate(SQL_Insert); 
		
			if(editddstr.isEmpty()) {
				System.out.println("Test1");
			}else {
				DeleteAtpTestValue(TaskID);
			}
		    
		    
		}catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		
		finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
	
		
		return null;
	}
	

	public ObjectNode DeleteAtpTestValue(String TaskID) {
		PreparedStatement preparedStmt =null;
		Connection conn 			   = null;
		
		
		
		JsonNode rootNode1;
		
		
		try {
			
			rootNode1 = objectMapper.readTree(TaskID);
			conn = GetDBConnection.getConnection();
				 String   SQL_Delete = "DELETE FROM atp_testvalues WHERE TestId="+rootNode1.path("updateid").asText()+" ";
				   System.out.println(SQL_Delete);
				   preparedStmt = conn.prepareStatement(SQL_Delete);
				   preparedStmt.executeUpdate();

				
			 InserAtpTestAfterupdate(TaskID);
		}catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
	
		finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
	
		
		return null;
	}
	
	public ObjectNode InserAtpTestAfterupdate(String TaskID) {
		PreparedStatement preparedStmt = null;
		Connection conn 			   = null;
		
		
		
		JsonNode rootNode1;
		
		
		try {
			
			rootNode1 = objectMapper.readTree(TaskID);
	
			conn = GetDBConnection.getConnection();
		    String  SQL_Insert = "INSERT INTO atp_testvalues(TestId,TestValue)  VALUES (?,?)";
			
		    String DDVAluestr = rootNode1.path("editDropDownVal").asText();
		    String str2 = rootNode1.path("updateid").asText();
			
			String[] DDVal = DDVAluestr.split(",");
		    
			preparedStmt = conn.prepareStatement(SQL_Insert);
		    for(int it=0;it<DDVal.length;it++)
			{
				preparedStmt.setString(1,str2);
				preparedStmt.setString(2,DDVal[it].trim());
				  preparedStmt.executeUpdate(); 
			}		
			System.out.println(SQL_Insert+"APPPPPPPPTTTTTTT");
		}catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		
		
		return null;
	}

	@Override
	public ObjectNode deleteRecord(String DelId) {
		PreparedStatement preparedStmt =null;
		Connection conn 			   = null;
		
		
		
		JsonNode rootNode1;
		
		
		try {
			
			rootNode1 = objectMapper.readTree(DelId);
			conn = GetDBConnection.getConnection();
			 String SQL_Delete = "DELETE FROM atp_tests WHERE TestId="+ rootNode1.path("DelId").asText()+" ";
			  System.out.println(SQL_Delete);
			  preparedStmt =conn.prepareStatement(SQL_Delete); 
			  preparedStmt.executeUpdate();
			 
				
			  DeleteRecordAtpTestValue(DelId);
		}catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
	
		finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		return null;
	}
	
	public ObjectNode DeleteRecordAtpTestValue(String DelId) {
		PreparedStatement preparedStmt =null;
		Connection conn 			   = null;
		
		
		
		JsonNode rootNode1;
		
		
		try {
			
			rootNode1 = objectMapper.readTree(DelId);
			conn = GetDBConnection.getConnection();
				 String   SQL_Delete = "DELETE FROM atp_testvalues WHERE TestId="+rootNode1.path("DelId").asText()+" ";
				   System.out.println(SQL_Delete);
				   preparedStmt = conn.prepareStatement(SQL_Delete);
				   preparedStmt.executeUpdate();
	}catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
	
		finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
	
		
		return null;
	}
	public ArrayNode getSeq(String reqstr) {
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		

		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		
			try
			{
				 				
				 String sql="SELECT MAX(Seq) FROM atp_tests where TaskId ="+reqstr+" ";

				 System.out.println(sql);
				
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
			
				
				rs = stmt.executeQuery(sql);
				
				int seq = 0;
				while(rs.next()){
					
					
					ObjectNode objectInNode = objectMapper.createObjectNode();
					  seq =rs.getInt("MAX(Seq)");
				}
				 arrayOutNode.add(seq+1);
				System.out.println(arrayOutNode+"%%%%%%");
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
			
			
		return arrayOutNode;
	}

	@Override
	public boolean updateSeq(String TaskID) {
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		
		boolean flag = false;
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
				
				
		
			try
			{
				 JsonNode	rootNode1 = objectMapper.readTree(TaskID);
				 String taskstr =rootNode1.path("TaskID").asText();
				 String seqstr =rootNode1.path("seqId").asText();
				 String  SQL_Check = "SELECT Seq FROM atp_tests where Seq="+seqstr+" and TaskId="+taskstr+" ";
				
		
	    		System.out.println(SQL_Check);
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
			
				
				rs = stmt.executeQuery(SQL_Check);
				
				while(rs.next()){
		          
			             System.out.print("true");   
			             flag =true;
					
			        }
	
	
					arrayOutNode.add(objectOutNode);
				
				
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
			
			objectOutNode.put("result", flag);
			
		return flag;
	}
	
	@Override
	public ObjectNode AptTestListnew(String checkboxD) {
		
		PreparedStatement preparedStmt = null;
		Connection conn 			   = null;
	
		
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		JsonNode rootNode;
		

		try {
			
			rootNode = objectMapper.readTree(checkboxD);
			System.out.println("\n---------rootNode ---------data----------"+rootNode);
			 System.out.println("-------job_Id-------------------"+rootNode.path("ProductId").asText());
			ArrayNode arrayOutNode = objectMapper.createArrayNode();
			
			
		   
			ResultSet rs = null;
			Statement stmt 	= null;
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			
		    String  sql = "select * from  atp_slottestvalues where ProductId="+rootNode.path("ProductId").asText()+""
		    		+ " and TestId="+rootNode.path("testID").asText()+" ";
			System.out.println("Sql query for atp slot value"+sql);
			
			rs = stmt.executeQuery(sql);
			ObjectNode objecttempNode =objectMapper.createObjectNode();
		
			while(rs.next()){
				
				
				ObjectNode objectInNode = objectMapper.createObjectNode();
				
				
				objectInNode.put("TestValue",rs.getString("TestValue"));
				
				arrayOutNode.add(objectInNode);
			}
			objectOutNode.put("result", arrayOutNode);
			System.out.println("result======="+arrayOutNode);
		}catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}
		
				
			return objectOutNode;
		
	}
	
	
	}

