package bankExample2;

import java.util.*;

import bankExample1.Bank;

public class Transfers extends Thread {
	Bank b;
	int times;
	int bankSize;
	
	public Transfers (Bank _b, int _times, int _bankSize) {
		b = _b;
		times = _times;
		bankSize = _bankSize;
	}
	
	public void run () {
		b.randomTransfers(times);
	}
}
