package com.zjm.springBoot.spring_boot_mode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("index")
public class IndexController {
	@GetMapping("/ok")
	public @ResponseBody String index() {
		return "ok";
	}
}
