package banking;

public abstract class Account {
	public static final double DEFAULT_BALANCE = 0.0;
	double balance;
	private double apr;
	private String uniqueId;

	private Integer time;

	public Account(String uniqueId, double apr) {
		this.balance = DEFAULT_BALANCE;
		this.apr = apr;
		this.uniqueId = uniqueId;
		this.time = 0;
	}

	public Account(String uniqueId, double balance, double apr) {
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

	public String getUniqueId() {
		return uniqueId;
	}

	public abstract double getMaximumDepositAmount();

	public Integer getTime() {
		return time;
	}

}
