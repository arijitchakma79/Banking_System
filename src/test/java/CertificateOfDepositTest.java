import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CertificateOfDepositTest {
	public static final int UNIQUE_ID = 60020001;
	CertificateOfDeposit certificateOfDeposit;

	@BeforeEach
	void setUp() {
		certificateOfDeposit = new CertificateOfDeposit(1000, 5, UNIQUE_ID);
	}

	@Test
	void cd_account_created_with_supplied_dollar() {
		double actual = certificateOfDeposit.getBalance();
		assertEquals(1000, actual);
	}
}
