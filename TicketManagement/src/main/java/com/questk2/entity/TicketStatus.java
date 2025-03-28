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
 * Entity class representing the status of a ticket.
 * This class maps to the 'ticket_status' table in the database.
 */
@Entity
@Table(name="ticket_status")
public class TicketStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Id for the ticket status")
	@NotBlank(message = "Id is auto generated")
	private Long id;
	
	@Schema(description = "Description for the ticket status")
	@Column(name="description")
	private String description;
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
		return "TicketStatus [id=" + id + ", description=" + description + "]";
	}
	
}
