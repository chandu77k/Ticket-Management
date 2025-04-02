package com.questk2.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.questk2.entity.Ticket;
import com.questk2.entity.User;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	List<Ticket> findByCreatedByOrAssignedToAndIsDeletedFalse(User user, User user1);
	
}
