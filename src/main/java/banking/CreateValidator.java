package banking;

public class CreateValidator {
	private final Bank bank;

	public CreateValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] tokens = command.split(" ");

		if (!isValidTokenLength(tokens)) {
			return false;
		}

		String accountType = tokens[1].toLowerCase();
		String uniqueId = tokens[2];
		String apr = tokens[3];

		return isValidAccountType(accountType) && isValidUnique(uniqueId) && !bank.accountExistByUniqueId(uniqueId)
				&& isValidApr(apr) && (accountType.equals("cd") ? isValidCDAmount(tokens[4]) : true);
	}

	private boolean isValidTokenLength(String[] tokens) {
		return ("cd".equalsIgnoreCase(tokens[1]) && tokens.length == 5)
				|| (!"cd".equalsIgnoreCase(tokens[1]) && tokens.length == 4);
	}

	private boolean isValidCDAmount(String amount) {
		return ValidationUtils.isValidDoubleInRange(amount, 1000, 10000);
	}

	private boolean isValidUnique(String uniqueId) {
		return ValidationUtils.isValidLong(uniqueId, 8);
	}

	private boolean isValidApr(String apr) {
		return ValidationUtils.isValidDoubleInRange(apr, 0, 10);
	}

	private boolean isValidAccountType(String accountType) {
		return "checking".equals(accountType) || "savings".equals(accountType) || "cd".equals(accountType);
	}
}
