package com.eci.bean;

import java.util.Date;
import java.util.List;

public class CompleteJobReport {

	
	//ApprovedTasksReport
	
	private int id;
	private int siId;
	
	private int TaskId;
	private String strMode; // atp_tests photo , update task ,tests given with
	
	
	public int getTaskId() {
		return TaskId;
	}
	public void setTaskId(int taskId) {
		TaskId = taskId;
	}
	private String dateTo;
	private String dateFrom;
	
	private String authKey;
	
	 private int CustomerId;
	  private int ProductId;
	
	
	public int getCustomerId() {
		return CustomerId;
	}
	public void setCustomerId(int customerId) {
		CustomerId = customerId;
	}
	public int getProductId() {
		return ProductId;
	}
	public void setProductId(int productId) {
		ProductId = productId;
	}
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	
	public int getId() {
		return id;
	}
	public String getStrMode() {
		return strMode;
	}
	public void setStrMode(String strMode) {
		this.strMode = strMode;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSiId() {
		return siId;
	}
	public void setSiId(int siId) {
		this.siId = siId;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	
	
}
