package com.emprendeStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // <--- Esto activa la capacidad de usar @Async
public class EmprendeStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmprendeStoreApplication.class, args);
	}

}
