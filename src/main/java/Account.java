public class Account {
	public static final double DEFAULT_BALANCE = 0.0;
	double balance;
	private double apr;
	private int uniqueId;

	public Account(int uniqueId, double apr) {
		this.balance = DEFAULT_BALANCE;
		this.apr = apr;
		this.uniqueId = uniqueId;
	}

	public Account(int uniqueId, double balance, double apr) {
		this.balance = balance;
		this.apr = apr;
		this.uniqueId = uniqueId;
	}

	public double getBalance() {
		return balance;
	}

	public double getAPR() {
		return apr;
	}

	public void depositBalance(double amountToDeposit) {
		balance += amountToDeposit;
	}

	public void withdrawBalance(double amountToWithdraw) {
		if (amountToWithdraw > 0 && balance < amountToWithdraw) {
			balance = 0;
		} else {
			balance -= amountToWithdraw;
		}
	}

	public int getUniqueId() {
		return uniqueId;
	}
}
