package com.questk2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entity class representing the priority levels of a ticket.
 * This class maps to the 'ticket_priority' table in the database.
 */
@Entity
@Table(name="ticket_priority")
public class TicketPriority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Id for the ticket priority")
	@NotBlank(message = "Id is auto generated")
	private Long id;
	
	@Schema(description = "Description for the ticket priority")
	@Column(name = "description")
	private String description;
	
	public TicketPriority() {
		
	}
	public TicketPriority(Long id, String description) {
		super();
		this.id=id;
		this.description=description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "TicketPriority [id=" + id + ", description=" + description + "]";
	}
	
}
