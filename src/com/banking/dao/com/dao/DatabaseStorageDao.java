package com.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.dto.BankAccount;
import com.dto.Customer;
import com.dto.FixedDepositAccount;
import com.dto.SavingsAccount;
import com.enums.AccountType;
import com.exception.CustomerNotFoundException;


public class DatabaseStorageDao implements daoInterface {

	
	private Connection openConnection() {
		//Class.forName("com.mysql.cj.jdbc.Driver"); //Type 4 driver // Does not work for MySQL 5
		
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");// Type 4 driver is registered with DriverManager // works for MySQL version 5
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/banking", "root","root");
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("MySQL suitable driver not found");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return con;
	}
	
	private void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public void saveAllCustomers(List<Customer> customers) {
		
		Connection con = openConnection();
		int n =0;
		// need to retrieve the bank account details
		// then insert into bank account table
		
		
		//then need to insert the customer into the customer table
		
		//then we need to insert into the has table to track the relationship
		
		for(Customer c: customers)
		{
			//get the bank details
			BankAccount b = c.getBa();
			long accountNumber = b.getAccountNumber();
			long bsbCode = b.getBsbCode();
			String bankName =b.getBankName();
			BigDecimal balance = b.getBalance();
			String openDate = b.getOpenDate().toString();
			
			double interestEarned = -1;
			AccountType isSalary = null;
			BigDecimal depositAmount = null;
			int tenure = -1; 
			
			if( b instanceof FixedDepositAccount)
			{
				FixedDepositAccount fda = (FixedDepositAccount) b;
				interestEarned = fda.getInterestEarned();
				isSalary = AccountType.NotSalary;
				depositAmount = fda.getDepositAmount();
				tenure = fda.getTenure();
			}
			else {
				SavingsAccount sa = (SavingsAccount)b;
				interestEarned = sa.getInterestEarned();
				isSalary = AccountType.Salary;
				
			}
			
			String sql1 = "insert into BankAccount(AccountNumber,BsBCode,BankName,Balance,OpenDate,InterestEarned,IsSalary,DepositAmount,Tenure) values(?,?,?,?,?,?,?,?,?);";
			PreparedStatement pstat1;
			
			
			if(tenure ==-1) // case of savings account
			{
				String s = String.valueOf(isSalary);
				
				try {
					pstat1 = con.prepareStatement(sql1);
					pstat1.setString(1, String.valueOf(accountNumber));
					pstat1.setString(2, String.valueOf(bsbCode));
					pstat1.setString(3, bankName);
					pstat1.setDouble(4, balance.doubleValue());
					pstat1.setString(5, openDate);
					pstat1.setDouble(6, interestEarned);
					pstat1.setString(7, String.valueOf(isSalary));
					pstat1.setNull(8, Types.NULL);
					pstat1.setNull(9, Types.NULL);
					int a = pstat1.executeUpdate();
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else
			{
				try {
					pstat1 = con.prepareStatement(sql1);
					pstat1.setString(1, String.valueOf(accountNumber));
					pstat1.setString(2, String.valueOf(bsbCode));
					pstat1.setString(3, bankName);
					pstat1.setDouble(4, balance.doubleValue());
					pstat1.setString(5, openDate);
					pstat1.setDouble(6, interestEarned);
					pstat1.setNull(7, Types.NULL);
					pstat1.setDouble(8, depositAmount.doubleValue());
					pstat1.setInt(9, tenure);
					int a = pstat1.executeUpdate();
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//now insert the customer into the table
			 
			String sql2 = "insert into customer(ID,Name,Age,MobileNumber,PassportNumber,DOB) values(?,?,?,?,?,?);";
			PreparedStatement pstat2;
			try {
				pstat2 = con.prepareStatement(sql2);
				pstat2.setInt(1, c.getId());
				pstat2.setString(2, c.getName());
				pstat2.setInt(3, c.getAge());
				pstat2.setString(4, String.valueOf(c.getMobileNumber()));
				pstat2.setString(5, c.getPassportNumber());
				pstat2.setString(6, c.getDob().toString());
			
				int a = pstat2.executeUpdate();
				
				
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//insert into has table
			
			String sql3 = "insert into has(CustomerID,BankAccountNumber) values(?,?);";
			PreparedStatement pstat3;
			try {
				pstat3 = con.prepareStatement(sql3);
				pstat3.setInt(1, c.getId());
				pstat3.setString(2, String.valueOf(accountNumber));
				
			
				int a = pstat3.executeUpdate();
				
				
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		
		}
		
		System.out.println("All Customers Saved");
		closeConnection(con);

	}

	@Override
	public List<Customer> retrieveAllCustomers() {
		
		List<Customer> list = new ArrayList<>();
		
		Connection con = openConnection();
		
		String sql = "select * from customer;";
		try {
			Statement statement =con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next())
			{
				int id = rs.getInt("Id");
				String name = rs.getString("Name");
				int age = rs.getInt("Age");
				int mobileNumber = Integer.parseInt(rs.getString("Mobile"));
				String passportNumber =  rs.getString("PassportNumber");
				LocalDate dob = LocalDate.parse(rs.getString("DOB")) ;
				
				Customer c = new Customer(id, name, age, mobileNumber, passportNumber);
				c.setDob(dob);
				list.add(c);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		closeConnection(con);
		
		return list;
		
		
	}
	
	public Customer getCustomer(String searchName) throws CustomerNotFoundException {
		
		
		
		Customer customer = null;
		Connection con = openConnection();
	
		String sql = "select * from customer where name = ?";
		PreparedStatement pstat;
		
		try {
			pstat = con.prepareStatement(sql);
			pstat.setString(1, searchName);
			ResultSet rs = pstat.executeQuery();
			
			
			
			while(rs.next())
			{
				int id = rs.getInt("Id");
				String name = rs.getString("Name");
				int age = rs.getInt("Age");
				int mobileNumber = Integer.parseInt(rs.getString("Mobile"));
				String passportNumber =  rs.getString("PassportNumber");
				LocalDate dob = LocalDate.parse(rs.getString("DOB")) ;
				
				customer = new Customer(id, name, age, mobileNumber, passportNumber);
				customer.setDob(dob);
				
			}
			
			if(customer == null)
			{
				throw new CustomerNotFoundException("No customer found that matches input name");
			}
			
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		closeConnection(con);
		
		return customer;
		
	}

}
