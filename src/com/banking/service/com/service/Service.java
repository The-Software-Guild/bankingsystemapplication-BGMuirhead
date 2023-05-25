package com.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

import com.dto.Customer;

public interface Service{
	
	void createCustomer();
	void assignBankAccount();
	void displayBalanceAndInterest();
	void sortData();
	void showAllCustomers();
	void persistCustomers();
	void searchCustomersByName();
	void printAllCustomers();

	

	
}
