package com.questk2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Main entry point for the Support Ticket Management System application.
 * This application allows users and admins to manage tickets.
 */
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Support Ticket Management System", version = "1.0", description = "This API manages the tickets from users and admin."))
public class TicketManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketManagementApplication.class, args);
	}

}
