package com.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.dto.FixedDepositAccount;
import com.dto.SavingsAccount;
import com.enums.AccountType;
import com.comparators.CustomerBankBalanceComparator;
import com.comparators.CustomerIdComparator;
import com.comparators.CustomerNameComparator;
import com.dao.DatabaseStorageDao;
import com.dao.FileStorageDao;
import com.dao.daoInterface;
import com.dto.BankAccount;
import com.dto.Customer;
import com.exception.CustomerNotFoundException;
import com.exception.InsufficientBalanceException;

public class ServiceMenu implements Service {

	//Map<Customer, BankAccount> customers = new TreeMap<>();
	List<Customer> customers = new ArrayList<>();
	int customerID = 99;
	Scanner sc = new Scanner(System.in);

	@Override
	public void createCustomer() {
		String name = null, passportNumber = null;
		int age = 0, mobileNumber = 0;
		int newCustomerID = customerID + 1;
		String dob = "";
		customerID += 1;
		boolean nameFlag, ageFlag, mobileFlag, passportFlag, dobFlag;
		nameFlag = ageFlag = mobileFlag = passportFlag = dobFlag = false;
		boolean validFlag = false;
		do {
			try {
				if (!nameFlag) {
					System.out.println("Please enter your name:");
					name = sc.nextLine();
					nameFlag = true;
				}
				if (!ageFlag) {
					System.out.println("Please enter your age:");
					age = Integer.parseInt(sc.nextLine());
					ageFlag = true;

				}
				if (!mobileFlag) {
					System.out.println("Please enter your mobile number:");
					mobileNumber = Integer.parseInt(sc.nextLine());
					mobileFlag = true;
				}
				if (!passportFlag) {
					System.out.println("Please enter your passport number:");
					passportNumber = sc.nextLine();
					passportFlag = true;
				}

				if (!dobFlag) {
					System.out.println("Please enter your date of birth (DD/MM/YYYY):");
					dob = sc.nextLine();
					if (dob.length() != 10) {
						System.out.println("Incorrect Format. Try Again");
						continue;
					}
					String[] split = dob.split("/");
					if (split.length != 3) {
						System.out.println("Incorrect Format. Try Again");
						continue;
					}

					int day = Integer.parseInt(split[0]);
					int month = Integer.parseInt(split[1]);
					int year = Integer.parseInt(split[2]);
					LocalDate dob2 = LocalDate.of(year, month, day);
					dobFlag = true;

					Customer c = new Customer(newCustomerID, name, age, mobileNumber, passportNumber);
					
					c.setDob(dob2);

					customers.add(c);

				}

				validFlag = true;

			} catch(DateTimeException dte)
			{
				System.out.println("Error formating DOB, please try again");
			}
			catch (NumberFormatException nfe) {
				System.out.println("A number format error has occured, please try again");

			}
		} while (!validFlag);

	}

	public static void savingsChoice(Scanner sc, BankAccount ba) {
		boolean validFlag = false;
		boolean choiceFlag = false;
		int input = 0;
		do {
			try {

				if (!choiceFlag) {
					System.out.println(
							"Please select if you would like to deposit(0), withdraw(1), check your balance(2) or enter any key to exit");
					input = Integer.parseInt(sc.nextLine());
					choiceFlag = true;
				}

				switch (input) {
				case 0: {
					System.out.println("Please Enter Amount: ");
					BigDecimal value = new BigDecimal(sc.nextLine()).setScale(2, RoundingMode.HALF_UP);
					SavingsAccount s = (SavingsAccount) ba;
					s.deposit(value);
					return;

				}
				case 1: {
					System.out.println("Please Enter Amount: ");
					BigDecimal value = new BigDecimal(sc.nextLine()).setScale(2, RoundingMode.HALF_UP);
					SavingsAccount s = (SavingsAccount) ba;
					s.withdraw(value);
					return;

				}
				case 2: {
					System.out.println("Your balance is $" + ba.getBalance());
					return;
				}

				}

				validFlag = true;

			}

			catch (NumberFormatException nfe) {

				return;

			} catch (InsufficientBalanceException e) {
				System.out.println(e.getMessage());
			}
		} while (!validFlag);

	}

