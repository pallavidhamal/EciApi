 package com.eci.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eci.bean.Installation;
import com.eci.bean.InstallationAction;
import com.eci.bean.PurchaseOrder;
import com.eci.service.PurchaseOrderService;
import com.eci.util.GetDBConnection;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Eci")
public class PurchaseOrderController {

	@Autowired
	 PurchaseOrderService	PoService;
	
	
	
//	list all purchase orders
	@RequestMapping(value = "/getPOList", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getPOList() {		
		ObjectNode listOfOrders = PoService.getAllOrders();	
		System.out.println("hi i am coming");
		return listOfOrders;
	}
	

//	insert purchase orders
	@RequestMapping(value = "/insertPO", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode insertPO(@RequestBody String data) throws ClassNotFoundException, IOException, SQLException {		
		ObjectNode listOfOrders = PoService.insertPO(data);	
		System.out.println("hi i am coming1");
		return listOfOrders;
	}
	
	
//	get details of purchase order for edit
	@RequestMapping(value = "/getPODetail", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode getPODetail(@RequestBody PurchaseOrder pOrder) {	
		ObjectNode listOfOrders = PoService.getPODetail(pOrder);	
		return listOfOrders;
	}
	
	//edit save porder
	@RequestMapping(value = "/updatePO", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode updatePO(@RequestBody String data) {		
		ObjectNode flag = PoService.updatePO(data);	
		return flag;
	}
	
	
//	list all purchase orders
	@RequestMapping(value = "/getCustWiseProductList/{customerId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ObjectNode getTypeWiseRole(@PathVariable int customerId) {	
		System.out.println("idroletype========="+customerId);
		ObjectNode listOfUsers = PoService.getCustWiseProductList(customerId);	
		return listOfUsers;
	}
	
//	get details of purchase order for edit
	@RequestMapping(value = "/getProdWiseCatagory", method = RequestMethod.POST, headers = "Accept=application/json")
	public ObjectNode getProdWiseCatagory(@RequestBody PurchaseOrder pOrder) {	
		ObjectNode listOfOrders = PoService.getProdWiseCatagory(pOrder);	
		return listOfOrders;
	}
	
	@RequestMapping(value = "/readPOFile", method = RequestMethod.GET, headers = "Accept=application/json")
	public boolean readPOFile() 	
	{
	//public ResponseEntity<Object> SIUpdateTask(@RequestParam("file") MultipartFile file,SI si)throws IOException {		
		System.out.println("readPOFile=========");
		boolean flag = PoService.readPOFile();		
		return flag;
	}
	
	
	
	
	
	
	//
	

	
	
}
