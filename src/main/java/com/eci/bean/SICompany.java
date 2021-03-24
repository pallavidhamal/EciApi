
package com.eci.bean;

import java.util.Date;
import java.util.List;

public class SICompany {

	
	private int Id ;
	private String Task ;
	
	private String IsDeleted;
	private int CreatedBy;
	private int ModifiedBy;
	private int siId;
	
	private Date CreatedDate = new Date();
	private Date ModifiedDate = new Date();
	
	
	private List <Task> task1;
	
	
	
	public List<Task> getTask1() {
		return task1;
	}
	public void setTask1(List<Task> task1) {
		this.task1 = task1;
	}
	
	
	public int getSiId() {
		return siId;
	}
	public void setSiId(int siId) {
		this.siId = siId;
	}
	
	
	public String getInstid() {
		return instid;
	}
	public void setInstid(String instid) {
		this.instid = instid;
	}
	private int pOLineId ;
	
	private String authKey;
	
	private String instid;
	
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	public int getpOLineId() {
		return pOLineId;
	}
	public void setpOLineId(int pOLineId) {
		this.pOLineId = pOLineId;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getTask() {
		return Task;
	}
	public void setTask(String task) {
		Task = task;
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
