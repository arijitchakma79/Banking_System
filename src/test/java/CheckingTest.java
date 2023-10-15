import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingTest {
	public static final int SUPPLIED_APR = 5;
	public static final int UNIQUE_ID = 60020001;
	Checking checking;

	@BeforeEach
	void setUp() {
		checking = new Checking(UNIQUE_ID, SUPPLIED_APR);
	}

	@Test
	void checking_account_created_with_zero_dollar_by_default() {
		double actual = checking.getBalance();
		assertEquals(0.0, actual);
	}
}
