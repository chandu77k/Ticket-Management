package com.questk2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.questk2.dto.TicketDTO;
import com.questk2.entity.Ticket;
import com.questk2.entity.TicketPriority;
import com.questk2.entity.TicketStatus;
import com.questk2.repository.TicketPriorityRepository;
import com.questk2.repository.TicketRepository;
import com.questk2.repository.TicketStatusRepository;
import com.questk2.repository.UserRepository;
import com.questk2.service.TicketOperationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller class for handling ticket-related operations.
 * Provides endpoints to fetch, create, update, and delete tickets.
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@Tag(name="Ticket Controller", description="APIs for controllers for getting tickets table,tickets priority table,ticket status table and posting tickets data and updating values in tickets and deleting ticket ")
public class TicketController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	TicketPriorityRepository ticketPriorityRepository;
	@Autowired
	TicketStatusRepository ticketStatusRepository;
	@Autowired
	TicketOperationService ticketOperationService;
	
	/**
     * Retrieves all tickets from the database.
     *
     * @return List of all tickets.
     */
	@Operation(summary = "Get mapping for tickets", description = "Get all details in tickets")
	@ApiResponse(responseCode = "200", description = "found", content = @Content(schema = @Schema(implementation = String.class)))
	@ApiResponse(responseCode = "404", description = "Method not found")
	@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
	@GetMapping("/tickets/{id}")
	public List<Ticket> getTickets(@PathVariable Long id){
		return ticketOperationService.getAllTickets(id);
	}
	
	/**
     * Retrieves all ticket priorities.
     *
     * @return List of ticket priorities.
     */
	@Operation(summary = "Get mapping for ticket priority", description = "Get all details in ticket priority")
	@ApiResponse(responseCode = "200", description = "found", content = @Content(schema = @Schema(implementation = String.class)))
	@ApiResponse(responseCode = "404", description = "Method not found")
	@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
	@GetMapping("/ticketPriority")
	public List<TicketPriority> getPriorities(){
		return ticketOperationService.getAllPriorities();
	}
	
	/**
     * Retrieves all ticket statuses.
     *
     * @return List of ticket statuses.
     */
	@Operation(summary = "Get mapping for ticket status", description = "Get all details in ticket status")
	@ApiResponse(responseCode = "200", description = "found", content = @Content(schema = @Schema(implementation = String.class)))
	@ApiResponse(responseCode = "404", description = "Method not found")
	@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
	@GetMapping("/ticketStatus")
	public List<TicketStatus> getStatus(){
		return ticketOperationService.getAllStatus();
	}
	
	/**
     * Creates a new ticket.
     *
     * @param ticketDTO The ticket data transfer object containing ticket details.
     */
	@Operation(summary = "POST mapping for tickets", description = "Post all details in ticket using DTO")
	@ApiResponse(responseCode = "200", description = "found", content = @Content(schema = @Schema(implementation = String.class)))
	@ApiResponse(responseCode = "404", description = "Method not found")
	@PostMapping("/tickets/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createTicket(@RequestBody TicketDTO ticketDTO) {
	    if (ticketDTO == null) {
	        throw new IllegalArgumentException("TicketDTO cannot be null");
	    }
	    ticketOperationService.createTicket(ticketDTO);
	}
	
	/**
     * Updates an existing ticket.
     *
     * @param id The ID of the ticket to update.
     * @param ticketDTO The updated ticket details.
     * @return The updated ticket object.
     */
	@Operation(summary = "Put mapping for tickets", description = "Update details in tickets using DTO")
	@ApiResponse(responseCode = "200", description = "found", content = @Content(schema = @Schema(implementation = String.class)))
	@ApiResponse(responseCode = "404", description = "Method not found")
	@PutMapping("/tickets/update/{id}")
	public Ticket updateTicket(@PathVariable Long id, @RequestBody TicketDTO ticketDTO) {
	    if (id == null || ticketDTO == null) {
	        throw new IllegalArgumentException("ID or TicketDTO cannot be null");
	    }
	    return ticketOperationService.updateTicket(id, ticketDTO);
	}

	
	/**
     * Deletes a ticket by ID.
     *
     * @param id The ID of the ticket to delete.
     */
	@Operation(summary = "delete mapping for tickets", description = "Delete the row using id")
	@ApiResponse(responseCode = "200", description = "found", content = @Content(schema = @Schema(implementation = String.class)))
	@ApiResponse(responseCode = "404", description = "Method not found")
	@PutMapping("/tickets/delete/{id}")
	public Ticket removeTicket(@PathVariable Long id, @RequestBody TicketDTO ticketDTO) {
	    return ticketOperationService.deleteTicket(id, ticketDTO);
	}
	
}
