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
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new CommandStorage());
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
	void sample_test_given_to_us() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);
		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
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
		assertEquals("Cd 23456789 0.00 1.20", actual.get(0));
		assertEquals("withdraw 23456789 3000", actual.get(1));
	}

	@Test
	void create_and_deposit_to_cd() {
		input.add("Create cd 34567890 1.5 3000");
		input.add("Deposit 34567890 2000");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Cd 34567890 3000.00 1.50", actual.get(0));
		assertEquals("Deposit 34567890 2000", actual.get(1));
	}

	@Test
	void invalid_withdrawal_from_cd_before_maturity() {
		input.add("Create cd 45678901 2.0 4000");
		input.add("Pass 6");
		input.add("Withdraw 45678901 2000"); // Attempt to withdraw from CD before maturity

		List<String> actual = masterControl.start(input);
		System.out.println(actual);
		assertEquals(2, actual.size());
		assertEquals("Cd 45678901 4163.10 2.00", actual.get(0));
		assertEquals("Withdraw 45678901 2000", actual.get(1));
	}

	@Test
	void invalid_deposit_to_savings_after_max_withdrawals() {
		input.add("Create savings 56789012 0.8");
		input.add("Deposit 56789012 1000");
		input.add("Withdraw 56789012 100");
		input.add("Withdraw 56789012 200"); // Attempting to withdraw twice in same month
		List<String> actual = masterControl.start(input);
		assertEquals(4, actual.size());
		assertEquals("Savings 56789012 900.00 0.80", actual.get(0));
		assertEquals("Deposit 56789012 1000", actual.get(1));
		assertEquals("Withdraw 56789012 100", actual.get(2));
		assertEquals("Withdraw 56789012 200", actual.get(3));
	}

	@Test
	void valid_deposit_to_savings_after_max_withdrawals() {
		input.add("Create savings 56789012 0.8");
		input.add("Deposit 56789012 1000");
		input.add("Withdraw 56789012 100");
		input.add("Pass 1");
		input.add("Withdraw 56789012 200"); // Attempting to withdraw twice in same month
		List<String> actual = masterControl.start(input);
		System.out.println(actual);
		assertEquals(4, actual.size());
		assertEquals("Savings 56789012 700.60 0.80", actual.get(0));
		assertEquals("Deposit 56789012 1000", actual.get(1));
		assertEquals("Withdraw 56789012 100", actual.get(2));
		assertEquals("Withdraw 56789012 200", actual.get(3));
	}

	@Test
	void transfer_between_checking_and_savings() {
		input.add("Create checking 67890123 0.01");
		input.add("Create savings 78901234 0.5");
		input.add("Deposit 67890123 1000");
		input.add("Deposit 78901234 500");
		input.add("Transfer 67890123 78901234 300");
		List<String> actual = masterControl.start(input);
		System.out.println(actual);
		assertEquals(5, actual.size());
		assertEquals("Savings 78901234 800.00 0.50", actual.get(0));
		assertEquals("Deposit 78901234 500", actual.get(1));
		assertEquals("Transfer 67890123 78901234 300", actual.get(2));
		assertEquals("Checking 67890123 700.00 0.01", actual.get(3));
		assertEquals("Deposit 67890123 1000", actual.get(4));
	}

	@Test
	void create_and_withdraw_from_invalid_account() {
		input.add("Create savings 89012345 0.6");
		input.add("Withdraw 12345678 200"); // Attempt to withdraw from a non-existent account

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Savings 89012345 0.00 0.60", actual.get(0));
		assertEquals("Withdraw 12345678 200", actual.get(1));
	}

	@Test
	void create_and_pass_invalid_months() {
		input.add("Create checking 90123456 0.01");
		input.add("Pass abc"); // Attempt to pass invalid months

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Checking 90123456 0.00 0.01", actual.get(0));
		assertEquals("Pass abc", actual.get(1));
	}

	@Test
	void create_and_deposit_negative_amount() {
		input.add("Create savings 01234567 0.5");
		input.add("Deposit 01234567 -200"); // Attempt to deposit a negative amount

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Savings 01234567 0.00 0.50", actual.get(0));
		assertEquals("Deposit 01234567 -200", actual.get(1));
	}

	@Test
	void withdraw_from_empty_account() {
		input.add("Create checking 12341234 0.01");
		input.add("Withdraw 12341234 100"); // Attempt to withdraw from an account with zero balance

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Checking 12341234 0.00 0.01", actual.get(0));
		assertEquals("Withdraw 12341234 100", actual.get(1));
	}

	@Test
	void transfer_to_invalid_account() {
		input.add("Create savings 23452345 0.8");
		input.add("Transfer 23452345 34563456 200"); // Attempt to transfer to a non-existent account

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Savings 23452345 0.00 0.80", actual.get(0));
		assertEquals("Transfer 23452345 34563456 200", actual.get(1));
	}

	@Test
	void invalid_create_command() {
		input.add("Create account 34563456 0.6"); // Attempt to create an account with an invalid type

		List<String> actual = masterControl.start(input);

		assertEquals(1, actual.size());
		assertEquals("Create account 34563456 0.6", actual.get(0));
	}

	@Test
	void create_and_deposit_into_multiple_accounts() {
		input.add("Create SaVings 12345678 0.8");
		input.add("Deposit 12345678 500");
		input.add("Create checking 23456789 0.02");
		input.add("Deposit 23456789 200");
		input.add("Create cd 89018901 1.0 1500");
		input.add("Deposit 89018901 1000");

		List<String> actual = masterControl.start(input);
		assertEquals(6, actual.size());
		assertEquals("Savings 12345678 500.00 0.80", actual.get(0));
		assertEquals("Deposit 12345678 500", actual.get(1));
		assertEquals("Checking 23456789 200.00 0.02", actual.get(2));
		assertEquals("Deposit 23456789 200", actual.get(3));
		assertEquals("Cd 89018901 1500.00 1.00", actual.get(4));
		assertEquals("Deposit 89018901 1000", actual.get(5));
	}

	@Test
	void checking_case_insensitivity() {
		input.add("CrEatE SaVings 12345678 0.8");
		input.add("DePosit 12345678 500");
		input.add("CreAte ChecKinG 23456789 0.02");
		input.add("DepOSIt 23456789 200");
		input.add("CREATe CD 89018901 1.0 1500");
		input.add("Deposit 89018901 1000");
		input.add("WIthdraw 89018901 100");

		List<String> actual = masterControl.start(input);

		assertEquals(7, actual.size());
		assertEquals("Savings 12345678 500.00 0.80", actual.get(0));
		assertEquals("DePosit 12345678 500", actual.get(1));
		assertEquals("Checking 23456789 200.00 0.02", actual.get(2));
		assertEquals("DepOSIt 23456789 200", actual.get(3));
		assertEquals("Cd 89018901 1500.00 1.00", actual.get(4));
		assertEquals("Deposit 89018901 1000", actual.get(5));
		assertEquals("WIthdraw 89018901 100", actual.get(6));
	}

	@Test
	void checking_max_deposit_for_savings_and_checking() {
		input.add("CrEatE SaVings 12345678 0.8");
		input.add("Deposit 12345678 500");
		input.add("Deposit 12345678 3000");
		input.add("CreAte ChecKinG 23456789 0.02");
		input.add("Deposit 23456789 200");
		input.add("Deposit 23456789 1100");
		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Savings 12345678 500.00 0.80", actual.get(0));
		assertEquals("Deposit 12345678 500", actual.get(1));
		assertEquals("Checking 23456789 200.00 0.02", actual.get(2));
		assertEquals("Deposit 23456789 200", actual.get(3));
		assertEquals("Deposit 12345678 3000", actual.get(4));
		assertEquals("Deposit 23456789 1100", actual.get(5));

	}

	@Test
	void checking_max_withdrawal_for_checking_and_savings() {
		input.add("Create savings 12345678 0.8");
		input.add("Deposit 12345678 2000");
		input.add("Withdraw 12345678 1100");
		input.add("Create Checking 23456789 0.02");
		input.add("Deposit 23456789 1000");
		input.add("Withdraw 23456789 500");
		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Savings 12345678 2000.00 0.80", actual.get(0));
		assertEquals("Deposit 12345678 2000", actual.get(1));
		assertEquals("Checking 23456789 1000.00 0.02", actual.get(2));
		assertEquals("Deposit 23456789 1000", actual.get(3));
		assertEquals("Withdraw 12345678 1100", actual.get(4));
		assertEquals("Withdraw 23456789 500", actual.get(5));
	}

	@Test
	void checking_withdrawing_more_amount_than_balance() {
		input.add("Create savings 12345678 0.8");
		input.add("Deposit 12345678 800");
		input.add("Withdraw 12345678 900");
		List<String> actual = masterControl.start(input);
		assertEquals(3, actual.size());
		assertEquals("Savings 12345678 0.00 0.80", actual.get(0));
		assertEquals("Deposit 12345678 800", actual.get(1));
		assertEquals("Withdraw 12345678 900", actual.get(2));

	}

}
