package com.questk2.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.questk2.entity.User;
import com.questk2.repository.TicketPriorityRepository;
import com.questk2.repository.TicketRepository;
import com.questk2.repository.TicketStatusRepository;
import com.questk2.repository.UserRepository;

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
	
	/**
     * Retrieves all tickets from the database.
     *
     * @return List of all tickets.
     */
	@Operation(summary = "Get mapping for tickets", description = "Get all details in tickets")
	@ApiResponse(responseCode = "200", description = "found", content = @Content(schema = @Schema(implementation = String.class)))
	@ApiResponse(responseCode = "404", description = "Method not found")
	@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
	@GetMapping("/tickets")
	public List<Ticket> getAllTickets(){
		List<Ticket> tickets = ticketRepository.findAll();
		return tickets;
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
	public List<TicketPriority> getAllPriorities(){
		List<TicketPriority> priorities = ticketPriorityRepository.findAll();
		return priorities;
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
	public List<TicketStatus> getAllStatus(){
		List<TicketStatus> status = ticketStatusRepository.findAll();
		return status;
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
		TicketPriority priority = ticketPriorityRepository.findById(ticketDTO.getPriority()).orElseThrow(()-> new RuntimeException("Priority not found"));
		TicketStatus status = ticketStatusRepository.findById(ticketDTO.getStatus()).orElseThrow(()-> new RuntimeException("Status not found"));
		User createdBy = userRepository.findById(ticketDTO.getCreatedBy()).orElseThrow(()-> new RuntimeException("User not found"));
		User assignedTo = userRepository.findById(ticketDTO.getAssignedTo()).orElseThrow(()-> new RuntimeException("User not found"));
		
		Ticket ticket = new Ticket();
		ticket.setTitle(ticketDTO.getTitle());
		ticket.setDescription(ticketDTO.getDescription());
		ticket.setPriority(priority);
		ticket.setStatus(status);
		ticket.setCreatedBy(createdBy);
		ticket.setAssignedTo(assignedTo);
		ticket.setTicketComment(ticketDTO.getTicketComment());
		ticketRepository.save(ticket);
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
	public Ticket updateTicket(@PathVariable Long id,@RequestBody TicketDTO ticketDTO) {
		return ticketRepository.findById(id).map(ticket -> {
			TicketPriority priority = ticketPriorityRepository.findById(ticketDTO.getPriority()).orElseThrow(()->new RuntimeException("Priority not found"));
			TicketStatus status = ticketStatusRepository.findById(ticketDTO.getStatus()).orElseThrow(()-> new RuntimeException("Status not found"));
			User createdBy = userRepository.findById(ticketDTO.getCreatedBy()).orElseThrow(()-> new RuntimeException("User not found"));
			User assignedTo = userRepository.findById(ticketDTO.getAssignedTo()).orElseThrow(()-> new RuntimeException("User not found"));
			
			ticket.setTitle(ticketDTO.getTitle());
			ticket.setDescription(ticketDTO.getDescription());
			ticket.setPriority(priority);
			ticket.setStatus(status);
			ticket.setCreatedBy(createdBy);
			ticket.setAssignedTo(assignedTo);
			ticket.setTicketComment(ticketDTO.getTicketComment());
			ticket.setModifiedDate(LocalDateTime.now());
			ticket.setDeleted(ticketDTO.isDeleted());
			if(ticketDTO.isDeleted()) {
				ticket.setDeleteDate(LocalDateTime.now());
			}else {
				ticket.setDeleteDate(null);
			}
			return ticketRepository.save(ticket);
		}).orElseThrow(()->new RuntimeException("User not found : "+ id));
	}
	
	/**
     * Deletes a ticket by ID.
     *
     * @param id The ID of the ticket to delete.
     */
	@Operation(summary = "delete mapping for tickets", description = "Delete the row using id")
	@ApiResponse(responseCode = "200", description = "found", content = @Content(schema = @Schema(implementation = String.class)))
	@ApiResponse(responseCode = "404", description = "Method not found")
	@DeleteMapping("/tickets/delete/{id}")
	public void removeTicket(@PathVariable Long id) {
		Ticket ticket = ticketRepository.findById(id).orElseThrow(()-> new RuntimeException("Ticket not found"));
		ticketRepository.delete(ticket);
	}
	
}
