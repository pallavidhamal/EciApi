package com.eci.bean;

import java.util.Date;

public class InstallationAction {

	
	
	private int id;
	private String jobId;
	private int incId;
	private String action;
	private int actionBy;
	private Date CreatedDate = new Date();
	private String remark;
	private int teId;
	
	private int taskId;
	private int testId;
	
	private String withVisit;
	private String imgPath;
	private String updateOrChkout;
	
	
	private String Latitude;
	private String Longitude;
	
	private String authKey;
	
	private String reason;
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
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
	public void setId(int id) {
		this.id = id;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public int getIncId() {
		return incId;
	}
	public void setIncId(int incId) {
		this.incId = incId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getActionBy() {
		return actionBy;
	}
	public void setActionBy(int actionBy) {
		this.actionBy = actionBy;
	}
	public Date getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getTeId() {
		return teId;
	}
	public void setTeId(int teId) {
		this.teId = teId;
	}
	public String getWithVisit() {
		return withVisit;
	}
	public void setWithVisit(String withVisit) {
		this.withVisit = withVisit;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getUpdateOrChkout() {
		return updateOrChkout;
	}
	public void setUpdateOrChkout(String updateOrChkout) {
		this.updateOrChkout = updateOrChkout;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getTestId() {
		return testId;
	}
	public void setTestId(int testId) {
		this.testId = testId;
	}
	
	
}
