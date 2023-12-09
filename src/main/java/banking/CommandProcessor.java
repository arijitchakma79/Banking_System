package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandProcessor {
	public Bank bank;
	private List<String> output;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
		this.output = new ArrayList<>();
	}

	public void processCommand(String command) {
		String[] parts = command.split(" ");
		String action = parts[0];

		switch (action) {
		case "create":
		case "Create":
			createCommand(parts);
			break;
		case "deposit":
		case "Deposit":
			depositCommand(parts);
			break;
		case "withdraw":
		case "Withdraw":
			withdrawCommand(parts);
			break;
		case "transfer":
		case "Transfer":
			transferCommand(parts);
			break;
		case "pass":
		case "Pass":
			passTimeCommand(parts);
			break;
		default:
			throw new IllegalArgumentException("Unsupported action: " + action);
		}

		output.addAll(collectAccountOutput());
	}

	public List<String> getOutput() {
		return new ArrayList<>(output);
	}

	private void createCommand(String[] parts) {
		String accountType = parts[1];
		String uniqueId = parts[2];
		double apr = Double.parseDouble(parts[3]);

		switch (accountType) {
		case "checking":
			bank.addAccount(new Checking(uniqueId, apr));
			break;
		case "savings":
			bank.addAccount(new Savings(uniqueId, apr));
			break;
		case "cd":
			double initialBalance = Double.parseDouble(parts[4]);
			bank.addAccount(new CertificateOfDeposit(uniqueId, apr, initialBalance));
			break;
		default:
			throw new IllegalArgumentException(
					"Unsupported banking.Account Type. Please choose between checking/savings/cd" + accountType);
		}

	}

	private void depositCommand(String[] parts) {
		String uniqueId = parts[1];
		double depositAmount = Double.parseDouble(parts[2]);

		Account account = bank.retrieveAccount(uniqueId);

		if (account != null) {
			if (!(account instanceof CertificateOfDeposit)) {
				bank.depositAmount(uniqueId, depositAmount);
			} else {
				throw new UnsupportedOperationException("Cannot deposit to CertificateOfDeposit");
			}
		} else {
			throw new IllegalArgumentException("Account not found: " + uniqueId);
		}

	}

	private void withdrawCommand(String[] parts) {
		String uniqueId = parts[1];
		double withdrawAmount = Double.parseDouble(parts[2]);

		Account account = bank.retrieveAccount(uniqueId);

		if (account != null) {
			if (account instanceof CertificateOfDeposit) {
				CertificateOfDeposit cdAccount = (CertificateOfDeposit) account;

				if (cdAccount.isEligibleForWithdrawal()) {
					if (withdrawAmount >= account.getBalance() && account.getTime() >= 12) {
						bank.withdrawAmount(uniqueId, withdrawAmount);
					} else {
						throw new UnsupportedOperationException(
								"For CD accounts with 12 or more months, you can only withdraw the entire balance at once.");
					}
				} else {
					throw new UnsupportedOperationException("Cannot withdraw from a CD account before 12 months.");
				}
			} else {
				bank.withdrawAmount(uniqueId, withdrawAmount);
			}
		} else {
			throw new IllegalArgumentException("Account Not Found: " + uniqueId);
		}
	}

	private void transferCommand(String[] parts) {
		String fromUniqueId = parts[1];
		String toUniqueId = parts[2];
		double transferAmount = Double.parseDouble(parts[3]);

		bank.transferAmount(fromUniqueId, toUniqueId, transferAmount);

	}

	private void passTimeCommand(String[] parts) {
		String monthString = parts[1];

		try {
			int months = Integer.parseInt(monthString);
			bank.passTime(months);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid months: " + monthString);
		}
	}

	private List<String> collectAccountOutput() {
		List<String> accountOutput = new ArrayList<>();
		for (Account account : bank.getAccounts().values()) {
			accountOutput.add(formatAccountState(account));
			accountOutput.addAll(account.getTransactionCommands());
		}
		return accountOutput;
	}

	private String formatAccountState(Account account) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);

		String accountType = account instanceof Checking ? "Checking"
				: account instanceof Savings ? "Savings" : account instanceof CertificateOfDeposit ? "CD" : "Unknown";

		String id = account.getUniqueId();
		String balance = decimalFormat.format(account.getBalance());
		String apr = decimalFormat.format(account.getAPR());

		return String.format(Locale.US, "%s %s %s %s", accountType, id, balance, apr);
	}

}
