package banking;

public class TransferValidator {
	private Bank bank;

	public TransferValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] tokens = command.split(" ");
		if (isInvalidTransferCommand(tokens)) {
			return false;
		}

		String fromId = tokens[1];
		String toId = tokens[2];
		String amount = tokens[3];

		if (!areValidAccounts(fromId, toId)) {
			return false;
		}

		Account fromAccount = bank.retrieveAccount(fromId);
		Account toAccount = bank.retrieveAccount(toId);

		if (isCertificateOfDeposit(fromAccount) || isCertificateOfDeposit(toAccount)) {
			return false;
		}

		double amountValue = parseAmount(amount);
		return isValidTransfer(fromAccount, toAccount, amountValue);
	}

	private boolean isInvalidTransferCommand(String[] tokens) {
		return tokens.length != 4;
	}

	private boolean areValidAccounts(String fromId, String toId) {
		return bank.accountExistByUniqueId(fromId) && bank.accountExistByUniqueId(toId);
	}

	private boolean isCertificateOfDeposit(Account account) {
		return account instanceof CertificateOfDeposit;
	}

	private double parseAmount(String amount) {
		try {
			return Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	private boolean isValidTransfer(Account fromAccount, Account toAccount, double amountValue) {
		return isNonNegativeAmount(amountValue) && isValidWithdrawal(fromAccount, amountValue)
				&& isValidDeposit(toAccount, amountValue);
	}

	private boolean isNonNegativeAmount(double amountValue) {
		return amountValue >= 0;
	}

	private boolean isValidWithdrawal(Account account, double amount) {
		if (account instanceof Savings) {
			return isWithdrawalValidForSaving(account, amount);
		} else if (account instanceof Checking) {
			return isWithdrawalValidForChecking(account, amount);
		}

		return false;
	}

	private boolean isWithdrawalValidForSaving(Account account, double amount) {
		return amount <= ((Savings) account).getMaximumWithdrawalAmount();
	}

	private boolean isWithdrawalValidForChecking(Account account, double amount) {
		return amount <= ((Checking) account).getMaximumWithdrawalAmount();
	}

	private boolean isValidDeposit(Account account, double amount) {
		return amount <= account.getMaximumDepositAmount();
	}
}
