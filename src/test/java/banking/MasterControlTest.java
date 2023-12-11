package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {
	MasterControl masterControl;
	ArrayList<String> input;

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		Bank bank = new Bank();
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new CommandStorage(),
				bank);
	}

	private void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

	@Test
	void typo_in_create_commands_is_invalid() {
		input.add("creat checking 12345678 1.0");

		List<String> actual = masterControl.start(input);

		assertSingleCommand("creat checking 12345678 1.0", actual);

	}

	@Test
	void typo_in_deposit_command_is_invalid() {
		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);

		assertSingleCommand("depositt 12345678 100", actual);
	}

	@Test

	void two_typo_commands_both_invalid() {
		input.add("creat checking 12345678 1.0");
		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);
		System.out.println(actual);
		assertEquals(2, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
		assertEquals("depositt 12345678 100", actual.get(1));
	}

	@Test
	void ene() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("Create checking 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);
		System.out.println(actual);
		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("CD 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}

	@Test
	void withdraw_from_savings() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 1000");
		input.add("Withdraw 12345678 300");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Savings 12345678 700.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 1000", actual.get(1));
		assertEquals("Withdraw 12345678 300", actual.get(2));
	}

	@Test
	void invalid_transfer_due_to_insufficient_balance() {
		input.add("Create checking 98765432 0.01");
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 400"); // Attempt to transfer more than available balance

		List<String> actual = masterControl.start(input);
		assertEquals(4, actual.size());
		assertEquals("Checking 98765432 300.00 0.01", actual.get(0));
		assertEquals("Deposit 98765432 300", actual.get(1));
		assertEquals("Savings 12345678 0.00 0.60", actual.get(2));
		assertEquals("Transfer 98765432 12345678 400", actual.get(3));

	}

	@Test
	void pass_multiple_months_and_check_cd_eligibility() {
		input.add("Create cd 23456789 1.2 2000");
		input.add("Pass 16");
		input.add("withdraw 23456789 3000");
		List<String> actual = masterControl.start(input);

		System.out.println(actual);
		assertEquals(2, actual.size());
		assertEquals("CD 23456789 2132.11 1.20", actual.get(0));
		assertEquals("withdraw 23456789 3000", actual.get(1));
	}

}
