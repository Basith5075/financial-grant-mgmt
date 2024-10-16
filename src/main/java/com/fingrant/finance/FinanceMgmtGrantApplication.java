package com.fingrant.finance;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class FinanceMgmtGrantApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceMgmtGrantApplication.class, args);
	}

}