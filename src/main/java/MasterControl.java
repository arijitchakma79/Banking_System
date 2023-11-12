import java.util.List;

public class MasterControl {
	private CommandProcessor commandProcessor;
	private AccountValidator accountValidator;
	private CommandStorage commandStorage;

	public MasterControl(AccountValidator accountValidator, CommandProcessor commandProcessor,
			CommandStorage commandStorage) {
		this.accountValidator = accountValidator;
		this.commandProcessor = commandProcessor;
		this.commandStorage = commandStorage;
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			if (accountValidator.validate(command)) {
				commandProcessor.processCommand(command);
			} else {
				commandStorage.addInvalidCommands(command);
			}
		}
		return commandStorage.getInvalidCommands();
	}
}
