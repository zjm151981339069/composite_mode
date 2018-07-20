package com.zjm.springBoot.spring_boot_mode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootApplicationStart {
	
	
	public static void main(String[] args) {
		System.err.println("****");
		SpringApplication.run(SpringBootApplicationStart.class, args);
		System.err.println("starting ****");
		try {
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}