package com.questk2.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) for Ticket.
 * This class is used to transfer ticket data between layers.
 */
public class TicketDTO {
	
	@Schema(description = "Title for the ticket")
	private String title;
	
	@Schema(description = "Description for the ticket")
	private String description;
	
	@Schema(description = "Priority Id from the ticketPriority table")
	private Long priority;
	
	@Schema(description = "Status Id from the ticketStatus table")
	private Long status;
	
	@Schema(description = "User Id from user table")
	private Long createdBy;
	
	@Schema(description = "User Id from user table")
	private Long assignedTo;
	
	@Schema(description = "Comment for a ticket")
	private String ticketComment;
	
	/**
     * Always returns false until the ticket is deleted.
     *
     * @return false, indicating the ticket is not deleted.
     */
	@Schema(description = "Always returns false until deleted")
	public boolean isDeleted() {
		return false;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Long getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getTicketComment() {
		return ticketComment;
	}
	public void setTicketComment(String ticketComment) {
		this.ticketComment = ticketComment;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
