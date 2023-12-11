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

		String amount = tokens[2];
		String uniqueId = tokens[1];

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

		if (!isDepositAmountValid(account, amountValue)) {
			return false;
		}
		return true;
	}

	private boolean isDepositAmountValid(Account account, double amountValue) {
		if (amountValue < 0 || amountValue > account.getMaximumDepositAmount()) {
			return false;
		}
		return true;
	}

}
