package com.app.naveen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("/ec2")
	public String hello() {
		return  "I am from Ec2";
	}

}
