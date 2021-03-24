package com.eci.service;

import java.io.IOException;
import java.sql.SQLException;

import com.eci.bean.Customer;
import com.eci.bean.Site;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface CustomerService {

	ObjectNode getAllCustomers();
	
	ObjectNode getCustomerDetail(int id);
	
	ObjectNode insertCustomer(Customer customer) throws ClassNotFoundException, IOException, SQLException;
	
	ObjectNode updateCustomer(Customer customer);
	
	ObjectNode inactivateCustomer(Customer customer);
	
	ObjectNode saveSite(Site customer) throws ClassNotFoundException, IOException, SQLException;


	ObjectNode getSiteDetail(int id);

	ObjectNode activateCustomer(Customer customer);

}
