package com.eci.daoImp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.eci.bean.PoKitty;
import com.eci.dao.PoKittyDao;
import com.eci.util.GetDBConnection;
import com.eci.util.TokenServiceImp;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class PoKittyDaoImp implements PoKittyDao {
	

//	private Connection conn = null;
//	private ResultSet rs	= null;
	GetDBConnection getConn = new GetDBConnection();
	ObjectMapper objectMapper = new ObjectMapper();
	TokenServiceImp tokService=new TokenServiceImp();

	@Override
	public ObjectNode getPOKittyList(int userid) {
		
		System.out.println("----PoKittyDaoImp getPOKittyList----"+userid);
		
		// TODO Auto-generated method stub
		
			ObjectNode objectOutNode =objectMapper.createObjectNode();
			ArrayNode arrayOutNode = objectMapper.createArrayNode();
			int i=0;
			
			Connection conn = null;
			Statement stmt 	= null;
			ResultSet rs	= null;
			String remark;
				
			/*String sql = "select * from purchaseorder POM, customer_master CM, product_master PM, region_master RM  where POM.CustomerId = CM.CustomerID and "
							+ " POM.ProductId = PM.ProductId and POM.RegionId = RM.RegionId and "
							+ " CURDATE() between POM.PO_Date and POM.PO_EndDate and " 
							+ " POM.IsDeleted = 'N' and POM.regionId in (select regionId from user_region where userId = "+userid+" ) " ;*/
			
			
			String sql = "select * from purchaseorder POM, customer_master CM, product_master PM, region_master RM  where POM.CustomerId = CM.CustomerID "
					    + "	and POM.ProductId = PM.ProductId and POM.RegionId = RM.RegionId AND CURDATE() between POM.PO_Date and POM.PO_EndDate and" + 
					     "  POM.IsDeleted = 'N' and POM.regionId in (select regionId from user_region where userId= "+userid +") "  ;
								
			System.out.println("----sql getPOKittyList----"+sql);
			
			//System.out.println("----sql getPOKittyList----"+rs.getString("KittyRemark"));
			
			
				try
				{
					conn = GetDBConnection.getConnection();
					stmt = conn.createStatement();
					
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
						
						remark = rs.getString("KittyRemark");
						if(remark==null)
						remark="";
						
						objectInNode.put("KittyRemark",remark);
						
						arrayOutNode.add(objectInNode);
						System.out.println("----sql getAllPorders-KittyRemark--arrayOutNode-"+arrayOutNode);
						
							
						
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
	public ObjectNode updatePOremark(PoKitty poKitty) {
		// TODO Auto-generated method stub
		ObjectNode objectOutNode =objectMapper.createObjectNode();
		String strAuth="Authorised";
		PreparedStatement pStmt = null;
		Connection conn 	= null;
		
		boolean flag = false;
		try
		{
			String strauth= poKitty.getAuthKey();
			
			if(tokService.isTokenValid(strauth))
			{
			conn = GetDBConnection.getConnection();
			
			System.out.println("QUERY For updatePOremark=getId=  "+poKitty.getId());
			
			/*String SQL_QUERY = "UPDATE purchaseorder SET KittyRemark  = ?  where  id = " +pOrder.getId() ;*/
			
			String SQL_QUERY = "UPDATE purchaseorder set KittyRemark  = ? , RemarkBy = '3' ,  RemarkUpdateDate = NOW() where id = " +poKitty.getId() ;
			
			System.out.println("QUERY For updatePOremark==  "+poKitty.getKittyRemark());
			
			System.out.println("QUERY For updatePOremark==  "+SQL_QUERY);
			
			pStmt = conn.prepareStatement(SQL_QUERY);
			
			pStmt.setString(1, poKitty.getKittyRemark());

			pStmt.executeUpdate();
			
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
			GetDBConnection.closeStmt(pStmt);
			GetDBConnection.closeConn(conn);
		}
		
		
		// TODO Auto-generated method stub
		return objectOutNode;
	}

	
}
