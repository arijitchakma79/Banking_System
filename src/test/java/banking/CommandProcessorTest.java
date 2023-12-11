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
	void test_create_checking_account() {
		String command = "create checking 12345678 2.0";
		commandProcessor.processCommand(command);

		assertTrue(bank.accountExistByUniqueId("12345678"));
	}

	@Test
	void test_create_saving_account() {
		String command = "create savings 12345677 2.0";
		commandProcessor.processCommand(command);

		assertTrue(bank.accountExistByUniqueId("12345677"));
	}

	@Test
	void test_create_certificate_of_deposit() {
		String command = "create cd 12345678 2.0 2000";
		commandProcessor.processCommand(command);
		assertTrue(bank.accountExistByUniqueId("12345678"));
	}

	@Test
	void test_deposit_to_newly_created_checking() {
		String createChecking = "create checking 12345678 0.8";
		String depositCommand = "deposit 12345678 100";
		commandProcessor.processCommand(createChecking);
		commandProcessor.processCommand(depositCommand);

		assertEquals(100, bank.retrieveAccount("12345678").getBalance(), 0.01);
	}

	@Test
	void test_deposit_to_existing_checking() {
		String createChecking = "create checking 12345678 0.8";
		String depositCommand = "deposit 12345678 100";
		String depositAgain = "deposit 12345678 100";
		commandProcessor.processCommand(createChecking);
		commandProcessor.processCommand(depositCommand);
		commandProcessor.processCommand(depositAgain);

		assertEquals(200, bank.retrieveAccount("12345678").getBalance(), 0.01);
	}

	@Test
	void test_deposit_to_newly_created_savings() {
		String createChecking = "create savings 12345678 0.8";
		String depositCommand = "deposit 12345678 100";
		commandProcessor.processCommand(createChecking);
		commandProcessor.processCommand(depositCommand);

		assertEquals(100, bank.retrieveAccount("12345678").getBalance(), 0.01);
	}

	@Test
	void test_deposit_to_existing_savings() {
		String createChecking = "create savings 12345678 0.8";
		String depositCommand = "deposit 12345678 100";
		String depositAgain = "deposit 12345678 100";
		commandProcessor.processCommand(createChecking);
		commandProcessor.processCommand(depositCommand);
		commandProcessor.processCommand(depositAgain);

		assertEquals(200, bank.retrieveAccount("12345678").getBalance(), 0.01);
	}

	@Test
	void test_deposit_to_cd_account() {
		String createCd = "create cd 12345678 2000 2";
		String depositCommand = "deposit 12345678 300";
		commandProcessor.processCommand(createCd);
		assertThrows(UnsupportedOperationException.class, () -> {
			commandProcessor.processCommand(depositCommand);
		});
	}

	@Test
	void test_withdraw_from_savings() {
		String createSavings = "Create savings 12345678 2";
		String depositSavings = "Deposit 12345678 2000";
		String savingWithdraw = "Withdraw 12345678 1000";
		commandProcessor.processCommand(createSavings);
		commandProcessor.processCommand(depositSavings);
		commandProcessor.processCommand(savingWithdraw);

		assertEquals(1000, bank.retrieveAccount("12345678").getBalance());
	}

	@Test
	void test_withdrawing_from_savings_twice() {
		String createSavings = "Create savings 12345678 2";
		String depositSavings = "Deposit 12345678 2000";
		String savingWithdraw = "Withdraw 12345678 1000";
		String savingsWithdraw2 = "Withdraw 12345678 200";

		commandProcessor.processCommand(createSavings);
		commandProcessor.processCommand(depositSavings);
		commandProcessor.processCommand(savingWithdraw);

		// Check the first withdrawal
		assertEquals(1000, bank.retrieveAccount("12345678").getBalance());

		// Now try to withdraw a second time in the same month
		Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
			commandProcessor.processCommand(savingsWithdraw2);
		});

		// Check the error message or other details as needed
		assertEquals("Cannot withdraw more than once in a month for Savings account.", exception.getMessage());

		// Ensure that the balance remains unchanged after the second withdrawal attempt
		assertEquals(1000, bank.retrieveAccount("12345678").getBalance());
	}

	@Test
	void testing_withdrawing_twice_after_passing_months() {
		String createSavings = "Create savings 12345678 2";
		String depositSavings = "Deposit 12345678 2000";
		String savingWithdraw = "Withdraw 12345678 1000";
		String passTime = "Pass 1";
		String savingsWithdraw2 = "Withdraw 12345678 200";
		commandProcessor.processCommand(createSavings);
		commandProcessor.processCommand(depositSavings);
		commandProcessor.processCommand(savingWithdraw);
		commandProcessor.processCommand(passTime);
		commandProcessor.processCommand(savingsWithdraw2);
		assertEquals(801.6666666666666, bank.retrieveAccount("12345678").getBalance());

	}

	@Test
	void testing_withdrawing_thrice_after_passing_months() {
		String createSavings = "Create savings 12345678 2";
		String depositSavings = "Deposit 12345678 2000";
		String savingWithdraw = "Withdraw 12345678 1000";
		String passTime = "Pass 1";
		String savingsWithdraw2 = "Withdraw 12345678 200";
		String savingsWithdraw3 = "Withdraw 12345678 300";
		commandProcessor.processCommand(createSavings);
		commandProcessor.processCommand(depositSavings);
		commandProcessor.processCommand(savingWithdraw);
		commandProcessor.processCommand(passTime);
		commandProcessor.processCommand(savingsWithdraw2);
		commandProcessor.processCommand(passTime);
		commandProcessor.processCommand(savingsWithdraw3);
		assertEquals(503.00277777777774, bank.retrieveAccount("12345678").getBalance());

	}

	@Test
	void get_test_withdrawing_from_checking() {
		String createChecking = "Create checking 12345678 2";
		String depositChecking = "Deposit 12345678 2000";
		String checkingWithdraw = "Withdraw 12345678 1000";
		commandProcessor.processCommand(createChecking);
		commandProcessor.processCommand(depositChecking);
		commandProcessor.processCommand(checkingWithdraw);

		assertEquals(1000, bank.retrieveAccount("12345678").getBalance());
	}

	@Test
	void test_withdrawing_from_checking_twice() {
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
	void test_with_drawing_from_cd() {
		String createCd = "Create cd 12345678 2000 2";
		String withdrawCd = "Withdraw 12345678 1000";
		commandProcessor.processCommand(createCd);

		assertThrows(UnsupportedOperationException.class, () -> {
			commandProcessor.processCommand(withdrawCd);
		});
	}

	@Test
	void test_withdraw_from_cd_2_after_12_months() {
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

	@Test
	void test_pass_time_command_working_successfully() {
		String checking = "Create checking 12345678 2.1";
		String deposit = "Deposit 12345678 1000";
		String passTime = "Pass 16";

		commandProcessor.processCommand(checking);
		commandProcessor.processCommand(deposit);
		commandProcessor.processCommand(passTime);

		assertEquals(16, bank.retrieveAccount("12345678").getTime());
	}

	@Test
	void test_transfer_command() {
		String checking = "Create checking 12345678 2.1";
		String depositChecking = "Deposit 12345678 1000";
		String saving = "Create savings 12345677 3";
		String depositSavings = "Deposit 12345677 2000";
		String transferCommand = "Transfer 12345677 12345678 800";

		commandProcessor.processCommand(checking);
		commandProcessor.processCommand(depositChecking);
		commandProcessor.processCommand(saving);
		commandProcessor.processCommand(depositSavings);
		commandProcessor.processCommand(transferCommand);
		assertEquals(1200, bank.retrieveAccount("12345677").getBalance());
		assertEquals(1800, bank.retrieveAccount("12345678").getBalance());
	}

	@Test
	void ene() {
		String saving = "Create savings 12345678 0.6";
		String deposit = "Deposit 12345678 700";
		String createChecking = "create checking 98765432 0.01";
		String depositChecking = "Deposit 98765432 300";
		String transfer = "Transfer 98765432 12345678 300";
		String passTime = "Pass 1";
		String createCD = "Create cd 23456789 1.2 2000";

		commandProcessor.processCommand(saving);
		commandProcessor.processCommand(deposit);
		commandProcessor.processCommand(createChecking);
		commandProcessor.processCommand(depositChecking);
		System.out.println("Saving before transfer: " + bank.retrieveAccount("12345678").getBalance());
		System.out.println("Checking before transfer: " + bank.retrieveAccount("98765432").getBalance());
		commandProcessor.processCommand(transfer);
		System.out.println("Saving after transfer: " + bank.retrieveAccount("12345678").getBalance());
		System.out.println("Checking after transfer: " + bank.retrieveAccount("98765432").getBalance());
		commandProcessor.processCommand(passTime);

		System.out.println(bank.retrieveAccount("12345678").getTime());
		System.out.println(bank.retrieveAccount("12345678").getBalance());
		System.out.println(bank.accountExistByUniqueId("12345678"));
		System.out.println(bank.accountExistByUniqueId("98765432"));
	}

}
