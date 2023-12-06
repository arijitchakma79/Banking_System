package banking;

public class WithdrawValidator {
	private Bank bank;

	public WithdrawValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] tokens = command.split(" ");
		if (tokens.length != 3) {
			return false;
		}

		String uniqueId = tokens[1];
		String amount = tokens[2];

		if (!bank.accountExistByUniqueId(uniqueId)) {
			return false;
		}

		Account account = bank.retrieveAccount(uniqueId);

		if (account instanceof CertificateOfDeposit) {
			return false;
		}

		double amountValue;
		try {
			amountValue = Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			return false;
		}

		if (amountValue < 0 || amountValue > getMaximumWithdrawalAmount(account)) {
			return false;
		}
		if (account instanceof Savings && !isWithdrawnValidForSaving(account, amountValue)) {
			return false;
		}

		if (account instanceof Checking && !isWithdrawnValidForChecking(account, amountValue)) {
			return false;
		}

		if (account instanceof CertificateOfDeposit && !isWithdrawnValidForCD(account, amountValue)) {
			return false;
		}
		return true;
	}

	private boolean isWithdrawnValidForCD(Account account, double amountValue) {
		CertificateOfDeposit cdAccount = (CertificateOfDeposit) account;

		if (!cdAccount.isEligibleForWithdrawal()) {
			return false;
		}
		if (amountValue < cdAccount.getBalance()) {
			return false;
		}
		return true;
	}

	private boolean isWithdrawnValidForChecking(Account account, double amountValue) {
		Checking checkingAccount = (Checking) account;
		return (amountValue <= 400);
	}

	private boolean isWithdrawnValidForSaving(Account account, double amountValue) {
		Savings savingsAccount = (Savings) account;

		if (amountValue > savingsAccount.getMaximumWithdrawalAmount()) {
			return false;
		}

		if (savingsAccount.getWithdrawalCount() >= savingsAccount.getMaxWithdrawalsPerMonth()) {
			return false;
		}

		// If both conditions are met, the withdrawal is valid
		return true;
	}

	private double getMaximumWithdrawalAmount(Account account) {
		if (account instanceof Savings) {
			return 1000;
		} else if (account instanceof Checking) {
			return 400;
		}
		return 0;
	}

}
