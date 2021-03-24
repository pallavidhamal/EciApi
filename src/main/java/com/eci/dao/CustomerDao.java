package com.eci.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.eci.bean.Customer;
import com.eci.bean.Site;
import com.fasterxml.jackson.databind.node.ObjectNode;


public interface CustomerDao {

	ObjectNode getCustomers();
	
	ObjectNode getCustomerDetail(int id);
	
	ObjectNode insertCustomer(Customer customer) throws ClassNotFoundException, IOException, SQLException;
	
	ObjectNode updateCustomer(Customer customer);
	
	ObjectNode inactivateCustomer(Customer customer);
	
	ObjectNode saveSite(Site customer) throws ClassNotFoundException, IOException, SQLException;

	ObjectNode getSiteDetail(int id);

	ObjectNode activateCustomer(Customer customer);

}
