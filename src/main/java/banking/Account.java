package banking;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
	public static final double DEFAULT_BALANCE = 0.0;
	double balance;
	private double apr;
	private String uniqueId;
	private Integer time;
	private List<String> transactionCommands;

	protected Account(String uniqueId, double balance, double apr) {
		initialize(uniqueId, balance, apr);
	}

	protected Account(String uniqueId, double apr) {

		initialize(uniqueId, DEFAULT_BALANCE, apr);
	}

	private void initialize(String uniqueId, double balance, double apr) {
		this.balance = balance;
		this.apr = apr;
		this.uniqueId = uniqueId;
		this.time = 0;
		this.transactionCommands = new ArrayList<>();
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

	public void transferBalance(Account toAccount, double amount) {
		if (this instanceof CertificateOfDeposit) {
			throw new UnsupportedOperationException("Transfers from CertificateOfDeposit accounts are not allowed.");
		}

		if (amount > 0 && this.getBalance() >= amount) {
			this.withdrawBalance(amount);
			toAccount.depositBalance(amount);
		}
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public abstract double getMaximumDepositAmount();

	public Integer getTime() {
		return time;
	}

	public void addTransactionCommand(String command) {
		transactionCommands.add(command);
	}

	public List<String> getTransactionCommands() {
		return new ArrayList<>(transactionCommands);
	}

	public void incrementTime() {
		time++;
	}
}
