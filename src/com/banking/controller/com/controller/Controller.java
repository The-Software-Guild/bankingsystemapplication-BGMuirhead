package com.controller;

import java.util.Scanner;

import com.service.Service;
import com.service.ServiceMenu;

public class Controller {

	public static void main(String[] args) {
		
		
		Service menu = new ServiceMenu();
		Scanner sc = new Scanner(System.in);
		menu.showAllCustomers(); // populate the list of customers so that there arent clashes in customer ID
		
		String menuText ="1. Create New Customer Data\r\n" +
                "2. Assign a Bank Account to a Customer\r\n" +
                "3. Display balance or interest earned of a Customer\r\n" +
                "4. Sort Customer Data\r\n" +
                "5. Persist Customer Data\r\n" +
                "6. Show All Customers\r\n" +
                "7. Search Customers by Name\r\n" +
                "8. Exit";

        System.out.println(menuText);
        int x=0;
        
        

        while(x!=8)
        {


            try {

                 x = Integer.parseInt(sc.nextLine());

                if (x < 9 && x > 0) {

                    switch(x){
                        case 1:{
                        	
                        	menu.createCustomer();
                            
                            break;
                        }
                        case 2:{
                        	menu.assignBankAccount();
                        	
                            break;
                        }
                        case 3:{
                        	///display interest
                        	menu.displayBalanceAndInterest();
                            break;
                        }
                        
                        case 4:{
                        	//sort data
                        	menu.sortData();
                            int j=4;
                            break;
                        }
                        case 5:{
                        	//persist data
                            menu.persistCustomers();
                            break;
                        }
                        case 6:{
                        	
                        	menu.showAllCustomers();
                        	
                            break;
                        }
                        case 7:{
                        	menu.searchCustomersByName();
                            break;
                        }
                    }



                }

            } catch (NumberFormatException e) // non numeric entry
            {


            }

            System.out.println();
            System.out.println(menuText);

        }



      
        System.out.println("\nApplication Closed");



	}

}
