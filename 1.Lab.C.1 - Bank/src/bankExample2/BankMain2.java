package bankExample2;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bankExample1.*;
public class BankMain2 {

	public static void main(String[] args) {
		
		// Concurrent version of the bank problem. Just to see the improvement in execution time.
		// But also to see several mutual exclusion problems

		int bankSize = 1000;
		int initialAmount = 1000;
		
		// Printing the number of cores of the machine in which the program is being executed
		int nproc = Runtime.getRuntime().availableProcessors();
		System.out.println("Number of cores: "+nproc);
		
		// Creating a bank with bankSize accounts. Each one with initialAmount euros
		Bank b = new Bank (bankSize,initialAmount);

		List<Thread> transferList = new ArrayList<Thread>();
		List<Thread> auditorList = new ArrayList<Thread>();
		
		// we obtain the current time		
		Long time = new Date().getTime();
		
		// performing transfers
		System.out.println("Initial amount of money: "+b.audit(0,bankSize));
		for (int i=0;i<nproc;i++) {
			// we launch nproc threads. Each thread performs bankSize/nproc transfers
			Transfers t = new Transfers (b,bankSize/nproc,bankSize);
			t.start();
			transferList.add(t);
		}
		
		// performing audits concurrently
		for (int i=0;i<10;i++) {
			Auditor a = new Auditor (b);
			a.start();
			auditorList.add(a);
		}
		
		// waiting for all the threads to finish
		try {
			for (int i=0;i<transferList.size();i++) 	
				transferList.get(i).join();
			for (int i=0;i<auditorList.size();i++) 
				auditorList.get(i).join();
		} catch (Exception e) {e.printStackTrace();}
		
		// obtaining the time elapsed in milliseconds
		System.out.println("Time elapsed: "+(new Date().getTime()-time));
		System.out.println("The amount of money at the end: "+b.audit(0,bankSize));
	}
}
