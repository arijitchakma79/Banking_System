package banking;

public class Checking extends Account {

	public Checking(String uniqueId, double apr) {
		super(uniqueId, apr);
	}

	@Override
	public double getMaximumDepositAmount() {
		return 1000;
	}
}
