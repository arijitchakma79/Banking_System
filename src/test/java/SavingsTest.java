import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingsTest {

	public static final int SUPPLIED_APR = 5;
	public static final String UNIQUE_ID = "60020001";
	Savings savings;

	@BeforeEach
	void setUp() {
		savings = new Savings(UNIQUE_ID, SUPPLIED_APR);
	}

	@Test
	void savings_account_created_with_zero_dollar_by_default() {
		double actual = savings.getBalance();
		assertEquals(0.0, actual);
	}
}
