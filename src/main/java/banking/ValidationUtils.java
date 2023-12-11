package banking;

public class ValidationUtils {
	public static boolean isValidDoubleInRange(String value, double min, double max) {
		try {
			double doubleValue = Double.parseDouble(value);
			return (doubleValue >= min && doubleValue <= max);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isValidLong(String value, int length) {
		if (value.length() != length) {
			return false;
		}

		try {
			long longValue = Long.parseLong(value);
			return longValue >= 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
