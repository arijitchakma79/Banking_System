package banking;

import java.util.Map;

public class TimePasser {
	private Bank bank;

	public TimePasser(Bank bank) {
		this.bank = bank;
	}

	public void passTime(int months) {
		if (months < 1 || months > 60) {
			throw new IllegalArgumentException("Months must be between 1 and 60");
		}

		for (int i = 0; i < months; i++) {
			processMonths();
		}
	}

	private void processMonths() {
		Map<String, Account> accounts = bank.getAccounts();

		for (Account account : accounts.values()) {
			if (account.getBalance() <= 0) {
				bank.removeAccount(account.getUniqueId());
			} else if (account.getBalance() < 100) {
				account.withdrawBalance(25);
			}

			double monthlyApr = (account.getAPR() / 100) / 12;

			if (account instanceof CertificateOfDeposit) {
				for (int j = 0; j < 4; j++) {
					double interest = account.getBalance() * monthlyApr;
					account.depositBalance(interest);
				}
			} else {
				double interest = account.getBalance() * monthlyApr;
				account.depositBalance(interest);
				if (account instanceof Savings) {
					((Savings) account).resetWithdrawalCount();
				}
			}
			account.incrementTime();
		}
	}
}
