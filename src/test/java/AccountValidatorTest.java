import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountValidatorTest {
	AccountValidator accountValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		accountValidator = new AccountValidator(bank);
	}

	@Test
	void valid_create_command() {
		boolean actual = accountValidator.validate("create savings 12345678 0.6");
		assertTrue(actual);
	}

	@Test
	void invalid_create_command_invalid_account_type() {
		boolean actual = accountValidator.validate("create invalid 1234567 0.6");
		assertFalse(actual);
	}

	@Test
	void invalid_create_command_invalid_id_length() {
		boolean actual = accountValidator.validate("create savings 1234567 0.6");
		assertFalse(actual);
	}

	@Test
	void invalid_create_command_duplicate_id() {
		Account savingsAccount = new Savings("12345678", 0.6);
		bank.addAccount(savingsAccount);
		boolean actual = accountValidator.validate("create savings 12345678 0.6");
		assertFalse(actual);
	}

	@Test
	void invalid_create_command_invalid_apr_format() {
		boolean actual = accountValidator.validate("create savings 12345678 invalid");
		assertFalse(actual);
	}

	@Test
	void invalid_create_command_invalid_apr_range() {
		boolean actual = accountValidator.validate("create savings 12345678 11");
		assertFalse(actual);
	}

	@Test
	void valid_create_cd_command() {
		boolean actual = accountValidator.validate("create cd 12345678 0.6 2000");
		assertTrue(actual);
	}

	@Test
	void invalid_create_cd_command_missing_amount() {
		boolean actual = accountValidator.validate("create cd 12345678 0.6");
		assertFalse(actual);
	}

	@Test
	void invalid_create_cd_command_invalid_amount_range() {
		boolean actual = accountValidator.validate("create cd 12345678 0.6 500");
		assertFalse(actual);
	}

	@Test
	void valid_deposit_command() {
		Account savingsAccount = new Savings("12345678", 0.6);
		bank.addAccount(savingsAccount);
		boolean actual = accountValidator.validate("deposit 12345678 500");
		assertTrue(actual);
	}

	@Test
	void invalid_deposit_command_account_not_found() {
		boolean actual = accountValidator.validate("deposit 12345678 500");
		assertFalse(actual);
	}

	@Test
	void invalid_deposit_command_invalid_amount() {
		Account savingsAccount = new Savings("12345678", 0.6);
		bank.addAccount(savingsAccount);
		boolean actual = accountValidator.validate("deposit 12345678 invalid");
		assertFalse(actual);
	}

	@Test
	void invalid_deposit_command_negative_amount() {
		Account savingsAccount = new Savings("12345678", 0.6);
		bank.addAccount(savingsAccount);
		boolean actual = accountValidator.validate("deposit 12345678 -500");
		assertFalse(actual);
	}

	@Test
	void invalid_deposit_command_exceeds_limit() {
		Account savingsAccount = new Savings("12345678", 0.6);
		bank.addAccount(savingsAccount);
		boolean actual = accountValidator.validate("deposit 12345678 3000");
		assertFalse(actual);
	}
}