	@Override
	public void assignBankAccount() {
		int type = 0, custID, tenure = 0;
		int depositAmount = 0;
		boolean idFlag = false, typeFlag = false, bankNameFlag = false, accNumFlag = false, bsbFlag = false,
				salaryFlag = false, balanceFlag = false, isSalary = false, depositFlag = false, tenureFlag = false;
		boolean validFlag = false;
		AccountType salary = AccountType.NotSalary;
		long accNum = 0, bsbCode = 0;
		String bankName = "";
		int balance = 0;
		Customer c = null;

		do {
			try {
				if (!idFlag) {
					System.out.println("Please enter the customer ID:");

					custID = Integer.parseInt((sc.nextLine()));

					// if it is not found
					if (custID < 100 || custID > customerID)

					{
						CustomerNotFoundException cnf = new CustomerNotFoundException(
								"Customer Does Not Exist, Try Again");
						throw cnf;

					}
					

					for (Customer cu : customers) {
						if (cu.getId() == custID) {
							c = cu;
							break;
						}
					}
					
					if(c.getBa() !=null)
					{
						System.out.println("Customer already has an account, please select another customer");
						return;
					}

					idFlag = true;

				}
				if (!typeFlag) {
					System.out.println("What type of account would you like. Savings (0) or Deposit (1)?");

					type = Integer.parseInt((sc.nextLine()));

					if (type < 0 || type > 1) {
						continue;
					}
					typeFlag = true;

				}
				if (!bankNameFlag) {
					System.out.println("Please enter the bank name:");
					bankName = sc.nextLine();
					bankNameFlag = true;
				}

				if (!accNumFlag) {
					System.out.println("Please enter the account number:");
					accNum = Long.parseLong(sc.nextLine());
					accNumFlag = true;
				}
				if (!bsbFlag) {
					System.out.println("Please enter the BSB code:");
					bsbCode = Long.parseLong(sc.nextLine());
					bsbFlag = true;
				}

				if (type == 0) // Savings account
				{
					if (!salaryFlag)

					{
						System.out.println("Is this a salary account (Y|N)");

						String line = sc.nextLine();

						if (line.toLowerCase().equals("y")) {
							isSalary = true;
							salaryFlag = true;
							salary = AccountType.Salary;
						} else if (line.toLowerCase().equals("n")) {
							isSalary = false;
							salaryFlag = true;
						} else {
							continue;
						}

					}
					if (!balanceFlag) {
						System.out.println("Please enter the balance:");
						balance = Integer.parseInt(sc.nextLine());
						if (balance < SavingsAccount.minBalance && isSalary == false) {

							throw new InsufficientBalanceException(
									"Insufficient balance for Savings Account, Minimum balance should be $100");

						}

						if (balance < 0 && isSalary == true) {
							throw new InsufficientBalanceException(
									"Insufficient balance for Salary Account, Minimum balance should be $0");
						}

					}

					BankAccount bAccount = new SavingsAccount(accNum, bsbCode, bankName, String.valueOf(balance),
							salary);
					c.setBa(bAccount);

					// give option to deposit withdraw or check balance
					savingsChoice(sc, bAccount);

				} else if (type == 1) {
					if (!depositFlag) {
						System.out.println("What is the deposit amount?");
						depositAmount = Integer.parseInt(sc.nextLine());
						balance = depositAmount;

						if (depositAmount < FixedDepositAccount.minDeposit) {
							System.out.println(
									"Minimum deposit is " + FixedDepositAccount.minDeposit + "\nPlease Try Again");
							continue;
						}
					}
					if (!tenureFlag) {
						System.out.println("What is the tenure?");
						tenure = Integer.parseInt(sc.nextLine());

						if (tenure < FixedDepositAccount.getMinTenure()
								|| tenure > FixedDepositAccount.getMaxTenure()) {
							System.out.println("Tenure must be within " + FixedDepositAccount.getMinTenure()
									+ " years and " + FixedDepositAccount.getMaxTenure() + "years \nPlease Try Again");
							continue;
						}
					}
					BankAccount bAccount = new FixedDepositAccount(accNum, bsbCode, bankName, String.valueOf(balance),
							String.valueOf(balance), tenure);
					c.setBa(bAccount);

				}

				validFlag = true;

			} catch (IndexOutOfBoundsException iob) {
				System.out.println("Customer ID out of bounds, try again");
			} catch (CustomerNotFoundException cnf) {
				System.out.println(cnf.getMessage());
			} catch (NumberFormatException e) {
				System.out.println("Incorrect Format. Try Again");
			} catch (InsufficientBalanceException ibe) {
				System.out.println(ibe.getMessage());
				return;// no account created
			}
		} while (!validFlag);

	}

