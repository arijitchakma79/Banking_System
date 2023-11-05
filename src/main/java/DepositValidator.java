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

		if (account instanceof CertificateOfDeposit) {
			return false;
		}

		double amountValue;
		try {
			amountValue = Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			return false;
		}

		if (amountValue < 0 || (account instanceof Savings && amountValue > 2500)
				|| (account instanceof Checking && amountValue > 1000)) {
			return false;
		}
		return true;
	}

}
