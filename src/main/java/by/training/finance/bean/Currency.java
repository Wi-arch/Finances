package by.training.finance.bean;

public enum Currency {

	BYN(1, 1, "Belarusian Ruble"), EUR(1, 2.3534, "Euro"), RUB(100, 3.4043, "Russian Ruble"),
	USD(1, 2.1036, "US Dollar");

	private Currency(int count, double rate, String name) {
		this.count = count;
		this.rate = rate;
		this.name = name;
	}

	private int count;
	private double rate;
	private String name;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
