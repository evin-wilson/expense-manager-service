package com.project.expensemanager;

import com.project.expensemanager.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpenseManagerApplication {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ExpenseManagerApplication.class, args);
	}

	@PostConstruct
	public void updateAllPasswords() {
		userService.encryptPasswordOnStartup();
	}

}
