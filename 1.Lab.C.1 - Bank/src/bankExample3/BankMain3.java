package bankExample3;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bankExample1.Account;
import bankExample1.Bank;

public class BankMain3 {
	public static void main(String[] args) {
		
		int bankSize = 1000;
		int initialAmount = 1000;
		int amountToGift = 1000;
		
		// Printing the number of cores of the machine in which the program is being executed
		int nproc = Runtime.getRuntime().availableProcessors();
		System.out.println("Number of cores: "+nproc);
		
		// Creating a bank with bankSize accounts. Each one with initialAmount euros
		Bank b = new Bank (bankSize,initialAmount);

		// SEQUENTIAL VERSION
		
//		Long time1 = new Date().getTime();
//		for (Account a:b.getListAccounts()) {
//			a.update (amountToGift);
//		}
//
//		System.out.println("SEQUENTIAL VERSION - Time elapsed: "+(new Date().getTime()-time1));
		
		// CONCURRENT VERSION 1
		// we do it concurrently with bankSize threads
		List<Thread> threadList = new ArrayList<Thread>();
		Long time2 = new Date().getTime();
		for (Account a:b.getListAccounts()) {
			GiftThread gt = new GiftThread (a,amountToGift);
			threadList.add (gt);
			gt.start();
		}
		
		// waiting for all the threads to finish
		try {
			for (int i=0;i<bankSize;i++) 	
				threadList.get(i).join();
		} catch (Exception e) {e.printStackTrace();}
		
		// obtaining the time elapsed in milliseconds
		System.out.println("CONCURRENT VERSION 1 - Time elapsed: "+(new Date().getTime()-time2));
		
		// CONCURRENT VERSION 2
		// we do it concurrently creating as many threads as cores you have, so we are
		// creating less threads than before
		
		Long time3 = new Date().getTime();
		int iteraciones = 0;
		List<Thread> threadList2 = new ArrayList<Thread>();
		for (int i=0;i<nproc;i++ ) {
			int vueltas = iteraciones;
			Thread t = new Thread (()->{for (int j=0;(j<(bankSize/nproc));j++) {b.getAccount(vueltas*(bankSize/nproc)+j).update (amountToGift);}}); //
			t.start();
			iteraciones++;
			threadList2.add(t);
		}

		// waiting for all the threads to finish
		try {
			for (int i=0;i<nproc;i++) 	
				threadList2.get(i).join();
		} catch (Exception e) {e.printStackTrace();}
		
		// obtaining the time elapsed in milliseconds
		System.out.println("CONCURRENT VERSION 2 - Time elapsed: "+(new Date().getTime()-time3));
	}
}

