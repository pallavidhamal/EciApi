package com.eci.daoImp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.eci.bean.PurchaseOrder;
import com.eci.dao.PurchaseOrderDao;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class PurchaseOrderDaoImp implements PurchaseOrderDao {

	
//	private Connection conn = null;
//	private ResultSet rs	= null;
	GetDBConnection getConn = new GetDBConnection();
	ObjectMapper objectMapper = new ObjectMapper();
	TokenServiceImp tokService=new TokenServiceImp();
	
	
	public ObjectNode getAllPorders() {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		int i=0;
		Statement stmt 	= null;
		ResultSet rs	= null;
		Connection conn = null;
		
		/*String sql = "select * from purchaseOrder PO , customer_master CM , product_master PM , region_master RM where "+
					"PO.CustID= CM.CustomerID and PO.ProductID=PM.ProductId and PO.RegionID=RM.RegionId and PO.IsDeleted='N'";*/
		
		/*String sql = " select * from purchaseorder POM, customer_master CM, product_master PM, region_master RM  "
				+ "  where POM.CustomerId = CM.CustomerID and POM.ProductId = PM.ProductId and POM.RegionId = RM.RegionId AND POM.IsDeleted = 'N' ";
		*/
		
		String sql = " select POM.Id,POM.PO_Number, date_format(POM.PO_Date, '%Y-%m-%d') as PO_Date,date_format(POM.PO_EndDate, '%Y-%m-%d')as PO_EndDate,  CM.CustName, POM.KittyRemark, "
					+ "  POM.LineId, PM.CatCode, PM.Description, PM.Name, RM.RegionName, POM.PO_Qty, POM.Balace_Qty, POM.Status " + 
						" from purchaseorder POM, customer_master CM, product_master PM, region_master RM " + 
						" where POM.CustomerId = CM.CustomerID and POM.ProductId = PM.ProductId and POM.RegionId = RM.RegionId AND POM.IsDeleted = 'N' " ;
						
		
		
		//+ " and CURDATE() between POM.PO_Date and POM.PO_EndDate " ;
		//removed as not required
						

		
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql getAllPorders----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					i=1;
					
					objectInNode.put("Id",rs.getString("Id"));
					objectInNode.put("PO_Number",rs.getString("PO_Number"));
					objectInNode.put("PO_Date",rs.getString("PO_Date"));
					objectInNode.put("PO_EndDate",rs.getString("PO_EndDate"));
					objectInNode.put("CustName",rs.getString("CustName"));
					objectInNode.put("LineId",rs.getString("LineId"));
					objectInNode.put("CatCode",rs.getString("CatCode"));
					objectInNode.put("Description",rs.getString("Description"));
					objectInNode.put("Name",rs.getString("Name"));				
					objectInNode.put("RegionName",rs.getString("RegionName"));
					objectInNode.put("PO_Qty",rs.getString("PO_Qty"));
					objectInNode.put("Balace_Qty",rs.getString("Balace_Qty"));
					objectInNode.put("Status",rs.getString("Status"));
					objectInNode.put("KittyRemark",rs.getString("KittyRemark"));
					
					arrayOutNode.add(objectInNode);
			//		System.out.println("----sql getAllPorders-arrayOutNode---"+arrayOutNode);
				}
				
			}
			catch(Exception e)
	    	{
	    		e.printStackTrace();
	    		objectOutNode.put("result", "error");
	    	}
			finally {
				GetDBConnection.closeRS(rs);
				GetDBConnection.closeStmt(stmt);
				GetDBConnection.closeConn(conn);
			}
			
			if(i==0)
			{
				objectOutNode.put("result", "nodata");
			}else
			{
				objectOutNode.put("result", arrayOutNode);
			}
			
		
		return objectOutNode;
	
	}
	

	@Override
	public ObjectNode  insertPO(String data) {
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		PreparedStatement pStmt = null;
		Connection conn         = null;
		
		String strAuth="Authorised";
		boolean flag = false;
		JsonNode rootNode;
		String lineId="",productId="",desc="",catalog="",regioId="",Poqty="",query="",balqty="";
		try{
			System.out.println("data======="+data);
			
			System.out.println("insert PO");
			rootNode = objectMapper.readTree(data);
			System.out.println("rootNode======="+rootNode);
			
		    
		    String strPONo=rootNode.path("PO_NO").asText();
		    String strCustomer=rootNode.path("Customer").asText();
		    String PODate = rootNode.path("PODate").asText();  // if U then update & continue is clicked , if E then update & exit
		    String POEDate = rootNode.path("POEDate").asText();
		    String authKey=rootNode.path("authKey").asText();
		    
		    JsonNode lineData = rootNode.get("lineData");			
		    ArrayNode lineData1 = (ArrayNode) lineData;			
			System.out.println("-----line data size------"+lineData1.size());
			
			
			 if(tokService.isTokenValid(authKey))
				{
			conn = null;
			conn = GetDBConnection.getConnection();
			
			for(int i=0;i<lineData1.size();i++){
				
				lineId=lineData1.get(i).path("lineid").asText();
				productId=lineData1.get(i).path("ProductId").asText();
				desc=lineData1.get(i).path("description").asText();
				catalog=lineData1.get(i).path("catlog").asText();
				regioId=lineData1.get(i).path("RegionId").asText();
				Poqty=lineData1.get(i).path("POQty").asText();
				balqty=lineData1.get(i).path("Balace_Qty").asText();
			
			query = "INSERT INTO purchaseorder (PO_Number, PO_Date, PO_EndDate, CustomerId,LineId,CatalogId,Description, ProductId, RegionId, PO_Qty, Balace_Qty, CreatedBy, CreatedDate, IsDeleted) " +
										" VALUES (?, 			?,		 ?, 		?, 		?, 		?, 			?, 			?, 			?, 		?, 		?,			 ?,		NOW(),		'N')";
			
			System.out.println("QUERY For insertPO "+i+"--"+query );
			pStmt = conn.prepareStatement(query);
			
			pStmt.setString(1, strPONo);
			pStmt.setString(2, PODate);
			pStmt.setString(3, POEDate);
			pStmt.setString(4, strCustomer);
			
			pStmt.setString(5, lineId);
			pStmt.setString(6, catalog);
			
			pStmt.setString(7, desc);
			pStmt.setString(8, productId);
			
			pStmt.setString(9, regioId);
			pStmt.setString(10, Poqty);
			
			pStmt.setString(11, balqty);// balance qty same at the time creation
			pStmt.setString(12, "1"); //currently hardcoded
			
	        pStmt.executeUpdate();
			
			}
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
			GetDBConnection.closePstmt(pStmt);
			GetDBConnection.closeConn(conn);
		}
		// TODO Auto-generated method stub
		return objectOutNode;
	}
	
	
	
	public ObjectNode getPODetail(PurchaseOrder pOrder) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		String result="error";
		
		Statement stmt 		= null;
		ResultSet rs		= null;
		Connection conn     = null;
		
	/*	String sql = "select * from purchaseOrder PO , customer_master CM , product_master PM , region_master RM where "+
				"PO.CustID= CM.CustomerID and PO.ProductID=PM.ProductId and PO.RegionID=RM.RegionId and PO.IsDeleted='N' and PO_Number = "+pOrder.getPO_Number();
		*/
	//	String sql = "select * from purchaseOrder PO ,product_master PM where PO.ProductId=PM.ProductId and PO.IsDeleted = 'N' and CURDATE() between PO.PO_Date and PO.PO_EndDate and  PO.PO_Number = '"+pOrder.getPo_Number()+"'";
		
		String sql = "select * from purchaseOrder PO ,product_master PM where PO.ProductId=PM.ProductId and PO.IsDeleted = 'N' and  PO.PO_Number = '"+pOrder.getPo_Number()+"'";
		
		
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("PoId",rs.getString("ID"));
					objectInNode.put("PO_Number",rs.getString("PO_Number"));					
					objectInNode.put("PO_Date",rs.getString("PO_Date"));
					objectInNode.put("PO_EndDate",rs.getString("PO_EndDate"));
					objectInNode.put("LineId",rs.getString("LineId"));
					objectInNode.put("CustomerId",rs.getString("CustomerId"));
					objectInNode.put("ProductId",rs.getString("ProductId"));
					objectInNode.put("ProductName",rs.getString("Name"));
					objectInNode.put("RegionId",rs.getString("RegionId"));
					objectInNode.put("PO_Qty",rs.getString("PO_Qty"));
					objectInNode.put("Bal_Qty",rs.getString("Balace_Qty"));

					//objectInNode.put("Price",rs.getString("Price"));				
				
					
					arrayOutNode.add(objectInNode);
				}
				objectOutNode.put("data", arrayOutNode.toString());
				result= "success";
				
			}
			catch(Exception e)
	    	{
	    		e.printStackTrace();
	    		objectOutNode.put("result", "error");
	    	}
			finally {
				GetDBConnection.closeRS(rs);
				GetDBConnection.closeStmt(stmt);
				GetDBConnection.closeConn(conn);
			}
			
			objectOutNode.put("result", arrayOutNode);
			
		
			
		return objectOutNode;
	
	}
	
