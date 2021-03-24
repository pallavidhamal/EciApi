package com.eci.bean;

import java.util.Date;

public class Region {

	private int Id ;
	
	private String Region ;
	private String Region_Id ;
	
	private String Desc;
	private String IsDeleted;
	private int CreatedBy;
	private int ModifiedBy;
	
	private String authKey;
	
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	private Date CreatedDate = new Date();
	private Date ModifiedDate = new Date();
	
	public String getRegion_Id() {
		return Region_Id;
	}
	public void setRegion_Id(String region_Id) {
		Region_Id = region_Id;
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getRegion() {
		return Region;
	}
	public void setRegion(String region) {
		Region = region;
	}
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		Desc = desc;
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
	
}
