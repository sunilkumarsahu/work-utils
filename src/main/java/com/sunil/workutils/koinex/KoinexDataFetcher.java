package com.sunil.workutils.koinex;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sunil.workutils.koinex.model.CoinBtc;
import com.sunil.workutils.koinex.model.CoinEth;
import com.sunil.workutils.koinex.model.CoinInr;
import com.sunil.workutils.koinex.model.CoinXrp;

public class KoinexDataFetcher {
	private static final String PRICES_TAG = "prices";
	private static final String INR_TAG = "inr";
	private static final String BTC_TAG = "bitcoin";
	private static final String ETH_TAG = "ether";
	private static final String XRP_TAG = "ripple";

	private static final int VALUE_IN_INR = 1;
	private static final int VALUE_IN_BTC = 2;
	private static final int VALUE_IN_ETH = 3;
	private static final int VALUE_IN_XRP = 4;

	private ArrayList<CoinInr> coinInInr = new ArrayList<>();
	private ArrayList<CoinBtc> coinInBtc = new ArrayList<>();
	private ArrayList<CoinEth> coinInEth = new ArrayList<>();
	private ArrayList<CoinXrp> coinInXrp = new ArrayList<>();

	public KoinexDataFetcher() {
		try {
			parse(fetchDataFromWeb());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String fetchDataFromWeb() throws Exception {
		URL url = new URL("https://koinex.in/api/ticker");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String output;
		StringBuffer response = new StringBuffer();

		while ((output = in.readLine()) != null) {
			response.append(output);
		}
		in.close();

		return response.toString();
	}

	private void parse(String jsonStr) {
		JsonParser parser = new JsonParser();
		JsonObject root = parser.parse(jsonStr).getAsJsonObject();
		JsonObject prices = root.get(PRICES_TAG).getAsJsonObject();

		JsonObject priceInInr = prices.get(INR_TAG).getAsJsonObject();
		parsePriceWithType(priceInInr, VALUE_IN_INR);

		JsonObject priceInBtc = prices.get(BTC_TAG).getAsJsonObject();
		parsePriceWithType(priceInBtc, VALUE_IN_BTC);

		JsonObject priceInEth = prices.get(ETH_TAG).getAsJsonObject();
		parsePriceWithType(priceInEth, VALUE_IN_ETH);

		JsonObject priceInXrp = prices.get(XRP_TAG).getAsJsonObject();
		parsePriceWithType(priceInXrp, VALUE_IN_XRP);

	}

	private void parsePriceWithType(JsonObject priceInInr, int valueIn) {
		Set<Entry<String, JsonElement>> coins = priceInInr.entrySet();
		for (Entry<String, JsonElement> coin : coins) {
			if (valueIn == VALUE_IN_INR) {
				coinInInr.add(new CoinInr(coin.getKey(), coin.getValue().getAsDouble()));
			} else if (valueIn == VALUE_IN_BTC) {
				coinInBtc.add(new CoinBtc(coin.getKey(), coin.getValue().getAsDouble()));
			} else if (valueIn == VALUE_IN_ETH) {
				coinInEth.add(new CoinEth(coin.getKey(), coin.getValue().getAsDouble()));
			} else if (valueIn == VALUE_IN_XRP) {
				coinInXrp.add(new CoinXrp(coin.getKey(), coin.getValue().getAsDouble()));
			}
		}
	}

	public ArrayList<CoinInr> getCoinInInr() {
		return coinInInr;
	}

	public ArrayList<CoinBtc> getCoinInBtc() {
		return coinInBtc;
	}

	public ArrayList<CoinEth> getCoinInEth() {
		return coinInEth;
	}

	public ArrayList<CoinXrp> getCoinInXrp() {
		return coinInXrp;
	}
}
