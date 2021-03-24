package com.eci.service;

import java.io.IOException;
import java.sql.SQLException;

import com.eci.bean.Product;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ProductService {
	
	ObjectNode getAllProducts();
	
	ObjectNode getProductDetail(int id);
	
	ObjectNode insertProduct(Product product) throws ClassNotFoundException, IOException, SQLException;
	
	ObjectNode updateProduct(Product product);
	
	ObjectNode inactivateProduct(Product product);

	ObjectNode activateProduct(Product product);
	
	
	
}
