package com.eci.bean;

import java.util.Date;
import java.util.List;

public class WipPendingReport {

	
	//ApprovedTasksReport
	
	private int id;
	private int siId;

	private String dateTo;
	private String dateFrom;
	
	private String authKey;
	
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	
	public int getId() {
		return id;
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
