package banking;

public class CertificateOfDeposit extends Account {

	public CertificateOfDeposit(String uniqueId, double apr, double suppliedBalance) {
		super(uniqueId, suppliedBalance, apr);
	}

	@Override
	public double getMaximumDepositAmount() {
		return 0;
	}

	public boolean isEligibleForWithdrawal() {
		return (getTime() > 12);
	}
}
