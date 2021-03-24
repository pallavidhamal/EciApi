package com.eci.bean;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class SI {

	
	private int id;
	private int si_Id;
	private int job_Id;
	private int te_Id;	
	private int tid;

	
	private MultipartFile file; 
	private int taskid;  //preinstall etc
	

	private String siUpdateRemark;
	private String SIRejectRemark;
	
	
	
	private int TestId; //site type etc 
	private String TestFlag;
	
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
	public int getJob_Id() {
		return job_Id;
	}
	public void setJob_Id(int job_Id) {
		this.job_Id = job_Id;
	}
	public String getSiUpdateRemark() {
		return siUpdateRemark;
	}
	public void setSiUpdateRemark(String siUpdateRemark) {
		this.siUpdateRemark = siUpdateRemark;
	}
	
	public int getSi_Id() {
		return si_Id;
	}
	public void setSi_Id(int si_Id) {
		this.si_Id = si_Id;
	}
	public int getTe_Id() {
		return te_Id;
	}
	public void setTe_Id(int te_Id) {
		this.te_Id = te_Id;
	}
	public String getSIRejectRemark() {
		return SIRejectRemark;
	}
	public void setSIRejectRemark(String sIRejectRemark) {
		SIRejectRemark = sIRejectRemark;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public int getTaskid() {
		return taskid;
	}
	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}
	public int getTestId() {
		return TestId;
	}
	public void setTestId(int testId) {
		TestId = testId;
	}
	public String getTestFlag() {
		return TestFlag;
	}
	public void setTestFlag(String testFlag) {
		TestFlag = testFlag;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}


	
	
	
}
