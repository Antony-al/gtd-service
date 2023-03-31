package com.aleshkin.gtd.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GtdServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GtdServiceApplication.class, args);
	}

}
