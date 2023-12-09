package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	Bank bank;
	CommandProcessor commandProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void testCreateCheckingAccount() {
		String command = "create checking 12345678 2.0";
		commandProcessor.processCommand(command);

		assertTrue(bank.accountExistByUniqueId("12345678"));
	}

	@Test
	void testCreateSavingAccount() {
		String command = "create savings 12345677 2.0";
		commandProcessor.processCommand(command);

		assertTrue(bank.accountExistByUniqueId("12345677"));
	}

	@Test
	void testCreateCertificateOfDeposit() {
		String command = "create cd 12345678 2.0 2000";
		commandProcessor.processCommand(command);
		assertTrue(bank.accountExistByUniqueId("12345678"));
	}

	@Test
	void testDepositToNewlyCreatedChecking() {
		String createChecking = "create checking 12345678 0.8";
		String depositCommand = "deposit 12345678 100";
		commandProcessor.processCommand(createChecking);
		commandProcessor.processCommand(depositCommand);

		assertEquals(100, bank.retrieveAccount("12345678").getBalance(), 0.01);
	}

	@Test
	void testDepositToExistingChecking() {
		String createChecking = "create checking 12345678 0.8";
		String depositCommand = "deposit 12345678 100";
		String depositAgain = "deposit 12345678 100";
		commandProcessor.processCommand(createChecking);
		commandProcessor.processCommand(depositCommand);
		commandProcessor.processCommand(depositAgain);

		assertEquals(200, bank.retrieveAccount("12345678").getBalance(), 0.01);
	}

	@Test
	void testDepositToNewlyCreatedSavings() {
		String createChecking = "create savings 12345678 0.8";
		String depositCommand = "deposit 12345678 100";
		commandProcessor.processCommand(createChecking);
		commandProcessor.processCommand(depositCommand);

		assertEquals(100, bank.retrieveAccount("12345678").getBalance(), 0.01);
	}

	@Test
	void testDepositToExistingSavings() {
		String createChecking = "create savings 12345678 0.8";
		String depositCommand = "deposit 12345678 100";
		String depositAgain = "deposit 12345678 100";
		commandProcessor.processCommand(createChecking);
		commandProcessor.processCommand(depositCommand);
		commandProcessor.processCommand(depositAgain);

		assertEquals(200, bank.retrieveAccount("12345678").getBalance(), 0.01);
	}

	@Test
	void testDepositToCdAccount() {
		String createCd = "create cd 12345678 2000 2";
		String depositCommand = "deposit 12345678 300";
		commandProcessor.processCommand(createCd);
		assertThrows(UnsupportedOperationException.class, () -> {
			commandProcessor.processCommand(depositCommand);
		});
	}

	@Test
	void testWithdrawFromSavings() {
		String createSavings = "Create savings 12345678 2";
		String depositSavings = "Deposit 12345678 2000";
		String savingWithdraw = "Withdraw 12345678 1000";
		commandProcessor.processCommand(createSavings);
		commandProcessor.processCommand(depositSavings);
		commandProcessor.processCommand(savingWithdraw);

		assertEquals(1000, bank.retrieveAccount("12345678").getBalance());
	}

	@Test
	void testWithDrawingFromSavingsTwice() {
		String createSavings = "Create savings 12345678 2";
		String depositSavings = "Deposit 12345678 2000";
		String savingWithdraw = "Withdraw 12345678 1000";
		String savingsWithdraw2 = "Withdraw 12345678 200";
		commandProcessor.processCommand(createSavings);
		commandProcessor.processCommand(depositSavings);
		commandProcessor.processCommand(savingWithdraw);
		commandProcessor.processCommand(savingsWithdraw2);

		assertEquals(800, bank.retrieveAccount("12345678").getBalance());
	}

	@Test
	void getTestWithDrawingFromChecking() {
		String createChecking = "Create checking 12345678 2";
		String depositChecking = "Deposit 12345678 2000";
		String checkingWithdraw = "Withdraw 12345678 1000";
		commandProcessor.processCommand(createChecking);
		commandProcessor.processCommand(depositChecking);
		commandProcessor.processCommand(checkingWithdraw);

		assertEquals(1000, bank.retrieveAccount("12345678").getBalance());
	}

	@Test
	void testWithdrawingFromCheckingTwice() {
		String createChecking = "Create checking 12345678 2";
		String depositChecking = "Deposit 12345678 2000";
		String checkingWithdraw = "Withdraw 12345678 1000";
		String checkingWithdraw2 = "Withdraw 12345678 200";
		commandProcessor.processCommand(createChecking);
		commandProcessor.processCommand(depositChecking);
		commandProcessor.processCommand(checkingWithdraw);
		commandProcessor.processCommand(checkingWithdraw2);

		assertEquals(800, bank.retrieveAccount("12345678").getBalance());
	}

	@Test
	void testWithdrawingFromCd() {
		String createCd = "Create cd 12345678 2000 2";
		String withdrawCd = "Withdraw 12345678 1000";
		commandProcessor.processCommand(createCd);

		assertThrows(UnsupportedOperationException.class, () -> {
			commandProcessor.processCommand(withdrawCd);
		});
	}

	@Test
	void testWithdrawFromCd2After12Months() {
		String createCd = "Create cd 12345678 2.1 2000";
		String passTime = "Pass 15";
		String withdrawCommand = "Withdraw 12345678 3000";

		commandProcessor.processCommand(createCd);

		commandProcessor.processCommand(passTime);
		commandProcessor.processCommand(withdrawCommand);

		assertEquals(0, bank.retrieveAccount("12345678").getBalance());

	}

	@Test
	void test_checking_account_getting_deleted_if_account_balance_gets_zero_each_month() {
		String checking = "Create checking 12345678 2.1";
		String deposit = "Deposit 12345678 1000";
		String pass_time = "Pass 1";
		String withdraw = "Withdraw 12345678 1000";

		commandProcessor.processCommand(checking);
		commandProcessor.processCommand(deposit);

		assertTrue(bank.accountExistByUniqueId("12345678"));

		commandProcessor.processCommand(withdraw);
		commandProcessor.processCommand(pass_time);

		assertFalse(bank.accountExistByUniqueId("12345678"));
	}

}
