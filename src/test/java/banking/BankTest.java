package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	public static final String UNIQUE_ID = "64002008";
	public static final String UNIQUE_ID2 = "64002009";
	public static final int APR_VALUE = 5;
	public static final int APR_VALUE2 = 3;
	Bank bank;
	Account savingsAccount;
	Account checkingAccount;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		savingsAccount = new Savings(UNIQUE_ID, APR_VALUE);
		checkingAccount = new Checking(UNIQUE_ID2, APR_VALUE2);
	}

	@Test
	void bank_has_no_accounts_initially() {
		assertTrue(bank.getAccounts().isEmpty());
	}

	@Test
	void retrieves_balance_successfully() {
		assertEquals(0, checkingAccount.getBalance());
	}

	@Test
	void retrieves_apr_successfully() {
		assertEquals(3, checkingAccount.getAPR());
	}

	@Test
	void retrieves_unique_id_successfully() {
		assertEquals("64002009", checkingAccount.getUniqueId());
	}

	@Test
	void retrieves_time_successfully() {

		assertEquals(0, checkingAccount.getTime());
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

	@Test
	void transfer_money_from_a_savings_account_to_checking_account_by_unique_ids() {
		bank.addAccount(savingsAccount);
		bank.addAccount(checkingAccount);
		bank.depositAmount(UNIQUE_ID, 2000);
		bank.transferAmount(UNIQUE_ID, UNIQUE_ID2, 500);

		assertEquals(1500, bank.retrieveAccount(UNIQUE_ID).getBalance());
		assertEquals(500, bank.retrieveAccount(UNIQUE_ID2).getBalance());

	}

	@Test
	void transfer_money_from_a_checking_account_to_savings_account_by_unique_ids() {
		bank.addAccount(savingsAccount);
		bank.addAccount(checkingAccount);
		bank.depositAmount(UNIQUE_ID2, 2000);
		bank.transferAmount(UNIQUE_ID2, UNIQUE_ID, 500);
		assertEquals(1500, bank.retrieveAccount(UNIQUE_ID2).getBalance());
		assertEquals(500, bank.retrieveAccount(UNIQUE_ID).getBalance());
	}

	@Test
	void transfer_money_from_a_checking_account_to_checking_account_by_unique_ids() {
		Account anotherCheckingAccount = new Checking("11111111", 2);
		bank.addAccount(checkingAccount);
		bank.addAccount(anotherCheckingAccount);
		bank.depositAmount(UNIQUE_ID2, 2000);
		bank.transferAmount(UNIQUE_ID2, "11111111", 500);
		assertEquals(1500, bank.retrieveAccount(UNIQUE_ID2).getBalance());
		assertEquals(500, bank.retrieveAccount("11111111").getBalance());
	}

	@Test
	void transfer_money_from_a_savings_account_to_savings_account_by_unique_ids() {
		Account anotherSavingsAccount = new Savings("12345666", 2);
		bank.addAccount(savingsAccount);
		bank.addAccount(anotherSavingsAccount);
		bank.depositAmount(UNIQUE_ID, 2000);
		bank.transferAmount(UNIQUE_ID, "12345666", 500);
		assertEquals(1500, bank.retrieveAccount(UNIQUE_ID).getBalance());
		assertEquals(500, bank.retrieveAccount("12345666").getBalance());
	}

	@Test
	void transfer_from_cd_account_throws_exception() {
		Account cdAccount = new CertificateOfDeposit(UNIQUE_ID, APR_VALUE, 1000);
		Account targetAccount = new Checking("targetId", APR_VALUE);

		bank.addAccount(cdAccount);
		bank.addAccount(targetAccount);

		assertThrows(UnsupportedOperationException.class, () -> cdAccount.transferBalance(targetAccount, 500));
	}

	@Test
	void transfer_to_same_checking_account_twice() {
		bank.addAccount(savingsAccount);
		bank.addAccount(checkingAccount);
		bank.depositAmount(UNIQUE_ID, 2000);
		bank.transferAmount(UNIQUE_ID, UNIQUE_ID2, 500);
		bank.transferAmount(UNIQUE_ID, UNIQUE_ID2, 300);
		assertEquals(1200, bank.retrieveAccount(UNIQUE_ID).getBalance());
		assertEquals(800, bank.retrieveAccount(UNIQUE_ID2).getBalance());

	}

	@Test
	void transfer_to_same_savings_account_twice() {
		bank.addAccount(savingsAccount);
		bank.addAccount(checkingAccount);
		bank.depositAmount(UNIQUE_ID2, 2000);
		bank.transferAmount(UNIQUE_ID2, UNIQUE_ID, 500);
		bank.transferAmount(UNIQUE_ID2, UNIQUE_ID, 300);
		assertEquals(1200, bank.retrieveAccount(UNIQUE_ID2).getBalance());
		assertEquals(800, bank.retrieveAccount(UNIQUE_ID).getBalance());
	}

	@Test
	void remove_account_command_removes_the_account_from_bank() {
		bank.addAccount(savingsAccount);
		assertTrue(bank.getAccounts().containsKey(UNIQUE_ID));

		bank.removeAccount(UNIQUE_ID);

		assertFalse(bank.getAccounts().containsKey(UNIQUE_ID));
	}

	@Test
	void pass_time_commands_increases_time_in_an_account() {
		Account cd = new CertificateOfDeposit("12345678", 2, 2000);
		bank.addAccount(cd);
		bank.passTime(12);

		assertEquals(12, bank.retrieveAccount("12345678").getTime());
	}

	@Test
	void pass_time_commands_increases_time_in_every_account() {
		Account cd = new CertificateOfDeposit("12345678", 2, 2000);
		bank.addAccount(cd);
		bank.addAccount(checkingAccount);
		bank.addAccount(savingsAccount);
		bank.depositAmount(UNIQUE_ID, 1000);
		bank.depositAmount(UNIQUE_ID2, 1000);
		bank.passTime(12);
		assertEquals(12, bank.retrieveAccount(UNIQUE_ID).getTime());
		assertEquals(12, bank.retrieveAccount(UNIQUE_ID2).getTime());
		assertEquals(12, bank.retrieveAccount("12345678").getTime());
	}

	@Test
	void apr_calculation_for_savings_account() {
		Account savings = new Savings("12345678", 3);
		bank.addAccount(savings);
		bank.depositAmount("12345678", 1000);
		bank.passTime(1);
		assertEquals(1002.5, bank.retrieveAccount("12345678").getBalance());
	}

	@Test
	void apr_calculation_for_checking_account() {
		Account checking = new Checking("12345678", 3);
		bank.addAccount(checking);
		bank.depositAmount("12345678", 1000);
		bank.passTime(1);
		assertEquals(1002.5, bank.retrieveAccount("12345678").getBalance());
	}

	@Test
	void apr_calculation_for_cd_account() {
		Account cd = new CertificateOfDeposit("12345678", 2.1, 2000);
		bank.addAccount(cd);
		bank.passTime(1);
		assertEquals(2014.0367928937578, bank.retrieveAccount("12345678").getBalance());
	}

}
