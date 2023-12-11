package banking;

public class passTimeValidator {

	public passTimeValidator() {

	}

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
