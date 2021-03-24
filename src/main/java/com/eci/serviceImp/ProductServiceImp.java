package com.eci.serviceImp;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eci.bean.Customer;
import com.eci.bean.Product;
import com.eci.dao.ProductDao;
import com.eci.service.ProductService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class ProductServiceImp implements ProductService {
	
	@Autowired
	ProductDao prodDao;
	
	public ObjectNode getAllProducts() {
		ObjectNode orderNode = prodDao.getProducts();
		return orderNode;
	}
	
	public ObjectNode getProductDetail(int id) {
		
		ObjectNode orderNode = prodDao.getProductDetail(id);
		return orderNode;
	}
	
	
	public ObjectNode updateProduct(Product product) {
		ObjectNode flag = prodDao.updateProduct(product);
		return flag;
	}


	@Override
	public ObjectNode insertProduct(Product product) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		ObjectNode flag=prodDao.insertProduct(product);
		return flag;
	}
	
	@Override
	public ObjectNode inactivateProduct(Product product) {
		// TODO Auto-generated method stub
		ObjectNode flag=prodDao.inactivateProduct(product);
		return flag;
	}

	@Override
	public ObjectNode activateProduct(Product product) {
		// TODO Auto-generated method stub
		ObjectNode flag=prodDao.activateProduct(product);
		return flag;
	}
	
}
