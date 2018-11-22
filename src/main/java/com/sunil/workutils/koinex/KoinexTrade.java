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

	private void doTrade(String marketName, String coinOneName, double coinOneVolume, ArrayList<? extends Coin> coinsIn,
	    ArrayList<CoinInr> coinsInInr, double principalInr, ArrayList<Trade> trades) {
		if (coinOneName.equals(marketName)) {
			// Buy other coin with Market coin.
			for (Coin coin : coinsIn) {
				String coinTwoName = coin.getName();
				if (SUPPORTED_COIN_TO_TRADE.contains(coinTwoName)) {
					double coinTwoVolume = coinOneVolume / (realTimeFetch ? coin.getLowestAskPrice() : coin.getLastTradedPrice());
					// TODO(sunil) compute transaction fee.
					double soldInInr = coinTwoVolume * (realTimeFetch ? getHighestBidPriceOf(coinsInInr, coinTwoName)
					    : getLastTradePriceOf(coinsInInr, coinTwoName));
					double profit = soldInInr - principalInr;
					trades
					    .add(new Trade(principalInr, coinOneName, coinOneVolume, coinTwoName, coinTwoVolume, soldInInr, profit));
				}
			}
		} else {
			// Buy Market coin with other coin.
			for (Coin coin : coinsIn) {
				String name = coin.getName();
				if (name.equals(coinOneName)) {
					double priceInMarketCoin = realTimeFetch ? coin.getHighestBidPrice() : coin.getLastTradedPrice();
					double coinTwoVolume = coinOneVolume * priceInMarketCoin;
					// TODO(sunil) compute transaction fee.
					double soldInInr = coinTwoVolume * (realTimeFetch ? getHighestBidPriceOf(coinsInInr, marketName)
					    : getLastTradePriceOf(coinsInInr, marketName));
					double profit = soldInInr - principalInr;
					trades.add(new Trade(principalInr, coinOneName, coinOneVolume, marketName, coinTwoVolume, soldInInr, profit));
				}
			}
		}
	}

	public ArrayList<Trade> startTrade(double principalInr) {
		ArrayList<Trade> trades = new ArrayList<>();
		ArrayList<CoinInr> coinsInInr = koinexDataFetcher.getCoinInInr();
		for (CoinInr coinInr : coinsInInr) {
			String coinOneName = coinInr.getName();
			if (SUPPORTED_COIN_BUY_WITH_INR.contains(coinOneName)) {
				// buy in inr
				double coinPriceInInr = realTimeFetch ? coinInr.getLowestAskPrice() : coinInr.getLastTradedPrice();
				// TODO(sunil) Compute transaction fee.
				double coinOneVolume = principalInr / coinPriceInInr;

				// Go BTC market
				ArrayList<CoinBtc> coinsInBtc = koinexDataFetcher.getCoinInBtc();
				doTrade("BTC", coinOneName, coinOneVolume, coinsInBtc, coinsInInr, principalInr, trades);

				// Go ETH market.
				ArrayList<CoinEth> coinsInEth = koinexDataFetcher.getCoinInEth();
				doTrade("ETH", coinOneName, coinOneVolume, coinsInEth, coinsInInr, principalInr, trades);

				// Go XRP market.
				ArrayList<CoinXrp> coinsInXrp = koinexDataFetcher.getCoinInXrp();
				doTrade("XRP", coinOneName, coinOneVolume, coinsInXrp, coinsInInr, principalInr, trades);
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
