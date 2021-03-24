package com.eci.dao;

import org.springframework.web.multipart.MultipartFile;

import com.eci.bean.PurchaseOrder;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface PurchaseOrderDao {

	ObjectNode getAllPorders();
	
	ObjectNode getAllOrders();
	
	ObjectNode getPODetail(PurchaseOrder pOrder);
	
	ObjectNode insertPO(String data);
	
	ObjectNode updatePO(String data);

	ObjectNode getCustWiseProductList(int customerId);

	ObjectNode getProdWiseCatagory(PurchaseOrder pOrder);

	boolean readPOFile();
	
}

