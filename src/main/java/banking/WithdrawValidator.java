package banking;

public class WithdrawValidator {
	private Bank bank;

	public WithdrawValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] tokens = command.split(" ");
		if (isInvalidCommand(tokens)) {
			return false;
		}

		String uniqueId = tokens[1];
		String amount = tokens[2];

		if (!bank.accountExistByUniqueId(uniqueId)) {
			return false;
		}

		Account account = bank.retrieveAccount(uniqueId);

		double amountValue = parseAmount(amount);
		if (isInvalidAmount(amountValue) || !isValidWithdrawal(account, amountValue)) {
			return false;
		}

		return true;
	}

	private boolean isInvalidCommand(String[] tokens) {
		return tokens.length != 3;
	}

	private double parseAmount(String amount) {
		try {
			return Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	private boolean isInvalidAmount(double amountValue) {
		return amountValue < 0;
	}

	private boolean isValidWithdrawal(Account account, double amountValue) {
		if (account instanceof Savings) {
			return isWithdrawnValidForSaving((Savings) account, amountValue);
		} else if (account instanceof Checking) {
			return isWithdrawnValidForChecking((Checking) account, amountValue);
		} else {
			return isWithdrawnValidForCD((CertificateOfDeposit) account, amountValue);
		}

	}

	private boolean isWithdrawnValidForCD(CertificateOfDeposit cdAccount, double amountValue) {
		return cdAccount.isEligibleForWithdrawal() && amountValue >= cdAccount.getBalance();
	}

	private boolean isWithdrawnValidForChecking(Checking checkingAccount, double amountValue) {
		return amountValue <= checkingAccount.getMaximumWithdrawalAmount();
	}

	private boolean isWithdrawnValidForSaving(Savings savingsAccount, double amountValue) {
		return amountValue <= savingsAccount.getMaximumWithdrawalAmount()
				&& savingsAccount.getWithdrawalCount() < savingsAccount.getMaxWithdrawalsPerMonth();
	}
}
