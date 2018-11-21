package com.sunil.workutils.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sunil.workutils.koinex.KoinexTrade;
import com.sunil.workutils.koinex.model.Trade;

@Controller
public class KoinexTradeController {

	@RequestMapping(value = "/trade", method = RequestMethod.POST)
	public String create(Model model, @RequestParam("principal_amount") String input) {
		KoinexTrade koinexTrade = new KoinexTrade();
		double principalInr = Double.parseDouble(input);
		ArrayList<Trade> trades = koinexTrade.startTrade(principalInr);
		model.addAttribute("principal", input);
		model.addAttribute("trades", trades);
		return "koinex_trade.jsp";
	}

}
