package banking;

/**
 * Validator for the "pass" command that advances time in the banking system.
 * This class currently has an empty constructor because no initialization is
 * needed.
 */
public class passTimeValidator {

	/**
	 * Empty constructor since no initialization is required.
	 */
	public passTimeValidator() {
	}

	/**
	 * Validates the "pass" command.
	 *
	 * @param command The pass command string.
	 * @return true if the command is valid, false otherwise.
	 */
	public boolean validate(String command) {
		String[] token = command.split(" ");
		if (token.length != 2) {
			return false;
		}

		String monthStr = token[1];

		try {
			int months = Integer.parseInt(monthStr);
			return isValidMonths(months);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean isValidMonths(int months) {
		return months >= 1 && months <= 60;
	}
}
