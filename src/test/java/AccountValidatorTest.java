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
	void validate_create_savings_command() {
		boolean actual = accountValidator.validate("create savings 12345678 0.6");
		assertTrue(actual);
	}

	@Test
	void validate_create_savings_command_with_invalid_id_length() {
		boolean actual = accountValidator.validate("create savings 123 0.6");
		assertFalse(actual);
	}

	@Test
	void validate_create_savings_account_with_existing_id() {
		Account savings = new Savings("12345678", 0.6);
		bank.addAccount(savings);
		boolean actual = accountValidator.validate("create savings 12345678 0.6");
		assertFalse(actual);
	}

	@Test
	void validate_create_savings_account_with_additional_additional_balance() {
		boolean actual = accountValidator.validate("create savings 12345678 0.6 1000");
		assertFalse(actual);
	}

	@Test
	void validate_create_savings_account_with_no_uniqueId() {
		boolean actual = accountValidator.validate("create savings 0.6");
		assertFalse(actual);
	}

	@Test
	void validate_create_savings_account_with_apr_value_of_0() {
		boolean actual = accountValidator.validate("create savings 12345678 0");
		assertTrue(actual);
	}

	@Test
	void validate_create_savings_account_with_apr_value_of_zero_point_one() {
		boolean actual = accountValidator.validate("create savings 12345678 0.1");
		assertTrue(actual);
	}

	@Test
	void validate_create_savings_account_with_apr_value_of_zero_point_zero_one() {
		boolean actual = accountValidator.validate("create savings 12345678 0.01");
		assertTrue(actual);
	}

	@Test
	void validate_create_savings_account_with_apr_value_ten() {
		boolean actual = accountValidator.validate("create savings 12345678 10");
		assertTrue(actual);
	}

	@Test
	void validate_create_savings_account_with_apr_value_near_the_boundaries() {
		boolean actual = accountValidator.validate("create savings 12345678 9.999");
		assertTrue(actual);
	}

	@Test
	void validate_create_savings_account_with_apr_value_of_ten_point_zero_one() {
		boolean actual = accountValidator.validate("create savings 12345678 10.01");
		assertFalse(actual);
	}

	@Test
	void validate_create_savings_account_with_apr_negative_value() {
		boolean actual = accountValidator.validate("create savings 12345678 -2");
		assertFalse(actual);
	}

	@Test
	void validate_create_savings_account_with_apr_value_in_string() {
		boolean actual = accountValidator.validate("create savings 12345678 two");
		assertFalse(actual);
	}

	@Test
	void validate_create_checking_account_with_valid_data() {
		boolean actual = accountValidator.validate("create checking 12345678 0.01");
		assertTrue(actual);
	}

	@Test
	void validate_create_checking_account_with_invalid_id_length() {
		boolean actual = accountValidator.validate("create checking 123 0.01");
		assertFalse(actual);
	}

	@Test
	void validate_create_checking_account_with_existing_id() {
		Account checking = new Checking("12345678", 0.01);
		bank.addAccount(checking);
		boolean actual = accountValidator.validate("create checking 12345678 0.01");
		assertFalse(actual);
	}

	@Test
	void validate_create_checking_account_with_additional_balance() {
		boolean actual = accountValidator.validate("create checking 12345678 0.01 1000");
		assertFalse(actual);
	}

	@Test
	void validate_create_checking_account_with_no_unique_id() {
		boolean actual = accountValidator.validate("create checking 0.01");
		assertFalse(actual);
	}

	@Test
	void validate_create_checking_account_with_apr_value_of_0() {
		boolean actual = accountValidator.validate("create checking 12345678 0");
		assertTrue(actual);
	}

	@Test
	void validate_create_checking_account_with_apr_value_of_zero_point_one() {
		boolean actual = accountValidator.validate("create checking 12345678 0.1");
		assertTrue(actual);
	}

	@Test
	void validate_create_checking_account_with_apr_value_of_zero_point_zero_one() {
		boolean actual = accountValidator.validate("create checking 12345678 0.01");
		assertTrue(actual);
	}

	@Test
	void validate_create_checking_account_with_apr_value_ten() {
		boolean actual = accountValidator.validate("create checking 12345678 10");
		assertTrue(actual);
	}

	@Test
	void validate_create_checking_account_with_apr_value_near_the_boundaries() {
		boolean actual = accountValidator.validate("create checking 12345678 9.999");
		assertTrue(actual);
	}

	@Test
	void validate_create_checking_account_with_apr_value_of_ten_point_zero_one() {
		boolean actual = accountValidator.validate("create checking 12345678 10.01");
		assertFalse(actual);
	}

	@Test
	void validate_create_checking_account_with_apr_negative_value() {
		boolean actual = accountValidator.validate("create checking 12345678 -2");
		assertFalse(actual);
	}

	@Test
	void validate_create_checking_account_with_apr_value_in_string() {
		boolean actual = accountValidator.validate("create checking 12345678 two");
		assertFalse(actual);
	}

}
