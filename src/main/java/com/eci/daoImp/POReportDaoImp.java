package com.eci.daoImp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;

import com.eci.dao.POReportDao;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@Repository
public class POReportDaoImp implements POReportDao {

		GetDBConnection getConn = new GetDBConnection();
	TokenServiceImp tokService=new TokenServiceImp();

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ObjectNode getPOReportData(int id) {
		boolean flag = false;
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		String result="error";
		
		String sql = "SELECT  inc.RegionId, inc.POLineId, PO.Id, PO.PO_Number,  inc.RegionId, PO.PO_Number, PO.PO_Date, PO.CatalogId, PO.LineId, PO.Description, PO.Balace_Qty, pm.Name, cm.CustName,rm.RegionId, PO.PO_Qty, inc.StatusId," + 
				"    inc.SI , rm.RegionName," + 
				"    SUM(IF(IA.Action = 15, 1 , 0))as  Completed, " + 
				"    SUM(IF(IA.Action IN (1,13, 5 ), 1, 0)) AS NotYetstarted, " + 
				"    SUM(IF(IA.Action IN (3 , 4, 10, 12), 1, 0)|| IF(ST.Action IN ( 7, 9), 1, 0)) as Pending , " + 
				"    SUM(IF(IA.Action IN (16,2, 17), 1, 0)|| IF(ST.Action IN (6, 8), 1, 0)) as  WIP " + 
				"	FROM installation_actions IA   left join eci.installation inc  on IA.IncId = inc.id" + 
				"   left join si_tejobupdates ST on ST.IncId= inc.id " + 
				"   left join purchaseorder PO on PO.Id = inc.POLineId  " + 
				"   left join  product_master pm on pm.ProductId = inc.ProductId  " + 
				"   left join customer_master cm on cm.CustomerID = inc.CustomerId  " + 
				"   left join  region_master rm on rm.RegionId = inc.RegionId    " + 					//where inc.CreatedBy = 3
				"   GROUP BY inc.POLineId ,inc.RegionId  " + 
				"   ORDER BY inc.RegionId , PO.Id ";
		//inc.CreatedBy = '"+id+"'
		System.out.println("----getPOReportData 111111----"+sql);
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
					
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					ObjectNode objectInNode = objectMapper.createObjectNode();

					objectInNode.put("RegionId", rs.getString("RegionId"));
					objectInNode.put("PO_Number", rs.getString("PO_Number"));
					objectInNode.put("LineId", rs.getString("LineId"));
					objectInNode.put("PO_Date", rs.getString("PO_Date"));
					objectInNode.put("CustName", rs.getString("CustName"));
					
					objectInNode.put("Name", rs.getString("Name"));
					objectInNode.put("RegionName", rs.getString("RegionName"));
					objectInNode.put("CatalogId", rs.getString("CatalogId"));
					objectInNode.put("Description", rs.getString("Description"));
					objectInNode.put("Balace_Qty", rs.getString("Balace_Qty"));
					
					objectInNode.put("Completed", rs.getString("Completed"));
					objectInNode.put("Pending", rs.getString("Pending"));
					objectInNode.put("WIP", rs.getString("WIP"));
					objectInNode.put("NotYetstarted", rs.getInt("NotYetstarted"));
					
					arrayOutNode.add(objectInNode);
					
					
				}
				objectOutNode.put("data", arrayOutNode.toString());
				result= "success";
				System.out.println("----getPOReportData--objectOutNode--"+objectOutNode);				
				
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

