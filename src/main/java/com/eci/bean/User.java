package com.eci.bean;

import java.util.Date;
import java.util.List;

public class User {

	
	private int id;
	private String name;
	private int sCompany ;
	private String customerName;
	private String userKey;
	private String apkVersion;
	private String mObileModel;
	private String oS;
	private String manufacturer;
	
	
	public String getApkVersion() {
		return apkVersion;
	}
	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}
	public String getMObileModel() {
		return mObileModel;
	}
	public void setMObileModel(String mObileModel) {
		this.mObileModel = mObileModel;
	}
	public String getOS() {
		return oS;
	}
	public void setOS(String oS) {
		this.oS = oS;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	private String oldpassword;
	private String newpassword;
	private String emppassword;
	
	
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getEmppassword() {
		return emppassword;
	}
	public void setEmppassword(String emppassword) {
		this.emppassword = emppassword;
	}
	private String address;
	private String email;
	private String contact;
	private int region;
	private String regionStr;  //concatenated regions
	
	
	private List <Region> reg;
	
	private int roletype;
	private int role;
	private String type;
	
	private String password;
	private int approverId;
	
	private String IsDeleted;
	private int CreatedBy;
	private int ModifiedBy;
	
	private String userName;
	
	private String authKey;
	
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	private Date CreatedDate = new Date();
	private Date ModifiedDate = new Date();
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getsCompany() {
		return sCompany;
	}
	public void setsCompany(int sCompany) {
		this.sCompany = sCompany;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public int getRoletype() {
		return roletype;
	}
	public void setRoletype(int roletype) {
		this.roletype = roletype;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public int getRegion() {
		return region;
	}
	public void setRegion(int region) {
		this.region = region;
	}
	public int getApproverId() {
		return approverId;
	}
	public void setApproverId(int approverId) {
		this.approverId = approverId;
	}
	public String getRegionStr() {
		return regionStr;
	}
	public void setRegionStr(String regionStr) {
		this.regionStr = regionStr;
	}
	public List<Region> getReg() {
		return reg;
	}
	public void setReg(List<Region> reg) {
		this.reg = reg;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	
	
	
}
