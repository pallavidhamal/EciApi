package com.eci.service;

import com.eci.bean.PurchaseOrder;
import com.fasterxml.jackson.databind.node.ObjectNode;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
public interface PurchaseOrderService {

	ObjectNode getAllOrders();
	
	ObjectNode getPODetail(PurchaseOrder pOrder);
	
	ObjectNode insertPO(String data) throws ClassNotFoundException, IOException, SQLException;
	
	ObjectNode updatePO(String data);

	ObjectNode getCustWiseProductList(int customerId);

	ObjectNode getProdWiseCatagory(PurchaseOrder pOrder);

	boolean readPOFile();
	
	
	
}
