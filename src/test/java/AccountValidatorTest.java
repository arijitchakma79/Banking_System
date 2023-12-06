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

	@Test
	void validate_create_cd_command_with_valid_data() {
		boolean actual = accountValidator.validate("create cd 12345678 0.6 1200");
		assertTrue(actual);
	}

	@Test
	void validate_create_cd_command_with_balance_one_thousand_point_one() {
		boolean actual = accountValidator.validate("create cd 12345678 0.6 1000.01");
		assertTrue(actual);
	}

	@Test
	void validate_create_cd_command_with_balance_one_thousand_point_zero_zero_two() {
		boolean actual = accountValidator.validate("create cd 12345678 0.6 1000.002");
		assertTrue(actual);
	}

	@Test
	void validate_create_cd_command_with_balance_nine_thousand_ninety_nine_point_nine_nine() {
		boolean actual = accountValidator.validate("create cd 12345678 0.6 9999.99");
		assertTrue(actual);
	}

	@Test
	void validate_create_cd_command_with_balance_maximum_value() {
		boolean actual = accountValidator.validate("create cd 12345678 0.6 10000");
		assertTrue(actual);
	}

	@Test
	void validate_create_cd_command_with_balance_exceeding_maximum_value() {
		boolean actual = accountValidator.validate("create cd 12345678 0.6 10000.01");
		assertFalse(actual);
	}

	@Test

	void validate_create_cd_command_with_balance_slightly_below_minimum_value() {
		boolean actual = accountValidator.validate("create cd 12345678 0.6 999.99");
		assertFalse(actual);
	}

	@Test
	void validate_create_cd_command_with_no_initial_balance() {
		boolean actual = accountValidator.validate("create cd 12345678 0.6");
		assertFalse(actual);
	}

	@Test
	void validate_create_cd_command_with_no_apr() {
		boolean actual = accountValidator.validate("create cd 12345678 1000");
		assertFalse(actual);
	}

	@Test
	void validate_create_cd_command_with_no_unique_id() {
		boolean actual = accountValidator.validate("create cd 0.6 1000");
		assertFalse(actual);
	}

	@Test
	void validate_create_cd_command_with_initial_balance_300() {
		boolean actual = accountValidator.validate("create cd 12345678 0.6 300");
		assertFalse(actual);
	}

	@Test
	void validate_create_cd_command_with_negative_initial_balance() {
		boolean actual = accountValidator.validate("create cd 12345678 0.6 -1300");
		assertFalse(actual);
	}

	@Test
	void validate_create_cd_command_with_string_initial_balance() {
		boolean actual = accountValidator.validate("create cd 12345678 0.6 'two_thousand'");
		assertFalse(actual);
	}

	@Test
	void validate_create_cd_command_with_string_apr_value() {
		boolean actual = accountValidator.validate("create cd 12345678 three 1000");
		assertFalse(actual);
	}

	@Test
	void validate_deposit_to_checking_account_with_valid_amount() {
		Account checking = new Checking("12345678", 0.01);
		bank.addAccount(checking);
		boolean actual = accountValidator.validate("deposit 12345678 500");
		assertTrue(actual);
	}

	@Test
	void validate_deposit_to_checking_account_with_exceeding_maximum_amount() {
		Account checking = new Checking("12345678", 0.01);
		bank.addAccount(checking);
		boolean actual = accountValidator.validate("deposit 12345678 1500");
		assertFalse(actual);
	}

	@Test
	void validate_deposit_to_checking_account_with_exceeding_boundaries_of_maximum_amount() {
		Account checking = new Checking("12345678", 0.01);
		bank.addAccount(checking);
		boolean actual = accountValidator.validate("deposit 12345678 1000.001");
		assertFalse(actual);
	}

	@Test
	void validate_deposit_to_checking_account_of_amount_zero() {
		Account checking = new Checking("12345678", 0.01);
		bank.addAccount(checking);
		boolean actual = accountValidator.validate("deposit 12345678 0");
		assertTrue(actual);
	}

	@Test
	void validate_deposit_to_checking_account_of_negative_amount() {
		Account checking = new Checking("12345678", 0.01);
		bank.addAccount(checking);
		boolean actual = accountValidator.validate("deposit 12345678 -200");
		assertFalse(actual);
	}

	@Test
	void validate_deposit_to_checking_account_of_string_amount() {
		Account checking = new Checking("12345678", 0.01);
		bank.addAccount(checking);
		boolean actual = accountValidator.validate("deposit 12345678 twenty");
		assertFalse(actual);
	}

	@Test
	void validate_deposit_to_checking_account_with_missing_unique_id() {
		Account checking = new Checking("12345678", 0.01);
		bank.addAccount(checking);
		boolean actual = accountValidator.validate("deposit 200");
		assertFalse(actual);
	}

	@Test
	void validate_deposit_to_checking_account_with_missing_deposited_amount() {
		Account checking = new Checking("12345678", 0.01);
		bank.addAccount(checking);
		boolean actual = accountValidator.validate("deposit 12345678");
		assertFalse(actual);
	}

	@Test
	void validate_deposit_to_savings_account_with_valid_amount() {
		Account savings = new Savings("98765432", 0.6);
		bank.addAccount(savings);
		boolean actual = accountValidator.validate("deposit 98765432 2000");
		assertTrue(actual);
	}

	@Test
	void validate_deposit_to_savings_account_with_exceeding_maximum_amount() {
		Account savings = new Savings("98765432", 0.6);
		bank.addAccount(savings);
		boolean actual = accountValidator.validate("deposit 98765432 3000");
		assertFalse(actual);
	}

	@Test
	void validate_deposit_to_savings_account_with_exceeding_boundaries_of_maximum_amount() {
		Account savings = new Savings("98765432", 0.6);
		bank.addAccount(savings);
		boolean actual = accountValidator.validate("deposit 98765432 2500.001");
		assertFalse(actual);
	}

	@Test
	void validate_deposit_to_savings_account_of_amount_zero() {
		Account savings = new Savings("98765432", 0.6);
		bank.addAccount(savings);
		boolean actual = accountValidator.validate("deposit 98765432 0");
		assertTrue(actual);
	}

	@Test
	void validate_deposit_to_savings_account_of_negative_amount() {
		Account savings = new Savings("98765432", 0.6);
		bank.addAccount(savings);
		boolean actual = accountValidator.validate("deposit 98765432 -500");
		assertFalse(actual);
	}

	@Test
	void validate_deposit_to_savings_account_of_string_amount() {
		Account savings = new Savings("98765432", 0.6);
		bank.addAccount(savings);
		boolean actual = accountValidator.validate("deposit 98765432 twenty");
		assertFalse(actual);
	}

	@Test
	void validate_deposit_to_savings_account_with_missing_unique_id() {
		Account savings = new Savings("98765432", 0.6);
		bank.addAccount(savings);
		boolean actual = accountValidator.validate("deposit 2000");
		assertFalse(actual);
	}

	@Test
	void validate_deposit_to_savings_account_with_missing_deposited_amount() {
		Account savings = new Savings("98765432", 0.6);
		bank.addAccount(savings);
		boolean actual = accountValidator.validate("deposit 98765432");
		assertFalse(actual);
	}

	@Test
	void validate_deposit_to_cd_accounts() {
		Account cd = new CertificateOfDeposit("12345678", 0.6, 1000);
		bank.addAccount(cd);
		boolean actual = accountValidator.validate("deposit 12345678 500");
		assertFalse(actual);

	}

}
