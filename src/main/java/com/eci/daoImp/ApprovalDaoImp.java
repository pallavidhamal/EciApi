package com.eci.daoImp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.eci.bean.Approval;
import com.eci.dao.ApprovalDao;
import com.eci.util.GetDBConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class ApprovalDaoImp implements ApprovalDao {

	GetDBConnection getConn = new GetDBConnection();
	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ObjectNode getApprovalHPMList() {
		// TODO Auto-generated method stub

		ObjectNode objectOutNode = objectMapper.createObjectNode();
		ArrayNode arrayOutNode = objectMapper.createArrayNode();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "    SELECT  inst.id AS id, inst.JobId AS JobId, cm.CustName AS CustomerName, um.Emp_Name AS SI, pm.Name AS ProductName,inst.Site AS Site, inst.Location AS Location, tm.TaskName as Stages, "
				+ " inst.CurrentStage AS Stages,inst.Remark, isc.Status AS Status FROM     installation inst "
				+ "inner join customer_master cm on inst.CustomerId = cm.CustomerID "
				+ " inner join product_master pm on inst.ProductId = pm.ProductId "
				+ "inner join user_master um on inst.SI = um.EmpId "
				+ "inner join inc_status_codes isc on inst.StatusId = isc.Id "
				+ "left join task_master tm on inst.CurrentStage = tm.TaskId  where inst.IsDeleted = 'N' and  StatusId IN (12)";

		try {
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();

			System.out.println("----sql getApprovalHPMList----" + sql);

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				ObjectNode objectInNode = objectMapper.createObjectNode();

				objectInNode.put("id", rs.getString("id"));
				objectInNode.put("JobId", rs.getString("JobId"));
				objectInNode.put("Remark", rs.getString("Remark"));
				objectInNode.put("CustomerName", rs.getString("CustomerName"));
				objectInNode.put("SI", rs.getString("SI"));
				objectInNode.put("ProductName", rs.getString("ProductName"));
				objectInNode.put("Site", rs.getString("Site"));
				objectInNode.put("Status", rs.getString("Status"));
				objectInNode.put("Stages", rs.getString("Stages"));
				objectInNode.put("Location", rs.getString("Location"));

				arrayOutNode.add(objectInNode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GetDBConnection.closeRS(rs);
			GetDBConnection.closeStmt(stmt);
			GetDBConnection.closeConn(conn);
		}
		objectOutNode.put("result", arrayOutNode);

		return objectOutNode;
	}

	@Override
	public boolean approvalStatusId(Approval approval) {
		// TODO Auto-generated method stub
		boolean flag = false;

		InstallationDaoImp insdaoimp = new InstallationDaoImp();

		Connection conn = null;
		PreparedStatement preparedStmt = null;
		Statement Stmt = null;
		ResultSet rs = null;
		int userId = 0;// here it is SI
		try {
			conn = GetDBConnection.getConnection();

			System.out.println("QUERY For approval.getId() ==  " + approval.getId());

			System.out.println("QUERY For approval.getAuthKey() ==  " + approval.getAuthKey());

			String SQL_QUERY = "update installation set StatusId = 13 where id = " + approval.getId();

			System.out.println("QUERY For approvalStatusId==  " + SQL_QUERY);

			preparedStmt = conn.prepareStatement(SQL_QUERY);

			preparedStmt.executeUpdate();

			flag = true;

			// newly added , required for HPm approved installation link
			
			  String sqlQry = " INSERT INTO installation_actions (IncId , Action , ActionDate , ActionBy ) VALUES(?, ?, Now(), ?) "  ;
			  
			  System.out.println("----sql insertInstallationAction----"+sqlQry);
			  
			  preparedStmt = conn.prepareStatement(sqlQry);
			  
			  preparedStmt.setInt(1, approval.getId());
			  preparedStmt.setInt(2, 13);
			  preparedStmt.setString(3, approval.getAuthKey());
			  preparedStmt.executeUpdate();
			 

			// newly added

			flag = true;

			if (flag == true)// send notification to SI that he has new task
			{
				String sql = "SELECT SI FROM installation where id = " + approval.getId();

				Stmt = conn.createStatement();
				rs = Stmt.executeQuery(sql);

				while (rs.next()) {
					userId = rs.getInt("SI");
				}
				System.out.println("Sendng notification to SI after ins approval- SI id" + sql + "===" + userId);

				boolean flg = insdaoimp.sendNotification(userId);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			GetDBConnection.closePstmt(preparedStmt);
			GetDBConnection.closeConn(conn);
		}

		// TODO Auto-generated method stub
		return flag;
	}

}
