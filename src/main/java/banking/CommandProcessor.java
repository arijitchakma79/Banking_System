package banking;

public class CommandProcessor {
	public Bank bank;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void processCommand(String command) {
		String[] parts = command.split(" ");
		String action = parts[0];

		switch (action) {
		case "create":
			createCommand(parts);
			break;

		case "deposit":
			depositCommand(parts);

		default:
			break;
		}
	}

	private void createCommand(String[] parts) {
		String accountType = parts[1];
		String uniqueId = parts[2];
		double apr = Double.parseDouble(parts[3]);

		switch (accountType) {
		case "checking":
			bank.addAccount(new Checking(uniqueId, apr));
			break;

		case "savings":
			bank.addAccount(new Savings(uniqueId, apr));
			break;

		case "cd":
			double initialBalance = Double.parseDouble(parts[4]);
			bank.addAccount(new CertificateOfDeposit(uniqueId, apr, initialBalance));
			break;

		default:
			throw new IllegalArgumentException(
					"Unsupported banking.Account Type. Please choose between checking/savings/cd" + accountType);
		}

	}

	private void depositCommand(String[] parts) {
		String uniqueId = parts[1];
		double depositAmount = Double.parseDouble(parts[2]);

		Account account = bank.retrieveAccount(uniqueId);

		if (account != null) {
			if (!(account instanceof CertificateOfDeposit)) {
				bank.depositAmount(uniqueId, depositAmount);
			} else {
				throw new UnsupportedOperationException("Cannot deposit to CertificateOfDeposit");
			}
		} else {
			throw new IllegalArgumentException("Amount not found: " + uniqueId);
		}
	}

}
