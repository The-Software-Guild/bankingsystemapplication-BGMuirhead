package com.comparators;

import java.util.Comparator;

import com.dto.Customer;

public class CustomerIdComparator implements Comparator<Customer> {

	@Override
	public int compare(Customer o1, Customer o2) {
		
		if(o1.getId() == o2.getId())
		{
			return 0;
		}
		else {
			return o1.getId() < o2.getId() ? -1 : 1;
		}
	}
	
	
	

}
