package banking;

import java.util.ArrayList;
import java.util.List;

public class MasterControl {
	private CommandProcessor commandProcessor;
	private CommandValidator commandValidator;
	private CommandStorage commandStorage;

	public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
			CommandStorage commandStorage) {
		this.commandValidator = commandValidator;
		this.commandProcessor = commandProcessor;
		this.commandStorage = commandStorage;

	}

	public List<String> start(List<String> input) {
		List<String> validInputs = new ArrayList<>();

		for (String command : input) {
			if (commandValidator.validate(command)) {
				commandProcessor.processCommand(command);
				validInputs.add(command);
			} else {
				commandStorage.addInvalidCommands(command);
			}
		}

		List<String> output = new ArrayList<>();

		// Collect current state of open accounts
		List<String> accountOutput = commandProcessor.getOutput();
		output.addAll(accountOutput);

		// Collect invalid commands
		List<String> invalidCommands = commandStorage.getInvalidCommands();
		output.addAll(invalidCommands);

		return output;
	}
}
