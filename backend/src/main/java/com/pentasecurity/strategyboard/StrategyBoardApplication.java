package com.pentasecurity.strategyboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StrategyBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrategyBoardApplication.class, args);
	}

}
