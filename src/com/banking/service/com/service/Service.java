package com.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

import com.dto.Customer;

public interface Service{
	
	void createCustomer(Scanner sc);
	void assignBankAccount(Scanner sc);
	void displayBalanceAndInterest(Scanner sc);
	void sortData(Scanner sc);
	void showAllCustomers(Scanner sc);
	void persistCustomers(Scanner sc);
	void searchCustomersByName(Scanner sc);
	void printAllCustomers();

	

	
}
