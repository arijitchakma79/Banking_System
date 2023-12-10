package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandStorage {
	private List<String> commands;

	public CommandStorage() {
		this.commands = new ArrayList<>();
	}

	public void addInvalidCommands(String invalidCommand) {
		if (invalidCommand != null && !invalidCommand.isEmpty()) {
			this.commands.add(invalidCommand);
		}
	}

	public List<String> getInvalidCommands() {
		return new ArrayList<>(this.commands);
	}
}
