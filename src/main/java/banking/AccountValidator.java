package banking;

public class AccountValidator {

	private Bank bank;
	private CreateValidator createValidator;
	private DepositValidator depositValidator;

	public AccountValidator(Bank bank) {
		this.bank = bank;
		this.createValidator = new CreateValidator(bank);
		this.depositValidator = new DepositValidator(bank);
	}

	public boolean validate(String command) {
		String[] tokens = command.split(" ");

		String action = tokens[0].toLowerCase();

		if (action.equals("create")) {
			return createValidator.validate(command);
		} else if (action.equals("deposit")) {
			return depositValidator.validate(command);
		}

		return false;
	}
}
