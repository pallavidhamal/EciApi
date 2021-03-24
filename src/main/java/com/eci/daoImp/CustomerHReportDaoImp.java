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

import com.eci.dao.CustomerHReportDao;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.DocumentException;

@Repository
public class CustomerHReportDaoImp implements CustomerHReportDao {

	GetDBConnection getConn = new GetDBConnection();
	TokenServiceImp tokService=new TokenServiceImp();

	ObjectMapper objectMapper = new ObjectMapper();

	
	@Override
	public ObjectNode getCustHReportList(int custId) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();
		ArrayNode arrTempNode = objectMapper.createArrayNode();

		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		
		String sql=" SELECT  inst.id AS id, inst.JobId AS JobId, cm.CustName AS CustomerName, um.Emp_Name AS SI, pm.Name AS ProductName,inst.Site AS Site, inst.Location AS Location, tm.TaskName as Stages,sic.Company, rm.RegionName as Region, " + 
				" inst.CurrentStage AS Stages,inst.Remark, isc.Status AS Status FROM     installation inst 	inner join customer_master cm on inst.CustomerId = cm.CustomerID " + 
				" inner join product_master pm on inst.ProductId = pm.ProductId 	inner join user_master um on inst.SI = um.EmpId " + 
				" inner join inc_status_codes isc on inst.StatusId = isc.Id  left join task_master tm on inst.CurrentStage = tm.TaskId   left join si_company sic on um.SICompany = sic.Id inner join region_master rm on inst.RegionId = rm.RegionId " + 
				"  where inst.IsDeleted = 'N' and   StatusId IN (15) and um.EmpId =  "+custId ;
		
			try
			{
				conn = GetDBConnection.getConnection();
				stmt = conn.createStatement();
				
				System.out.println("---getCustHReportList sql---"+sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					ObjectNode objectInNode = objectMapper.createObjectNode();
					objectInNode.put("id",rs.getString("id"));
					objectInNode.put("JobId",rs.getString("JobId"));
					objectInNode.put("CustomerName",rs.getString("CustomerName"));
					objectInNode.put("ProductName",rs.getString("ProductName"));
					objectInNode.put("SI",rs.getString("SI"));
					objectInNode.put("Company",rs.getString("Company"));
					objectInNode.put("Site",rs.getString("Site"));
					objectInNode.put("Location",rs.getString("Location"));
					objectInNode.put("Stages",rs.getString("Stages"));
					objectInNode.put("Remark",rs.getString("Remark"));
					objectInNode.put("Status",rs.getString("Status"));
					objectInNode.put("Region",rs.getString("Region"));
					
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
		
			System.out.println("=getCustHReportList===="+objectOutNode);
			
		return objectOutNode;
	}


}
