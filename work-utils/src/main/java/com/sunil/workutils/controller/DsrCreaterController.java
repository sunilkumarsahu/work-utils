package com.sunil.workutils.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DsrCreaterController {
	private DsrGenerator dsrGenerator = new DsrGenerator();

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@RequestParam("input_str") String input, @RequestParam("output_str") String output) {
		String mail = dsrGenerator.constructDsr(input);
		System.out.println(mail);
		return "dsrcreater.jsp";
	}
}
