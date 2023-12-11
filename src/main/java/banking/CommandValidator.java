package banking;

public class CommandValidator {

	private Bank bank;
	private CreateValidator createValidator;
	private DepositValidator depositValidator;
	private WithdrawValidator withdrawValidator;
	private TransferValidator transferValidator;
	private passTimeValidator passTimeValidator;

	public CommandValidator(Bank bank) {
		this.bank = bank;
		this.createValidator = new CreateValidator(bank);
		this.depositValidator = new DepositValidator(bank);
		this.withdrawValidator = new WithdrawValidator(bank);
		this.transferValidator = new TransferValidator(bank);
		this.passTimeValidator = new passTimeValidator(bank);
	}

	public boolean validate(String command) {
		String[] tokens = command.split(" ");
		String action = tokens[0].toLowerCase();
		if (action.equals("create")) {
			return createValidator.validate(command);
		} else if (action.equals("deposit")) {
			return depositValidator.validate(command);
		} else if (action.equals("withdraw")) {
			return withdrawValidator.validate(command);
		} else if (action.equals("transfer")) {
			return transferValidator.validate(command);
		} else if (action.equals("pass")) {
			return passTimeValidator.validate(command);
		}
		return false;
	}

}
