import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	public static final String UNIQUE_ID = "64002008";
	public static final String UNIQUE_ID2 = "64002009";
	public static final int APR_VALUE = 5;
	Bank bank;
	Account savingsAccount;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		savingsAccount = new Savings(UNIQUE_ID, APR_VALUE);

	}

	@Test
	void bank_has_no_accounts_initially() {
		assertTrue(bank.getAccounts().isEmpty());
	}

	@Test
	void bank_has_1_account_after_an_account_is_added() {
		bank.addAccount(savingsAccount);
		assertEquals(1, bank.getAccounts().size());
	}

	@Test
	void bank_has_2_accounts_after_an_account_is_added() {
		bank.addAccount(savingsAccount);
		Account checkingAccount = new Checking(UNIQUE_ID2, APR_VALUE);
		bank.addAccount(checkingAccount);
		assertEquals(2, bank.getAccounts().size());
	}

	@Test
	void retrieve_the_correct_account_from_bank() {
		bank.addAccount(savingsAccount);
		Account account = bank.retrieveAccount(UNIQUE_ID);
		assertEquals(UNIQUE_ID, account.getUniqueId());
	}

	@Test
	void deposit_money_to_the_correct_account_by_unique_id() {
		bank.addAccount(savingsAccount);
		bank.depositAmount(UNIQUE_ID, 1200);
		double balance = bank.getAccounts().get(UNIQUE_ID).getBalance();
		assertEquals(1200, balance);
	}

	@Test
	void deposit_twice_to_the_correct_account_by_unique_id() {
		bank.addAccount(savingsAccount);
		bank.depositAmount(UNIQUE_ID, 1200);
		bank.depositAmount(UNIQUE_ID, 600);
		double balance = bank.getAccounts().get(UNIQUE_ID).getBalance();
		assertEquals(1800, balance);
	}

	@Test
	void withdraw_money_from_the_correct_account_by_unique_id() {
		bank.addAccount(savingsAccount);
		bank.depositAmount(UNIQUE_ID, 2000);
		bank.withdrawAmount(UNIQUE_ID, 500);
		double balance = bank.getAccounts().get(UNIQUE_ID).getBalance();
		assertEquals(1500, balance);

	}

	@Test
	void withdraw_twice_from_the_correct_account_by_unique_id() {
		bank.addAccount(savingsAccount);
		bank.depositAmount(UNIQUE_ID, 2000);
		bank.withdrawAmount(UNIQUE_ID, 500);
		bank.withdrawAmount(UNIQUE_ID, 300);
		double balance = bank.getAccounts().get(UNIQUE_ID).getBalance();
		assertEquals(1200, balance);
	}

}
