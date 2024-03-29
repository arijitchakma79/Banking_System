package banking;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandProcessor {

	private Bank bank;
	private List<String> output;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
		this.output = new ArrayList<>();
	}

	public void processCommand(String command) {
		String[] parts = command.split(" ");
		String action = parts[0].toLowerCase();

		switch (action) {
		case "create":
			createCommand(parts);
			break;
		case "deposit":
			depositCommand(parts);
			break;
		case "withdraw":
			withdrawCommand(parts);
			break;
		case "transfer":
			transferCommand(parts);
			break;
		case "pass":
			passTimeCommand(parts);
			break;
		default:
			throw new IllegalArgumentException("Unsupported action: " + action);
		}

		if (!action.equalsIgnoreCase("create") && !action.equalsIgnoreCase("pass")) {
			String accountUniqueId = action.equalsIgnoreCase("transfer") ? parts[2] : parts[1];
			Account account = bank.retrieveAccount(accountUniqueId);

			account.addTransactionCommand(command);
		}
	}

	public List<String> getOutput() {
		List<String> accountOutput = collectAccountOutput();
		output.addAll(accountOutput);
		return new ArrayList<>(output);
	}

	private void createCommand(String[] parts) {
		String accountType = parts[1].toLowerCase();
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
			throw new IllegalArgumentException("Unsupported Account Type: " + accountType);
		}
	}

	private void depositCommand(String[] parts) {
		String uniqueId = parts[1];
		double depositAmount = Double.parseDouble(parts[2]);
		Account account = bank.retrieveAccount(uniqueId);

		if (!(account instanceof CertificateOfDeposit)) {
			bank.depositAmount(uniqueId, depositAmount);
		} else {
			throw new UnsupportedOperationException("Cannot deposit to CertificateOfDeposit");
		}
	}

	private void withdrawCommand(String[] parts) {
		String uniqueId = parts[1];
		double withdrawAmount = Double.parseDouble(parts[2]);
		Account account = bank.retrieveAccount(uniqueId);

		if (account instanceof CertificateOfDeposit) {
			processCertificateOfDepositWithdrawal((CertificateOfDeposit) account, withdrawAmount);
		} else if (account instanceof Savings) {
			processSavingsWithdrawal((Savings) account, withdrawAmount);
		} else {
			bank.withdrawAmount(uniqueId, withdrawAmount);
		}
	}

	private void processCertificateOfDepositWithdrawal(CertificateOfDeposit cdAccount, double withdrawAmount) {
		if (cdAccount.isEligibleForWithdrawal()) {
			if (withdrawAmount >= cdAccount.getBalance() && cdAccount.getTime() >= 12) {
				bank.withdrawAmount(cdAccount.getUniqueId(), withdrawAmount);
			} else {
				throw new UnsupportedOperationException(
						"For CD accounts with 12 or more months, you can only withdraw the entire balance at once.");
			}
		} else {
			throw new UnsupportedOperationException("Cannot withdraw from a CD account before 12 months.");
		}
	}

	private void processSavingsWithdrawal(Savings savingsAccount, double withdrawAmount) {
		if (savingsAccount.getWithdrawalCount() < savingsAccount.getMaxWithdrawalsPerMonth()) {
			bank.withdrawAmount(savingsAccount.getUniqueId(), withdrawAmount);
			savingsAccount.incrementWithdrawalCount();
		} else {
			throw new UnsupportedOperationException("Cannot withdraw more than once in a month for Savings account.");
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

		String accountType = getAccountType(account);
		String id = account.getUniqueId();
		String balance = decimalFormat.format(account.getBalance());
		String apr = decimalFormat.format(account.getAPR());

		return String.format(Locale.UK, "%s %s %s %s", accountType, id, balance, apr);
	}

	private String getAccountType(Account account) {
		if (account instanceof Checking) {
			return "Checking";
		} else if (account instanceof Savings) {
			return "Savings";
		} else if (account instanceof CertificateOfDeposit) {
			return "Cd";
		} else {
			return "Unknown";
		}
	}

}
