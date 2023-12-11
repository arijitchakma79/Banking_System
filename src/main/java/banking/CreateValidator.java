package banking;

public class CreateValidator {
	public Bank bank;

	public CreateValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] tokens = command.split(" ");
		if (!"cd".equals(tokens[1].toLowerCase())) {
			if (tokens.length != 4) {
				return false;
			}
		}

		String accountType = tokens[1].toLowerCase();
		String uniqueId = tokens[2];
		String apr = tokens[3];

		if (!isValidAccountType(accountType)) {
			return false;
		}

		if (!isValidUnique(uniqueId)) {
			return false;
		}

		if (bank.accountExistByUniqueId(uniqueId)) {
			return false;
		}

		if (!isValidApr(apr)) {
			return false;
		}

		if (accountType.equals("cd")) {
			if (tokens.length != 5) {
				return false;
			}

			String amount = tokens[4];
			if (!isValidCDAmount(amount)) {
				return false;
			}
		}
		return true;
	}

	private boolean isValidCDAmount(String amount) {
		try {
			double amountValue = Double.parseDouble(amount);
			return (amountValue >= 1000 && amountValue <= 10000);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean isValidUnique(String uniqueId) {
		if (uniqueId.length() != 8) {
			return false;
		}

		try {
			long idValue = Long.parseLong(uniqueId);
			return idValue >= 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean isValidApr(String apr) {
		try {
			double aprValue = Double.parseDouble(apr);
			return (aprValue >= 0 && aprValue <= 10);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean isValidAccountType(String accountType) {
		return "checking".equals(accountType) || "savings".equals(accountType) || "cd".equals(accountType);
	}

}
