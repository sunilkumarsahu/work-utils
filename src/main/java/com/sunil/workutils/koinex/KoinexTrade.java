package com.sunil.workutils.koinex;

import java.util.ArrayList;
import java.util.Comparator;

import com.sunil.workutils.koinex.model.Coin;
import com.sunil.workutils.koinex.model.CoinBtc;
import com.sunil.workutils.koinex.model.CoinEth;
import com.sunil.workutils.koinex.model.CoinInr;
import com.sunil.workutils.koinex.model.CoinXrp;
import com.sunil.workutils.koinex.model.Trade;

public class KoinexTrade {
	private boolean realTimeFetch = true;

	// Add only the coins can be trade with other coins.
	private static final ArrayList<String> SUPPORTED_COIN_BUY_WITH_INR = new ArrayList<String>() {
		{
			add("BTC");
			add("ETH");
			add("XRP");
		}
	};

	// Add only the coins can to be sold with INR to gain profit.
	private static final ArrayList<String> SUPPORTED_COIN_TO_TRADE = new ArrayList<String>() {
		{
			add("BTC");
			add("ETH");
			add("XRP");
		}
	};

	private final KoinexDataFetcher koinexDataFetcher;

	public KoinexTrade() {
		koinexDataFetcher = new KoinexDataFetcher();
	}

	private double getLastTradePriceOf(ArrayList<? extends Coin> coins, String coinType) {
		for (Coin coin : coins) {
			if (coin.getName().equals(coinType)) {
				return coin.getLastTradedPrice();
			}
		}
		throw new RuntimeException("No price available for the coin " + coinType);
	}

	private double getHighestBidPriceOf(ArrayList<? extends Coin> coins, String coinType) {
		for (Coin coin : coins) {
			if (coin.getName().equals(coinType)) {
				return coin.getHighestBidPrice();
			}
		}
		throw new RuntimeException("No highest bid available for the coin " + coinType);
	}

	private double getLowestAskPriceOf(ArrayList<? extends Coin> coins, String coinType) {
		for (Coin coin : coins) {
			if (coin.getName().equals(coinType)) {
				return coin.getLowestAskPrice();
			}
		}
		throw new RuntimeException("No highest bid available for the coin " + coinType);
	}

