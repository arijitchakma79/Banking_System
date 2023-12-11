package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	CommandValidator accountValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		accountValidator = new CommandValidator(bank);
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
		boolean actual = accountValidator.validate("deposit 12345678 700");
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

	@Test
	void validate_withdrawal_of_savings_account_with_valid_amount() {
		Account savings = new Savings("12345678", 2);
		bank.addAccount(savings);
		bank.depositAmount("12345678", 2000);
		boolean actual = accountValidator.validate("Withdraw 12345678 1000");
		assertTrue(actual);
	}

	@Test
	void validate_withdrawal_of_savings_account_with_more_than_maximum_amount() {
		Account savings = new Savings("12345678", 2);
		bank.addAccount(savings);
		bank.depositAmount("12345678", 2000);
		boolean actual = accountValidator.validate("Withdraw 12345678 1200");
		assertFalse(actual);
	}

	@Test
	void validate_withdrawal_of_negative_amount_from_savings_account() {
		Account savings = new Savings("12345678", 2);
		bank.addAccount(savings);
		bank.depositAmount("12345678", 2000);
		boolean actual = accountValidator.validate("Withdraw 12345678 -1000");
		assertFalse(actual);
	}

	@Test
	void validate_withdraw_of_string_amount_from_savings_account() {
		Account savings = new Savings("12345678", 2);
		bank.addAccount(savings);
		bank.depositAmount("12345678", 2000);
		boolean actual = accountValidator.validate("Withdraw 12345678 'five hundred'");
		assertFalse(actual);
	}

	@Test
	void validate_withdrawal_of_savings_account_with_values_slightly_exceeding_maximum_amount() {
		Account savings = new Savings("12345678", 2);
		bank.addAccount(savings);
		bank.depositAmount("12345678", 2000);
		boolean actual = accountValidator.validate("Withdraw 12345678 1000.001");
		assertFalse(actual);
	}

	@Test
	void validate_withdrawing_of_amount_zero_from_savings_account() {
		Account savings = new Savings("12345678", 2);
		bank.addAccount(savings);
		bank.depositAmount("12345678", 2000);
		boolean actual = accountValidator.validate("Withdraw 12345678 0");
		assertTrue(actual);
	}

	@Test
	void validate_withdrawing_of_amount_exceeding_the_savings_account_balance() {
		Account savings = new Savings("12345678", 2);
		bank.addAccount(savings);
		bank.depositAmount("12345678", 500);
		boolean actual = accountValidator.validate("Withdraw 12345678 800");
		assertTrue(actual);
	}

	@Test
	void validate_withdrawing_amount_from_savings_account_with_one_argument_missing() {
		Account savings = new Savings("12345678", 2);
		bank.addAccount(savings);
		bank.depositAmount("12345678", 500);
		boolean actual = accountValidator.validate("Withdraw 12345678");
		assertFalse(actual);
	}

	@Test
	void validate_withdrawing_amount_from_savings_account_with_one_additional_argument() {
		Account savings = new Savings("12345678", 2);
		bank.addAccount(savings);
		bank.depositAmount("12345678", 500);
		boolean actual = accountValidator.validate("Withdraw 12345678 800 0");
		assertFalse(actual);
	}

	@Test
	void validate_withdrawing_from_savings_with_case_insensitive_command() {
		Account savings = new Savings("12345678", 2);
		bank.addAccount(savings);
		bank.depositAmount("12345678", 500);
		boolean actual = accountValidator.validate("witHdRaW 12345678 200");
		assertTrue(actual);

	}

	@Test
	void validate_checking_account_with_valid_amount() {
		Account checking = new Checking("12345678", 2);
		bank.addAccount(checking);
		bank.depositAmount("12345678", 2000);
		boolean actual = accountValidator.validate("Withdraw 12345678 400");
		assertTrue(actual);
	}

	@Test
	void validate_checking_account_with_more_than_maximum_amount() {
		Account checking = new Checking("12345678", 2);
		bank.addAccount(checking);
		bank.depositAmount("12345678", 2000);
		boolean actual = accountValidator.validate("Withdraw 12345678 1200");
		assertFalse(actual);
	}

	@Test
	void validate_negative_checking_account_withdrawal() {
		Account checking = new Checking("12345678", 2);
		bank.addAccount(checking);
		bank.depositAmount("12345678", 2000);
		boolean actual = accountValidator.validate("Withdraw 12345678 -1000");
		assertFalse(actual);
	}

	@Test
	void validate_string_checking_account_withdrawal() {
		Account checking = new Checking("12345678", 2);
		bank.addAccount(checking);
		bank.depositAmount("12345678", 2000);
		boolean actual = accountValidator.validate("Withdraw 12345678 'five hundred'");
		assertFalse(actual);
	}

	@Test
	void validate_checking_account_withdrawal_slightly_exceeding_maximum_amount() {
		Account checking = new Checking("12345678", 2);
		bank.addAccount(checking);
		bank.depositAmount("12345678", 2000);
		boolean actual = accountValidator.validate("Withdraw 12345678 400.001");
		assertFalse(actual);
	}

	@Test
	void validate_checking_account_withdrawal_of_amount_zero() {
		Account checking = new Checking("12345678", 2);
		bank.addAccount(checking);
		bank.depositAmount("12345678", 2000);
		boolean actual = accountValidator.validate("Withdraw 12345678 0");
		assertTrue(actual);
	}

	@Test
	void validate_checking_account_withdrawal_exceeding_balance() {
		Account checking = new Checking("12345678", 2);
		bank.addAccount(checking);
		bank.depositAmount("12345678", 500);
		boolean actual = accountValidator.validate("Withdraw 12345678 800");
		assertFalse(actual);
	}

	@Test
	void validate_checking_account_with_one_argument_missing() {
		Account checking = new Checking("12345678", 2);
		bank.addAccount(checking);
		bank.depositAmount("12345678", 500);
		boolean actual = accountValidator.validate("Withdraw 12345678");
		assertFalse(actual);
	}

	@Test
	void validate_checking_account_with_one_additional_argument() {
		Account checking = new Checking("12345678", 2);
		bank.addAccount(checking);
		bank.depositAmount("12345678", 500);
		boolean actual = accountValidator.validate("Withdraw 12345678 200 00");
		assertFalse(actual);
	}

	@Test
	void validate_checking_account_with_case_insensitive_command() {
		Account checking = new Checking("12345678", 2);
		bank.addAccount(checking);
		bank.depositAmount("12345678", 500);
		boolean actual = accountValidator.validate("WitHdraW 12345678 200");
		assertTrue(actual);
	}

	@Test
	void validate_withdrawing_from_cd_account() {
		Account cd = new CertificateOfDeposit("12345678", 2, 2000);
		bank.addAccount(cd);
		boolean actual = accountValidator.validate("withdraw 12345678 2000");
		assertFalse(actual);
	}

	@Test
	void validate_withdrawing_less_amount_than_its_actual_balance_from_cd_account() {
		Account cd = new CertificateOfDeposit("12345678", 2, 2000);
		bank.addAccount(cd);
		bank.passTime(13);
		boolean actual = accountValidator.validate("Withdraw 12345678 1000");
		assertFalse(actual);
	}

	@Test
	void validate_withdrawing_negative_from_cd_account() {
		Account cd = new CertificateOfDeposit("12345678", 2, 2000);
		bank.addAccount(cd);
		bank.passTime(13);
		boolean actual = accountValidator.validate("Withdraw 12345678 -1000");
		assertFalse(actual);

	}

	@Test
	void validate_checking_if_cd_account_is_eligible_to_withdraw_after_12_months() {
		Account cd = new CertificateOfDeposit("12345678", 2, 2000);
		bank.addAccount(cd);
		bank.passTime(13);
		boolean isEligible = ((CertificateOfDeposit) cd).isEligibleForWithdrawal();
		assertTrue(isEligible);
	}

	@Test
	void validate_transfer_of_correct_amount() {
		Account saving = new Savings("12345677", 2);
		bank.addAccount(saving);
		Account checking = new Checking("12345678", 3);
		bank.addAccount(checking);
		boolean actual = accountValidator.validate("Transfer 12345677 12345678 400");
		assertTrue(actual);
	}

	@Test
	void validate_transfer_of_balance_from_non_existing_id_to_existing_id() {
		Account saving = new Savings("12345678", 3);
		bank.addAccount(saving);
		boolean actual = accountValidator.validate("Transfer 12345679 12345678 400");
		assertFalse(actual);
	}

	@Test
	void validate_transfer_of_balance_from_existing_id_to_non_existing_id() {
		Account saving = new Savings("12345678", 3);
		bank.addAccount(saving);
		boolean actual = accountValidator.validate("Transfer 12345678 12345676 400");
		assertFalse(actual);
	}

	@Test
	void validate_transfer_of_zero_amount() {
		Account saving = new Savings("12345677", 2);
		bank.addAccount(saving);
		Account checking = new Checking("12345678", 3);
		bank.addAccount(checking);
		boolean actual = accountValidator.validate("Transfer 12345677 12345678 0");
		assertTrue(actual);
	}

	@Test
	void validate_transfer_of_negative_amount() {
		Account saving = new Savings("12345677", 2);
		bank.addAccount(saving);
		Account checking = new Checking("12345678", 3);
		bank.addAccount(checking);
		boolean actual = accountValidator.validate("Transfer 12345677 12345678 -100");
		assertFalse(actual);
	}

	@Test
	void validate_transfer_from_cd_account() {
		Account cd = new CertificateOfDeposit("12345678", 2, 2000);
		bank.addAccount(cd);
		Account saving = new Savings("12345677", 2);
		bank.addAccount(saving);
		boolean actual = accountValidator.validate("Transfer 12345678 12345677 400");
		assertFalse(actual);
	}

	@Test
	void validate_transfer_to_cd_account() {
		Account cd = new CertificateOfDeposit("12345678", 2, 2000);
		bank.addAccount(cd);
		Account saving = new Savings("12345677", 2);
		bank.addAccount(saving);
		boolean actual = accountValidator.validate("Transfer 12345677 12345678 400");
		assertFalse(actual);
	}

	@Test
	void validate_transfer_of_amount_in_string() {
		Account cd = new CertificateOfDeposit("12345678", 2, 2000);
		bank.addAccount(cd);
		Account saving = new Savings("12345677", 2);
		bank.addAccount(saving);
		boolean actual = accountValidator.validate("Transfer 12345677 12345678 ten");
		assertFalse(actual);
	}

	@Test
	void validate_transfer_from_savings_to_checking_exceeding_savings_withdrawal_max_limit() {

		Account saving = new Savings("12345677", 2);
		bank.addAccount(saving);
		Account checking = new Checking("12345678", 3);
		bank.addAccount(checking);
		boolean actual = accountValidator.validate("Transfer 12345677 12345678 20000");
		assertFalse(actual);
	}

	@Test
	void validate_transfer_from_savings_to_checking_exceeding_savings_withdrawal_max_limit_boundaries_slightly() {

		Account saving = new Savings("12345677", 2);
		bank.addAccount(saving);
		Account checking = new Checking("12345678", 3);
		bank.addAccount(checking);
		boolean actual = accountValidator.validate("Transfer 12345677 12345678 1000.0001");
		assertFalse(actual);
	}

	@Test
	void validate_transfer_from_checking_to_saving_exceeding_checking_withdrawal_max_limit() {

		Account saving = new Savings("12345677", 2);
		bank.addAccount(saving);
		Account checking = new Checking("12345678", 3);
		bank.addAccount(checking);
		boolean actual = accountValidator.validate("Transfer 12345678 12345677 5000");
		assertFalse(actual);
	}

	@Test
	void validate_transfer_from_checking_to_saving_exceeding_checking_withdrawal_max_limit_boundaries_slightly() {

		Account saving = new Savings("12345677", 2);
		bank.addAccount(saving);
		Account checking = new Checking("12345678", 3);
		bank.addAccount(checking);
		boolean actual = accountValidator.validate("Transfer 12345678 12345677 400.000001");
		assertFalse(actual);
	}

	@Test
	void validate_pass_time_command_by_proving_right_values() {
		boolean actual = accountValidator.validate("Pass 1");
		assertTrue(actual);
	}

	@Test
	void validate_pass_time_command_by_giving_values_less_than_min_value() {
		boolean actual = accountValidator.validate("Pass 0");
		assertFalse(actual);
	}

	@Test
	void validate_pass_time_command_by_giving_values_more_than_max_value() {
		boolean actual = accountValidator.validate("Pass 61");
		assertFalse(actual);
	}

	@Test
	void validate_pass_time_by_providing_negative_input() {
		boolean actual = accountValidator.validate("Pass -1");
		assertFalse(actual);
	}

	@Test
	void validate_pass_time_by_providing_string_input() {
		boolean actual = accountValidator.validate("Pass ten");
		assertFalse(actual);
	}

	@Test
	void testing_case_insensitivity_for_checking() {
		boolean actual = accountValidator.validate("CreaTe cHeckIng 12345678 0.2");
		assertTrue(actual);
	}

	@Test
	void testing_case_insensitivity_for_savings() {
		boolean actual = accountValidator.validate("CreaTe SAvinGS 12345678 0.2");
		assertTrue(actual);
	}

	@Test
	void testing_case_insensitivity_for_cd() {
		boolean actual = accountValidator.validate("CreaTe cD 12345678 0.2 2000");
		assertTrue(actual);
	}

}
