package banking;

public class CommandValidator {

	private CreateValidator createValidator;
	private DepositValidator depositValidator;
	private WithdrawValidator withdrawValidator;
	private TransferValidator transferValidator;
	private passTimeValidator passTimeValidator;

	public CommandValidator(Bank bank) {
		this.createValidator = new CreateValidator(bank);
		this.depositValidator = new DepositValidator(bank);
		this.withdrawValidator = new WithdrawValidator(bank);
		this.transferValidator = new TransferValidator(bank);
		this.passTimeValidator = new passTimeValidator();
	}

	public boolean validate(String command) {
		String[] tokens = command.split(" ");
		String action = tokens[0].toLowerCase();

		switch (action) {
		case "create":
			return createValidator.validate(command);
		case "deposit":
			return depositValidator.validate(command);
		case "withdraw":
			return withdrawValidator.validate(command);
		case "transfer":
			return transferValidator.validate(command);
		case "pass":
			return passTimeValidator.validate(command);
		default:
			return false;
		}
	}
}