	public ArrayList<Trade> startTrade(double principalInr) {
		ArrayList<Trade> trades = new ArrayList<>();
		ArrayList<CoinInr> coinsInInr = koinexDataFetcher.getCoinInInr();
		for (CoinInr coinInr : coinsInInr) {
			String coinOneName = coinInr.getName();
			if (SUPPORTED_COIN_BUY_WITH_INR.contains(coinOneName)) {
				// buy in inr
				double coinPriceInInr = realTimeFetch ? coinInr.getLowestAskPrice() : coinInr.getLastTradedPrice();
				double coinOneVolume = principalInr / coinPriceInInr;

				// Go BTC market
				ArrayList<CoinBtc> coinsInBtc = koinexDataFetcher.getCoinInBtc();
				if (coinOneName.equals("BTC")) {
					// Buy other coin with BTC.
					for (CoinBtc coinInBtc : coinsInBtc) {
						String coinTwoName = coinInBtc.getName();
						if (SUPPORTED_COIN_TO_TRADE.contains(coinTwoName)) {
							double coinTwoVolume = coinOneVolume
									/ (realTimeFetch ? coinInBtc.getLowestAskPrice() : coinInBtc.getLastTradedPrice());
							double soldInInr = coinTwoVolume
									* (realTimeFetch ? getHighestBidPriceOf(coinsInInr, coinTwoName)
											: getLastTradePriceOf(coinsInInr, coinTwoName));
							double profit = soldInInr - principalInr;
							trades.add(new Trade(principalInr, coinOneName, coinOneVolume, coinTwoName, coinTwoVolume,
									soldInInr, profit));
						}
					}
				} else {
					// Buy BTC with other coin.
					for (CoinBtc coinInBtc : coinsInBtc) {
						String name = coinInBtc.getName();
						if (name.equals(coinOneName)) {
							double priceInBtc = realTimeFetch ? coinInBtc.getHighestBidPrice()
									: coinInBtc.getLastTradedPrice();
							double coinTwoVolume = coinOneVolume * priceInBtc;
							double soldInInr = coinTwoVolume * (realTimeFetch ? getHighestBidPriceOf(coinsInInr, "BTC")
									: getLastTradePriceOf(coinsInInr, "BTC"));
							double profit = soldInInr - principalInr;
							trades.add(new Trade(principalInr, coinOneName, coinOneVolume, "BTC", coinTwoVolume,
									soldInInr, profit));
						}
					}
				}

				// Go ETH market.
				ArrayList<CoinEth> coinsInEth = koinexDataFetcher.getCoinInEth();
				if (coinOneName.equals("ETH")) {
					// Buy other coin with ETH.
					for (CoinEth coinInEth : coinsInEth) {
						String coinTwoName = coinInEth.getName();
						if (SUPPORTED_COIN_TO_TRADE.contains(coinTwoName)) {
							double coinTwoVolume = coinOneVolume
									/ (realTimeFetch ? coinInEth.getLowestAskPrice() : coinInEth.getLastTradedPrice());
							double soldInInr = coinTwoVolume
									* (realTimeFetch ? getHighestBidPriceOf(coinsInInr, coinTwoName)
											: getLastTradePriceOf(coinsInInr, coinTwoName));
							double profit = soldInInr - principalInr;
							trades.add(new Trade(principalInr, coinOneName, coinOneVolume, coinTwoName, coinTwoVolume,
									soldInInr, profit));
						}
					}
				} else {
					// Buy ETH with other coin.
					for (CoinEth coinInEth : coinsInEth) {
						String name = coinInEth.getName();
						if (name.equals(coinOneName)) {
							double priceInEth = realTimeFetch ? coinInEth.getHighestBidPrice()
									: coinInEth.getLastTradedPrice();
							;
							double coinTwoVolume = coinOneVolume * priceInEth;
							double soldInInr = coinTwoVolume * (realTimeFetch ? getHighestBidPriceOf(coinsInInr, "ETH")
									: getLastTradePriceOf(coinsInInr, "ETH"));
							double profit = soldInInr - principalInr;
							trades.add(new Trade(principalInr, coinOneName, coinOneVolume, "ETH", coinTwoVolume,
									soldInInr, profit));
						}
					}
				}

				// Go XRP market.
				ArrayList<CoinXrp> coinsInXrp = koinexDataFetcher.getCoinInXrp();
				if (coinOneName.equals("XRP")) {
					// Buy other coin with XRP.
					for (CoinXrp coinInXrp : coinsInXrp) {
						String coinTwoName = coinInXrp.getName();
						if (SUPPORTED_COIN_TO_TRADE.contains(coinTwoName)) {
							double coinTwoVolume = coinOneVolume
									/ (realTimeFetch ? coinInXrp.getLowestAskPrice() : coinInXrp.getLastTradedPrice());
							double soldInInr = coinTwoVolume
									* (realTimeFetch ? getHighestBidPriceOf(coinsInInr, coinTwoName)
											: getLastTradePriceOf(coinsInInr, coinTwoName));
							double profit = soldInInr - principalInr;
							trades.add(new Trade(principalInr, coinOneName, coinOneVolume, coinTwoName, coinTwoVolume,
									soldInInr, profit));
						}
					}
				} else {
					// Buy XRP with other coin.
					for (CoinXrp coinInXrp : coinsInXrp) {
						String name = coinInXrp.getName();
						if (name.equals(coinOneName)) {
							double priceInXrp = realTimeFetch ? coinInXrp.getHighestBidPrice()
									: coinInXrp.getLastTradedPrice();
							double coinTwoVolume = coinOneVolume * priceInXrp;
							double soldInInr = coinTwoVolume * (realTimeFetch ? getHighestBidPriceOf(coinsInInr, "XRP")
									: getLastTradePriceOf(coinsInInr, "XRP"));
							double profit = soldInInr - principalInr;
							trades.add(new Trade(principalInr, coinOneName, coinOneVolume, "XRP", coinTwoVolume,
									soldInInr, profit));
						}
					}
				}
			}
		}

		trades.sort(new Comparator<Trade>() {
			@Override
			public int compare(Trade o1, Trade o2) {
				return (int) (o2.getProfit() - o1.getProfit());
			}
		});
		return trades;
	}

	public boolean isRealTimeFetch() {
		return realTimeFetch;
	}

	public void setRealTimeFetch(boolean realTimeFetch) {
		this.realTimeFetch = realTimeFetch;
	}
}
