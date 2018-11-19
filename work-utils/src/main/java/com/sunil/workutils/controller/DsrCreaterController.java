package com.sunil.workutils.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DsrCreaterController {
	private DsrGenerator dsrGenerator = new DsrGenerator();

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model, @RequestParam("input_str") String input) {
		String mail = dsrGenerator.constructDsr(input);
		model.addAttribute("cr_input", input);
		model.addAttribute("mail", mail);
		return "dsrcreater.jsp";
	}
}
