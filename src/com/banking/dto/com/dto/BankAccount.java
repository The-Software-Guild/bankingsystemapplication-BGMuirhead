package com.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public abstract class BankAccount implements Serializable {
	
	private long accountNumber;
	private long bsbCode;
	private String bankName;
	protected BigDecimal balance;
	private LocalDate openDate;
	
	public BankAccount(long accountNumber, long bsbCode, String bankName, String balance) {
		super();
		this.accountNumber = accountNumber;
		this.bsbCode = bsbCode;
		this.bankName = bankName;
		this.balance = new BigDecimal(balance).setScale(2,RoundingMode.HALF_UP);
		this.openDate = LocalDate.now();
	}
	
	public abstract BigDecimal calculateInterest();

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public long getBsbCode() {
		return bsbCode;
	}

	public void setBsbCode(long bsbCode) {
		this.bsbCode = bsbCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public LocalDate getOpenDate() {
		return openDate;
	}

	public void setOpenDate(LocalDate openDate) {
		this.openDate = openDate;
	}

	@Override
	public String toString() {
		return "BankAccount [balance=" + balance + "]";
	}
	
	
	
	

}
