package com.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.dto.Customer;

public class FileStorageDao implements daoInterface {

	//File file = new File(".\\customers.txt");
	File file = new File("C:\\C353\\customers.txt");
	
	
	@Override
	public void saveAllCustomers(List<Customer> customers) {
		
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;
		
		try {
			
			if(!file.exists())
			{
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			oos = new ObjectOutputStream(bos);
			

			oos.writeObject(customers);
		
		
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		catch(Exception ef)
		{
			ef.printStackTrace();
			System.out.println(ef.getMessage());
		}
		finally{
			try {
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		

	}

	@Override
	public List<Customer> retrieveAllCustomers() {
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		
		
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				ois = new ObjectInputStream(bis);
				
				
				//brings in the object without needing to reconstruct it
				
				Object obj = ois.readObject(); 
				if(obj ==null)
				{
					System.out.println("No customers to be retrieved");
				}
				
				
				if(obj instanceof ArrayList)
				{
					return (List<Customer>) obj;
				}
			
			
			} catch (FileNotFoundException fnf) {
				// TODO Auto-generated catch block
				fnf.printStackTrace();
			} catch (ClassNotFoundException cnf) {
				// TODO Auto-generated catch block
				cnf.printStackTrace();
			} catch (IOException ioe) {
				// TODO Auto-generated catch block
				ioe.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally{
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		
		
		return null;
	}

}
