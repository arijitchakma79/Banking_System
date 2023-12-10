package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
	public static final double SUPPLIED_APR = 5.0;
	public static final String UNIQUE_ID = "60020001";
	Account checkingAccount;

	@BeforeEach
	void setUp() {
		checkingAccount = new Checking(UNIQUE_ID, SUPPLIED_APR);
	}

	@Test
	void account_created_with_supplied_apr() {
		double actual = checkingAccount.getAPR();
		assertEquals(5.0, actual);
	}

	@Test
	void account_has_deposited_supplied_amount() {
		checkingAccount.depositBalance(500.02);
		double actual = checkingAccount.getBalance();
		assertEquals(500.02, actual);
	}

	@Test
	void depositing_two_times_to_the_same_account() {
		checkingAccount.depositBalance(500);
		checkingAccount.depositBalance(300.02);
		double actual = checkingAccount.getBalance();
		assertEquals(800.02, actual);
	}

	@Test
	void account_has_withdrawn_supplied_amount() {
		checkingAccount.depositBalance(1000);
		checkingAccount.withdrawBalance(500);
		double actual = checkingAccount.getBalance();
		assertEquals(500, actual);
	}

	@Test
	void account_balance_cannot_go_below_0_dollars() {
		checkingAccount.depositBalance(1000);
		checkingAccount.withdrawBalance(2000);
		double actual = checkingAccount.getBalance();
		assertEquals(0.00, actual);
	}

	@Test
	void withdrawing_two_times_from_the_same_account() {
		checkingAccount.depositBalance(1000);
		checkingAccount.withdrawBalance(300);
		checkingAccount.withdrawBalance(200);
		double actual = checkingAccount.getBalance();
		assertEquals(500, actual);
	}

	@Test
	void getTransferring_amount_gets_withdrawn() {
		Account saving = new Savings("12345556", 2);
		checkingAccount.depositBalance(2000);
		checkingAccount.transferBalance(saving, 1000);
		assertEquals(1000, checkingAccount.getBalance());
	}

	@Test
	void transferring_amount_gets_deposited() {
		Account saving = new Savings("12345556", 2);
		checkingAccount.depositBalance(2000);
		saving.depositBalance(1000);
		checkingAccount.transferBalance(saving, 1000);
		assertEquals(2000, saving.getBalance());
	}

}