	@Override
	public ObjectNode getClosedPOReportData(int id) {
		// TODO Auto-generated method stub

		boolean flag = false;
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		String result="error";
		
		/*
		 * String sql =
		 * "SELECT  inc.RegionId, PO.PO_Number, PO.PO_Date, PO.CatalogId, PO.LineId, PO.Description, PO.Balace_Qty, pm.Name, cm.CustName, inc.SI,rm.RegionName, um.Emp_Name,rm.RegionId,  "
		 * + "     SUM(IF(inc.StatusId = 5, 1, 0)) AS Pending," +
		 * "    SUM(IF(inc.StatusId = 15, 1, 0)) AS Completed," +
		 * "    SUM(IF(inc.StatusId = 16, 1, 0)) AS WIP" +
		 * "	 FROM eci.installation inc, purchaseorder PO, product_master pm, customer_master cm,\r\n"
		 * + "    region_master rm, user_master um  WHERE " +
		 * "    inc.RegionId = rm.RegionId and PO.Id = inc.POLineId and inc.ProductId = pm.ProductId  and inc.CustomerId = cm.CustomerID "
		 * + "    AND inc.SI = um.EmpId AND inc.CreatedBy = '"+id+"' " +
		 * "	GROUP BY rm.RegionId , inc.SI " + "	ORDER BY inc.RegionId , inc.SI; ";
		 */
		
		
		
		String sql = "SELECT  inc.RegionId, inc.POLineId, PO.Id, PO.PO_Number,  inc.RegionId, PO.PO_Number, PO.PO_Date, PO.CatalogId, PO.LineId, PO.Description, PO.Balace_Qty, pm.Name, cm.CustName,rm.RegionId, PO.PO_Qty, inc.StatusId," + 
				"    inc.SI , rm.RegionName," + 
				"    SUM(IF(IA.Action = 15, 1 , 0))as  Completed, " + 
				"    SUM(IF(IA.Action IN (1,13, 5 ), 1, 0)) AS NotYetstarted, " + 
				"    SUM(IF(IA.Action IN (3 , 4, 10, 12), 1, 0)|| IF(ST.Action IN ( 7, 9), 1, 0)) as Pending , " + 
				"    SUM(IF(IA.Action IN (16,2, 17), 1, 0)|| IF(ST.Action IN (6, 8), 1, 0)) as  WIP " + 
				"	FROM installation_actions IA   left join eci.installation inc  on IA.IncId = inc.id" + 
				"   left join si_tejobupdates ST on ST.IncId= inc.id " + 
				"   left join purchaseorder PO on PO.Id = inc.POLineId  " + 
				"   left join  product_master pm on pm.ProductId = inc.ProductId  " + 
				"   left join customer_master cm on cm.CustomerID = inc.CustomerId  " + 
				"   left join  region_master rm on rm.RegionId = inc.RegionId  where PO.Balace_Qty = 0 " + 						//inc.CreatedBy = 3  and
				"   GROUP BY inc.POLineId ,inc.RegionId  " + 
				"   ORDER BY inc.RegionId , PO.Id ";
		
		//'"+id+"' inc.CreatedBy = '"+id+"' 
		
		System.out.println("----getClosedPOReportData getClosedPOReportData ----"+sql);
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
					
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					ObjectNode objectInNode = objectMapper.createObjectNode();
					objectInNode.put("RegionId", rs.getString("RegionId"));
					objectInNode.put("PO_Number", rs.getString("PO_Number"));
					objectInNode.put("LineId", rs.getString("LineId"));
					objectInNode.put("PO_Date", rs.getString("PO_Date"));
					objectInNode.put("CustName", rs.getString("CustName"));
					
					objectInNode.put("Name", rs.getString("Name"));
					objectInNode.put("RegionName", rs.getString("RegionName"));
					objectInNode.put("CatalogId", rs.getString("CatalogId"));
					objectInNode.put("Description", rs.getString("Description"));
					objectInNode.put("Balace_Qty", rs.getString("Balace_Qty"));
					
					objectInNode.put("Completed", rs.getString("Completed"));
					objectInNode.put("Pending", rs.getString("Pending"));
					objectInNode.put("WIP", rs.getString("WIP"));
					objectInNode.put("NotYetstarted", rs.getInt("NotYetstarted"));
					
					arrayOutNode.add(objectInNode);
					
					
				}
				objectOutNode.put("data", arrayOutNode.toString());
				result= "success";
				System.out.println("----getPOReportData--objectOutNode--"+objectOutNode);				
				
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



	
	
	
	
}
