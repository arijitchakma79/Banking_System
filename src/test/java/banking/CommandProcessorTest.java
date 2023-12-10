package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		String command = "create cd 12345666 2.0 1200";
		commandProcessor.processCommand(command);
		assertTrue(bank.accountExistByUniqueId("12345666"));
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

}
