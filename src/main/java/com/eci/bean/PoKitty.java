package com.eci.bean;

import java.sql.Date;

public class PoKitty {

	private int Id;
	private String po_Number;
	private Date PO_Date;
	private String Name;
	
	
	private int purId;
	private String LineId;
	private int CustomerId;
	
	private int ProductId;
	
	private String authKey;
	
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	public String getPo_Number() {
		return po_Number;
	}
	public void setPo_Number(String po_Number) {
		this.po_Number = po_Number;
	}
	private int RegionId;

	private Date startDate;
	private Date endDate;
	
	private double quantity;
	private double Price;
	
	private String IsDeleted;
	private int CreatedBy;
	private int ModifiedBy;
	
	private Date CreatedDate ;
	private Date ModifiedDate;
	private int AssignedTo;
	
	private String KittyRemark;
	
	
	public String getKittyRemark() {
		return KittyRemark;
	}
	public void setKittyRemark(String kittyRemark) {
		KittyRemark = kittyRemark;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	
	
	
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}

	public Date getPO_Date() {
		return PO_Date;
	}
	public void setPO_Date(Date pO_Date) {
		PO_Date = pO_Date;
	}
	public String getLineId() {
		return LineId;
	}
	public void setLineId(String lineId) {
		LineId = lineId;
	}
	public int getCustomerId() {
		return CustomerId;
	}
	public void setCustomerId(int customerId) {
		CustomerId = customerId;
	}
	




	public int getPurId() {
		return purId;
	}
	public void setPurId(int purId) {
		this.purId = purId;
	}
	
	public int getProductId() {
		return ProductId;
	}
	public void setProductId(int productId) {
		ProductId = productId;
	}
	public int getRegionId() {
		return RegionId;
	}
	public void setRegionId(int regionId) {
		RegionId = regionId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		Price = price;
	}
	public String getIsDeleted() {
		return IsDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		IsDeleted = isDeleted;
	}
	public int getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(int createdBy) {
		CreatedBy = createdBy;
	}
	public int getModifiedBy() {
		return ModifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		ModifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}
	public Date getModifiedDate() {
		return ModifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		ModifiedDate = modifiedDate;
	}
	public int getAssignedTo() {
		return AssignedTo;
	}
	public void setAssignedTo(int assignedTo) {
		AssignedTo = assignedTo;
	}
	
	
	
	
}