	@Override
	public void displayBalanceAndInterest() {
		int id = -1;
		while (true) {
			try {
				System.out.println("Please enter a customer ID:");
				id = Integer.parseInt(sc.nextLine());

				Customer target = null;

				for (Customer c : customers) {
					if (c.getId() == id) {
						target = c;
						break;
					}
				}

				if (target == null) {
					throw new CustomerNotFoundException("There is no customer matching that ID");
				}

				BankAccount b = customers.get(customers.indexOf(target)).getBa();
				if (b == null) {
					System.out.println("Customer does not have a bank account");
					return;
				}

				System.out.println("Balance : " + b.getBalance());
				System.out.println("Interest : " + b.calculateInterest());

				break;
			} catch (CustomerNotFoundException cnf) {
				System.out.println(cnf.getMessage());
				return;
			} catch (Exception e) {
				System.out.println("Invalid input, please try again ");
			}

		}

	}

	@Override
	public void sortData() {
		int selection = -1;
		while (true) {
			try {
				System.out.println("Please enter 0 to sort by name, 1 to sort by ID or 2 to sort by balance:");
				selection = Integer.parseInt(sc.nextLine());
				if (selection == 1) {
					Comparator<Customer> cId = new CustomerIdComparator();
					customers.sort(cId);
					printAllCustomers();
					break;

				}
				if (selection == 0) {
					Comparator<Customer> cName = new CustomerNameComparator();
					customers.sort(cName);
					printAllCustomers();
					break;
				}

				if (selection == 2) {
					Comparator<Customer> cBlance = new CustomerBankBalanceComparator();
					customers.sort(cBlance);
					printAllCustomers();
					break;
				}

			} catch (Exception e) {
				System.out.println("Invalid input, please try again ");
			}

		}

	}

	@Override
	public void printAllCustomers() {
		System.out.println("Current Customers: ");

		

		for (Customer c : customers) {
			System.out.println(c);
		}
	}
	@Override
	public void showAllCustomers()
	{
		
		
		
		boolean validFlag = false;
		boolean choiceFlag = false;
		int input = 0;
		do {
			try {

//				if (!choiceFlag) {
//					System.out.println(
//							"Please select if you would like to retrieve from flat file(0), retrieve from database(1) or enter any key to exit");
//					input = Integer.parseInt(sc.nextLine());
//					choiceFlag = true;
//				}
//
//				switch (input) {
//					case 0: {
//						if(customers.size()!=0)
//						{
//							System.out.println("Can only retrieve before adding customer");
//							return;
//						}
//						daoInterface dao = new FileStorageDao();
//						List<Customer> cs = dao.retrieveAllCustomers();
//						
//						if(cs==null)
//						{
//							return;
//						}
//						
//						int id = cs.get(cs.size()-1).getId();
//						customerID = id; 
//						customers = cs;
//						
//						
//						System.out.println("Customers Imported Successfully");
//						printAllCustomers();
//						return;
//	
//					}
//					case 1: {
						
//						if(customers.size()!=0)
//						{
//							System.out.println("Can only retrieve before adding customer");
//							return;
//						}
						daoInterface dao = new DatabaseStorageDao();
						dao.saveAllCustomers(customers); // save so new customers do not get deleted
						List<Customer> cs = dao.retrieveAllCustomers();
						
						if(cs==null)
						{
							return;
						}
						
						int id = cs.get(cs.size()-1).getId();
						customerID = id; 
						customers = cs;
						
						
						System.out.println("Customers Imported Successfully");
						printAllCustomers();
						System.out.println();
						return;
	
//					}

//				}
//
//				validFlag = true;

			}

			catch (NumberFormatException nfe) {

				return;

			}
		} while (!validFlag);
		
		
		
		
		
	}

	@Override
	public void persistCustomers() {

		boolean validFlag = false;
		boolean choiceFlag = false;
		int input = 0;
		do {
			try {

				if (!choiceFlag) {
					System.out.println(
							"Please select if you would like to save to flat file(0), save to database(1) or enter any key to exit");
					input = Integer.parseInt(sc.nextLine());
					choiceFlag = true;
				}

				switch (input) {
					case 0: {
						daoInterface dao = new FileStorageDao();
						
						dao.saveAllCustomers(customers);
	
						return;
	
					}
					case 1: {
						
						daoInterface dao = new DatabaseStorageDao();
						dao.saveAllCustomers(customers);
						
	
						return;
	
					}

				}

				validFlag = true;

			}

			catch (NumberFormatException nfe) {

				return;

			}
		} while (!validFlag);

		

	}

	@Override
	public void searchCustomersByName() {
		
		System.out.println("Please enter the name to search for:");
		String name = sc.nextLine();
		
		DatabaseStorageDao dao = new DatabaseStorageDao();
		
		Customer c = null;
		try {
			c = dao.getCustomer(name);
			System.out.println("Customer found:");
			System.out.println(c);
		} catch (CustomerNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	
		
	
	}

	
	

}
