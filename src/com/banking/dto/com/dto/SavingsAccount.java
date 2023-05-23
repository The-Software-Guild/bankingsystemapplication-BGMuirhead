package com.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.enums.AccountType;
import com.exception.InsufficientBalanceException;

public class SavingsAccount extends BankAccount {

	private AccountType isSalary;
	public static int minBalance = 100;
	double interestEarned =0.04;
	
	
	public SavingsAccount(long accountNumber, long bsbCode, String bankName, String balance, AccountType isSalary) {
		super(accountNumber,bsbCode,bankName,balance);
		this.isSalary = isSalary;
		
	}
	
	
	
	
	public AccountType getIsSalary() {
		return isSalary;
	}




	public static int getMinBalance() {
		return minBalance;
	}




	public double getInterestEarned() {
		return interestEarned;
	}




	public void deposit(BigDecimal value)
	{
		BigDecimal b1 = this.getBalance().setScale(2,RoundingMode.HALF_UP);
		BigDecimal deposit = value;
		;
		this.setBalance(deposit.add(b1));
		System.out.println("Transaction Successful. New Balanace: " + this.getBalance());
		
	}
	
	public void withdraw(BigDecimal value) throws InsufficientBalanceException
	{
		BigDecimal balance = this.getBalance().setScale(2,RoundingMode.HALF_UP);
		if(this.isSalary==AccountType.NotSalary) // case it is non salary
		{
			if(balance.subtract(value).compareTo(new BigDecimal(minBalance)) <=0 )
			{
				throw new InsufficientBalanceException("Insufficient balance remaining for Savings Account, Minimum balance should be $100");
			}
		}
		else
		{
			if(balance.subtract(value).compareTo(new BigDecimal(0)) <=0)
			{
				throw new InsufficientBalanceException("Insufficient balance remaining for Salary Account, Minimum balance should be $0");
			}
			}
		
		this.setBalance(balance.subtract(value));
		System.out.println("Transaction Successful. New Balanace: " + this.getBalance());
		
	}


	@Override
	public BigDecimal calculateInterest() {
		
		return this.balance.multiply(new BigDecimal("0.04")).setScale(2,RoundingMode.HALF_UP);
		
	}


	
	
}
