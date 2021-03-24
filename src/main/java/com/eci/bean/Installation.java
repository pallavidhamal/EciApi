
 package com.eci.bean;

import java.util.Date;
import java.util.List;

public class Installation
{
  private String pdfid;
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
  private int taskCategoryId;
  private String Type_of_Migration;
  
  private String ringname;
  private String planId;
  private String irNumber;
  private String srnumber;
  private String circlename;
  
  private String releaseDt;
  private String rfiDt;
  
  private String fromEngineer;
  private String toEngineer;
  private String Purpose;

public String getPurpose() {
	return Purpose;
}

public void setPurpose(String purpose) {
	this.Purpose = purpose;
}

public String getReleaseDt() {
	return releaseDt;
}

public void setReleaseDt(String releaseDt) {
	this.releaseDt = releaseDt;
}

public String getRfiDt() {
	return rfiDt;
}

public void setRfiDt(String rfiDt) {
	this.rfiDt = rfiDt;
}

public String getRingname() {
	return ringname;
}

public void setRingname(String ringname) {
	this.ringname = ringname;
}

public String getPlanId() {
	return planId;
}

public void setPlanId(String planId) {
	this.planId = planId;
}

public String getIrNumber() {
	return irNumber;
}

public void setIrNumber(String irNumber) {
	this.irNumber = irNumber;
}

public String getSrnumber() {
	return srnumber;
}

public void setSrnumber(String srnumber) {
	this.srnumber = srnumber;
}

public String getCirclename() {
	return circlename;
}

public void setCirclename(String circlename) {
	this.circlename = circlename;
}


public Installation() {}
  
  public String getCustomerName()
  {
    return customerName;
  }
  
  public void setCustomerName(String customerName)
  { this.customerName = customerName;
  }
  
  public String getProductName() {
    return productName;
  }
  
  public void setProductName(String productName) { this.productName = productName; }
  
  public String getType() {
    return Type;
  }
  
  public void setType(String type) { Type = type; }
  

  private String IsDeleted;
  
  private String CurrentStatus;
  
  private String CreatedBy;
  
  private String UpdateBY;
  private String customerName;
  private String productName;
  private String typeFlag;
  
  public String getStagecategoryId()
  {
    return stagecategoryId;
  }
  
  public void setStagecategoryId(String stagecategoryId) { this.stagecategoryId = stagecategoryId; }
  
  public String getSite_To() {
    return site_To;
  }
  
  public void setSite_To(String site_To) { this.site_To = site_To; }
  
  public String getLocation_To() {
    return location_To;
  }
  
  public void setLocation_To(String location_To) { this.location_To = location_To; }
  
  public String getRecId() {
    return recId;
  }
  
  public void setRecId(String recId) { this.recId = recId; }
  
  public String getTypeFlag() {
    return typeFlag;
  }
  
  public void setTypeFlag(String typeFlag) { this.typeFlag = typeFlag; }
  
  private String recId;
  private String site_To;
  private String location_To;
  private String stagecategoryId;
  
  public int getStatusId()
  {
    return StatusId;
  }
  
  public void setStatusId(int statusId) { StatusId = statusId; }
  
  private String Type;
  private String authKey;
  private int StatusId;
  private String Remark;
  public String getRemark() {
    return Remark;
  }
  
  public void setRemark(String remark) { Remark = remark; }
  




  private Date CreatedDate = new Date();
  private Date UpdatedDate = new Date();
  private List<Task> task;
  
  public List<Task> getTask()
  {
    return task;
  }
  
  public void setTask(List<Task> task) { this.task = task; }
  

  public int getId()
  {
    return id;
  }
  
  public void setId(int id) { this.id = id; }
  
  public String getJobId() {
    return JobId;
  }
  
  
  
  public void setJobId(String jobId) { JobId = jobId; }
  
  public String getType_of_Migration() {
		return Type_of_Migration;
	}

	public void setType_of_Migration(String type_of_Migration) {
		Type_of_Migration = type_of_Migration;
	}
  
  public int getCustomerId() {
    return CustomerId;
  }
  
  public void setCustomerId(int customerId) 
  { 
	  CustomerId = customerId; 
	  }
  
  public int getProductId() {
    return ProductId;
  }
  
  public void setProductId(int productId) { ProductId = productId; }
  
  public int getSiId()
  {
    return siId;
  }
  
  public void setSiId(int siId) { this.siId = siId; }
  
  public int getQuantity()
  {
    return Quantity;
  }
  
  public void setQuantity(int quantity) { Quantity = quantity; }
  
  public String getSite() {
    return Site;
  }
  
  public void setSite(String site) { Site = site; }
  
  public String getLocation() {
    return Location;
  }
  
  public void setLocation(String location) { Location = location; }
  
  public int getRegionId() {
    return RegionId;
  }
  
  public void setRegionId(int regionId) { RegionId = regionId; }
  
  public int getPOLineId() {
    return POLineId;
  }
  
  public void setPOLineId(int pOLineId) { POLineId = pOLineId; }
  
  public String getCurrentStage() {
    return CurrentStage;
  }
  
  public void setCurrentStage(String currentStage) { CurrentStage = currentStage; }
  
  public String getIsDeleted() {
    return IsDeleted;
  }
  
  public void setIsDeleted(String isDeleted) { IsDeleted = isDeleted; }
  
  public String getCurrentStatus() {
    return CurrentStatus;
  }
  
  public void setCurrentStatus(String currentStatus) { CurrentStatus = currentStatus; }
  
  public String getCreatedBy() {
    return CreatedBy;
  }
  
  public void setCreatedBy(String createdBy) { CreatedBy = createdBy; }
  
  public String getUpdateBY() {
    return UpdateBY;
  }
  
  public void setUpdateBY(String updateBY) { UpdateBY = updateBY; }
  
  public Date getCreatedDate() {
    return CreatedDate;
  }
  
  public void setCreatedDate(Date createdDate) { CreatedDate = createdDate; }
  
  public Date getUpdatedDate() {
    return UpdatedDate;
  }
  
  public void setUpdatedDate(Date updatedDate) { UpdatedDate = updatedDate; }
  
  public int getTeId() {
    return teId;
  }
  
  public void setTeId(int teId) { this.teId = teId; }
  
  public int getTaskId() {
    return taskId;
  }
  
  public void setTaskId(int taskId) { this.taskId = taskId; }
  
  public int getTaskCategoryId() {
    return taskCategoryId;
  }
  
  public void setTaskCategoryId(int taskCategoryId) { this.taskCategoryId = taskCategoryId; }
  
  public String getAuthKey() {
    return authKey;
  }
  
  public void setAuthKey(String authKey) { this.authKey = authKey; }
  
  public String getPdfid() {
    return pdfid;
  }
  
  public void setPdfid(String pdfid) { this.pdfid = pdfid; }

public String getFromEngineer() {
	return fromEngineer;
}

public void setFromEngineer(String fromEngineer) {
	this.fromEngineer = fromEngineer;
}

public String getToEngineer() {
	return toEngineer;
}

public void setToEngineer(String toEngineer) {
	this.toEngineer = toEngineer;
}
  
  
  
  
}
 