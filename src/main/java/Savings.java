public class Savings extends Account {

	public Savings(String uniqueId, double apr) {
		super(uniqueId, apr);
	}

	@Override
	public double getMaximumDepositAmount() {
		return 2500.00;
	}
}
