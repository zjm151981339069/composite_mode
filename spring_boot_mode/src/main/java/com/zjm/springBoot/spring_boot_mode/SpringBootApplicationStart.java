package com.zjm.springBoot.spring_boot_mode;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import com.zjm.springBoot.spring_boot_mode.kafka.KafkaSender;

@SpringBootApplication
public class SpringBootApplicationStart {
   @Resource
   private KafkaSender kafkaSender;
	
	
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
	 //然后每隔1分钟执行一次
    @Scheduled(fixedRate = 1000)
    public void testKafka() throws Exception {
        kafkaSender.sendTest();
    }
}