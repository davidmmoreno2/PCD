package bankExample1;

public class Account {
	private double balance;
	
	public Account (double _balance) {
		balance = _balance;
	}
	
	public void deposit (double amount) {
		balance +=amount;
	}
	
	public void withdraw (double amount) {
		balance -=amount;
	}
	
	public double getBalance () {
		return balance;
	}
	
	public void setBalance (double _balance) {
		balance = _balance;
	}
	
	public void update (double amount) {
		
		balance += amount;
		
		try {
			Thread.sleep(0); // lo usamos para simular que esta operaci�n tarda un poco. Jugaremos con el par�metro en las clases
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}