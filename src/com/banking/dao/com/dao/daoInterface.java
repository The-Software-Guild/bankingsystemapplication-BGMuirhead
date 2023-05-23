package com.dao;

import java.util.List;

import com.dto.Customer;

public interface daoInterface {
	
	void saveAllCustomers(List<Customer> customers);
	
	List<Customer> retrieveAllCustomers();

}
