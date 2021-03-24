package com.eci.serviceImp;
  
import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eci.bean.Customer;
import com.eci.bean.Site;
import com.eci.dao.CustomerDao;
import com.eci.service.CustomerService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class CustomerServiceImp implements CustomerService {
	
	
	@Autowired
	CustomerDao custDao;
	
	public ObjectNode getAllCustomers() {
		ObjectNode orderNode = custDao.getCustomers();
		return orderNode;
	}
	
	public ObjectNode getCustomerDetail(int id) {
		
		ObjectNode orderNode = custDao.getCustomerDetail(id);
		return orderNode;
	}
	
	public ObjectNode updateCustomer(Customer customer) {
		ObjectNode flag = custDao.updateCustomer(customer);
		return flag;
	}
	@Override
	public ObjectNode insertCustomer(Customer customer) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		ObjectNode flag=custDao.insertCustomer(customer);
		return flag;
	}

	@Override
	public ObjectNode inactivateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		ObjectNode flag=custDao.inactivateCustomer(customer);
		return flag;
	}

	@Override
	public ObjectNode saveSite(Site customer) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		ObjectNode flag=custDao.saveSite(customer);
		return flag;	}

	@Override
	public ObjectNode getSiteDetail(int id) {
		ObjectNode orderNode = custDao.getSiteDetail(id);
		return orderNode;
	}

	@Override
	public ObjectNode activateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		ObjectNode flag=custDao.activateCustomer(customer);
		return flag;
	}
	
}
