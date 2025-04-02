package com.questk2.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.questk2.dto.TicketDTO;
import com.questk2.entity.Ticket;
import com.questk2.entity.TicketPriority;
import com.questk2.entity.TicketStatus;
import com.questk2.entity.User;
import com.questk2.repository.TicketPriorityRepository;
import com.questk2.repository.TicketRepository;
import com.questk2.repository.TicketStatusRepository;
import com.questk2.repository.UserRepository;

@Service
public class TicketOperationService {
	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	TicketPriorityRepository ticketPriorityRepository;
	@Autowired
	TicketStatusRepository ticketStatusRepository;
	@Autowired
    private UserRepository userRepository;
	
	/**
     * Retrieves all tickets based on the user ID.
     * 
     * @param id The ID of the user requesting the ticket information. If the user is an admin (ID = 1), all non-deleted tickets will be returned. 
     *           For regular users, only tickets that they have created or are assigned to will be returned.
     * @return A list of tickets based on the user's access rights (admin or regular user).
     */
	public List<Ticket> getAllTickets(Long id) {
	    List<Ticket> ticketDetails = null;
	    
	    if (id == 1) {
	        ticketDetails = ticketRepository.findAll();
	    } else {
	        User user = new User();
	        user.setId(id);
	        ticketDetails = ticketRepository.findByCreatedByOrAssignedToAndIsDeletedFalse(user, user);
	    }
	    
	    return ticketDetails;
	}

	
	/**
     * Retrieves all ticket priorities.
     * 
     * @return A list of all available ticket priorities.
     */
	public List<TicketPriority> getAllPriorities(){
		return ticketPriorityRepository.findAll();
	}
	
	/**
     * Retrieves all ticket statuses.
     * 
     * @return A list of all available ticket statuses.
     */
	public List<TicketStatus> getAllStatus(){
		return ticketStatusRepository.findAll();
	}
	
	/**
     * Creates a new ticket using the information provided in the TicketDTO.
     * 
     * @param ticketDTO A Data Transfer Object containing the necessary ticket information such as title, description, priority, status, etc.
     * @throws IllegalArgumentException if any required fields createdBy or assignedTo are missing in the DTO.
     * @throws RuntimeException if any referenced entities priority, status, user are not found.
     */
	public void createTicket(TicketDTO ticketDTO) {
	    if (ticketDTO == null || ticketDTO.getCreatedBy() == null || ticketDTO.getAssignedTo() == null) {
	        throw new IllegalArgumentException("Ticket creation failed due to missing required fields.");
	    }

	    TicketPriority priority = ticketPriorityRepository.findById(ticketDTO.getPriority())
	            .orElseThrow(() -> new RuntimeException("Priority not found"));

	    TicketStatus status = ticketStatusRepository.findById(ticketDTO.getStatus())
	            .orElseThrow(() -> new RuntimeException("Status not found"));

	    User createdBy = userRepository.findById(ticketDTO.getCreatedBy())
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    User assignedTo = userRepository.findById(ticketDTO.getAssignedTo())
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    Ticket ticket = new Ticket();
	    ticket.setTitle(ticketDTO.getTitle());
	    ticket.setDescription(ticketDTO.getDescription());
	    ticket.setPriority(priority);
	    ticket.setStatus(status);
	    ticket.setCreatedBy(createdBy);
	    ticket.setAssignedTo(assignedTo);
	    ticket.setTicketComment(ticketDTO.getTicketComment());
	    ticket.setDeleted(false);
	    ticketRepository.save(ticket);
	}

	/**
     * Updates an existing ticket based on the provided ticket ID and new ticket data from TicketDTO.
     * 
     * @param id The ID of the ticket to be updated.
     * @param ticketDTO A DTO containing the new ticket information to be updated.
     * @return The updated Ticket entity.
     * @throws RuntimeException if the ticket with the specified ID does not exist.
     */
    public Ticket updateTicket(Long id, TicketDTO ticketDTO) {
        return ticketRepository.findById(id).map(ticket -> {
            TicketPriority priority = ticketPriorityRepository.findById(ticketDTO.getPriority())
                    .orElseThrow(() -> new RuntimeException("Priority not found"));

            TicketStatus status = ticketStatusRepository.findById(ticketDTO.getStatus())
                    .orElseThrow(() -> new RuntimeException("Status not found"));

            User createdBy = userRepository.findById(ticketDTO.getCreatedBy())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            User assignedTo = userRepository.findById(ticketDTO.getAssignedTo())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            ticket.setTitle(ticketDTO.getTitle());
            ticket.setDescription(ticketDTO.getDescription());
            ticket.setPriority(priority);
            ticket.setStatus(status);
            ticket.setCreatedBy(createdBy);
            ticket.setAssignedTo(assignedTo);
            ticket.setTicketComment(ticketDTO.getTicketComment());
            ticket.setModifiedDate(LocalDateTime.now());
            ticket.setDeleted(false);
            ticket.setDeleted(ticketDTO.isDeleted());

            if (ticketDTO.isDeleted()) {
                ticket.setDeleteDate(LocalDateTime.now());
            } else {
                ticket.setDeleteDate(null);
            }

            return ticketRepository.save(ticket);
        }).orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + id));
    }
    
    /**
     * Soft deletes a ticket by setting its deleted flag to true and assigning a deletion date.
     * 
     * @param id The ID of the ticket to be deleted.
     * @param ticketDTO A DTO containing any necessary details for deleting the ticket (though only deletion status is used here).
     * @return The updated Ticket entity marked as deleted.
     * @throws RuntimeException if the ticket with the specified ID does not exist.
     */
    public Ticket deleteTicket(Long id, TicketDTO ticketDTO) {
        return ticketRepository.findById(id).map(ticket -> {
            ticket.setDeleted(true);
            ticket.setDeleteDate(LocalDateTime.now());
            ticket.setModifiedDate(LocalDateTime.now());

            return ticketRepository.save(ticket);
        }).orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + id));
    }


    
}
