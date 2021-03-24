package com.eci.bean;

import java.util.Date;
import java.util.List;

public class Summary {

	
	private int id;
	private String JobId;
	private int CustomerId;
	
	private int ProductId;
	private int siId;
	private int teId;
	private int Quantity;
	
	private String Site;  
	private String Location;
	private int RegionId;
	private int POLineId;
	private String CurrentStage;
	private int taskId;
	private int taskCategoryId; // whether general requirement or gauge reqmt 

	private String IsDeleted;
	private String CurrentStatus;
	private String CreatedBy;
	private String UpdateBY;
	private String typeFlag;
	private String recId;
	
	private String site_To;
	private String location_To;
	
	
	
	
	public String getSite_To() {
		return site_To;
	}
	public void setSite_To(String site_To) {
		this.site_To = site_To;
	}
	public String getLocation_To() {
		return location_To;
	}
	public void setLocation_To(String location_To) {
		this.location_To = location_To;
	}
	public String getRecId() {
		return recId;
	}
	public void setRecId(String recId) {
		this.recId = recId;
	}
	public String getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}


	private String authKey;
	
	private int StatusId;
	
	public int getStatusId() {
		return StatusId;
	}
	public void setStatusId(int statusId) {
		StatusId = statusId;
	}
	
	
	private String Remark;
	
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	private List <Task> task;
	
	
	private Date CreatedDate = new Date();
	private Date UpdatedDate = new Date();
	
	
	public List<Task> getTask() {
		return task;
	}
	public void setTask(List<Task> task) {
		this.task = task;
	}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJobId() {
		return JobId;
	}
	public void setJobId(String jobId) {
		JobId = jobId;
	}
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
	
	public int getSiId() {
		return siId;
	}
	public void setSiId(int siId) {
		this.siId = siId;
	}

	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public String getSite() {
		return Site;
	}
	public void setSite(String site) {
		Site = site;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public int getRegionId() {
		return RegionId;
	}
	public void setRegionId(int regionId) {
		RegionId = regionId;
	}
	public int getPOLineId() {
		return POLineId;
	}
	public void setPOLineId(int pOLineId) {
		POLineId = pOLineId;
	}
	public String getCurrentStage() {
		return CurrentStage;
	}
	public void setCurrentStage(String currentStage) {
		CurrentStage = currentStage;
	}
	public String getIsDeleted() {
		return IsDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		IsDeleted = isDeleted;
	}
	public String getCurrentStatus() {
		return CurrentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		CurrentStatus = currentStatus;
	}
	public String getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}
	public String getUpdateBY() {
		return UpdateBY;
	}
	public void setUpdateBY(String updateBY) {
		UpdateBY = updateBY;
	}
	public Date getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}
	public Date getUpdatedDate() {
		return UpdatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		UpdatedDate = updatedDate;
	}
	public int getTeId() {
		return teId;
	}
	public void setTeId(int teId) {
		this.teId = teId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getTaskCategoryId() {
		return taskCategoryId;
	}
	public void setTaskCategoryId(int taskCategoryId) {
		this.taskCategoryId = taskCategoryId;
	}
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	

	
}
