package com.comparators;

import java.util.Comparator;


import com.dto.BankAccount;
import com.dto.Customer;

public class CustomerBankBalanceComparator implements Comparator<Customer> {
	
	@Override
	public int compare(Customer o1, Customer o2) {
		
		if(o1.getBa().getBalance() == o2.getBa().getBalance())
		{
			return 0;
		}
		else {
			return o1.getBa().getBalance().compareTo(o2.getBa().getBalance());
		}
	}

}