/*	public boolean updatePO(PurchaseOrder pOrder) {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		String query = "update purchaseorder set CustID = ? ,ProductID= ? ,RegionID= ? ,Quantity = ?,StartDate = ?,EndDate = ?,IsDeleted ='N' ,UpdatedBy =1 ,"
				+ " UpdatedDate=NOW() where ID= "+pOrder.getPurId();
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql updatePO----"+query);
				
				preparedStmt = conn.prepareStatement(query);
			    
				preparedStmt.setInt(1, pOrder.getCustId());
				preparedStmt.setInt(2, pOrder.getProductId());
				preparedStmt.setInt(3, pOrder.getRegionId());
				preparedStmt.setDouble(4, pOrder.getQuantity());
				preparedStmt.setDate(5,  (Date) pOrder.getStartDate());
				preparedStmt.setDate(6,  (Date) pOrder.getEndDate());
				
			      // execute the java preparedstatement
		        preparedStmt.executeUpdate();
			    flag=true;
			
				
			}
			catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
			
		
		return flag;
	
	}*/

	@Override
	public ObjectNode getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}


	
	

	@Override
	public ObjectNode getCustWiseProductList(int customerId) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		int i=0;
		
		Statement stmt 	= null;
		ResultSet rs	= null;
		Connection conn     = null;
		
		
		/*String sql = "select * from purchaseOrder PO , customer_master CM , product_master PM , region_master RM where "+
					"PO.CustID= CM.CustomerID and PO.ProductID=PM.ProductId and PO.RegionID=RM.RegionId and PO.IsDeleted='N'";*/
		
		String sql = "select distinct Name, ProductId, CatCode, Description FROM eci.product_master where CustomerId = "+customerId+"  GROUP BY Name ";
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql getAllPorders----"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					i=1;
					
					objectInNode.put("ProductId",rs.getString("ProductId"));
					objectInNode.put("Name",rs.getString("Name"));
					objectInNode.put("CatCode",rs.getString("CatCode"));
					objectInNode.put("Description",rs.getString("Description"));
					
					arrayOutNode.add(objectInNode);
				//	System.out.println("----sql getAllPorders-arrayOutNode---"+arrayOutNode);
				}
			}
			catch(Exception e)
	    	{
	    		e.printStackTrace();
	    		objectOutNode.put("result", "error");
	    	}
			finally {
				GetDBConnection.closeRS(rs);
				GetDBConnection.closeStmt(stmt);
				GetDBConnection.closeConn(conn);
			}
			if(i==0)
			{
				objectOutNode.put("result", "nodata");
			}else
			{
				objectOutNode.put("result", arrayOutNode);
			}
			
		
		return objectOutNode;
	}


	@Override
	public ObjectNode getProdWiseCatagory(PurchaseOrder pOrder) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		
		String result="error";
		
		Statement stmt 	= null;
		ResultSet rs	= null;
		Connection conn     = null;
		
		System.out.println("----pid pid----"+pOrder.getId());
		
		//String sql = "select * from product_master where Name ='" +pOrder.getName() +"'";
		String sql = "select * from product_master where ProductId ='" +pOrder.getId()+"'";
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("----sql----"+sql);
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					ObjectNode objectInNode = objectMapper.createObjectNode();
					
					objectInNode.put("ProductId",rs.getString("ProductId"));
					objectInNode.put("CatCode",rs.getString("CatCode"));
					objectInNode.put("Name",rs.getString("Name"));
					objectInNode.put("Description",rs.getString("Description"));
					
					arrayOutNode.add(objectInNode);
				}
				//objectOutNode.put("data", arrayOutNode.toString());
				result= "success";
				
			}
			catch(Exception e)
	    	{
	    		e.printStackTrace();
	    		objectOutNode.put("result", "error");
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
	public ObjectNode  updatePO(String data) {
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		PreparedStatement pStmt = null;
		Connection conn     = null;
		
		String strAuth="Authorised";
		boolean flag = false;
		JsonNode rootNode;
		String lineId="",productId="",desc="",catalog="",regioId="",Poqty="",query="",strPOId="",BalQty="";
		try{
			
			conn = GetDBConnection.getConnection();

			System.out.println("update PO");
			rootNode = objectMapper.readTree(data);
			System.out.println("\n--insert PO----data--"+rootNode);
			
		    String strPONo=rootNode.path("PO_NO").asText();
		    String strCustomer=rootNode.path("Customer").asText();
		    String PODate = rootNode.path("PODate").asText();  
		    String POEDate = rootNode.path("POEDate").asText();
		    String authKey=rootNode.path("authKey").asText();
		    String userId=rootNode.path("userId").asText();

			System.out.println("-----PPPAAAALLLLLL------"+userId);

		    
		    //delete Po lineid's 
		    String PoDeleted = rootNode.path("PoDeleted").asText();
		    String[] PoDeletedArr = PoDeleted.split(",");
		    
		    String DelSql = "Update purchaseOrder set IsDeleted ='Y' where Id = ?";
		    pStmt = conn.prepareStatement(DelSql);

		    if(tokService.isTokenValid(authKey))
			{
		    
		    if(!PoDeleted.equals(""))
			{
			for(int it=0;it<PoDeletedArr.length;it++)
			{
				pStmt.setString(1, PoDeletedArr[it]);
				pStmt.executeUpdate();
			}
			}
		    //delete Po lineid's 
		    pStmt = null;
		    
		    
		    JsonNode lineData = rootNode.get("lineData");			
		    ArrayNode lineData1 = (ArrayNode) lineData;			
			System.out.println("-----line data size------"+lineData1.size());
			
			
			for(int i=0;i<lineData1.size();i++){
				
				strPOId=lineData1.get(i).path("PO_Id").asText();  // rootNode.path("PO_Id").asText();
				lineId=lineData1.get(i).path("lineid").asText();
				productId=lineData1.get(i).path("ProductId").asText();
				desc=lineData1.get(i).path("description").asText();
				catalog=lineData1.get(i).path("catlog").asText();
				regioId=lineData1.get(i).path("RegionId").asText();
				Poqty=lineData1.get(i).path("POQty").asText();
				BalQty=lineData1.get(i).path("BalQty").asText();
			
				if(strPOId.isEmpty())
				{
				query = "INSERT INTO purchaseorder (PO_Number,PO_Date,PO_EndDate,CustomerId,LineId,CatalogId,Description, ProductId, RegionId, PO_Qty, Balace_Qty, CreatedBy, CreatedDate, IsDeleted) " +
						" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,NOW(),'N')";
				
				System.out.println("QUERY For insertPO "+i+"--"+query );
				pStmt = conn.prepareStatement(query);
				
				pStmt.setString(1, strPONo);
				pStmt.setString(2, PODate);
				pStmt.setString(3, POEDate);
				pStmt.setString(4, strCustomer);
				
				pStmt.setString(5, lineId);
				pStmt.setString(6, catalog);
				
				pStmt.setString(7, desc);
				pStmt.setString(8, productId);
				
				pStmt.setString(9, regioId);
				pStmt.setString(10, Poqty);
				
				pStmt.setString(11, BalQty);// balance qty same at the time creation //now changed bcz it can be changed 
				pStmt.setString(12, userId); 
				
		        pStmt.executeUpdate();
				}
				else
				{
					
				 query = "Update purchaseorder set  PO_Date = ? ,PO_EndDate =? ,LineId=?,CatalogId=?,Description=?, ProductId=?, RegionId=?, PO_Qty=? ,Balace_Qty = ? ,"
							+ " UpdateBy = ? , UpdatedDate = NOW() where Id = " + strPOId;
					
					System.out.println("updateCustomer query"+query);
					
					pStmt = conn.prepareStatement(query);
			    
					pStmt.setString(1, PODate);
					pStmt.setString(2, POEDate);
					
					pStmt.setString(3, lineId);
					pStmt.setString(4, catalog);
					
					pStmt.setString(5, desc);
					pStmt.setString(6, productId);
					
					pStmt.setString(7, regioId);
					pStmt.setString(8, Poqty);
					pStmt.setString(9, BalQty);

					pStmt.setString(10, userId);
					
					
				    pStmt.executeUpdate();
				}
		        
			
			}
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
			GetDBConnection.closePstmt(pStmt);
			GetDBConnection.closeConn(conn);
		}
		// TODO Auto-generated method stub
		return objectOutNode;
	}
	

	public void CheckPO(PurchaseOrder PO) {
		Connection conn 	= null;
		Statement stmt =null;
	    ResultSet rs =null;
		String sql="";
		

		
		
		
		
		
if(PO.getPo_Number()!=null &&PO.getPO_Date()!=null||PO.getEndDate()!=null||PO.getCustomerId()!=0||PO.getLineId()!=null||PO.getCatalog()!=null||PO.getDesc()!=null||PO.getProductId()!=0||PO.getRegionId()!=0) {
			
		
			
		
		
		try
		{
		
		conn = GetDBConnection.getConnection();
		
		
		
		
		
		sql = "SELECT  * from purchaseorder where  CustomerId = '" + PO.getCustomerId() + "'"; 
		
		stmt = conn.createStatement();
		 rs = stmt.executeQuery(sql);
		
		 
		
		 String str1 = Integer.toString(PO.getCustomerId()); 
	
		 if(rs.next()) {
			 
			 
			 
			    if(rs.getString(5).equals(str1)) {
			    	 System.out.println("Customer Exist-->"+str1);
			    }
			} else {
				
				InsertRowInDB(PO);
				System.out.println("New PO-->"+str1);
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
		}
	
	else {
		System.out.println("Row Not Proper or Empty");
	}	
	}
	
	
	
	
	
	
	public void InsertRowInDB(PurchaseOrder PO)
	{
		Connection conn 	= null;
		Statement stmt =null;
	    ResultSet rs		= null;
		String flag="true";
		String sql="";
		PreparedStatement preparedStmt = null;
		
		try
		{
		
		

				conn = GetDBConnection.getConnection();
				 sql = "INSERT INTO purchaseorder(PO_Number, PO_Date, PO_EndDate, CustomerId,LineId,CatalogId,Description, ProductId, RegionId, PO_Qty,Balace_Qty) "
				 		+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
 				preparedStmt = conn.prepareStatement(sql);
 				preparedStmt.setString(1, PO.getPo_Number());
 				preparedStmt.setDate(2, PO.getPO_Date());
 				preparedStmt.setDate(3, PO.getEndDate());
 				preparedStmt.setInt(4, PO.getCustomerId());
 				
 				preparedStmt.setString(5, PO.getLineId());
 				preparedStmt.setString(6, PO.getCatalog());
 				
 				preparedStmt.setString(7, PO.getDesc());
 				preparedStmt.setInt(8, PO.getProductId());
 				
 				preparedStmt.setInt(9, PO.getRegionId());
 				preparedStmt.setDouble(10, PO.getQuantity());
 				preparedStmt.setDouble(11, PO.getQuantity());
				preparedStmt.executeUpdate();
			
		   
			
		 
				
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


	@Override
	// public boolean readPOFile(MultipartFile file)
	@SuppressWarnings("deprecation")
	public boolean readPOFile() {
		System.out.println("readPOFile dao=========");
		PurchaseOrder PO = new PurchaseOrder();
		boolean flag = false;
		boolean flag1 = false;
		String msg = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Connection conn = null;
		PreparedStatement preparedStmt = null;

		String fpath = "", poNumber = "", PoDate = "", lineId = "";
		try {

			File file = new File("C:/uploads/");
			System.out.println("this is uploade file : " + file);
			
			
			File[] files = file.listFiles();
			
			for (File f : files) {

				System.out.println(file + f.getName());

				FileInputStream stream = new FileInputStream(f);

				XSSFWorkbook workbook = new XSSFWorkbook(stream);

				Iterator<Sheet> sheetIterator = workbook.sheetIterator();

				System.out.println("Retrieving Sheets using Iterator" + sheetIterator);

				Sheet sheet = workbook.getSheetAt(0);

				if (sheet.getPhysicalNumberOfRows() == 0) {
					System.out.println("Empty Excel File");
				} else {

				}
				XSSFRow row;

				Iterator<Row> rowIterator = sheet.iterator();

				System.out.println("rowIterator=== " + rowIterator);

				while (rowIterator.hasNext()) {
					row = (XSSFRow) rowIterator.next();

					// System.out.println("row=== "+row);

					Iterator<Cell> cellIterator = row.cellIterator();

					System.out.println("cellIterator=== " + cellIterator);

					while (cellIterator.hasNext()) {

						Cell cell = cellIterator.next();

						System.out.println("row.getCell(0)=== " + row.getCell(0));
						
						if(row.getCell(0)!=null) {
						
						
						java.util.Date dtcellVl = row.getCell(1).getDateCellValue();
						java.util.Date dtcellVl2 = row.getCell(2).getDateCellValue();
						
						System.out.println("row.getCell(2) getDateCellValue=== " +dtcellVl);
						System.out.println("row.getCell(2) getDateCellValue222222=== " +dtcellVl2);
						
							
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						String formattedString = dateFormat.format(dtcellVl);
						String formattedString2 = dateFormat.format(dtcellVl2);
						
						System.out.println("formattedString=== " +formattedString);
						System.out.println("formattedString2=== " +formattedString2);
						
						int cID =(int) row.getCell(10).getNumericCellValue(); 
						//int cID =(int) row.getCell(3).getNumericCellValue(); 
						System.out.println("cID=== " +cID);
						
						String lineID =row.getCell(4).getStringCellValue(); 
						System.out.println("lineID=== " +lineID);
						
						int pID =(int) row.getCell(11).getNumericCellValue(); 
						System.out.println("pID=== " +pID);
						
						String catCoDe =row.getCell(6).getStringCellValue(); 
						System.out.println("catCoDe=== " +catCoDe);
						
						String desc =row.getCell(7).getStringCellValue(); 
						System.out.println("desc=== " +desc);
						
						
						int regID =(int) row.getCell(12).getNumericCellValue(); 
						System.out.println("regID=== " +regID);
						
						int poqty =(int) row.getCell(9).getNumericCellValue(); 
						System.out.println("poqty=== " +poqty);
						
						double dpoqty = poqty;
						
						 switch (cell.getColumnIndex()) { // switch
						  
						  case 0: 
							  cell.setCellType(CellType.STRING);
							  PO.setPo_Number(cell.getStringCellValue()); 
							  break; 
						   
						  case 1:
							  PO.setNwpodate(formattedString); 
							  break; 
						  
						  case 2:
							  PO.setNwpodate1(formattedString2);
							  break; 
						
						 case 3: 
							 PO.setCustomerId(cID); 
						 break;
						
						 case 4: 
							 PO.setLineId(lineID); 
						 break;

						 case 5: 
							 PO.setProductId(pID); 
						 break;
						 
						 case 6: 
							 PO.setCatalog(catCoDe);
						 break;
						 
						 case 7: 
							 PO.setDesc(desc); 
						 break;
						 
						 case 8: 
							 PO.setRegionId(regID); 
						 break;
						 
						 case 9: 
							 PO.setQuantity(dpoqty);
						 break;
						  
						  }
					}
					}

					CheckPO(PO);
					System.out.println("Excel file not empty");
				}
			}

			System.out.println("PO obj ===" + PO);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;

	}

	

	
	
	
	
}
