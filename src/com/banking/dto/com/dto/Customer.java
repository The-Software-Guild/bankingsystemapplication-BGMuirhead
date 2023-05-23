package com.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class Customer implements Comparable<Customer>, Serializable{
	private int id;
	private String name;
	private int age;
	private int mobileNumber;
	private String passportNumber;
	private BankAccount ba;
	private LocalDate dob;
	
	
	
	public Customer(int id, String name, int age, int mobileNumber, String passportNumber) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.mobileNumber = mobileNumber;
		this.passportNumber = passportNumber;
	}

	
	


	public int getId() {
		return id;
	}





	public String getName() {
		return name;
	}





	public int getAge() {
		return age;
	}





	public int getMobileNumber() {
		return mobileNumber;
	}





	public String getPassportNumber() {
		return passportNumber;
	}





	public LocalDate getDob() {
		return dob;
	}





	public void setDob(LocalDate dob) {
		this.dob = dob;
	}





	public BankAccount getBa() {
		return ba;
	}





	public void setBa(BankAccount ba) {
		this.ba = ba;
	}





	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", age=" + age + ", mobileNumber=" + mobileNumber
				+ ", passportNumber=" + passportNumber + ", ba=" + ba + ", dob=" + dob + "]";
	}





	@Override
	public int hashCode() {
		
		return this.id;
	}





	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof Customer)
		{
			Customer c1 = (Customer) obj;
			return this.id == c1.id ? true : false;
		}
		return false;
	}





	@Override
	public int compareTo(Customer c) {

		if(this.id < c.id)
		{
			return -1;
		}
		else if (this.id == c.id)
		{
			return 0;
		}
		
		return 1;
	} 
	
	
	

}
