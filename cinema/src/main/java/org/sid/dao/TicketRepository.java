package org.sid.dao;

import java.util.List;

import org.sid.entities.Cinema;
import org.sid.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
@RepositoryRestResource
@CrossOrigin("*")
public interface TicketRepository extends JpaRepository<Ticket, Long> {


		
		@Query("select b from Ticket b where b.reservee = ?1")
		List<Ticket> findByTicketReservee(boolean var);
		
		
	

}
