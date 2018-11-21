package com.sunil.workutils.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sunil.workutils.koinex.KoinexTrade;

@Controller
public class KoinexTradeController {

	@RequestMapping(value = "/trade", method = RequestMethod.POST)
	public String create(Model model, @RequestParam("principal_amount") String input) {
		KoinexTrade koinexTrade = new KoinexTrade();
		double principalInr = Double.parseDouble(input);
		koinexTrade.startTrade(principalInr);
		model.addAttribute("principal", input);
		System.out.println("################## TRADE######################## " + input);
		return "koinex_trade.jsp";
	}

}
