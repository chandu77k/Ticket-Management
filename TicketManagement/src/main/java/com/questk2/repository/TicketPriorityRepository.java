package com.questk2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.questk2.entity.TicketPriority;
@Repository
public interface TicketPriorityRepository extends JpaRepository<TicketPriority, Long> {

}
