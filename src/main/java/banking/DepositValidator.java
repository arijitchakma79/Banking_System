package banking;

public class DepositValidator {
	private Bank bank;

	public DepositValidator(Bank bank) {
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

		if (account instanceof CertificateOfDeposit || !isDepositAmountValid(account, amount)) {
			return false;
		}

		return true;
	}

	private boolean isDepositAmountValid(Account account, String amount) {
		try {
			double amountValue = Double.parseDouble(amount);
			return amountValue >= 0 && amountValue <= account.getMaximumDepositAmount();
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
