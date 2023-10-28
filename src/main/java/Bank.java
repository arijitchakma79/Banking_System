import java.util.HashMap;
import java.util.Map;

public class Bank {
	private Map<Integer, Account> accounts;

	Bank() {
		accounts = new HashMap<>();
	}

	public Map<Integer, Account> getAccounts() {
		return accounts;
	}

	public void addAccount(int uniqueId, double aprValue) {
		accounts.put(uniqueId, new Savings(uniqueId, aprValue));
	}

	public Account retrieveAccount(int uniqueId) {
		return accounts.get(uniqueId);
	}

	public void depositAmount(int uniqueId, double amountToDeposit) {
		accounts.get(uniqueId).depositBalance(amountToDeposit);
	}

	public void withdrawAmount(int uniqueId, double withdrawnAmount) {
		accounts.get(uniqueId).withdrawBalance(withdrawnAmount);
	}
}
