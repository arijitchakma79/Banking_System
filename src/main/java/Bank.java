import java.util.HashMap;
import java.util.Map;

public class Bank {
	private Map<String, Account> accounts;

	Bank() {
		accounts = new HashMap<>();
	}

	public Map<String, Account> getAccounts() {
		return accounts;
	}

	public void addAccount(Account account) {
		accounts.put(account.getUniqueId(), account);
	}

	public Account retrieveAccount(String uniqueId) {
		return accounts.get(uniqueId);
	}

	public void depositAmount(String uniqueId, double amountToDeposit) {
		accounts.get(uniqueId).depositBalance(amountToDeposit);
	}

	public void withdrawAmount(String uniqueId, double withdrawnAmount) {
		accounts.get(uniqueId).withdrawBalance(withdrawnAmount);
	}

	public boolean accountExistByUniqueId(String uniqueId) {
		if (accounts.containsKey(uniqueId)) {
			return true;
		} else {
			return false;
		}
	}
}
