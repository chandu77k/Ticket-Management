package com.questk2.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entity class representing a Ticket in the system.
 * This class maps to the 'ticket' table in the database.
 */
@Entity
@Table(name="ticket")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Id for the ticket")
	@NotBlank(message = "Id is auto generated for ticket")
	private Long id;
	
	@Schema(description = "Title for the ticket")
	@Column(name = "title")
	private String title;
	
	@Schema(description = "Description for the ticket")
	@Column(name = "description")
	private String description;
	
	@Schema(description = "Joining Ticket Priority table to Ticket table")
	@ManyToOne
	@JoinColumn(name="priority_id")
	private TicketPriority priority;
	
	@Schema(description = "Joining Ticket Status table to Ticket table")
	@ManyToOne
	@JoinColumn(name="status_id")
	private TicketStatus status;
	
	@Schema(description = "Joining User table to Ticket table")
	@ManyToOne
	@JoinColumn(name="created_by")
	private User createdBy;
	
	@Schema(description = "set createdDate to present time")
	private LocalDateTime createdDate = LocalDateTime.now();
	
	@Schema(description = "set modified Date to present time when changes are made")
	@Column(nullable=true)
	private LocalDateTime modifiedDate;
	
	@Schema(description = "Joining User table to Ticket table")
	@ManyToOne
	@JoinColumn(name="assigned_to")
	private User assignedTo;
	
	@Schema(description = "Comment for Ticket")
	@Column(name="ticket_comment")
	private String ticketComment;
	
	@Schema(description = "set isDeleted false when user clicks on delete it should change to true")
	private boolean isDeleted = false;
	
	@Schema(description = "set Delete date when ticket is deleted")
	@Column(nullable=true)
	private LocalDateTime deleteDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public TicketPriority getPriority() {
		return priority;
	}
	public void setPriority(TicketPriority priority) {
		this.priority = priority;
	}
	public TicketStatus getStatus() {
		return status;
	}
	public void setStatus(TicketStatus status) {
		this.status = status;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public User getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getTicketComment() {
		return ticketComment;
	}
	public void setTicketComment(String ticketComment) {
		this.ticketComment = ticketComment;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public LocalDateTime getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(LocalDateTime deleteDate) {
		this.deleteDate = deleteDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Ticket [id=" + id + ", title=" + title + ", description=" + description + ", priority=" + priority
				+ ", status=" + status + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", modifiedDate="
				+ modifiedDate + ", assignedTo=" + assignedTo + ", ticketComment=" + ticketComment + ", isDeleted="
				+ isDeleted + ", deleteDate=" + deleteDate + "]";
	}
}
