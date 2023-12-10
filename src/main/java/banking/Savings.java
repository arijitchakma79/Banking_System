package banking;

public class Savings extends Account {
	public static final int MAX_WITHDRAWALS_AMOUNT = 1000;
	private static final int MAX_WITHDRAWALS_PER_MONTH = 1;
	private int withdrawalCount;

	public Savings(String uniqueId, double apr) {
		super(uniqueId, apr);
		this.withdrawalCount = 0;
	}

	@Override
	public void withdrawBalance(double amountToWithdraw) {
		super.withdrawBalance(amountToWithdraw);
		withdrawalCount++;
	}

	@Override
	public double getMaximumDepositAmount() {
		return 2500.00;
	}

	public int getWithdrawalCount() {
		return withdrawalCount;
	}

	public void resetWithdrawalCount() {
		withdrawalCount = 0;
	}

	public double getMaximumWithdrawalAmount() {
		return MAX_WITHDRAWALS_AMOUNT;
	}

	public int getMaxWithdrawalsPerMonth() {
		return MAX_WITHDRAWALS_PER_MONTH;
	}
}
