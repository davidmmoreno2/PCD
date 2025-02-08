package bankExample2;

import bankExample1.Bank;

public class Auditor extends Thread {
	Bank b;
	public Auditor (Bank _b) {
		b=_b;
	}
	
	public void run () {
		System.out.println ("Audit: "+b.audit(0,(b.bankSize()-1)));
	}

}
