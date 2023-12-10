package banking;

import java.util.List;

public class MasterControl {
	private CommandProcessor commandProcessor;
	private CommandValidator commandValidator;
	private CommandStorage commandStorage;

	public MasterControl(CommandValidator accountValidator, CommandProcessor commandProcessor,
			CommandStorage commandStorage) {
		this.commandValidator = accountValidator;
		this.commandProcessor = commandProcessor;
		this.commandStorage = commandStorage;
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			if (commandValidator.validate(command)) {
				commandProcessor.processCommand(command);
			} else {
				commandStorage.addInvalidCommands(command);
			}
		}
		return commandStorage.getInvalidCommands();
	}
}
