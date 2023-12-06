package banking;

public class TransferValidator {
	private Bank bank;

	public TransferValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] token = command.split(" ");
		if (token.length != 4) {
			return false;
		}

		String fromId = token[1];
		String toId = token[2];
		String amount = token[3];

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
		return true;
	}
}
