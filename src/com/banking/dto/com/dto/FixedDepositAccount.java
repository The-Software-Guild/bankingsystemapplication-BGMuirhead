package com.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FixedDepositAccount extends BankAccount {

	private BigDecimal depositAmount; // min is 1000
	private int tenure; // min: 1yr, max: 7yr
	double interestEarned =0.08;
	public static int minDeposit=1000;
	private static double minTenure =1;
	private static int maxTenure =7;
	
	
	public FixedDepositAccount(long accountNumber, long bsbCode, String bankName, String balance, String depositAmount, int tenure) {
		super(accountNumber,bsbCode,bankName,balance);
		this.depositAmount = new BigDecimal(depositAmount).setScale(2,RoundingMode.HALF_UP); 
		this.tenure = tenure;
	}


	@Override
	public BigDecimal calculateInterest() {
		
		return depositAmount.multiply(new BigDecimal("0.08")).multiply(new BigDecimal(tenure)).setScale(2,RoundingMode.HALF_UP);
		
		
	}




	public static double getMinTenure() {
		return minTenure;
	}


	public static int getMaxTenure() {
		return maxTenure;
	}


	public BigDecimal getDepositAmount() {
		return depositAmount;
	}


	public int getTenure() {
		return tenure;
	}


	public double getInterestEarned() {
		return interestEarned;
	}


	public static int getMinDeposit() {
		return minDeposit;
	}
	
}
