package com.sunil.workutils.koinex.model;

public class Coin {
	private final String name;
	private final double lastTradedPrice;
	private final double highestBidPrice;
	private final double lowestAskPrice;

	public Coin(String name, double lastTradedPrice, double highestBidPrice, double lowestAskPrice) {
		this.name = name;
		this.lastTradedPrice = lastTradedPrice;
		this.highestBidPrice = highestBidPrice;
		this.lowestAskPrice = lowestAskPrice;
	}

	public String getName() {
		return name;
	}

	public double getLastTradedPrice() {
		return lastTradedPrice;
	}

	public double getHighestBidPrice() {
		return highestBidPrice;
	}

	public double getLowestAskPrice() {
		return lowestAskPrice;
	}

	@Override
	public String toString() {
		return name + "  :  LT: " + lastTradedPrice + "  :  HB: " + highestBidPrice + "  :  LA: " + lowestAskPrice;
	}
}