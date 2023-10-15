import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
	public static final double SUPPLIED_APR = 5.0;
	public static final int SUPPLIED_BALANCE = 1000;
	public static final int UNIQUE_ID = 60020001;
	Account certificateOfDeposit;

	@BeforeEach
	void setUp() {
		certificateOfDeposit = new CertificateOfDeposit(SUPPLIED_BALANCE, SUPPLIED_APR, UNIQUE_ID);
	}

	@Test
	void account_created_with_supplied_apr() {
		double actual = certificateOfDeposit.getAPR();
		assertEquals(5.0, actual);
	}

	@Test
	void account_has_deposited_supplied_amount() {
		certificateOfDeposit.depositBalance(500.02);
		double actual = certificateOfDeposit.getBalance();
		assertEquals(1500.02, actual);
	}

	@Test
	void depositing_two_times_to_the_same_account() {
		certificateOfDeposit.depositBalance(500);
		certificateOfDeposit.depositBalance(300.02);
		double actual = certificateOfDeposit.getBalance();
		assertEquals(1800.02, actual);
	}

	@Test
	void account_has_withdrawn_supplied_amount() {
		certificateOfDeposit.withdrawBalance(500);
		double actual = certificateOfDeposit.getBalance();
		assertEquals(500, actual);
	}

	@Test
	void account_balance_cannot_go_below_0_dollars() {
		certificateOfDeposit.withdrawBalance(2000);
		double actual = certificateOfDeposit.getBalance();
		assertEquals(0.00, actual);
	}

	@Test
	void withdrawing_two_times_from_the_same_account() {
		certificateOfDeposit.withdrawBalance(300);
		certificateOfDeposit.withdrawBalance(200);
		double actual = certificateOfDeposit.getBalance();
		assertEquals(500, actual);
	}
}
