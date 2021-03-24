package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.eci.bean.PurchaseOrder;
import com.eci.dao.PurchaseOrderDao;
import com.eci.service.PurchaseOrderService;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Repository
public class PurchaseOrderServiceImp implements PurchaseOrderService {

	@Autowired
	PurchaseOrderDao pOrderDao;
	
	public ObjectNode getAllOrders() {
		ObjectNode orderNode = pOrderDao.getAllPorders();
		return orderNode;
	}
	
	
	/*public ObjectNode insertPO(PurchaseOrder pOrder) {
		ObjectNode orderNode = pOrderDao.insertPO(pOrder);
		return orderNode;
	}*/
	
	public ObjectNode getPODetail(PurchaseOrder pOrder) {
		
		ObjectNode orderNode = pOrderDao.getPODetail(pOrder);
		return orderNode;
	}
	
	
	public ObjectNode updatePO(String data) {
		ObjectNode flag = pOrderDao.updatePO(data);
		return flag;
	}


	@Override
	public ObjectNode insertPO(String data) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		ObjectNode flag = pOrderDao.insertPO(data);

		return flag;
	}


	@Override
	public ObjectNode getCustWiseProductList(int customerId) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = pOrderDao.getCustWiseProductList(customerId);
		return orderNode;
	}


	@Override
	public ObjectNode getProdWiseCatagory(PurchaseOrder pOrder) {
		// TODO Auto-generated method stub
		ObjectNode orderNode = pOrderDao.getProdWiseCatagory(pOrder);
		return orderNode;
	}


	@Override
	public boolean readPOFile() {
		// TODO Auto-generated method stub
		System.out.println("readPOFile 11=========");
		boolean flag= pOrderDao.readPOFile();
		
		return flag;
	}


	
}
