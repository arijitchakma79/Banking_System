package banking;

public class TransferValidator {
	private Bank bank;

	public TransferValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] tokens = command.split(" ");
		if (tokens.length != 4) {
			return false;
		}

		String fromId = tokens[1];
		String toId = tokens[2];
		String amount = tokens[3];

		if (!bank.accountExistByUniqueId(fromId) || !bank.accountExistByUniqueId(toId)) {
			return false;
		}

		Account fromAccount = bank.retrieveAccount(fromId);
		Account toAccount = bank.retrieveAccount(toId);

		if (fromAccount instanceof CertificateOfDeposit || toAccount instanceof CertificateOfDeposit) {
			return false;
		}

		double amountValue;
		try {
			amountValue = Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			return false;
		}

		if (amountValue < 0) {
			return false;
		}

		if (!isValidWithdrawal(fromAccount, amountValue)) {
			return false;
		}

		if (!isValidDeposit(toAccount, amountValue)) {
			return false;
		}

		return true;
	}

	private boolean isValidWithdrawal(Account account, double amount) {
		if (account instanceof Savings && amount > ((Savings) account).getMaximumWithdrawalAmount()) {
			return false;
		}

		if (account instanceof Checking && amount > ((Checking) account).getMaximumWithdrawalAmount()) {
			return false;
		}

		return true;
	}

	private boolean isValidDeposit(Account account, double amount) {
		if (account instanceof Savings && amount > ((Savings) account).getMaximumDepositAmount()) {
			return false;
		}

		if (account instanceof Checking && amount > ((Checking) account).getMaximumDepositAmount()) {
			return false;
		}

		return true;
	}
}
