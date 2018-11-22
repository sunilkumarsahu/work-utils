package com.sunil.workutils.koinex.model;

public class Trade {
	private final double principalInr;
	private final String coinOneName;
	private final double coinOneVolume;
	private final String coinTwoName;
	private final double coinTwoVolume;
	private final double soldInInr;
	private final double profit;

	public Trade(double principalInr, String coinOneName, double coinOneVolume, String coinTwoName, double coinTwoVolume,
	    double soldInInr, double profit) {
		this.principalInr = principalInr;
		this.coinOneName = coinOneName;
		this.coinOneVolume = coinOneVolume;
		this.coinTwoName = coinTwoName;
		this.coinTwoVolume = coinTwoVolume;
		this.soldInInr = soldInInr;
		this.profit = profit;
	}

	public double getPrincipalInr() {
		return principalInr;
	}

	public String getCoinOneName() {
		return coinOneName;
	}

	public double getCoinOneVolume() {
		return coinOneVolume;
	}

	public String getCoinTwoName() {
		return coinTwoName;
	}

	public double getCoinTwoVolume() {
		return coinTwoVolume;
	}

	public double getSoldInInr() {
		return soldInInr;
	}

	public double getProfit() {
		return profit;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Profit: " + profit);
		sb.append("\n");
		sb.append("Principal: " + principalInr);
		sb.append("\n");
		sb.append("coin1: " + coinOneName + "  :  vol: " + coinOneVolume);
		sb.append("\n");
		sb.append("coin2: " + coinTwoName + "  :  vol: " + coinTwoVolume);
		sb.append("\n");
		sb.append("Sold price: " + soldInInr);
		return sb.toString();
	}

}
